package de.comfit.sport;

import java.util.HashMap;
import java.util.HashSet;

import de.comfit.R;
import de.comfit.TweetActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public abstract class SportActiv extends BroadcastReceiver
{
   protected Activity context;
   protected int index;
   private int progess;
   private View source;
   public static final HashSet< SportActiv> sportActivs =  new HashSet<SportActiv>();

   public SportActiv(Activity context)
   {
	   this();
      this.context = context;
   }
   
   public SportActiv() {
	   sportActivs.add(this);
   }

   public void updateProgress(int percent)
   {
      this.progess = percent;
      Log.d("de.comfit", "update: "+percent);
      ProgressBar p = (ProgressBar) getSource().findViewById(R.id.stepactivprogress);
      p.setProgress(progess);
      getSource().setBackgroundColor(getSource().getResources().getColor(R.color.done));
      if(percent==100){
    	  createShareDialog();
      }
   }

   private void createShareDialog() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

			// set title
			alertDialogBuilder.setTitle("Share on Twitter?");

			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent i= new Intent(context, TweetActivity.class);
						i.putExtra("message", "Yuhu ...  ;) ");
						context.startActivity(i);

					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.dismiss();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
	
   
   public abstract double getCalStep();

   public void setIndex(int index)
   {
      this.index = index;

   }

   public abstract void start(View source);

public View getSource() {
	return source;
}

public void setSource(View source) {
	this.source = source;
}

public SportActiv getSportActivByHash(int hashcode) {
	for (SportActiv a : sportActivs) {
		if (a.hashCode() == hashcode) {
			return a;
		}
	}
	return null;
}

}
