package fr.univtours.polytech.punchingmanagement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Company;

public class SaveController {
	private static Timer timer;
	private static final String SAVE_FILE_COMPANY = "company.ser";
	private static final String ERROR_MESSAGE_LOADING = "Error while loading %s : ";
	private static final String ERROR_MESSAGE_SAVING = "Error while saving %s : ";
	private static final String STATUS_MESSAGE_LOADED = "Company loaded with %d employees, %d departments and %d punching days";

	private static final int AUTO_BACKUP_DELAY = 10000;

	private SaveController() {
	}

	public static void save() {
		try {
			File nf = new File(SAVE_FILE_COMPANY);

			nf.createNewFile();

			FileOutputStream fos = new FileOutputStream(nf, false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(MainApp.getCompany());

			oos.close();
		} catch (Exception e) {
			System.err.println(String.format(ERROR_MESSAGE_SAVING, SAVE_FILE_COMPANY) + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void load() {
		try (FileInputStream ios = new FileInputStream(SAVE_FILE_COMPANY)) {
			ObjectInputStream ois = new ObjectInputStream(ios);

			Company company = MainApp.getCompany();

			Company loadedCompany = (Company) ois.readObject();

			// We need to keep the object company of MainApp,
			// so we just copy the data from the loaded company
			company.getDepartments().setAll(loadedCompany.getDepartments());
			company.getEmployees().setAll(loadedCompany.getEmployees());
			company.getPunchingDays().setAll(loadedCompany.getPunchingDays());

			System.out.println(String.format(STATUS_MESSAGE_LOADED,
					company.getEmployees().size(),
					company.getDepartments().size(),
					company.getPunchingDays().size()));

			ois.close();
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException | ClassNotFoundException e) {
			System.err.println(String.format(ERROR_MESSAGE_LOADING, SAVE_FILE_COMPANY) + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void startAutoBackup() {
		if (timer != null)
			return;
		try {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					save();
				}
			}, 1000, AUTO_BACKUP_DELAY);
		} catch (Exception e) {
			System.err.println("Error whith the autoBackup : " + e.getMessage());
			System.exit(1);
		}
	}

	public static void stopAutoBackup() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
