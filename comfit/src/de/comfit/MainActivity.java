package de.comfit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutHistory;
import de.comfit.history.WorkoutHistoryCreater;
import de.comfit.util.StableArrayAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		saveTestData();
	}

	/*
	 * Saves test workout data
	 */
	private void saveTestData() {
		WorkoutHistory history = new WorkoutHistory();

		history.setData(WorkoutHistoryCreater.getTestData());

		if (history != null) {
			try {
				FileOutputStream fos = openFileOutput(
						getString(R.string.history_file_name),
						Context.MODE_PRIVATE);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				history.writeToFile(os);
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setWelcomeMessage();
		createListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.personal_data) {
			Intent intent = new Intent(this, PersonalDataActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Called when start button was clicked
	 */
	public void onClickStartWorkout(View view) {
		final TextView textView = (TextView) findViewById(R.id.editText1);

		Intent intent = new Intent(this, SportActivity.class);
		if ("".equals(textView.getText().toString())) {
			return;
		} else {
			intent.putExtra("caloriesToBurn",
					Integer.valueOf(textView.getText().toString()) * 1000);
			startActivity(intent);
		}
	}

	/*
	 * Shows a welcome message with the name provided in personal data window
	 */
	private void setWelcomeMessage() {
		final TextView textView = (TextView) findViewById(R.id.textView2);
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String name = sharedPref.getString(getString(R.string.name), null);
		String welcome = getString(R.string.welcome);
		if (name != null) {
			welcome += ", " + name;
		}
		textView.setText(welcome);
	}

	/*
	 * Creates a list view which shows the workouts saved in the history file
	 */
	private void createListView() {
		final ListView listview = (ListView) findViewById(R.id.list1);
		// Load data from file and show every item in list view
		WorkoutData[] data = new WorkoutHistory().loadWorkoutData(this);
		if (data != null) {
			String[] values = new String[data.length];
			for (int i = 0; i < data.length; i++) {
				values[i] = String.valueOf(data[i].getLabel());
			}

			final ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < values.length; ++i) {
				list.add(values[i]);
			}
			final StableArrayAdapter adapter = new StableArrayAdapter(this,
					android.R.layout.simple_list_item_1, list);
			listview.setAdapter(adapter);

			final Intent intent = new Intent(this, WorkItemsActivity.class);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					intent.putExtra("position", position);
					startActivity(intent);
				}

			});
		}
	}

}
