package ontologyAndDB;
import java.sql.*;
import java.util.ArrayList;

import ontologyAndDB.exception.OWLConnectionUnknownTypeException;
import ontologyAndDB.exception.OntologyConnectionDataPropertyException;
import ontologyAndDB.exception.OntologyConnectionIndividualAreadyExistsException;
import ontologyAndDB.exception.OntologyConnectionUnknowClassException;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.OWLEntityRemover;



public class OntToDbConnection {
	
	private 	DBConnection 		dbCon;
	private		OntologyConnection  ontCon;
	private 	boolean				reachCitiesView;
	
	public OntToDbConnection(){
		 dbCon = new DBConnection();
		 ontCon = new OntologyConnection("evntologie_latest.owl");
		 reachCitiesView =false;
	}
	
	public void fillOntWithAllEvents() throws SQLException, OntologyConnectionDataPropertyException, OWLConnectionUnknownTypeException, OntologyConnectionIndividualAreadyExistsException, OntologyConnectionUnknowClassException{	
	  ResultSet rs = dbCon.executeQuery("Select * from \"Event\"");
	  fillOntWithEvents(rs);
	}
	
	public void fillOntWithEvents(ResultSet dataBaseEvents) throws SQLException, OntologyConnectionDataPropertyException, OWLConnectionUnknownTypeException, OntologyConnectionIndividualAreadyExistsException, OntologyConnectionUnknowClassException{	
		  ResultSet rs2;
		  ResultSet rs3;
		  ResultSet rs4;
		  while(dataBaseEvents.next()){
			 
			  // Individual erzeugen
			  OWLNamedIndividual individ = ontCon.createIndividual(String.valueOf(dataBaseEvents.getInt("event_id")));
			  
			  // data propertie : Kinderbetreeung
			  ontCon.setObjectPropertieToIndividual(individ, "hasChildCare", dataBaseEvents.getBoolean("kinderbetreuung"));
			  // data propertie: childfriendly
			  ontCon.setObjectPropertieToIndividual(individ, "isChildFriendly", dataBaseEvents.getBoolean("kinderfreundlich"));
			  // data propertie : hasconcreteDuration 
			  	//TODO : data propertie hinzufügen : concrete duration
			  
			  // hinzufügen zur passenden Event-Klasse
			  rs2 = (dbCon.executeQuery("select bezeichnung from \"Kategorie\" where kategorie_id="+  dataBaseEvents.getInt("kategorie") ));
			  rs2.next();
			  String eventKategorie = rs2.getString(1);
			  ontCon.addIndividualToClass(individ, eventKategorie);
			 
			  // falls genre gesetzt ist , hinzufügen zur Genre-Klasse
			  rs3 = (dbCon.executeQuery("select genre from \"Event_Genre\" where event=" + String.valueOf(dataBaseEvents.getInt("event_id") )));
			  if (rs3.next()){
			  int genreID = rs3.getInt(1);
			  rs4 = (dbCon.executeQuery("select bezeichnung from \"Genre\" where genre_id=" + String.valueOf(genreID )));
			  rs4.next();
				  String genreKategorie = rs4.getString(1);
				  ontCon.addIndividualToClass(individ, genreKategorie);
			  }


			  ontCon.saveOntologie();
		  }	
		}
	
	
	
	////////////////////////////////// Entfernungsmethoden /////////////////////////////////////////////////////////////
	
	/**
	 * Returns the Cities which occur in the DB
	 * @throws SQLException 
	 */
	public ArrayList<String> getCitiesFromDB () throws SQLException{
		ArrayList<String> cities = new ArrayList<String>();
		ResultSet rs = dbCon.executeQuery("Select Distinct ort from \"Event\"");
		while(rs.next()){
			cities.add(rs.getString("ort"));
		}
		return cities;
	}
	
	/**
	 * Creates a view in the DB with all reachable Events
	 * @param reachableCities all Cities that are reachable
	 * @throws SQLException 
	 */
	public void setDistanceView (ArrayList<String> reachableCities) throws SQLException{
		String sqlInStat = reachableCities.toString().replace("[","").replace("]","").trim();
		ResultSet rs = dbCon.executeQuery( "CREATE VIEW reachableEvents AS SELECT * FROM \"Event\" WHERE ort IN ("+sqlInStat+"(" );
		reachCitiesView =true;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public ResultSet executeQuery (String sqlStatement)throws SQLException{		
		return dbCon.executeQuery(sqlStatement);
	}
	
	public void removeAllIndividuals (){
		ontCon.removeAllIndividuals();
	}
	
	public ArrayList<Integer> getInvidualsFromOntologieClass (String className){
		return ontCon.getEventIdsByClass(className);
	}
  
	public ResultSet getDataFromDbByEvent_Id ( ArrayList<Integer> eventIDs) throws SQLException{
		String s = new String(" ");
		int i ;
		for ( i=0  ; i < eventIDs.size()-1;i++){
			s = s.concat(String.valueOf(eventIDs.get(i))+"," );
		}
		s = s.concat(String.valueOf(eventIDs.get(i)));
		return  dbCon.executeQuery("Select * from \"Event\" where \"event_id\" in (" + s + ")");
	}
}