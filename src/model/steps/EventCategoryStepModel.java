package model.steps;

import java.util.HashMap;

public class EventCategoryStepModel extends InformationGatherStepModel 
{
	private static EventCategoryStepModel instance;
	
	private HashMap<String, String> leisureTimeCategories;
	private HashMap<String, String> sportCategories;
	private HashMap<String, String> cultureCategories;
	
	private EventCategoryStepModel() 
	{
		
	}
	
	public static EventCategoryStepModel getInstance() 
	{
		return instance != null ? instance : (instance=new EventCategoryStepModel());
	}

	public String getError() 
	{
		return null;
	}
	
	public void setLeisureTimeCategories(HashMap<String, String> leisureTimeList) 
	{
		this.leisureTimeCategories = leisureTimeList;
		updateAlredayFilled();
	}

	public HashMap<String, String> getSportCategories() {
		return sportCategories;
	}

	public void setSportCategories(HashMap<String, String> sportCategories) {
		this.sportCategories = sportCategories;
		updateAlredayFilled();
	}

	public HashMap<String, String> getCultureCategories() {
		return cultureCategories;
	}

	public void setCultureCategories(HashMap<String, String> cultureCategories) {
		this.cultureCategories = cultureCategories;
		updateAlredayFilled();
	}
	
	
}
