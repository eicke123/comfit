package de.comfit.sport;

import android.content.Context;

public class RunningActiv extends SportActiv
{
   public static final int CAL_PER_STEP = 1;
   private int steps;
   private int percent;
   
    public RunningActiv(Context context)
   {
       super(context);
   }
   
   @Override
   public void start()
   {
     System.out.println("start");
   }

   @Override
   public void updateProgress(int percent)
   {
      this.setPercent(percent);
      // TODO Auto-generated method stub

   }

   @Override
   public void complete()
   {
      // TODO Auto-generated method stub

   }

   public int getSteps()
   {
      return steps;
   }

   public void setSteps(int steps)
   {
      this.steps = steps;
   }

   public int getPercent()
   {
      return percent;
   }

   public void setPercent(int percent)
   {
      this.percent = percent;
   }

   @Override
   public int getCalStep()
   {
      // TODO Auto-generated method stub
      return 500;
   }

}
