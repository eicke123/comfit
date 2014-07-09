package de.comfit;

import android.app.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class PersonalDataActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
	}

	protected void onPause() {
		super.onPause(); // Always call the superclass method first
		Context context = this;
		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		// save user name
		EditText edit = (EditText) findViewById(R.id.EditTextName);
		editor.putString(getString(R.string.name), edit.getText().toString());
		editor.commit();
		// save user weight
		edit = (EditText) findViewById(R.id.EditTextWeight);
		Integer value = Integer.getInteger(edit.getText().toString());
		if (value != null) {
			editor.putInt(getString(R.string.weight), value);
			editor.commit();
		}
		// save user height
		edit = (EditText) findViewById(R.id.EditTextSize);
		value = Integer.getInteger(edit.getText().toString());
		if (value != null) {
			editor.putInt(getString(R.string.height), value);
			editor.commit();
		}
	}

	public void onResume() {
		super.onResume();

		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);

		// get user name
		EditText edit = (EditText) findViewById(R.id.EditTextName);
		String name = sharedPref.getString(getString(R.string.name), edit
				.getText().toString());
		edit.setText(name);
		// get user weight
		edit = (EditText) findViewById(R.id.EditTextWeight);
		Integer value = Integer.getInteger(edit.getText().toString());
		if (value != null) {
			int weight = sharedPref.getInt(getString(R.string.weight), value);
			edit.setText(weight);
		}
		// get user height
		edit = (EditText) findViewById(R.id.EditTextSize);
		value = Integer.getInteger(edit.getText().toString());
		if (value != null) {
			int height = sharedPref.getInt(getString(R.string.height), value);
			edit.setText(height);
		}
	}
}
