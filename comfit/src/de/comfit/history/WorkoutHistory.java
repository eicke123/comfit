package de.comfit.history;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import de.comfit.R;

/**
 * 
 * @author Waldo This class saves and loads the whole workout data in a history
 *         file
 * 
 */
public class WorkoutHistory implements Serializable {

	private static final long serialVersionUID = -4735131709016438522L;
	private WorkoutData[] data;

	public void writeToFile(ObjectOutputStream out) throws IOException {
		out.writeObject(data);
	}

	public void readToFile(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		setData((WorkoutData[]) in.readObject());
	}

	public WorkoutData[] getData() {
		return data;
	}

	public void setData(WorkoutData[] data) {
		this.data = data;
	}

	/*
	 * Load workout data from history file
	 */
	public WorkoutData[] loadWorkoutData(Context c) {
		WorkoutData[] data = null;
		try {
			FileInputStream fin = c.openFileInput(c
					.getString(R.string.history_file_name));
			ObjectInputStream ois = new ObjectInputStream(fin);
			data = (WorkoutData[]) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.data = data;
		return data;
	}

	/*
	 * Saves workout data provided by SportActivity to file
	 */
	public void saveWorkoutData(Context c) {
		// retrieved from SportActivity

		try {
			FileOutputStream fos = c.openFileOutput(
					c.getString(R.string.history_file_name),
					Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			this.writeToFile(os);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
