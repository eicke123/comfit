package de.comfit.history;

import java.io.Serializable;

public class WorkoutData implements Serializable {

	private WorkoutItem[] workoutItems;
	private String label;
	private int caloriesInTotal;
	private int caloriesGoalInTotal;
	private int durationInSeconds;

	public WorkoutItem[] getWorkoutItems() {
		return workoutItems;
	}

	public void setWorkoutItems(WorkoutItem[] workoutItems) {
		this.workoutItems = workoutItems;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String id) {
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
