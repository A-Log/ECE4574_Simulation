package edu.vt.ece5574.events;

/**
 * The class for all UserMessageEvents with attributes specific to these types
 * @author David Kindel
 */
public class UserMessageEvent extends Event {

	public UserMessageEvent() {
		
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}
}
