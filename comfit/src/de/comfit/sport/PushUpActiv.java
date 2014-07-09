package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.PushupActivity;

public class PushUpActiv extends SportActiv {

	public static double weight;

	public PushUpActiv(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(View source) {
		Intent intent = new Intent(context, PushupActivity.class);
		intent.putExtra("count", 20);
		context.startActivity(intent);
	    this.source = source;
	}

	@Override
	public void updateProgress(int percent) {
		// TODO Auto-generated method stub

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

}
