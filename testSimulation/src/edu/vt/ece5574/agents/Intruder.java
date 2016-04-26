package edu.vt.ece5574.agents;

import edu.vt.ece5574.agents.ClockTime.TIMEPERIOD;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

public class Intruder extends User {

	private static final long serialVersionUID = 1L;	

	public Intruder(Simulation sim, String id, String buildingID, boolean bAppUser, int i, int j) {		
		super(sim,id,buildingID,true,i,j);
	}

	@Override
	public boolean isTimeForActivity(SimState state)
	{
		Simulation simState = (Simulation)state;	
		Building bld = (Building)simState.getAgentByID(buildingID);
		//Start an event only during the first quarter of an hour
		if (bld.getBuildingTime().getMinutes() < 15 && bld.getBuildingTime().getTimePeriod()==TIMEPERIOD.NIGHT)
		{		 
			return true;
		}			
		else 
		{
			return false;
		}
	}
}

