package dev.liebegott.Sentido;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


/**
 * A class for packaging the speech data analyzed into a neat format for export and usage elsewhere. To database and to JSON.
 * @author Michael Liebegott
 *
 */
public class Packager<E> {
	private Connection connection = null;
	private String outputFP = null; 
	private Gson gson;
	private ArrayList<E> arr;
	private String json;
	private File outputFile;
	
	//TODO: Constructor for DB connection and Constructor for JSON output file
	
	/**
	 * Constructor for the Packager object. Takes in an ArrayList of any type to package for export as a JSON to a Database.
	 * @param arr
	 */
	Packager(ArrayList<E> arr, Connection connection) {
		this.arr = arr;
		this.gson = new Gson();
		this.connection = connection;
	}
	
	/**
	 * Constructor for the Packager object. Takes in an ArrayList of any type to package for export as a JSON to a filepath.
	 * @param arr
	 */
	Packager(ArrayList<E> arr, String outputFP) {
		this.arr = arr;
		this.gson = new Gson();
		this.outputFP = outputFP;
		createFile(outputFP);
	}
	
	private void createFile(String outputFP) {
		File file = new File(outputFP);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.outputFile = file;
	}
	
	public void outputToFile() {
		try {
			FileWriter fileWriter = new FileWriter(this.outputFile);
			fileWriter.write(jsonify());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the provided ArrayList to a JSON type.
	 */
	public String jsonify() {
		this.json = this.gson.toJson(this.arr);
		return this.json;
	}
}
