package it.smartcommunitylab.roomreservation.calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.util.IOUtils;

public class RoomService {

	static RoomService me = null;

	public static RoomService getInstance() {
		if (me == null)
			me = new RoomService();
		return me;
	}

	public Object getRoomList() throws RestClientException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForObject(CalendarServiceProperties.getProperty("roomservice.url.rooms"),
				Object.class);
		System.err.println(response);
		return response;
	}
//
//	public static void main(String args[]) {
//		try {
//			//System.err.println(RoomService.getInstance().getRoomList());
//			System.err.println(RoomService.getInstance().getRoom("931148"));
//		} catch (RestClientException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	public Object getRoom(String roomId) throws RestClientException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		String url = CalendarServiceProperties.getProperty("roomservice.url.room").replaceAll("nodeId", roomId);
		System.err.println("get room -> " + url);
        Object response = restTemplate.getForObject(url, Object.class);


        // just in test mode

        response = getDebugResource("sala_" + roomId);
		return response;
	}

	private String getDebugResource(String debugResourceName) throws IOException {

		String resourceName = "/"+debugResourceName+".json";
		InputStream in =
	            CalendarService.class.getResourceAsStream(resourceName);

		StringBuilder sb=new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String read;

		while((read=br.readLine()) != null) {
		    //System.out.println(read);
		    sb.append(read);
		}

		br.close();
		return sb.toString();
	}



}
