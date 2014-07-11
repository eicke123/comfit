package de.comfit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutItem;
import de.comfit.util.StableArrayAdapter;

public class WorkItemsActivity extends Activity
{

   private int dataPosition = -1;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_work_items);
      Bundle bundle = getIntent().getExtras();
      dataPosition = bundle.getInt("position", dataPosition);
   }

   @Override
   public void onResume()
   {
      super.onResume();
      createListView();
   }

   /*
    * Creates a list view which shows the workouts saved in the history file
    */
   private void createListView()
   {
      final ListView listview = (ListView) findViewById(R.id.workout_list);
      // Load data from file and show every item in list view
      WorkoutData[] data = loadWorkoutData();
      if (data != null && dataPosition != -1)
      {
         final WorkoutItem[] items = data[dataPosition].getWorkoutItems();
         String[] values = new String[items.length];
         for (int i = 0; i < items.length; i++)
         {
            String line = "Workout type: ";
            line += items[i].getLabel();
            values[i] = line;
         }

         final ArrayList<String> list = new ArrayList<String>();
         for (int i = 0; i < values.length; ++i)
         {
            list.add(values[i]);
         }
         final StableArrayAdapter adapter = new StableArrayAdapter(this,
               android.R.layout.simple_list_item_1, list);
         listview.setAdapter(adapter);

         final Intent intent = new Intent(this, GraphActivity.class);

         listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
         {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                  int itemPosition, long id)
            {
               if (items[itemPosition].getGraphData() != null)
               {
                  final String item = (String) parent
                     .getItemAtPosition(itemPosition);
                  intent.putExtra("dataPosition", dataPosition);
                  intent.putExtra("itemPosition", itemPosition);
                  startActivity(intent);
               }
            }

         });
      }
   }

   /*
    * Load workout data from history file
    */
   private WorkoutData[] loadWorkoutData()
   {
      WorkoutData[] data = null;
      try
      {
         FileInputStream fin = openFileInput(getString(R.string.history_file_name));
         ObjectInputStream ois = new ObjectInputStream(fin);
         data = (WorkoutData[]) ois.readObject();
         ois.close();
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return data;
   }
}
