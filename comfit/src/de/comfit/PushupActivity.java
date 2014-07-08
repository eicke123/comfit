package de.comfit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PushupActivity extends Activity implements SensorEventListener {

	/*
	 * declare variables for pushup counter,
	 * done pushups textView and
	 * imageButton 
	 */
	private int pushUps;
	private ImageButton imageButton;
	private Button toggleButton;
	private Boolean toggled;
	
	private TextView buzz;
	private TextView pushupCounterTextView;
	private TextView proximityLabel;
	
	private double burnedCalories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pushup);
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
		pushUps = 0;
		toggled = false;
		
	    pushupCounterTextView = (TextView)findViewById(R.id.textView1);
	    pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps);
	    
	    buzz = (TextView)findViewById(R.id.textView2);
	    buzz.setVisibility(0);
	    
	    proximityLabel = (TextView)findViewById(R.id.textView4);
	    proximityLabel.setVisibility(4);
	    
	    pushupCounterTextView.setGravity(Gravity.CENTER);
	    buzz.setGravity(Gravity.CENTER);
	    proximityLabel.setGravity(Gravity.CENTER);
	    
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
 
				pushUps++;
				
			    pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps + "\n"+burnedCalories(70.0));
			}
 
		});
 
	}
	
	/*
	 * Register listener for imageButton
	 */
	private void addListenerOnToggleButton() {
 
		toggleButton = (Button)findViewById(R.id.toggleButton);
 
		toggleButton.setOnClickListener(new View.OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				// proximity sensor active
				if (toggled) {
					toggled = !toggled;
					

					pushupCounterTextView.setVisibility(0);
					imageButton.setVisibility(0);
					
					proximityLabel.setVisibility(4);
					pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps + "\n"+burnedCalories(70.0));

					buzz.setText("Buzz here");
				}
				// buzz button active
				else {
					toggled = !toggled;
					
					pushupCounterTextView.setVisibility(4);
					imageButton.setVisibility(4);
					
					proximityLabel.setVisibility(0);
					buzz.setText("Liegestütz-Counter: " + pushUps + "\n"+burnedCalories(70.0));
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
	 * returns burnedCalories according to done pushups
	 */
	public double burnedCalories(double weight) {
		burnedCalories = ((weight * 0.7) * 9.81 * 0.3)/4.1868;
		burnedCalories = burnedCalories / 1000;
		burnedCalories = burnedCalories + burnedCalories/2;
		return burnedCalories;
	}

	private SensorManager mSensorManager;
    private Sensor mSensor;

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
    	float[] value = event.values;

        if(value[0] > 1) {
        }
        else {
        	if (toggled) {
        		pushUps++;
        		buzz.setText("Liegestütz-Counter: " + pushUps + "\n"+burnedCalories(70.0) + " kcal");
        	}
        }  
    }
}
