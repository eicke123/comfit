package de.comfit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView.BufferType;

public class PersonalDataActivity extends Activity
{

   private static final int EMPTY_VALUE = -1;

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_personal_data);
   }

   @Override
   protected void onPause()
   {
      super.onPause();
      saveUserData();
   }

   @Override
   public void onResume()
   {
      super.onResume();
      retrieveUserData();
   }

   /*
    * Saves data the user provided (name, weight, height)
    */
   private void saveUserData()
   {
      SharedPreferences sharedPref = getSharedPreferences(
         getString(R.string.preference_file_key), Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPref.edit();

      // save user name
      EditText edit = (EditText) findViewById(R.id.EditTextName);
      editor.putString(getString(R.string.name), edit.getText().toString());
      editor.commit();
      // save user weight
      edit = (EditText) findViewById(R.id.EditTextWeight);
      String number = edit.getText().toString();
      if (!number.isEmpty())
      {
         Integer value = Integer.valueOf(number);
         editor.putInt(getString(R.string.weight), value);
         editor.commit();
      }
      // save user height
      edit = (EditText) findViewById(R.id.EditTextHeight);
      number = edit.getText().toString();
      if (!number.isEmpty())
      {
         Integer value = Integer.valueOf(number);
         editor.putInt(getString(R.string.height), value);
         editor.commit();
      }
   }

   /*
    * Loads data the user provided earlier (name, weight, height) and shows them
    */
   private void retrieveUserData()
   {
      SharedPreferences sharedPref = getSharedPreferences(
         getString(R.string.preference_file_key), Context.MODE_PRIVATE);

      // get user name
      EditText edit = (EditText) findViewById(R.id.EditTextName);
      String name = sharedPref.getString(getString(R.string.name), edit
         .getText().toString());
      edit.setText(name);
      // get user weight
      edit = (EditText) findViewById(R.id.EditTextWeight);
      int weight = sharedPref.getInt(getString(R.string.weight), EMPTY_VALUE);
      if (weight != EMPTY_VALUE)
      {
         edit.setText(String.valueOf(weight), BufferType.EDITABLE);
      }
      // get user height
      edit = (EditText) findViewById(R.id.EditTextHeight);
      int height = sharedPref.getInt(getString(R.string.height), EMPTY_VALUE);
      if (height != EMPTY_VALUE)
      {
         edit.setText(String.valueOf(height), BufferType.EDITABLE);
      }
   }
}
