<<<<<<< HEAD
package de.comfit.history;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WorkoutHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private WorkoutData[] data;

	public void writeToFile(ObjectOutputStream out) throws IOException {
		out.writeObject(getData());
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
}
=======
package de.comfit.history;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WorkoutHistory implements Serializable {

	private static final long serialVersionUID = 1L;

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
}
>>>>>>> 6b643b90bdabc65c9e9fb78b9e1681825189b738
