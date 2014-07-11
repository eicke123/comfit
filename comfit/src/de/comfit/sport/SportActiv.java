package de.comfit.sport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import de.comfit.ClickSportActiv;
import de.comfit.R;
import de.comfit.SportActivity;
import de.comfit.TweetActivity;
import de.comfit.history.WorkoutData;
import de.comfit.history.WorkoutItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public abstract class SportActiv extends BroadcastReceiver {
	protected SportActivity context;
	protected int index;
	private int progess;
	private View source;
	public static final HashSet<SportActiv> sportActivs = new HashSet<SportActiv>();
   private WorkoutItem workoutItem;
   private LinkedHashMap<Long, Long> data;

	public SportActiv(SportActivity context) {
		this();
		this.context = context;
		workoutItem  = new WorkoutItem();
		workoutItem.setLabel(getLabel());
		data = new LinkedHashMap<Long, Long>();
	}

	public abstract String getLabel();

   public SportActiv() {
		sportActivs.add(this);
	}

	public void updateProgress(int percent, int stepsSitups, SportActiv activ) {
		this.setProgess(percent);
		ProgressBar p = (ProgressBar) getSource().findViewById(
				R.id.stepactivprogress);
		p.setProgress(getProgess());
		
		data.put(System.currentTimeMillis(), (long) percent);
		
		if (percent == 100) {
		   if (workoutItem.getGraphData() == null)
         {
		      Long startTime = data.keySet().iterator().next();
		      int [][] d =  new int[data.size()][2];
            int i = 0;
            for (Entry<Long, Long> da : data.entrySet())
            {
               d[i][0] = (int) (da.getKey() -startTime);
               d[i][1] = da.getValue().intValue();
            }
            workoutItem.setGraphData(d);
         }
		   
		   getSource().setBackgroundColor(
            getSource().getResources().getColor(R.color.done));
		   enableOthers();
			if (activ != null && activ instanceof SitUpActiv) {
				createShareDialog(stepsSitups, "SitUps");
			}
			if (activ != null && activ instanceof RunningActiv) {
				createShareDialog(stepsSitups, "Schritte");
			}
		}
	}

	private void createShareDialog(final int stepsSitups, final String text) {
		// TODO Auto-generated method stub

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Share on Twitter?");

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(context,
										TweetActivity.class);
								i.putExtra("message",
										"Yuhu ...  ich habe gerade  "
												+ stepsSitups + " " + text
												+ " gemacht!");
								context.startActivity(i);

							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
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

	public abstract double getCalStep(double weight);
	public abstract int getMinWorkoutSize();
	public abstract int getMaxWorkoutSize();
	public abstract void setWorkoutSize(int size);
	public abstract int getWorkoutSize();

	public void setIndex(int index) {
		this.index = index;

	}

	public void disableOthers() {
		ArrayList<SportActiv> sportActivs2 = context.getSportActivs();
		for (SportActiv sportActiv : sportActivs2) {
			if (sportActiv != this) {
				sportActiv.disable();
			}
		}
		ClickSportActiv.setActive(false);
		;
	}

	private void disable() {
		source.setBackgroundColor(getSource().getResources().getColor(
				R.color.disabled));
	}

	public void enableOthers() {
		ArrayList<SportActiv> sportActivs2 = context.getSportActivs();
		for (SportActiv sportActiv : sportActivs2) {
			if (sportActiv != this) {
				sportActiv.enable();
			}
		}
		ClickSportActiv.setActive(true);
		;
	}

	private void enable() {
		if (progess <100)
      {
         source.setBackgroundColor(getSource().getResources().getColor(
            R.color.standart));
      }
	}

	public abstract void start(View source);

	public View getSource() {
		return source;
	}

	public void setSource(View source) {
		this.source = source;
		source.setBackgroundColor(getSource().getResources().getColor(
         R.color.standart));
	}

	public SportActiv getSportActivByHash(int hashcode) {
		for (SportActiv a : sportActivs) {
			if (a.hashCode() == hashcode) {
				return a;
			}
		}
		return null;
	}

   public int getProgess()
   {
      return progess;
   }

   public void setProgess(int progess)
   {
      this.progess = progess;
   }
   
   public WorkoutItem getWorkoutItem()
   {
      return workoutItem;
   }

}