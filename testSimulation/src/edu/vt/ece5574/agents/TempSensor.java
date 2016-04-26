package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import sim.engine.SimState;
import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
//Class for temperature sensors. - Author - Ameya Khandekar

/*
Status :-
- at each step send temperatureValue to cloud storage.
- handle Sensor events - works as though there's an event list addressed to this sensor. But right now it supports just
fire event
- Sensor ON-OFF Events need to be implemented.
*/

public class TempSensor extends Sensor {

	private static final long serialVersionUID = 1;
 	int x,y;
 	Building bld;
 	Temperature temp;
//	private boolean fireStatus;

 	public TempSensor(String id_, String buildingID_)
 	{
 		//dummy constructor kept to avoid conflicts for now.
		super(id_,buildingID_,"temperature");

 	}

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public TempSensor(String id_, String buildingID_,SimState state_, int x_, int y_)
	{
		super(id_,buildingID_,"temperature");
		Simulation state = (Simulation)state_;
		x = x_;
		y = y_;
	    bld = (Building)state.getAgentByID(buildingID);
	    temp = bld.getRoomTempById(bld.getRoomId(x,y));
	}

/*	public boolean getFireStatus(){
		return fireStatus;
	}
*/
	@Override
	public void handleSensorEvents(){
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
			if(currentEvent instanceof FireEvent){
				FireEvent fireevent = (FireEvent)currentEvent;
				// UPDATE :- MOVING FIRE TEMPERATURE CHANGE TO THE BUILDING
				/*if(fireevent.is_fireActive()){
					temp.fireTempChange(5); // using a default severity of 5.
					//will have to request server logic to implement way of sending severity value.

				}*/
/*				else{
					//send sensor data = "Fire de-activated" to storage API.
					//fireStatus = false;
				}
				*/
			}


		}
	}

	public int getTempValue()
	{
	    return temp.getTemperature();
	}
//send Temperature Data in each timestep to storage API.

	@Override
	public void step(SimState state)
	{
		super.step(state);

		//TO_DO
		//SEND temperature value to cloud storage.
		int temperatureValue = temp.getTemperature();
		//

		if(!events.isEmpty()){
			handleSensorEvents();
		}
	}


}
