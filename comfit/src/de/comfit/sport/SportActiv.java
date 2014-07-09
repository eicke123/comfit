package de.comfit.sport;

import android.content.Context;

public abstract class SportActiv
{
   protected Context context;
   public SportActiv(Context context)
   {
      this.context = context;
   }
public abstract void start();
public abstract void updateProgress(int percent);
public abstract void complete();
public abstract int getCalStep();


}
