package de.comfit;

import java.util.ArrayList;

import de.comfit.sport.RunningActiv;
import de.comfit.sport.SportActiv;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
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
         sportActiv.start();
      }
   }


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
     
     for (final SportActiv sportActiv : generateActivitys)
   {
        
        if (sportActiv instanceof RunningActiv)
      {
         RunningActiv run = (RunningActiv) sportActiv;
         LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         final LinearLayout row = (LinearLayout) li.inflate(R.layout.stepasctivity, null);
         
         TextView text = (TextView) row.findViewById(R.id.text);
         text.setText("Now run " + 100 + "steps");
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
         activitys.addView(row);
         
      }
      
   }
   }


public ArrayList<SportActiv> generateActivitys(int cal)
{
   ArrayList<SportActiv> sportActivs =  new ArrayList<SportActiv>();
   
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   sportActivs.add(new RunningActiv(100,this));
   
   
   return sportActivs;
   
   
}

}
