<<<<<<< HEAD
package de.comfit.history;

public /*abstract*/ class WorkoutItem {
	private String workoutType;
	private long durationInSeconds;
	private int calories;
	private int caloriesGoal;
	
	public String getWorkoutType() {
		return workoutType;
	}
	public void setWorkoutType(String workoutType) {
		this.workoutType = workoutType;
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
=======
package de.comfit.history;

import java.io.Serializable;

public /*abstract*/ class WorkoutItem implements Serializable {
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
>>>>>>> 6b643b90bdabc65c9e9fb78b9e1681825189b738
