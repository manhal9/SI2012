package model.steps;

import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import model.Model;
import gui.steps.Tableview;

public class TableviewModel extends InformationGatherStepModel{
	
	private DefaultTableModel dtm;
	private HashMap<String, String>[] events;
	private static TableviewModel instance;

	public TableviewModel() {
		super("Ergebnis", new Tableview());
		events = new Model().getEvents();
	}
	
	public static TableviewModel getInstance() 
	{
		if(instance != null)
			return instance;
		
		instance = new TableviewModel();
		return instance;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void fillTableModel(){
		Tableview tv = (Tableview) super.getViewModelConnection();
		DefaultTableModel dtm = tv.getTableModel();
		for (int i=0; i<events.length; i++){
			HashMap<String, String> tmp = events[i];
			String[] line = new String[2];
			line[0] = tmp.get("event_id");
			line[1] = tmp.get("name");
			dtm.addRow(line);
			
		}
	}
	
	public HashMap<String, String> getEventInfo(int i){
		return events[i];
	}

}