package de.comfit;

import java.util.ArrayList;
import de.comfit.util.StableArrayAdapter;

import android.annotation.SuppressLint;
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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onResume(){
		super.onResume();
		setWelcomeMessage();
		createListView();
	}

	private void setWelcomeMessage() {
		final TextView textView = (TextView) findViewById(R.id.textView2);
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String name = sharedPref.getString(getString(R.string.name), null);
		String welcome = getString(R.string.welcome);
		if(name != null){
			welcome += ", " + name;
		}
		textView.setText(welcome);
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
			Intent intent = new Intent(this, CounterActivity.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.pushup) {
			Intent intent = new Intent(this, PushupActivity.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.tracker) {
			Intent intent = new Intent(this, TrackerActivity.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.personal_data) {
			Intent intent = new Intent(this, PersonalDataActivity.class);
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

		final Intent intent = new Intent(this, GraphActivity.class);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				startActivity(intent);
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(50).withEndAction(new Runnable() {
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
