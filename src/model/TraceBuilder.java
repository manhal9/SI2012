package model;

import java.util.ArrayList;
import java.util.List;

import model.steps.*;

/**
 * Helper Class used in the model class
 */
class TraceBuilder
{
	private static PersonDescriptionStepModel	personDescriptionStep = PersonDescriptionStepModel.getInstance();
	private static TimeRangeStepModel 			timeRangeStep = TimeRangeStepModel.getInstance();
	private static DurationStepModel 			durationStep = DurationStepModel.getInstance();	
	private static SchoolLocationStepModel		schoolLocationStep = SchoolLocationStepModel.getInstance();
	private static PersonAgeStepModel 			personAgeStep = PersonAgeStepModel.getInstance();
	private static VoyageMethodStepModel		voyageMethodStep = VoyageMethodStepModel.getInstance();
	private static DistanceStepModel			distanceStep = DistanceStepModel.getInstance();
	private static OriginLocationStepModel		originLocationStep = OriginLocationStepModel.getInstance();
	private static KindOfEventSelectionStepModel kindOfEventSelectionStepModel = KindOfEventSelectionStepModel.getInstance();
	private static EventCategoryStepModel		eventCategoryStepModel = EventCategoryStepModel.getInstance();
	private static GenreSelectionStepModel 		genreSelectionStepModel = GenreSelectionStepModel.getInstance();
	private static BudgetStepModel				budgetStepModel = BudgetStepModel.getInstance();
	
	public InformationGatherStepModel[] build()
	{
		return initianalTrace();
		
//		if(!personDescriptionStep.isAlredyFilled())
//			return initianalTrace();
//		
//		return dynamicTrace();
	}
	
	private InformationGatherStepModel[] initianalTrace()
	{
		List<InformationGatherStepModel> result = new ArrayList<InformationGatherStepModel>();
		
		result.add(personDescriptionStep);
		result.add(timeRangeStep);
		result.add(durationStep);
		result.add(voyageMethodStep);
		result.add(distanceStep);
		result.add(originLocationStep);
		result.add(kindOfEventSelectionStepModel);
		result.add(eventCategoryStepModel);
		result.add(genreSelectionStepModel);
		result.add(budgetStepModel);
		
		return (InformationGatherStepModel[])result.toArray();
	}
	
	private InformationGatherStepModel[] dynamicTrace()
	{
		List<InformationGatherStepModel> result = new ArrayList<InformationGatherStepModel>();
		
		result.add(personDescriptionStep);
		result.add(timeRangeStep);
		
		if(timeRangeStep.isAlredyFilled() && "summerbreak".equals(timeRangeStep.getTimeRangeTyp())) {
			result.add(schoolLocationStep);
		}
		
		result.add(durationStep);
		
		if(personDescriptionStep.isAlredyFilled() && !"single".equals(personDescriptionStep.getGroup())) {
			result.add(personAgeStep);
		} 	
		
		if(personDescriptionStep.getAge() >= 18 || 
		   personAgeStep.hasAdultPerson() ||
		   !personDescriptionStep.isAlredyFilled()) {
			result.add(voyageMethodStep);
		}
		
		result.add(distanceStep);
		result.add(originLocationStep);
		result.add(kindOfEventSelectionStepModel);
		
		
		
		
		result.add(budgetStepModel);
		
		
		return (InformationGatherStepModel[])result.toArray();
	}
	
}