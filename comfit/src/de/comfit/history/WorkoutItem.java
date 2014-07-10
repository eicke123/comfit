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
