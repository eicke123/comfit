package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.PushupActivity;
import de.comfit.StepService;

public class PushUpActiv extends SportActiv {

	public static double weight;
	
	private int pushups;

	public PushUpActiv(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(View source) {
		this.setSource(source);
		
		Intent intent = new Intent(context, PushUpActiv.class);
		intent.putExtra("count", pushups);
		intent.putExtra("hashcode", this.hashCode());

	    context.startService(intent);
	    
	}

	@Override
	public double getCalStep() {
		// TODO Auto-generated method stub

		double burnedCaloriesPerPushup = ((weight * 0.7) * 9.81 * 0.3) / 4.1868;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup / 1000;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup
				+ burnedCaloriesPerPushup / 2;

		return burnedCaloriesPerPushup;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra("hashcode",0));
		int pushupsDone = intent.getIntExtra("pushups", 1);
		activ.updateProgress(pushupsDone*100/((PushUpActiv)activ).getPushups());
	}
	
	public int getPushups() {
		return pushups;
	}

	public void setPushups(int pushups) {
		this.pushups = pushups;
	}

}
