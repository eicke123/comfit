<<<<<<< HEAD
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
=======
package de.comfit.history;

import java.io.Serializable;

public class WorkoutData implements Serializable {

	private WorkoutItem[] workoutItems;
	private String label;
	private int caloriesInTotal;
	private int caloriesGoalInTotal;
	private int durationInSeconds;
	
	public WorkoutItem[] getActivityTypes() {
		return workoutItems;
	}
	public void setActivityTypes(WorkoutItem[] activityTypes) {
		this.workoutItems = activityTypes;
	}
	public String getId() {
		return label;
	}
	public void setId(String id) {
		this.label = id;
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
	public int getDurationInSeconds() {
		return durationInSeconds;
	}
	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
}
>>>>>>> 6b643b90bdabc65c9e9fb78b9e1681825189b738
