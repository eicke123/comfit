package de.comfit.sport;

import java.util.HashMap;
import java.util.HashSet;

import de.comfit.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public abstract class SportActiv extends BroadcastReceiver
{
   protected Activity context;
   protected int index;
   private int progess;
   private View source;
   public static final HashSet< SportActiv> sportActivs =  new HashSet<SportActiv>();

   public SportActiv(Activity context)
   {
	   this();
      this.context = context;
   }
   
   public SportActiv() {
	   sportActivs.add(this);
   }

   public void updateProgress(int percent)
   {
      this.progess = percent;
      Log.d("de.comfit", "update: "+percent);
      System.out.println(hashCode());
      ProgressBar p = (ProgressBar) getSource().findViewById(R.id.stepactivprogress);
      p.setProgress(progess);
      getSource().setBackgroundColor(getSource().getResources().getColor(R.color.done));
   }

   public abstract double getCalStep();

   public void setIndex(int index)
   {
      this.index = index;

   }

   public abstract void start(View source);

public View getSource() {
	return source;
}

public void setSource(View source) {
	this.source = source;
}

public SportActiv getSportActivByHash(int hashcode) {
	for (SportActiv a : sportActivs) {
		if (a.hashCode() == hashcode) {
			return a;
		}
	}
	return null;
}

}
