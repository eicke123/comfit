package de.comfit.sport;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import de.comfit.PushupActivity;
import de.comfit.R;
import de.comfit.SportActivity;

public class PushUpActiv extends SportActiv {

	private int pushups;
	
	private int source;

	public PushUpActiv(SportActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(View source) {
		this.source = this.hashCode();
		Intent intent = new Intent(context, PushupActivity.class);
		intent.putExtra("count", pushups);
		intent.putExtra("hashcode", this.source);
		context.startActivity(intent);
		this.setSource(source);
	}

	@Override
	public double getCalStep(double weight) {
		double burnedCaloriesPerPushup = ((weight * 0.7) * 9.81 * 0.3) / 4.1868;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup / 1000;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup
				+ burnedCaloriesPerPushup / 2;

		return burnedCaloriesPerPushup;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int pushupsDone = intent.getIntExtra("pushups", 1);
		activ.updateProgress(
				pushupsDone * 100 / ((PushUpActiv) activ).getPushups(), 0, activ);
	}

	public int getPushups() {
		return pushups;
	}

	public void setPushups(int pushups) {
		this.pushups = pushups;
	}
	
	@Override
	public int getMaxWorkoutSize() {
		// TODO Auto-generated method stub
		return 50;
	}
	
	@Override
	public int getMinWorkoutSize() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	@Override
	public void setWorkoutSize(int size) {
		// TODO Auto-generated method stub
		pushups = size;
	}

   @Override
   public String getLabel()
   {
      // TODO Auto-generated method stub
      return "pushup";
   }

}
