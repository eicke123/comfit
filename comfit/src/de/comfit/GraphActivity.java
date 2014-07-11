package de.comfit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutItem;

public class GraphActivity extends Activity {

	private int dataPosition = -1;
	private int itemPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		Bundle bundle = getIntent().getExtras();
		dataPosition = bundle.getInt("dataPosition", dataPosition);
		itemPosition = bundle.getInt("itemPosition", itemPosition);
	}

	public void onResume() {
		super.onResume();
		showGraph();
	}

	private void showGraph() {
		// init example series data
		WorkoutData[] data = loadWorkoutData();
		if (data != null && dataPosition != -1 && itemPosition != -1) {
			WorkoutItem[] items = data[dataPosition].getActivityTypes();
			GraphViewData[] graphViewData = new GraphViewData[items.length];
			for (int i = 0; i < items.length; i++) {
				graphViewData[i] = new GraphViewData(items[i].getCalories(),
						items[i].getDurationInSeconds());
			}

			GraphViewSeries exampleSeries = new GraphViewSeries(graphViewData);

			GraphView graphView = new LineGraphView(this // context
					, "History Graph" // heading
			);
			graphView.addSeries(exampleSeries); // data

			LinearLayout layout = (LinearLayout) findViewById(R.id.graph_activity);
			layout.addView(graphView);
		}
	}

	/*
	 * Load workout data from history file
	 */
	private WorkoutData[] loadWorkoutData() {
		WorkoutData[] data = null;
		try {
			FileInputStream fin = openFileInput(getString(R.string.history_file_name));
			ObjectInputStream ois = new ObjectInputStream(fin);
			data = (WorkoutData[]) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
