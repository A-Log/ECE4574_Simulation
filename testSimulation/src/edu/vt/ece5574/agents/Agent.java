package edu.vt.ece5574.agents;

import java.awt.Color;
//import java.awt.Color;
//import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.sim.PushAPICaller;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.simple.OvalPortrayal2D;


/**
 * Abstract class for agents to inherit.  These will have base
 * implementations that are generic across all agents
 *
 * @author David Kindel
 */
public abstract class Agent extends OvalPortrayal2D implements Steppable {

	private static final long serialVersionUID = 1;

	protected String id = "abcd";
	protected String buildingID = "0"; //a building will have the same ID as building ID

	protected LinkedList<Event> events;

	protected boolean messageWaiting = false;

	private void init(String id_, String buildingID_){
		id = id_;
		buildingID = buildingID_;
		events = new LinkedList<Event>();
	}

	public Agent(String id_, String buildingID_){
		super();
		init(id_, buildingID_);
	}

	public Agent(double scale, String id_, String buildingID_){
		super(scale);
		init(id_, buildingID_);
	}

	public Agent(double scale, boolean filled, String id_, String buildingID_){
		super(scale, filled);
		init(id_, buildingID_);
	}

	public Agent(Color c, String id_, String buildingID_){
		super(c);
		init(id_, buildingID_);
	}

	public Agent(Color c, boolean filled, String id_, String buildingID_){
		super(c, filled);
		init(id_, buildingID_);
	}

	public Agent(Color c, double scale, String id_, String buildingID_){
		super(c, scale);
		init(id_, buildingID_);
	}

	public Agent(Color c, double scale, boolean filled, String id_, String buildingID_){
		super(c, scale, filled);
		init(id_, buildingID_);
	}

	public void addEvent(Event event) {
		if(event != null){
			events.add(event);
		}
	}

	public LinkedList<Event> getEventList(){
		return events;
	}

	public String getID(){
		return id;
	}

	public String getBuildingID(){
		return buildingID;
	}

	/**
	 * Sets the message waiting flag.  No one else may unset this flag other than the
	 * agent itself so there is no method to unset it.
	 */
	public void setMessageWaiting(){
		messageWaiting = true;
	}

	public boolean getMessageWaiting(){
		return messageWaiting;
	}

	public void step(SimState state) {
		ArrayList<Event> myEvents =checkPushNotification();
		for(Event e:myEvents){
		if(e!=null)
		this.addEvent(e);
		}

	}

	public ArrayList<Event> checkPushNotification(){
		ArrayList<Event> myEvents=new ArrayList<Event>();
		//super.paint=Color.blue; //FIXME: Commenting this because all agents color are modified to blue.
		if(messageWaiting){
        super.paint=Color.red;
		myEvents=PushAPICaller.callPushSystemAPI(id);
		for(int i=0;i<myEvents.size();i++){
			System.out.println("Event:"+myEvents.get(i).getEventType());
			System.out.println("AgentID:"+ this.id);
			System.out.println("Event in building "+myEvents.get(i).getBuilding()+" Room:" + myEvents.get(i).getRoom());
		}
		messageWaiting=false;
		}

		return myEvents;

	}

}
