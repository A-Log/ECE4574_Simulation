package edu.vt.ece5574.events;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * The abstract class  for all events to inherit
 * @author David Kindel
 * @updated by Saket
 *
 */
public abstract class Event {


	protected String eventID = "-1"; 
	protected long room = -1;
	protected int floor = -1;
	protected int x_pos = -1;
	protected int y_pos = -1;
	protected int severity = -1; 
	protected String type = null;
	protected String action = null;
	protected String message = null;
	protected String building = "-1";
	protected String newAgentID = "-1";
	
	public Event(){

	}
	


	protected boolean setBaseDetails(String details){
		JSONParser parser = new JSONParser();
		try{
			Object obj = parser.parse(details);
			JSONObject json = (JSONObject) obj;

			Object attr = json.get("messageId");
			if(attr == null){
				System.out.println("Missing required event/message ID.");
				return false;
			}
			eventID = (String) attr;

			//json = (JSONObject) json.get("message");
			
			attr = json.get("messageType");
			if(attr == null){
				System.out.println("Missing required type.");
				return false;
			}
			type = ((String) attr).toLowerCase();
			
			
			JSONObject body = (JSONObject) json.get("body");
			attr = body.get("building");
			if(attr != null){
				building = (String) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status") 
					|| type.equals("new robot") || type.equals("new sensor") 
					|| type.equals("new user"))
			{
				System.out.println("Missing required building ID for type " + type);
				//return false;
			}

			attr = body.get("room");
			if(attr != null){
				room = (int) (long) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status") 
					|| type.equals("new robot") || type.equals("new sensor") 
					|| type.equals("new user"))
			{
				System.out.println("Missing required room number for type " + type);
				//return false;
			}
			
			attr = body.get("floor");
			if(attr != null){
				floor = (int) (long) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status") 
					|| type.equals("new robot") || type.equals("new sensor") 
					|| type.equals("new user"))
			{
				System.out.println("Missing required floor number for type " + type);
				//return false;
			}
			
			attr = body.get("xpos");
			if(attr != null){
				x_pos = (int) (long) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status") 
					|| type.equals("new robot") || type.equals("new sensor") 
					|| type.equals("new user"))
			{
				System.out.println("Missing required x position for type " + type);
				//return false;
			}
			

			attr = body.get("ypos");
			if(attr != null){
				y_pos = (int) (long) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status") 
					|| type.equals("new robot") || type.equals("new sensor") 
					|| type.equals("new user"))
			{
				System.out.println("Missing required y position for type " + type);
				//return false;
			}
			
			attr = body.get("severity");
			if(attr != null){
				severity = (int) (long) attr;
			}
			else if(type.equals("fire")	|| type.equals("water leak") 
					|| type.equals("gas leak") || type.equals("intruder") 
					|| type.equals("move robot") || type.equals("update status"))
			{
				System.out.println("Missing required severity level for type " + type);
				//return false;
			}
			
			
			attr = body.get("action");
			if(attr != null){
				action = ((String) attr).toLowerCase();
			}

			attr = body.get("message");
			if(attr != null){
				message = (String) attr;
			}
			else if(type.equals("notification"))
			{
				System.out.println("Missing required message for type " + type);
				//return false;
			}
			
			
		}catch (ParseException e){
			System.out.println(e);
			System.out.println("found");
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the type of emergency this is.  Water leak, fire, intruder, etc.
	 * @return The emergency type by string
	 */
	public String getEventType(){
		return type;
	}

	/**
	 * @return the room
	 */
	public long getRoom() {
		return room;
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return the x_pos
	 */
	public int getX_pos() {
		return x_pos;
	}

	/**
	 * @return the y_pos
	 */
	public int getY_pos() {
		return y_pos;
	}

	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * @return the building ID
	 */
	public String getBuilding() {
		return building;
	}
	/**
	 * Returns the event ID
	 * @return The event ID
	 */
	public String getEventID(){
		return eventID;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * Just get the event type of the incoming message to be able to build the event
	 * itself correctly. 
	 * 
	 * @param details The string of the JSON returned from the push service
	 * @return The string of the key "msg_type" in the message or null if it could not be found 
	 */
	public static String getEventType(String details){
		JSONParser parser = new JSONParser();
		try{
			Object obj = parser.parse(details);
			if(obj == null) return null;
			JSONObject json = (JSONObject) obj;


			json = (JSONObject) json.get("message");
			if(json == null) return null;
			
			Object attr = json.get("msg_type");
			if(attr == null){
				System.out.println("Missing required type.");
				return null;
			}
			else{
				return (String) attr;
			}

		}catch (ParseException e){
			System.out.println("position: " + e.getPosition());
			System.out.println(e);
		}
		return null;
	}
}
