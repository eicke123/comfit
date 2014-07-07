package de.comfit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SportActivity extends Activity
{
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activitys);

      LinearLayout list = (LinearLayout) findViewById(R.id.);

      LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      LinearLayout row = (LinearLayout) li.inflate(R.layout.stepasctivity, null);
      this.addView(row);
   }
}
