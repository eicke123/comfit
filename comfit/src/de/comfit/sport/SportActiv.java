package de.comfit.sport;

import java.util.ArrayList;
import java.util.HashSet;

import de.comfit.ClickSportActiv;
import de.comfit.R;
import de.comfit.SportActivity;
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

public abstract class SportActiv extends BroadcastReceiver {
	protected SportActivity context;
	protected int index;
	private int progess;
	private View source;
	public static final HashSet<SportActiv> sportActivs = new HashSet<SportActiv>();

	public SportActiv(SportActivity context) {
		this();
		this.context = context;
	}

	public SportActiv() {
		sportActivs.add(this);
	}

	public void updateProgress(int percent, int stepsSitups, SportActiv activ) {
		this.setProgess(percent);
		Log.d("de.comfit", "update: " + percent);
		ProgressBar p = (ProgressBar) getSource().findViewById(
				R.id.stepactivprogress);
		p.setProgress(getProgess());
		if (percent == 100) {
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

	public abstract double getCalStep();

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

}