package de.comfit;

import de.comfit.sport.SportActiv;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchLitener implements OnTouchListener {
private SportActiv sportActiv;
public TouchLitener(SportActiv sportActiv)
{
   this.sportActiv = sportActiv;
   // TODO Auto-generated constructor stub
}
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (ClickSportActiv.isActive()&& sportActiv.getProgess() <100) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				v.setBackgroundColor(v.getResources().getColor(R.color.pressed));
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			default:
				v.setBackgroundColor(v.getResources()
						.getColor(R.color.standart));
				break;
			}

			return false;
		}
		return false;
	}

}
