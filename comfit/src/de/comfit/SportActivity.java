package de.comfit;

import java.util.ArrayList;

import de.comfit.sport.RunningActiv;
import de.comfit.sport.SportActiv;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SportActivity extends Activity
{
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activitys);
    
    
    ArrayList<SportActiv> generateActivitys = generateActivitys(1000);
    addViewsForActivitys(generateActivitys);
   }
   
   
  private void addViewsForActivitys(ArrayList<SportActiv> generateActivitys)
   {
     final LinearLayout activitys = (LinearLayout) findViewById(R.id.count);
     
     for (SportActiv sportActiv : generateActivitys)
   {
        
        if (sportActiv instanceof RunningActiv)
      {
         RunningActiv run = (RunningActiv) sportActiv;
         LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         final LinearLayout row = (LinearLayout) li.inflate(R.layout.stepasctivity, null);
         
         TextView text = (TextView) row.findViewById(R.id.textstepactiv);
         text.setText("Now run " + 100 + "steps");
         
         View cancelButton = row.findViewById(R.id.cancelButton);
         cancelButton.setOnClickListener(new View.OnClickListener()
         {
            
            @Override
            public void onClick(View v)
            {
              activitys.removeView(row);
            }
         });
         activitys.addView(row);
         
      }
      
   }
   }


public ArrayList<SportActiv> generateActivitys(int cal)
{
   ArrayList<SportActiv> sportActivs =  new ArrayList<SportActiv>();
   
   sportActivs.add(new RunningActiv());
   sportActivs.add(new RunningActiv());
   
   return sportActivs;
   
   
}

}
