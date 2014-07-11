package de.comfit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutHistory;
import de.comfit.history.WorkoutItem;

/**
 * 
 * @author Waldo This activity shows a graph for a certain workout item
 * 
 */
public class GraphActivity extends Activity {

	private static final int DEFAULT_POSITION = -1;

	private int dataPosition = DEFAULT_POSITION;
	private int itemPosition = DEFAULT_POSITION;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// get the position, which is used for finding the right data in history
		// file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		Bundle bundle = getIntent().getExtras();
		dataPosition = bundle.getInt("dataPosition", dataPosition);
		itemPosition = bundle.getInt("itemPosition", itemPosition);
	}

	@Override
	public void onResume() {
		super.onResume();
		showGraph();
	}

	private void showGraph() {
		// init example series data
		WorkoutData[] data = (new WorkoutHistory()).loadWorkoutData(this);
		if (data != null && dataPosition != DEFAULT_POSITION && itemPosition != DEFAULT_POSITION) {
			// get the data from file and show only the data which belongs to
			// the right workout item (item position) in the right workout data
			// (data position)
			WorkoutItem item = data[dataPosition].getWorkoutItems()[itemPosition];
			int graphData[][] = item.getGraphData();
			GraphViewData[] graphViewData = new GraphViewData[graphData.length];
			for (int i = 0; i < graphData.length; i++) {
				graphViewData[i] = new GraphViewData(graphData[i][0],
						graphData[i][1]);
			}

			GraphViewSeries series = new GraphViewSeries(graphViewData);

			GraphView graphView = new LineGraphView(this // context
					, "History Graph" // heading
			);
			graphView.addSeries(series); // data

			LinearLayout layout = (LinearLayout) findViewById(R.id.graph_activity);
			layout.addView(graphView);
		}
	}
}
