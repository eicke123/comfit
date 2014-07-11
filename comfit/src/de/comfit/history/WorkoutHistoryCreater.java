package de.comfit.history;

/**
 * 
 * @author Waldo Creates test data for the history file
 * 
 */
public class WorkoutHistoryCreater {

	public static WorkoutData[] getTestData() {
		// create 2 workouts with 3 workout items each 
		WorkoutData workoutData[] = new WorkoutData[2];
		WorkoutItem workoutItem[] = new WorkoutItem[3];
		String[] workoutTypes = { "pushUps", "jogging", "walking" };

		// create the data for the graph which usually comes from sensors
		for (int i = 0; i < workoutData.length; i++) {
			workoutData[i] = new WorkoutData();
			for (int j = 0; j < workoutItem.length; j++) {
				int[][] workoutItemData = new int[5][2];
				workoutItem[j] = new WorkoutItem();
				workoutItem[j].setCalories(10);
				workoutItem[j].setCaloriesGoal(20);
				workoutItem[j].setDurationInSeconds(100);
				workoutItem[j].setLabel(workoutTypes[j]);
				createGraphData(workoutItemData, j * 10);
				workoutItem[j].setGraphData(workoutItemData);
			}
			workoutData[i].setWorkoutItems(workoutItem);
			workoutData[i].setLabel("Workout " + i);
			workoutData[i].setCaloriesGoalInTotal(1000);
			workoutData[i].setCaloriesInTotal(1000);
		}

		return workoutData;
	}

	private static void createGraphData(int[][] data, int offset) {
		for (int i = 0; i < data.length; i++) {
			data[i][0] = i;
			data[i][1] = i + ((int) Math.pow(-1, i)) * offset;
		}
	}

}
