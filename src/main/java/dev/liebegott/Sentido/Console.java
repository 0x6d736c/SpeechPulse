package dev.liebegott.Sentido;

import dev.liebegott.Sentido.SpeechReader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


/**
 * A class to permit simple processing of speech documents from the terminal.
 * @author Michael Liebegott
 *
 */
public class Console {
	//Used for terminal coloring.
	private static final String RED = "\033[0;31m";
	private static final String YELLOW = "\033[0;33m";
	private static final String RESET = "\033[0m";
	
	
	//File IO
	private static Scanner scanner;
	private static String inputFP = null;
	private static String outputFP = null;
	
	//Datetime
	private static SimpleDateFormat datetime;
	private static Date date;

	
	public static void main(String[] args) {
		
		System.out.println(System.getProperty("user.dir"));
		//Sets date/time for use in console output.
		date = new Date();
		datetime = new SimpleDateFormat("HH:mm", Locale.getDefault());
		
		//Check if input exists in CLI args | warn if FP is invalid
		if (hasInput(args)) {
			inputFP = args[0];
			if (!fpExists(inputFP)) {
				inputFP = null;
				inputInvalid();
			}
		}
		
		//Check if output exists in CLI args | warn if FP is invalid
		if (hasOutput(args)) {
			outputFP = args[1];
			if (fpExists(outputFP)) { 
				outputFP = null;
				outputInvalid();
			}
		}
		
		//If either FPs are null, create a scanner object
		if (inputFP == null || outputFP == null) {
			scanner = new Scanner(System.in);
			
			//Loop until user enters valid FP >> file must exist for input to work.
			while (inputFP == null) {
				inputFP = promptFP("Enter filepath for input text: ", scanner);
				if (!fpExists(inputFP)) { 
					inputFP = null;
					inputInvalid();
				}
			}
			
			//Loop until user enters valid FP >> file must NOT exist, program will NOT overwrite existing
			while (outputFP == null) {
				outputFP = promptFP("Enter filepath for output: Press ENTER to use default filename: %s".formatted(generateDefaultFileName()), scanner);
				if (outputFP.isBlank()) {
					outputFP = generateDefaultFileName();
				}
				
				if(fpExists(outputFP)) {
					outputFP = null;
					outputInvalid();
				}
			}
			
			scanner.close(); //Closes scanner once files are validated
		}
		
		
		System.out.println(YELLOW + datetime.format(date) + RESET + " - Beginning analysis of \"%s...\"".formatted(inputFP));
		
		//Speech analysis program.
		SpeechReader reader = new SpeechReader(inputFP);					//Read in speech
		Analyzer analyzer = new Analyzer(reader.getCurrentSpeech());		//Analyze speech
		ArrayList<Integer> sentimentValues = analyzer.analyze();			//Get sentiment values
		//DescriptiveStatistics statistics = analyzer.getStatistics();		//Get desc. statistics
		
		
		//DOES NOT WORK IN CURRENT STATE
		Packager packager = new Packager(sentimentValues, outputFP);		//Create file packager
		packager.outputToFile();
		
		System.out.println(YELLOW + datetime.format(date) + RESET + " - Finished analysis of \"%s\"".formatted(inputFP));
	}
	
	private static String generateDefaultFileName() {
		String[] defaultFile = inputFP.split("\\.");
		return defaultFile[0] + ".js";
	}
	
	private static void inputInvalid() {
		System.out.println(RED + "ERROR: " + RESET + "Input file does not exist :: Existing file name required.");
	}
	
	private static void outputInvalid() {
		System.out.println(RED + "ERROR: " + RESET + "Output file already exists :: New file name required.");
	}
	
	private static boolean fpExists(String fp) {
		File file = new File(fp);
		return file.exists();
	}
	
	private static boolean hasInput(String[] args) {
		return args.length > 0;
	}
	
	private static boolean hasOutput(String[] args) {
		return args.length > 1;
	}
	
	private static String promptFP(String message, Scanner scanner) {
		System.out.print(message);
		String fp = scanner.nextLine().trim().strip();
		return fp;
	}
}
