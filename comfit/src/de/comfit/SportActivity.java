package de.comfit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.comfit.sport.PushUpActiv;
import de.comfit.sport.RunningActiv;
import de.comfit.sport.SportActiv;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SportActivity extends Activity
{
   private ArrayList<SportActiv> generateActivitys;

   private final class ClickSportActiv implements View.OnClickListener
   {
      private final SportActiv sportActiv;

      private ClickSportActiv(SportActiv sportActiv)
      {
         this.sportActiv = sportActiv;
      }

      @Override
      public void onClick(View v)
      {
         v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
         sportActiv.start(v);
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activitys);

      generateActivitys = generateActivitys(5000);
      addViewsForActivitys(generateActivitys);
   }

   private void addViewsForActivitys(ArrayList<SportActiv> generateActivitys)
   {
      final LinearLayout activitys = (LinearLayout) findViewById(R.id.count);
      LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      for (final SportActiv sportActiv : generateActivitys)
      {
         sportActiv.setIndex(activitys.getChildCount());
         if (sportActiv instanceof RunningActiv)
         {
            RunningActiv run = (RunningActiv) sportActiv;
            final LinearLayout row = (LinearLayout) li.inflate(R.layout.stepasctivity, null);

            TextView text = (TextView) row.findViewById(R.id.text);
            text.setText("Now run " + 100 + "steps");
            registerListensers(activitys, run, row);
            activitys.addView(row);
         }
         else if (sportActiv instanceof PushUpActiv)
         {
            PushUpActiv run = (PushUpActiv) sportActiv;
            final LinearLayout row = (LinearLayout) li.inflate(R.layout.pushupactivity, null);

            TextView text = (TextView) row.findViewById(R.id.text);
            text.setText("Make pushups");
            registerListensers(activitys, run, row);
            activitys.addView(row);
         }

      }
   }

   private void registerListensers(final LinearLayout activitys, SportActiv run, final LinearLayout row)
   {
      row.setOnClickListener(new ClickSportActiv(run));
      row.setOnTouchListener(new TouchLitener());
      View cancelButton = row.findViewById(R.id.cancelButton);
      cancelButton.setOnClickListener(new View.OnClickListener()
      {

         @Override
         public void onClick(View v)
         {
            activitys.removeView(row);
         }
      });
   }

   public ArrayList<SportActiv> generateActivitys(int cal)
   {
      ArrayList<SportActiv> sportActivs = new ArrayList<SportActiv>();
      Class<? extends SportActiv>[] activs = new Class[]
      { RunningActiv.class, PushUpActiv.class };
      while (cal > 0)
      {
         double random2 = Math.random();
         int random = (int)Math.floor( (random2 * activs.length));
         Class activ  =  activs[random];
            SportActiv ac;
            try
            {
               ac = (SportActiv) activ.getConstructor(Activity.class).newInstance(this);
               cal -= ac.getCalStep();
               sportActivs.add(ac);
            }
            catch (InstantiationException e)
            {
               e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
               e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
      }
      return sportActivs;

   }
   
   @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int progress = data.getExtras().getInt("progress");
		SportActiv sportActiv = generateActivitys.get(requestCode);
		sportActiv.updateProgress(progress);
		
	}

}