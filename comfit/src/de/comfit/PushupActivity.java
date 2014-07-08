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
	//private TextView pushupCounterTextViewProximitySensor;
	private TextView proximityLabel;
	
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
				
			    pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps);
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
					pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps);

					buzz.setText("Buzz here");
				}
				// buzz button active
				else {
					toggled = !toggled;
					
					pushupCounterTextView.setVisibility(4);
					imageButton.setVisibility(4);
					
					proximityLabel.setVisibility(0);
					buzz.setText("Liegestütz-Counter: " + pushUps);
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
        // TODO: implement your action here.
    	
    	float[] value = event.values;

        if(value[0] > 1) {
           
        }
        else {
        	if (toggled) {
        		pushUps++;
        		pushupCounterTextView.setText("Liegestütz-Counter: " + pushUps);
        	}
        }  
    }
}
