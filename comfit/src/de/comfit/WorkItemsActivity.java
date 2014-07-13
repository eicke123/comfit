package de.comfit;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutHistory;
import de.comfit.history.WorkoutItem;
import de.comfit.util.StableArrayAdapter;

/**
 * 
 * @author Waldo This activity shows the work items for a certain workout
 * 
 */
public class WorkItemsActivity extends Activity {

	private int dataPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_items);
		Bundle bundle = getIntent().getExtras();
		dataPosition = bundle.getInt("position", dataPosition);
	}

	@Override
	public void onResume() {
		super.onResume();
		createListView();
	}

	/*
	 * Creates a list view which shows the workouts saved in the history file
	 */
	private void createListView() {
		final ListView listview = (ListView) findViewById(R.id.workout_list);
		// Load data from file and show every item in list view
		WorkoutData[] data = (new WorkoutHistory()).loadWorkoutData(this);
		if (data != null && dataPosition != -1) {
			final WorkoutItem[] items = data[dataPosition].getWorkoutItems();
			String[] values = new String[items.length];
			for (int i = 0; i < items.length; i++) {
				String line = "Workout type: ";
				line += items[i].getLabel();
				values[i] = line;
			}

			final ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < values.length; ++i) {
				list.add(values[i]);
			}
			final StableArrayAdapter adapter = new StableArrayAdapter(this,
					android.R.layout.simple_list_item_1, list);
			listview.setAdapter(adapter);

			final Intent intent = new Intent(this, GraphActivity.class);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int itemPosition, long id) {
					if (items[itemPosition].getGraphData() != null) {
						intent.putExtra("dataPosition", dataPosition);
						intent.putExtra("itemPosition", itemPosition);
						startActivity(intent);
					}
				}

			});
		}
	}
}
