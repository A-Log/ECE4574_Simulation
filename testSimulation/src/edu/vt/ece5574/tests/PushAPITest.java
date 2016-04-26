package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.sim.PushAPICaller;

public class PushAPITest {

	String xapikey;

	// Define a custom response handler for API calls
	ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(final HttpResponse response) // throws
																	// ClientProtocolException,
																	// IOException
		{
			int status = response.getStatusLine().getStatusCode();
			try {
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					return ("Unexpected response status");
				}
			} catch (Exception e) {
				return e.getMessage();
			}
		}

	};
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void init() {
		xapikey = "F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj";
	}

	// Check that the function returns no events for incorrect user id
	@Test
	public void callIncorrectUserID() {
		String userID = "DummyUser";
		ArrayList<Event> myEvents = PushAPICaller.callPushSystemAPI(userID);
		assertEquals(0, myEvents.size());
	}

	// check if the message is being parsed correctly to return the message type

	@Test
	public void callgetMessageType() {
		String body = " {\"messageId\": \"ac7ba131-efcf-11e5-b194-0bcd942e918c\",\"message\": {\"msg_type\": \"Fire\",\"body\": {\"floor\": 5,\"room\": 7}}}";
		String msg_type = "";
		try {
			JSONObject fullbody = new JSONObject(body);
			msg_type = PushAPICaller.getMessageType(fullbody);
			assertEquals("fire", msg_type);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	// Check if correct number of events and event types are returned
	@Test
	public void validateEventType() {

		// Post a message for a dummy user and validate the event generated
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			HttpPost httppost = new HttpPost("https://2bj29vv7f3.execute-api.us-east-1.amazonaws.com/testing");
			httppost.addHeader("x-api-key", xapikey);
			String JSON_STRING = "{" + "\"id\": [\"Dummy\"]," + "\"message\": {" + "\"msg_type\": \"fire\","
					+ "\"body\": {" + "\"building\": \"0\"," + "\"room\": 1," + "\"floor\": 2," + "\"xpos\": 3,"
					+ "\"ypos\": 4," + "\"severity\": 5," + "\"action\": \"Extinguish\"" + "}" + "}" + "}";

			StringEntity requestEntity = new StringEntity(JSON_STRING);
			httppost.setEntity(requestEntity);

			// Execute the get method
			String responseBody = httpclient.execute(httppost, responseHandler);
			if (responseBody == "Unexpected response status") {
				System.out.println(responseBody);

			}
			ArrayList<Event> myEvents = PushAPICaller.callPushSystemAPI("Dummy");
			// Only one fire event should be generated as per the input JSON
			assertEquals(1, myEvents.size());
			assertEquals("edu.vt.ece5574.events.FireEvent", myEvents.get(0).getClass().getName().toString());
			Thread.sleep(1000); //introduce a sleep so the email can be sent from the push service 
								//and we can delete it before any tests run
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 finally {
		        try {
		        	
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		    }
	}

	// Check if the message is getting deleted after reading
	@Test
	public void checkIfMessageDeleted() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(
					"https://2bj29vv7f3.execute-api.us-east-1.amazonaws.com/testing/Dummy/messages");
			httpget.addHeader("x-api-key", xapikey);

			String responseBody = httpclient.execute(httpget, responseHandler);

			// responseBody shouldn't have any message as the message should be
			// deleted after reading
			assertEquals("Unexpected response status", responseBody);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 finally {
		        try {
		        	
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		    }
	}

}
