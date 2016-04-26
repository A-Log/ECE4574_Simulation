/**
 * @author Saket
 */


package edu.vt.ece5574.sim;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.events.WaterLeakEvent;


public class PushAPICaller  {

 
//Call get and delete methods of Push system API
public static ArrayList<Event> callPushSystemAPI(String userID) {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    	ArrayList<Event> myEvents=new ArrayList<Event>();
    //String url="https://55izr0k3b7.execute-api.us-east-1.amazonaws.com/test/"+userID+"/messages/"+messageID;
    //String url="https://2bj29vv7f3.execute-api.us-east-1.amazonaws.com/testing/"+userID+"/messages/";
     String url= "http://localhost:8080/"+ userID + "/messages";
    try {
    	
        HttpGet httpget = new HttpGet(url);
      //  httpget.addHeader("x-api-key", "F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj");
        HttpDelete httpdelete;
       

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) //throws ClientProtocolException, IOException 
            {
                int status = response.getStatusLine().getStatusCode();
                try{
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } 
                else {
                    return("Unexpected response status");
                }
                }
                catch(Exception e){
                	return e.getMessage();
                }
            }

        };
        //Execute the get method
        String responseBody = httpclient.execute(httpget, responseHandler);
        if(responseBody=="Unexpected response status"){
        	//System.out.println(responseBody);
        	return myEvents;
        }
        
        JSONObject myObject = new JSONObject(responseBody);
        final JSONArray MessageArray = myObject.getJSONArray("messages");
        //System.out.println("no of messages="+MessageArray.length());
        for (int i = 0; i < MessageArray.length(); i++) {
        	String msg_type="";
        	JSONObject fullBody=MessageArray.getJSONObject(i);
        //	System.out.println(fullBody);
        	msg_type=getMessageType(fullBody);
        	//System.out.println(msg_type);
        	//Create and initialize events based on message type
        	Event e=getEvent(msg_type,fullBody);
        	//System.out.println(e.getEventType());
        	if(e!=null)
        		myEvents.add(e);
        	String messageId=fullBody.getString("messageId");
        	
        	//delete messages once events are added
        	httpdelete = new HttpDelete(url+messageId);
      	  	//httpdelete.addHeader("x-api-key", "F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj");
      	  	
      	  	//Delete the message once read
      	    httpclient.execute(httpdelete, responseHandler);
        		
        }
        return myEvents;
    }
    catch(Exception e){
    	System.out.println(e.getMessage());
    }
    finally {
        try {
        	
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    return myEvents;
}

public static String getMessageType(JSONObject fullBody){
	String msg_type="";
	try {
		//JSONObject message=fullBody.getJSONObject("messageType");
		//if(!message.isNull("msg_type"))
       	 msg_type=fullBody.getString("messageType");
       	 msg_type=msg_type.toLowerCase();
       	 return msg_type;
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
	return msg_type;
}

public static Event getEvent(String msg_type, JSONObject fullBody){
	
	if(msg_type.equals("fire")){
    	FireEvent fire = new FireEvent();
    	//System.out.println("fire detected");
    	if(fire.init(fullBody.toString())){
    		//System.out.print("sucess");
    		return fire;
    	}
    	return null;
    	
    }	
    else if(msg_type.equals("intruder")){
    	IntruderEvent intruder = new IntruderEvent();
    	if(intruder.init(fullBody.toString())){
    		return intruder;
    	}
    	return null;
    	
    }
    else if(msg_type.equals("water leak")){
    	WaterLeakEvent waterleak = new WaterLeakEvent();
    	if(waterleak.init(fullBody.toString())){
    		return waterleak ;
    	}
    	return null;
    	
    }
    else if(msg_type.equals("move robot")){
    	MoveRobotEvent moverobot = new MoveRobotEvent();
    	if(moverobot.init(fullBody.toString())){
    		return moverobot;
    	}
    	return null;
    	
    }
	return null;
	
}












}