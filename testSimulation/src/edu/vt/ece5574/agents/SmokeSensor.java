package edu.vt.ece5574.agents;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import sim.engine.SimState;
import edu.vt.ece5574.roomconditions.Smoke;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
//Class for temperature sensors. - Author - Matthew Grier

public class SmokeSensor extends Sensor {
	private static final long serialVersionUID = 1;
 	int x,y;
 	String bld;
	private int fireStatus;
	private long room;

	/**
	 * This creates a new Smoke Sensor object
	 * Preconditions: None
	 * @param id_
	 * @param buildingID_
	 * @param state_
	 * @param x_
	 * @param y_
	 * Postconditions: None
	 * Invariants: None
	 */
	public SmokeSensor(String id_, String buildingID_,SimState state_, int x_, int y_){
		super(id_,buildingID_,"smoke");
		Simulation state = (Simulation)state_;
		x = x_;
		y = y_;
		room = 0;
	    bld = buildingID;
	}
	
	/**
	 * This returns the X coordinate of the sensor
	 * Precondition: Smoke sensor must have an X coordinate
	 * Postcondition: None
	 * Invariant: Smoke sensor must always have an X coordinate
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * This sets the X coordinate of the sensor
	 * Precondition: None
	 * Postcondition: Smoke sensor will have an X coordinate
	 * Invariant: Smoke sensor must always have an X coordinate
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * This returns the Y coordinate of the sensor
	 * Precondition: Smoke sensor must have a Y coordinate
	 * Postcondition: None
	 * Invariant: Smoke sensor must always have a Y coordinate
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * This sets the Y coordinate of the sensor
	 * Precondition: None
	 * Postcondition: Smoke sensor will have a Y coordinate
	 * Invariant: Smoke sensor must always have a Y coordinate
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * This returns the building ID that the sensor is in
	 * Precondition: Smoke sensor must have be in a building
	 * Postcondition: None
	 * Invariant: Smoke sensor must always be in a building
	 * @return
	 */
	public String getBld() {
		return bld;
	}

	/**
	 * This sets the building ID that the sensor is in
	 * Precondition: None
	 * Postcondition: Smoke sensor will have be in a building
	 * Invariant: Smoke sensor must always have be in a building
	 * @param bld
	 */
	public void setBld(String bld) {
		this.bld = bld;
	}
	
	/**
	 * This returns the fire status of the smoke sensor
	 * Precondition: Smoke sensor must have have a fire status
	 * Postcondition: None
	 * Invariant: Smoke sensor must always have a fire status
	 * @return
	 */
	public int getFireStatus() {
		return fireStatus;
	}

	/**
	 * This sets the fire status of the sensor
	 * Precondition: None
	 * Postcondition: Smoke sensor will have have a fire status
	 * Invariant: Smoke sensor must always have a fire status
	 * @param fireStatus
	 */
	public void setFireStatus(int fireStatus) {
		this.fireStatus = fireStatus;
	}

	/**
	 * This returns the room that the sensor is in
	 * Precondition: Smoke sensor must be in a room
	 * Postcondition: None
	 * Invariant: Smoke sensor must always be in a room
	 * @return
	 */
	public long getRoom() {
		return room;
	}

	/**
	 * This sets the room that the sensor is in
	 * Precondition: None
	 * Postcondition: Smoke sensor will have be in a room
	 * Invariant: Smoke sensor must always have be in a room
	 * @param room
	 */
	public void setRoom(long room) {
		this.room = room;
	}

	/**
	 * Precondition: None
	 * Postcondition: None
	 * Invariant: There must be some sort of event to respond too
	 * @param currentEvent
	 */
	public void handleEvent(Event currentEvent){
		//only respond in the case of a fire event
		if(currentEvent instanceof FireEvent && currentEvent.getRoom() == room 
				&& currentEvent.getBuilding().equals(bld) == true){
			FireEvent fireevent = (FireEvent)currentEvent;
			
			//if the fire event is active, detect smoke
			if(fireevent.is_fireActive()){
				fireStatus = 1;	
			} else{	//if not active, no smoke
				fireStatus = 0;
			}	
			
		} else {	//if not a fire event or not in correct room
			fireStatus = 0;
		}
	}
}
