package de.comfit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.comfit.sport.PushUpActiv;
import de.comfit.sport.RunningActiv;
import de.comfit.sport.SitUpActiv;
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
   private ArrayList<SportActiv> sportActivs;


   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activitys);

      sportActivs = generateActivitys(5000);
      addViewsForActivitys(sportActivs);
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
            createRunningActivity(activitys, li, sportActiv);
         }
         else if (sportActiv instanceof PushUpActiv)
         {
            createPushupActivity(activitys, li, sportActiv);
         }
         else if (sportActiv instanceof SitUpActiv)
         {
            SitUpActiv run = (SitUpActiv) sportActiv;
            final LinearLayout row = (LinearLayout) li.inflate(R.layout.situpactivity, null);

            TextView text = (TextView) row.findViewById(R.id.text);
            text.setText("Make situps");
            registerListensers(activitys, run, row);
            sportActiv.setSource(row);
            activitys.addView(row);
         }

      }
   }

   private void createPushupActivity(final LinearLayout activitys, LayoutInflater li, final SportActiv sportActiv)
   {
      PushUpActiv run = (PushUpActiv) sportActiv;
      final LinearLayout row = (LinearLayout) li.inflate(R.layout.pushupactivity, null);

      TextView text = (TextView) row.findViewById(R.id.text);
      text.setText("Make pushups");
      registerListensers(activitys, run, row);
      sportActiv.setSource(row);
      activitys.addView(row);
   }

   private void createRunningActivity(final LinearLayout activitys, LayoutInflater li, final SportActiv sportActiv)
   {
      RunningActiv run = (RunningActiv) sportActiv;
      final LinearLayout row = (LinearLayout) li.inflate(R.layout.stepasctivity, null);

      TextView text = (TextView) row.findViewById(R.id.text);
      text.setText("Now run " + 100 + "steps");
      registerListensers(activitys, run, row);
      sportActiv.setSource(row);
      activitys.addView(row);
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
      { RunningActiv.class, PushUpActiv.class, SitUpActiv.class };
      while (cal > 0)
      {
         double random2 = Math.random();
         int random = (int)Math.floor( (random2 * activs.length));
         Class activ  =  activs[random];
            SportActiv ac;
            try
            {
               ac = (SportActiv) activ.getConstructor(SportActivity.class).newInstance(this);
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
		SportActiv sportActiv = sportActivs.get(requestCode);
		sportActiv.updateProgress(progress,0,null);
		
	}

   public ArrayList<SportActiv> getSportActivs()
   {
      return sportActivs;
   }

   public void setSportActivs(ArrayList<SportActiv> sportActivs)
   {
      this.sportActivs = sportActivs;
   }

}
