package edu.vt.ece5574.events;

/**
 * The class for all MoveRobotEvents with attributes specific to these types
 * @author David Kindel
 *
 */
/**
 * Edit: Added simple flags to allow starting and stopping an event to allow sensor/robot interaction
 * @author Deepak Rajendrakumaran
 */
public class MoveRobotEvent extends Event{

	
	private boolean reachedLoc;
	
	public MoveRobotEvent() {
		reachedLoc = false;
	}
	
	/**
	 * used to check if even still active
	 */
	public boolean hasRobotReachedLoc() {
		return reachedLoc;
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void RobotReachedLoc() {
		reachedLoc = true;
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}
}
