package de.comfit;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchLitener implements OnTouchListener {

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.setBackgroundColor(v.getResources().getColor(R.color.pressed));
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			v.setBackgroundColor(v.getResources().getColor(R.color.standart));
			break;
		}
		return false;
	}

}
