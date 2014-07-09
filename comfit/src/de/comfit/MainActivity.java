package de.comfit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.comfit.util.StableArrayAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		if (id == R.id.counter) {
			// use this to start and trigger a service
			Intent i;
			i = new Intent(this, StepService.class);
			// // potentially add data to the intent
			i.putExtra("sportactiv", "string");
			startService(i);
			return true;
		} else if (id == R.id.pushup) {
			Intent intent = new Intent(this, PushupActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.tracker) {
			Intent intent = new Intent(this, TrackerActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onClickStartWorkout(View view) {
		Intent intent = new Intent(this, SportActivity.class);
		startActivity(intent);
	}

	/*
	 * Called when start button was clicked
	 */
	public void startWorkoutRandomizer(View view) {
		// TODO change class to workout randomizer
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void createListView() {
		final ListView listview = (ListView) findViewById(R.id.list1);
		String[] values = new String[] { "History1", "History2", "History3",
				"History4" };

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {
								// TODO: open graph for item
								// list.remove(item);
								// adapter.notifyDataSetChanged();
								// view.setAlpha(1);
							}
						});
			}

		});
	}
}
