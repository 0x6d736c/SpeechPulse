package dev.liebegott.Sentido;
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 * Reads the text file from the inputted file path and returns the entire text as a String.
 * @author Michael Liebegott
 *
 */
public class SpeechReader {
	/*
	 * PRIVATE MEMBER VARIABLES
	 */
	private String currentSpeech;
	private String currentSpeechFP;
	
	/*
	 * CONSTRUCTORS
	 */
	/**
	 * Creates a default constructor with an empty currentSpeech file.
	 */
	SpeechReader() {
		this.currentSpeech = "";
	}
	
	/**
	 * Reads the speech file into currentSpeechFP and stores its contents in currentSpeech.
	 * @param currentSpeechFP - the file path pointing to the speech to be read.
	 */
	SpeechReader(String currentSpeechFP) {
		this.currentSpeechFP = currentSpeechFP;
		readSpeech();
	}
	
	/**
	 * Reads in the speech given based on the filepath specified in currentSpeechFP. 
	 */
	private void readSpeech() {
		try {
			File speechFile = new File(this.currentSpeechFP);
			Scanner fileScanner = new Scanner(speechFile);
			this.currentSpeech = "";
			while (fileScanner.hasNextLine()) {
				this.currentSpeech += (fileScanner.nextLine());
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Speech file not found.");
			e.printStackTrace();
		}
	}
	

	public String getCurrentSpeech() {
		return this.currentSpeech;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */

	/**
	 * Sets the currentSpeech to the provided String.
	 * @param currentSpeech
	 */
	public void setCurrentSpeech(String currentSpeech) {
		this.currentSpeech = currentSpeech;
	}

	public String getCurrentSpeechFP() {
		return currentSpeechFP;
	}

	/**
	 * Sets the currentSpeechFP to the provided String. Reads the speech into currentSpeech.
	 * @param currentSpeechFP
	 */
	public void setCurrentSpeechFP(String currentSpeechFP) {
		this.currentSpeechFP = currentSpeechFP;
		readSpeech();
	}
	
	/**
	 * Returns the current speech as a String.
	 */
	@Override
	public String toString() {
		return this.currentSpeech;
	}
	
}
