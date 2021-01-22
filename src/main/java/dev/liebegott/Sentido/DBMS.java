package dev.liebegott.Sentido;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * Helper class to manage database integration.
 * @author Michael Liebegott
 *
 */

public class DBMS {
	/**
	 * Creates the URL required for the database connection DriverManager.
	 * @return String: the url for connecting to the database in the format of "jdbc:dbms://host:port"
	 * @throws Exception - if any of the properties are missing, throws an exception.
	 */
	protected static String createDBURL(Properties props) throws Exception {
		String url = null;
		
		if (props != null) {
			String dbms = props.getProperty("dbms");
			String host = props.getProperty("host");
			String port = props.getProperty("port");
			
			if (dbms == null || host == null || port == null) throw new Exception("Missing values for database properties object.");
			
			url = String.format("jdbc:%s://%s:%s", dbms, host, port);
		}
		
		return url;
	}
	
	/**
	 * Generates a connection to the DBMS using input paramaters.
	 * @param connectionURL - the dbms, host, and connection port.
	 * @param username - the dbms username.
	 * @param password - the dbms password.
	 * @return Connection object - a connection to the DBMS. null if no connection made.
	 */
	protected static Connection connect(String connectionURL, String username, String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Loads the database management properties (dbms, host, port, username, and password) from the specified filepath. Returns null if properties not found.
	 *  Properties should be in JSON format:
	 *  Example:
	 *  {"username":"root", "password":"root", "host":"localhost","port":"8000", "dbms":"mysql"}
	 * @param propertiesFP - filepath to the properties file.
	 * @return
	 */
	protected static Properties createDBProperties(String propertiesFP) {
		File credentials = new File(propertiesFP);
		Properties dbProperties = null;
		
		try {
			Scanner credentialScanner = new Scanner(credentials);
			Gson gson = new Gson();
			dbProperties = gson.fromJson(credentialScanner.nextLine(), Properties.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return dbProperties;
		
	}
}
