package de.comfit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutHistory;
import de.comfit.history.WorkoutItem;
import de.comfit.sport.PushUpActiv;
import de.comfit.sport.RunningActiv;
import de.comfit.sport.SitUpActiv;
import de.comfit.sport.SportActiv;

public class SportActivity extends Activity
{
   private ArrayList<SportActiv> sportActivs;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activitys);

      int caloriesToBurn = getIntent().getIntExtra("caloriesToBurn", 100);
      sportActivs = generateActivitys(caloriesToBurn);
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
            createRunningActivity(activitys, li, sportActiv, sportActiv.getWorkoutSize());
         }
         else if (sportActiv instanceof PushUpActiv)
         {
            createPushupActivity(activitys, li, sportActiv, sportActiv.getWorkoutSize());
         }
         else if (sportActiv instanceof SitUpActiv)
         {
            SitUpActiv run = (SitUpActiv) sportActiv;

            final LinearLayout row = (LinearLayout) li.inflate(
               R.layout.situpactivity, null);

            TextView text = (TextView) row.findViewById(R.id.text);
            text.setText("Make " + sportActiv.getWorkoutSize() + " situps");
            registerListensers(activitys, run, row);
            sportActiv.setSource(row);
            activitys.addView(row);
         }

      }
   }

   private void createPushupActivity(final LinearLayout activitys,
         LayoutInflater li, final SportActiv sportActiv, int toDo)
   {
      PushUpActiv run = (PushUpActiv) sportActiv;
      final LinearLayout row = (LinearLayout) li.inflate(
         R.layout.pushupactivity, null);

      TextView text = (TextView) row.findViewById(R.id.text);
      text.setText("Make " + toDo + " pushups");
      registerListensers(activitys, run, row);
      sportActiv.setSource(row);
      activitys.addView(row);
   }

   private void createRunningActivity(final LinearLayout activitys,
         LayoutInflater li, final SportActiv sportActiv, int toDo)
   {
      RunningActiv run = (RunningActiv) sportActiv;
      final LinearLayout row = (LinearLayout) li.inflate(
         R.layout.stepasctivity, null);

      TextView text = (TextView) row.findViewById(R.id.text);
      text.setText("Now run " + toDo + " steps");
      registerListensers(activitys, run, row);
      sportActiv.setSource(row);
      activitys.addView(row);
   }

   private void registerListensers(final LinearLayout activitys,
         final SportActiv run, final LinearLayout row)
   {
      row.setOnClickListener(new ClickSportActiv(run));
      row.setOnTouchListener(new TouchLitener(run));
      View cancelButton = row.findViewById(R.id.cancelButton);
      cancelButton.setOnClickListener(new View.OnClickListener()
      {

         @Override
         public void onClick(View v)
         {
            run.enableOthers();
            activitys.removeView(row);
         }
      });
   }

   public ArrayList<SportActiv> generateActivitys(int cal)
   {
      ArrayList<SportActiv> sportActivs = new ArrayList<SportActiv>();
      Class<? extends SportActiv>[] activs = new Class[]
      {
            RunningActiv.class, PushUpActiv.class, SitUpActiv.class };
      while (cal > 0)
      {
         double random2 = Math.random();
         int random = (int) Math.floor((random2 * activs.length));
         Class activ = activs[random];
         SportActiv ac;
         try
         {
            ac = (SportActiv) activ.getConstructor(SportActivity.class)
               .newInstance(this);

            SharedPreferences sharedPref = getSharedPreferences(
               getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            // get user name
            double weight = Double.valueOf((sharedPref.getInt(getString(R.string.weight), 75)));

            int toDo = ac.getMinWorkoutSize() + (int) (Math.random() * ac.getMaxWorkoutSize());
            cal -= toDo * ac.getCalStep(weight);

            ac.setWorkoutSize(toDo);
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
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      int progress = data.getExtras().getInt("progress");
      SportActiv sportActiv = sportActivs.get(requestCode);
      sportActiv.updateProgress(progress, 0, null);

   }

   public ArrayList<SportActiv> getSportActivs()
   {
      return sportActivs;
   }

   public void setSportActivs(ArrayList<SportActiv> sportActivs)
   {
      this.sportActivs = sportActivs;
   }

   @Override
   protected void onPause()
   {
      WorkoutHistory workoutHistory = new WorkoutHistory();
      workoutHistory.loadWorkoutData(this);
      WorkoutData[] oldData = workoutHistory.getData();
      WorkoutData[] newData = new WorkoutData[oldData.length + 1];
      System.arraycopy(oldData, 0, newData, 0, oldData.length);

      WorkoutData workoutData = new WorkoutData();

      int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
      int minute = Calendar.getInstance().get(Calendar.MINUTE);
      workoutData.setLabel(hour + ":" + minute + " workout");
      WorkoutItem[] items = new WorkoutItem[sportActivs.size()];

      for (int i = 0; i < items.length; i++)
      {
         items[i] = sportActivs.get(i).getWorkoutItem();
      }

      workoutData.setWorkoutItems(items);
      newData[newData.length - 1] = workoutData;
      workoutHistory.setData(newData);
      workoutHistory.saveWorkoutData(this);
      System.out.println("--- save");
      super.onPause();
   }

}
