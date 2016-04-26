package edu.vt.ece5574.agents;

public class Item {
	
	public static enum Obstacle {
	    obstacle, emergency, wall, door 
	}
	
	private String buildingID;
	public Obstacle type;
	
	public Item(String buildingID, Obstacle type){
		this.buildingID = buildingID;
		this.type = type;
	}

	public String getBuildingId() {
		return buildingID;
	}


}
