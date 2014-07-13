package de.comfit.sport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import de.comfit.ClickSportActiv;
import de.comfit.R;
import de.comfit.SportActivity;
import de.comfit.TweetActivity;
import de.comfit.history.WorkoutItem;

/**
 * Abstract class SportActiv
 * 
 */
public abstract class SportActiv extends BroadcastReceiver {
	protected SportActivity context;
	protected int index;
	private int progess;
	private View source;

	public static final HashSet<SportActiv> sportActivs = new HashSet<SportActiv>();
	private WorkoutItem workoutItem;
	private LinkedHashMap<Long, Long> data;

	public abstract String getLabel();

	public abstract double getCalStep(double weight);

	public abstract int getMinWorkoutSize();

	public abstract int getMaxWorkoutSize();

	public abstract void setWorkoutSize(int size);

	public abstract int getWorkoutSize();

	public abstract void start(View source);

	/*
	 * Initialize variables
	 */
	public SportActiv(SportActivity context) {
		this();
		this.context = context;
		workoutItem = new WorkoutItem();
		workoutItem.setLabel(getLabel());
		data = new LinkedHashMap<Long, Long>();
	}

	/*
	 * Default constructor
	 */
	public SportActiv() {
		sportActivs.add(this);
	}

	/**
	 * Method to update the progressbar of a list item.
	 * 
	 * @param percent
	 *            - percentage of completed actions in activity
	 * @param stepsSitups
	 *            - the final count, to share on twitter
	 * @param activ
	 *            - right object belonging to the list item
	 */
	public void updateProgress(int percent, int stepsSitups, SportActiv activ) {
		this.setProgess(percent);
		ProgressBar p = (ProgressBar) getSource().findViewById(
				R.id.stepactivprogress);
		p.setProgress(getProgess());

		data.put((long) percent, System.currentTimeMillis());

		if (percent == 100) {
			if (workoutItem.getGraphData() == null) {
				Long startTime = data.values().iterator().next();
				int[][] d = new int[data.size()][2];
				int i = 0;
				for (Entry<Long, Long> da : data.entrySet()) {
					d[i][0] = (int) (da.getValue() - startTime) / 1000;
					d[i][1] = da.getKey().intValue();
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

	/**
	 * Method to create a share dialog to share done activities to twitter. Done
	 * by simple AlertDialog with two buttons
	 * 
	 * @param stepsSitups
	 * @param text
	 */
	private void createShareDialog(final int stepsSitups, final String text) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Share on Twitter?");

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
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
					@Override
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

	public void setIndex(int index) {
		this.index = index;

	}

	/*
	 * Disable all list items except this
	 */
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

	/*
	 * disable done activity
	 */
	private void disable() {
		source.setBackgroundColor(getSource().getResources().getColor(
				R.color.disabled));
	}

	/*
	 * Enable all list items except this
	 */
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

	/*
	 * Enables list items if activity is not finished
	 */
	private void enable() {
		if (progess < 100) {
			source.setBackgroundColor(getSource().getResources().getColor(
					R.color.standart));
		}
	}

	/*
	 * Getter and Setter for source source is previous calling activity
	 */
	public View getSource() {
		return source;
	}

	public void setSource(View source) {
		this.source = source;
		source.setBackgroundColor(getSource().getResources().getColor(
				R.color.standart));
	}

	/**
	 * Searches the right SportActiv object in array
	 * 
	 * @param hashcode
	 * @return hashcode from object
	 */
	public SportActiv getSportActivByHash(int hashcode) {
		for (SportActiv a : sportActivs) {
			if (a.hashCode() == hashcode) {
				return a;
			}
		}
		return null;
	}

	public int getProgess() {
		return progess;
	}

	public void setProgess(int progess) {
		this.progess = progess;
	}

	public WorkoutItem getWorkoutItem() {
		return workoutItem;
	}

	public void setActive() {
		source.setBackgroundColor(getSource().getResources().getColor(
				R.color.pressed));
	}

}