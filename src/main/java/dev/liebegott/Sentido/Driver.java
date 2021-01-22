package dev.liebegott.Sentido;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

class Driver {
	public static void main( String[] args ) {

		String obamaFP = "./presidential_speeches/obama/";

	
		//TODO: 
		/* 1. Connect to Database ++
		 * 2. Package data properly ?
		 * 	- WHAT query to use?
		 * 3. Send data to DB ?
		 * 	- Should be easy with DBMS tool
		 * 4. Confirm data storage in DB ?
		 * 	- SELECT statement ?? check if in data
		 * 5. Repeat for each speech.
		 * - LOOP
		 */
		
		Properties dbProperties = DBMS.createDBProperties("credentials.json");				//Get credentials from JSON
		String username = dbProperties.getProperty("username");								//Unpack username + password
		String password = dbProperties.getProperty("password");
		
		String dbURL = null;																//Generate dbURL for connection obj
		Connection dbConnection = null;
		try {
			dbURL = DBMS.createDBURL(dbProperties);
			dbConnection = DBMS.connect(dbURL, username, password);							//Establish connection
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For each speech
		String directoryFP = obamaFP;
		File directory = new File(directoryFP);
		File[] directoryFPs = directory.listFiles();
		if (directoryFPs != null) {
			for (File fileInDirectory : directoryFPs) {
				//Do something for each file in the directory.
			}
		}
		
		String[] speechFPs; 					//Contains each speech FP
		SpeechReader speechReader;				//SpeechReader object to read each speech
		String speech;							//Contains entire speech in String format
		Analyzer analyzer;						//Analyzer object to analyze each speech for sentiment
		ArrayList<Integer> speechSentiment;		//Contains sentiment values for each speech
		
//		for (String speechFP : speechFPs) {
//			speechReader = new SpeechReader(speechFP);
//			speech = speechReader.getCurrentSpeech();
//			analyzer = new Analyzer(speech);
//			speechSentiment = analyzer.analyze();
//			//TODO: Implement Packager functionality
//			/*
//			 * 1. Speech sentiment needs to be packaged into a neat format.
//			 * 2. Needs to be appropriately stored in DB.
//			 * 3. Needs to be checked for validity before moving on.
//			 * 4. Console output for status would be useful.
//			 */
//		}
	}
}

