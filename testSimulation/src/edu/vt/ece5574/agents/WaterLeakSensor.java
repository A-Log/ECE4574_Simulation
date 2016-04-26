package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.roomconditions.WaterLevel;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

// Class for sensors. - Author - Ameya Khandekar

// Edited by Stephanie Marin, 4/18/16

public class WaterLeakSensor extends Sensor {

	private static final long serialVersionUID = 1;
    int x,y;
    Building bld;
    private WaterLevel waterLevel;
	private boolean waterLeakStatus;

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public WaterLeakSensor(String id_, String buildingID_)
	{
		super(id_,buildingID_,"waterleak");
	}

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
    public WaterLeakSensor(String id_, String buildingID_,SimState state_, int x_, int y_)
    {
        super(id_,buildingID_,"waterleak");

        Simulation state = (Simulation)state_;
        x = x_;
        y = y_;
        bld = (Building)state.getAgentByID(buildingID);
        waterLevel = bld.getRoomWaterLevelById(bld.getRoomId(x,y));
    }

	public boolean getWaterLeakStatus()
	{
	    waterLevel = bld.getRoomWaterLevelById(bld.getRoomId(x,y));

	    if(waterLeakStatus)
	        return true;

	    if(waterLevel.getWaterLevel() < 40) {

	        //System.out.println("..........." + waterLevel.getWaterLevel());
	        waterLeakStatus = false;

	    } else
	        waterLeakStatus = true;

		return waterLeakStatus;
	}

	@Override
	public void handleSensorEvents()
	{
		while(bld.events.size()!=0)
		{
			Event currentEvent = bld.events.removeFirst();

			if(currentEvent instanceof WaterLeakEvent) {
				WaterLeakEvent waterleakevent = (WaterLeakEvent)currentEvent;

				if(waterleakevent.is_WaterLeakActive()) {
					//send sensor data = "WaterLeak Active" to storage API.
					waterLeakStatus = true;
				}
				else{
					//send sensor data = "WaterLeak de-activated" to storage API.
					waterLeakStatus = false;
				}
			}
		}
	}

	@Override
	public void step(SimState state)
	{
		super.step(state);

        waterLevel = bld.getRoomWaterLevelById(bld.getRoomId(x,y));

        if(waterLevel.getWaterLevel() < 40)
            waterLeakStatus = true;
        else
            waterLeakStatus = false;

        if(!events.isEmpty()){

            this.handleSensorEvents();
        }
	}


}
