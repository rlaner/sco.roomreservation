package it.smartcommunitylab.roomreservation.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CalendarServiceProperties {

	static Properties properties = null;

	public static Properties getProperties() throws IOException {
		if (properties == null) {
			 // Load client secrets.
	        InputStream in =
	        		CalendarServiceProperties.class.getResourceAsStream("/properties.txt");
	        properties = new Properties();
	        properties.load(in);
		}
		return properties;
	}



	public static String getProperty(String name) throws IOException {
		return getProperties().get(name).toString();
	}




}
