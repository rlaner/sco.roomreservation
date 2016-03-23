package it.smartcommunitylab.roomreservation.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.Creator;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.gson.JsonParseException;

public class EventWrapper  {

	String title = null;

	String id = null;

	String start = null;

	String end = null;

	String status = null;

	String email = null;

	String name = null;

	String roomName = null;



	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EventWrapper() {

	}

	public EventWrapper (Event event){
		setTitle (event.getSummary());
		setId(event.getId());
		setStart(event.getStart().getDateTime().toStringRfc3339());
		setEnd(event.getEnd().getDateTime().toStringRfc3339());
		setStatus(event.getStatus());
		setName(event.getCreator().getDisplayName());
		setEmail(event.getCreator().getEmail());
	}

	public Event toEvent() throws ParseException {

		Event event = new Event()
		    .setSummary(getTitle())
		    .setLocation("")
		    .setDescription(getTitle());

		SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateTime startDateTime = new DateTime(dateParser.parse(getStart()));
		EventDateTime start = new EventDateTime()
		    .setDateTime(startDateTime)
		    .setTimeZone("Europe/Rome");
		event.setStart(start);

		DateTime endDateTime = new DateTime(dateParser.parse(getEnd()));
		EventDateTime end = new EventDateTime()
		    .setDateTime(endDateTime)
		    .setTimeZone("Europe/Rome");
		event.setEnd(end);


//		EventAttendee referee = new EventAttendee();
//		referee.setEmail(getEmail());
//		referee.setResponseStatus("tentative");
//
		EventAttendee requestor = new EventAttendee();
		requestor.setEmail(getEmail());
		requestor.setResponseStatus("accepted");


		EventAttendee[] attendees = new EventAttendee[] {
				requestor
		};
		event.setAttendees(Arrays.asList(attendees));


		EventReminder[] reminderOverrides = new EventReminder[] {
		    new EventReminder().setMethod("email").setMinutes(24 * 60),
		};
		Event.Reminders reminders = new Event.Reminders()
		    .setUseDefault(false)
		    .setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		Creator creator= new Creator();
		creator.setEmail(getEmail());
		creator.setDisplayName(getName());

		event.setCreator(creator);

		return event;

	}


	public String toString() {
		return "{title: '" + title + "',name: '" + name + "'}";
	}


}
