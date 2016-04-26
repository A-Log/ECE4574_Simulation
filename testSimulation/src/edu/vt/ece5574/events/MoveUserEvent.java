package edu.vt.ece5574.events;

public class MoveUserEvent extends Event{
	private boolean reachedLoc;

	public MoveUserEvent() {
		// TODO Auto-generated constructor stub
		reachedLoc = false;
	}	
	
	/**
	 * used to check if even still active
	 */
	public boolean hasUserReachedLoc() {
		return reachedLoc;
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void UserReachedLoc() {
		reachedLoc = true;
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}

}


