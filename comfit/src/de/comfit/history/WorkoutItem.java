package de.comfit.history;

import java.io.Serializable;

public/* abstract */class WorkoutItem implements Serializable {
	private String label;
	private long durationInSeconds;
	private int calories;
	private int caloriesGoal;

	public String getWorkoutType() {
		return label;
	}

	public void setWorkoutType(String workoutType) {
		this.label = workoutType;
	}

	public long getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getCaloriesGoal() {
		return caloriesGoal;
	}

	public void setCaloriesGoal(int caloriesGoal) {
		this.caloriesGoal = caloriesGoal;
	}
}
