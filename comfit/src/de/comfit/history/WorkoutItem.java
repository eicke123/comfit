package de.comfit.history;

import java.io.Serializable;

public class WorkoutItem implements Serializable {
	private String label;
	private long durationInSeconds;
	private int calories;
	private int caloriesGoal;
	private int[][] graphData;

	public String getLabel() {
		return label;
	}

	public void setLabel(String workoutType) {
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

	public int[][] getGraphData() {
		return graphData;
	}

	public void setGraphData(int[][] graphData) {
		this.graphData = graphData;
	}
}
