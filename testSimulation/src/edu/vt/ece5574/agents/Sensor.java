package edu.vt.ece5574.agents;


import sim.engine.SimState;

//Class for sensors. - Author - Ameya Khandekar

public class Sensor extends Agent {
	



	private static final long serialVersionUID = 1;
	private String sensorType;  

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public Sensor(String id_, String buildingID_, String sensorType_){
		super(id_,buildingID_);
		sensorType = sensorType_;	
	}


	public String getSensorType() { return sensorType; }

	public void handleSensorEvents(){
		//do nothing in base class
	}




	@Override
	public void step(SimState state) {
		
		super.step(state);
	}


}
