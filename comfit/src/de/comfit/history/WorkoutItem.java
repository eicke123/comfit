package de.comfit.history;

import java.io.Serializable;

/**
 * 
 * @author Waldo This class holds the data for one workout item (like push ups
 *         or sit ups)
 * 
 */
public class WorkoutItem implements Serializable {

	private static final long serialVersionUID = 6121009836013828691L;
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
