package de.comfit;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Pushup Activity This activity controls the training of pushups. You can
 * either buzz the button with your nose or toggle to use the proximity sensor.
 * Therefore you have to lay your phone down on the floor and do your pushups
 * above it.
 * 
 * @author hcohm
 * 
 */
public class PushupActivity extends Activity implements SensorEventListener,
		SportsactivityInterface {

	/*
	 * variables for pushup counter, toggleButton, toggled value and imageButton
	 */
	private int pushUps;
	private ImageButton imageButton;
	private Button toggleButton;
	private Boolean toggled;

	private int pushupToDo;
	private int progress;

	/*
	 * TextViews
	 */
	private TextView buzz;
	private TextView pushupCounterTextView;
	private TextView proximityLabel;

	/*
	 * Save burned calories in this class. Will be reseted when the activity is
	 * reloaded.
	 */
	private double burnedCalories;

	/*
	 * On create the proximity sensor is activated. values and views are
	 * initialized.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pushup);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

		// get number of iterations
		pushupToDo = getIntent().getIntExtra("count", 1);

		// set initial values
		pushUps = 0;
		progress = 0;
		toggled = false;

		// bind views to variables
		pushupCounterTextView = (TextView) findViewById(R.id.textView1);
		pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps);

		buzz = (TextView) findViewById(R.id.textView2);
		buzz.setVisibility(0);

		proximityLabel = (TextView) findViewById(R.id.textView4);
		proximityLabel.setVisibility(4);

		pushupCounterTextView.setGravity(Gravity.CENTER);
		buzz.setGravity(Gravity.CENTER);
		proximityLabel.setGravity(Gravity.CENTER);

		/*
		 * add listeners to the text views
		 */
		addListenerOnToggleButton();

		addListenerOnImageButton();
	}

	/*
	 * Register listener for imageButton
	 */
	private void addListenerOnImageButton() {

		imageButton = (ImageButton) findViewById(R.id.imageButton1);

		imageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				/*
				 * update pushup counter and output label
				 */
				increaseDonePushups();

				setPushupCounterLabel();
				
			}

		});

	}

	private void createShareDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle("Share on Twitter?");
 
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent i= new Intent(getApplicationContext(), TweetActivity.class);
						i.putExtra("message", "Yuhu ... ich habe "+pushUps+" PushUp(s) gemacht ;)");
						startActivity(i);
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				try {
				      alertDialog.show();
				 } catch(Exception e){
				   Log.d("de.comfit", "Exception");
				 }
			}
	
	/*
	 * Register listener for imageButton
	 */
	private void addListenerOnToggleButton() {

		toggleButton = (Button) findViewById(R.id.toggleButton);

		/*
		 * control two toggled states - buzz button - use of proximity sensor
		 */
		toggleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// proximity sensor active
				if (toggled) {
					toggled = !toggled;

					pushupCounterTextView.setVisibility(0);
					imageButton.setVisibility(0);

					proximityLabel.setVisibility(4);
					setPushupCounterLabel();

					buzz.setText("Buzz here");
				}
				// buzz button active
				else {
					toggled = !toggled;

					pushupCounterTextView.setVisibility(4);
					imageButton.setVisibility(4);

					proximityLabel.setVisibility(0);
					setPushupCounterLabel();
				}
			}

		});

	}

	/*
	 * Getter for pushup counter
	 */
	public int getPushups() {
		return pushUps;
	}

	/*
	 * returns burnedCalories according to done pushups 70% of own weight is
	 * pushed 9.81m/s earth acceleration 0.3m = 30cm distance from bottom to top
	 * Joule / 4.1868 = Calories calories = bottom to top calories / 2 = top to
	 * bottom
	 */
	public String burnedCalories(double weight) {
		burnedCalories = ((weight * 0.7) * 9.81 * 0.3) / 4.1868;
		burnedCalories = burnedCalories / 1000;
		burnedCalories = burnedCalories + burnedCalories / 2;
		DecimalFormat df = new DecimalFormat("#0.000");
		String burned = df.format(burnedCalories * pushUps);
		return burned;
	}

	/*
	 * needed values and methods for sensor management
	 */
	private SensorManager mSensorManager;
	private Sensor mSensor;

	// register listener on resume
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	// unregister listener on pause
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		finish();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		float[] value = event.values;

		if (value[0] > 1) {
		} else {
			/*
			 * update counter label only when proximity sensor is active in
			 * toggled state
			 */
			if (toggled) {
				increaseDonePushups();
				setPushupCounterLabel();
			}
		}
	}

	/*
	 * Set pushup counter labels
	 */
	private void setPushupCounterLabel() {
		if (toggled)
			buzz.setText("Liegestütz-Counter: " + pushUps + "\n"
					+ burnedCalories(70.0) + " kcal");
		else
			pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps
					+ "\n" + burnedCalories(70.0) + " kcal");
	}

	/*
	 * encapsulated method to increase done pushups check for progress
	 */
	private void increaseDonePushups() {
		pushUps++;
		progress = (pushUps * 100 / pushupToDo);

		if (progress == 100) {
			finish();
			createShareDialog();
			Toast.makeText(this, "You have finished this activity",
					Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * give progress back to activity overview
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		i.putExtra("progress", pushUps);
		setResult(0, i);
		super.finish();

	}
}
