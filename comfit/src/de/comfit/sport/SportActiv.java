package de.comfit.sport;

import de.comfit.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

public abstract class SportActiv
{
   protected Activity context;
   protected int index;
   private int progess;
   protected View source;

   public SportActiv(Activity context)
   {
      this.context = context;
   }

   public void updateProgress(int percent)
   {
      this.progess = percent;
      ProgressBar p = (ProgressBar) source.findViewById(R.id.stepactivprogress);
      p.setProgress(progess);
      source.setBackgroundColor(source.getResources().getColor(R.color.done));
   }

   public abstract double getCalStep();

   public void setIndex(int index)
   {
      this.index = index;

   }

   public abstract void start(View source);

}
