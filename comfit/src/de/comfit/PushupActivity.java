package de.comfit;

import java.text.DecimalFormat;

import de.comfit.sport.RunningActiv;

import android.app.Activity;
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
 * Pushup Activity
 * This activity controls the training of pushups.
 * You can either buzz the button with your nose or toggle to use the proximity sensor.
 * Therefore you have to lay your phone down on the floor and do your pushups above it.
 * @author hcohm
 *
 */
public class PushupActivity extends Activity implements SensorEventListener, SportsactivityInterface {

	/*
	 * variables for pushup counter,
	 * toggleButton, toggled value and
	 * imageButton 
	 */
	private int pushUps;
	private ImageButton imageButton;
	private Button toggleButton;
	private Boolean toggled;
	
	private RunningActiv obj=null;
	private int pushupToDo;
	private boolean challengeIsNotDone=true;
	private int progress;
	
	/*
	 * TextViews
	 */
	private TextView buzz;
	private TextView pushupCounterTextView;
	private TextView proximityLabel;
	
	/*
	 * Save burned calories in this class.
	 * Will be reseted when the activity is reloaded.
	 */
	private double burnedCalories;
	
	/*
	 * On create the proximity sensor is activated.
	 * values and views are initialized.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pushup);
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        
        obj=(RunningActiv)getIntent().getParcelableExtra("sportactiv");
        pushupToDo = getIntent().getIntExtra("toDo", 1);

        pushUps = 0;
		progress = 0;
		toggled = false;
		
	    pushupCounterTextView = (TextView)findViewById(R.id.textView1);
	    pushupCounterTextView.setText("LiegestŸtz-Counter: " + pushUps);
	    
	    buzz = (TextView)findViewById(R.id.textView2);
	    buzz.setVisibility(0);
	    
	    proximityLabel = (TextView)findViewById(R.id.textView4);
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
	
	/*
	 * Register listener for imageButton
	 */
	private void addListenerOnToggleButton() {
 
		toggleButton = (Button)findViewById(R.id.toggleButton);
 
		/*
		 * control two toggled states
		 * - buzz button
		 * - use of proximity sensor 
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
	 * returns burnedCalories according to done pushups
	 * 70% of own weight is pushed
	 * 9.81m/s earth acceleration
	 * 0.3m = 30cm distance from bottom to top
	 * Joule / 4.1868 = Calories
	 * calories = bottom to top
	 * calories / 2 = top to bottom
	 */
	public String burnedCalories(double weight) {
		burnedCalories = ((weight * 0.7) * 9.81 * 0.3)/4.1868;
		burnedCalories = burnedCalories / 1000;
		burnedCalories = burnedCalories + burnedCalories/2;
		DecimalFormat df = new DecimalFormat("#0.000");
		String burned = df.format(burnedCalories * pushUps);
		return burned;
	}

	/*
	 * needed values and methods for sensor management
	 */
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
        	/*
        	 * update counter label only when proximity sensor is active in toggled state
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
    		buzz.setText("LiegestŸtz-Counter: " + pushUps + "\n" + burnedCalories(70.0) + " kcal");
    	else
    		pushupCounterTextView.setText("LiegestŸtz-Counter: " + pushUps + "\n" + burnedCalories(70.0) + " kcal");
    }
    
    private void increaseDonePushups() {
    	pushUps++;
		progress=(int)((pushUps/pushupToDo)/100);
		Toast.makeText(this, "prog: "+progress, Toast.LENGTH_SHORT).show();
		if(pushUps>(pushupToDo/100)){
			obj.updateProgress(progress);
			if(pushUps>=pushupToDo){
				challengeIsNotDone=false;
				obj.updateProgress(progress);
				obj.complete();
			}
		}
    }
    
    @Override
    public void finish() {
    	// TODO Auto-generated method stub
    	Intent i = new Intent();
    	i.putExtra("progress", progress);
    	setResult(0, i);
    	super.finish();
    	
    }
}
