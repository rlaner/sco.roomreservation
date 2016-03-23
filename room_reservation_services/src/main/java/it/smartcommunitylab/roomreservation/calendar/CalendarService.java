package it.smartcommunitylab.roomreservation.calendar;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class CalendarService {
	private static com.google.api.services.calendar.Calendar _service = null;

    /** Application name. */
    private static final String APPLICATION_NAME =
        "MyGoogleCalandarAPI";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/" + APPLICATION_NAME);

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
    	Credential credential = new GoogleCredential.Builder()
        .setClientSecrets(
        		CalendarServiceProperties.getProperty("google.clientid"),
        		CalendarServiceProperties.getProperty("google.clientsecret"))
        .setJsonFactory(JSON_FACTORY)
        .setTransport(HTTP_TRANSPORT).build();

    	credential.setRefreshToken(CalendarServiceProperties.getProperty("google.refreshtoken"));
    	credential.setAccessToken(CalendarServiceProperties.getProperty("google.accesstoken"));

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Event> getCalendarEvents(String calendarId) throws IOException {
    	// List the next 10 events from the primary calendar.
    	System.err.println("Returing event list for calendar " + calendarId);
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = _service.events().list(calendarId)
            .setMaxResults(100)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        return events.getItems();
    }

    public Event getCalendarEvent(String calendarId, String eventId) throws IOException {
    	Event event = _service.events().get(calendarId, eventId).execute();
    	System.out.printf("Event returned: %s\n", event.getHtmlLink());
        return event;
    }

    public Event newCalendarEvent(String calendarId, Event event) throws IOException {


    	event = _service.events().insert(calendarId, event).execute();
    	System.out.printf("Event created: %s\n", event.getHtmlLink());
        return event;
    }

    public void deleteCalendarEvent(String calendarId, String eventId) throws IOException {
    	_service.events().delete(calendarId, eventId).execute();
    	System.out.printf("Event deleted: %s\n", eventId);
	}

    public CalendarService() throws IOException {
    	if (_service == null) {
    		_service =  getCalendarService();
    	}
    }
}
