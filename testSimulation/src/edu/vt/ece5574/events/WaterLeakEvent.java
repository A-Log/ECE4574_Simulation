package edu.vt.ece5574.events;

/**
 * The class for all WaterLeakEvents with attributes specific to these types
 * @author David Kindel
 *
 */
/**
 * Edit: Added simple flags to allow starting and stopping an event to allow sensor/robot interaction
 * @author Deepak Rajendrakumaran
 */
public class WaterLeakEvent extends Event{
	
	private boolean WaterLeakActive;
	
	public WaterLeakEvent() {
		WaterLeakActive = true;
	}
	
	/**
	 * used by sensor to check if even still active
	 */
	public boolean is_WaterLeakActive() {
		return WaterLeakActive;
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void turn_LeakOff() {
		WaterLeakActive = false;
	}
	

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}


}
