package edu.vt.ece5574.events;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class for all FireEvents with attributes specific to these types
 * @author David Kindel
 *
 */
/**
 * Edit: Added simple flags to allow starting and stopping an event to allow sensor/robot interaction
 * @author Deepak Rajendrakumaran
 * @co-author Ameya Khandekar
 */
public class FireEvent extends Event {

	private AtomicBoolean fireActive;
	
	public FireEvent() {
		fireActive = new AtomicBoolean(true);
	}
	
	/**
	 * used by sensor to check if even still active
	 */
	public boolean is_fireActive() {
		return fireActive.get();
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void turn_fireOff() {
		 fireActive.set(false); 
	}

	
	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}

}
