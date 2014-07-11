package de.comfit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import de.comfit.history.WorkoutItem;
import de.comfit.util.StableArrayAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onResume() {
		super.onResume();
		setWelcomeMessage();
		createListView();
		saveWorkoutData();
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
//		if (id == R.id.counter) {
//			// use this to start and trigger a service
//			Intent i;
//			i = new Intent(this, StepService.class);
//			// // potentially add data to the intent
//			i.putExtra("sportactiv", "string");
//			startService(i);
//			return true;
//		} else if (id == R.id.pushup) {
//			Intent intent = new Intent(this, PushupActivity.class);
//			startActivity(intent);
//			return true;
//		} else if (id == R.id.tracker) {
//			Intent intent = new Intent(this, TrackerActivity.class);
//			startActivity(intent);
//			return true;
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
			
		}
		else {
			intent.putExtra("caloriesToBurn", Integer.valueOf(textView.getText().toString()));
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
		WorkoutData[] data = loadWorkoutData();
		if (data != null) {
			// TODO: use workout history data from file instead of dummy data
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

				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					final String item = (String) parent
							.getItemAtPosition(position);
					intent.putExtra("position", position);
					startActivity(intent);
				}

			});
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

	/*
	 * Saves workout data provided by SportActivity to file
	 */
	private void saveWorkoutData() {
		WorkoutHistory history = new WorkoutHistory(); // TODO: data has to be
														// retrieved from SportActivity
		
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
}
