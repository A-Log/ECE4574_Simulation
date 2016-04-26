package edu.vt.ece5574.events;


/**
 * The class for all IntruderEvents with attributes specific to these types
 * @author David Kindel
 *
 */
/**
 * Edit: Added simple flags to allow starting and stopping an event to allow sensor/robot interaction
 * @author Deepak Rajendrakumaran
 */
public class IntruderEvent extends Event {

	
	private boolean intruderActive;
	
	public IntruderEvent() {
		intruderActive = true;
	}
	/**
	 * used by sensor to check if even still active
	 */
	public boolean is_intruderActive() {
		return intruderActive;
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void dealWithIntruder() {
		intruderActive = false;
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}
}
