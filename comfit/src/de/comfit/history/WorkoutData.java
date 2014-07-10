package de.comfit.history;

public class WorkoutData {

	private WorkoutItem[] workoutItems;
	private String id;
	private int caloriesInTotal;
	private int caloriesGoalInTotal;
	
	public WorkoutItem[] getActivityTypes() {
		return workoutItems;
	}
	public void setActivityTypes(WorkoutItem[] activityTypes) {
		this.workoutItems = activityTypes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCaloriesInTotal() {
		return caloriesInTotal;
	}
	public void setCaloriesInTotal(int caloriesInTotal) {
		this.caloriesInTotal = caloriesInTotal;
	}
	public int getCaloriesGoalInTotal() {
		return caloriesGoalInTotal;
	}
	public void setCaloriesGoalInTotal(int caloriesGoalInTotal) {
		this.caloriesGoalInTotal = caloriesGoalInTotal;
	}
}
