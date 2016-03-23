package it.smartcommunitylab.roomreservation.calendar;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

@RestController
public class CalendarServiceController {

	CalendarService service = null;

	private CalendarService getCalendarService() throws IOException {
		if (service == null)
			service = new CalendarService();

		return service;
	}

	@RequestMapping(value = "/roomList/", method = RequestMethod.GET)
	public ResponseEntity<Object> listRoom(UriComponentsBuilder ucBuilder) {

		Object roomList = null;
		try {
			roomList = RoomService.getInstance().getRoomList();
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<Object>(roomList, HttpStatus.OK);
		} catch (RestClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(roomList, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getRoom(@PathVariable String roomId,UriComponentsBuilder ucBuilder) {
		System.err.println("roomId =" + roomId);
		Object room = null;
		try {
			room = RoomService.getInstance().getRoom(roomId);
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<Object>(room, HttpStatus.OK);
		} catch (RestClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(room, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/eventList/{calendarId:.*}", method = RequestMethod.GET)
	public ResponseEntity<List<EventWrapper>> listCales(@PathVariable String calendarId) {

		System.err.println("calendarId =" + calendarId);
		List<EventWrapper> items = new ArrayList<EventWrapper>();
		System.err.println("1");
		try {
			CalendarService service = new CalendarService();
			System.err.println("2");
			List<Event> events = service.getCalendarEvents(calendarId);
			System.err.println("3");
			if (events.size() == 0) {
				System.out.println("No upcoming events found.");
				return new ResponseEntity<List<EventWrapper>>(HttpStatus.NO_CONTENT);// You
																						// many
																						// decide
																						// to
																						// return
																						// HttpStatus.NOT_FOUND
			} else {
				System.out.println("Upcoming events");
				for (Event event : events) {
					DateTime start = event.getStart().getDateTime();
					if (start == null) {
						start = event.getStart().getDate();
					}
					items.add(new EventWrapper(event));
					System.out.printf("status ", event.getStatus(), start);
					if (event.getAttendees() != null)
						for (EventAttendee a : event.getAttendees()) {
							System.err.println("  name " + a.getDisplayName() + " email " + a.getDisplayName()
									+ " status " + a.getResponseStatus());
						}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<List<EventWrapper>>(items, HttpStatus.OK);
	}

	@RequestMapping(value = "/eventAdd/{calendarId:.*}", method = RequestMethod.POST)
	public ResponseEntity<Void> addEvent(@PathVariable String calendarId, @RequestBody EventWrapper event,
			UriComponentsBuilder ucBuilder) {
		System.err.println("Creating Event " + calendarId);
		System.err.println("Creating Event " + event.getTitle());

		try {
			CalendarService service = getCalendarService();
			service.newCalendarEvent(calendarId, event.toEvent());

			// send email to referee
			String message = CalendarServiceProperties.getProperty("alert.addevent..email.message");
			message = message.replaceAll("calendarId", calendarId);
			message = message.replaceAll("roomName", event.getRoomName());
			message = message.replaceAll("requestorName", event.getName());
			message = message.replaceAll("requestorEmail", event.getEmail());

			String subject = CalendarServiceProperties.getProperty("alert.addevent..email.subject");
			subject = subject.replaceAll("calendarId", calendarId);
			subject = subject.replaceAll("roomName", event.getRoomName());
			subject = subject.replaceAll("requestorName", event.getName());
			subject = subject.replaceAll("requestorEmail", event.getEmail());

			EmailSender.getEmailSender().sendMail(event.getEmail(), event.getEmail(), subject, message);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/eventAdd").buildAndExpand(calendarId).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/eventAdd").buildAndExpand(calendarId).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/eventDelete/{calendarId:.*}/{eventId:.*}/{userEmail}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEvent(@PathVariable String calendarId, @PathVariable String eventId, @PathVariable String userEmail,
			UriComponentsBuilder ucBuilder) {
		System.err.println("Calendar id " + calendarId);
		System.err.println("Delete Event " + eventId);

		try {

			Event eventFormCalendar = getCalendarService().getCalendarEvent(calendarId, eventId);

			if (!userEmail.equals(eventFormCalendar.getAttendees().get(0).getEmail())) {
				System.err.println("Error! The user email " + userEmail
						+ " non corrisponde alla mail dell'organizzatore " + eventFormCalendar.getAttendees().get(0).getEmail());
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/eventDelete").buildAndExpand(calendarId, eventId).toUri());
				return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);

			}
			getCalendarService().deleteCalendarEvent(calendarId, eventFormCalendar.getId());

			// // send email to referee
			// String message = CalendarServiceProperties.getAlertMessage();
			// message = message.replaceAll("calendarId", calendarId);
			// message = message.replaceAll("roomName", event.getRoomName());
			// message = message.replaceAll("requestorName", event.getName());
			// message = message.replaceAll("requestorEmail", event.getEmail());
			//
			// String subject = CalendarServiceProperties.getAlertSubject();
			// subject = subject.replaceAll("calendarId", calendarId);
			// subject = subject.replaceAll("roomName", event.getRoomName());
			// subject = subject.replaceAll("requestorName", event.getName());
			// subject = subject.replaceAll("requestorEmail", event.getEmail());
			//
			// EmailSender.getEmailSender().sendMail(event.getEmail(),
			// event.getRefereeEmail(), subject, message);
			//
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/eventDelete").buildAndExpand(calendarId).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/eventDelete").buildAndExpand(calendarId).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
	}

}
