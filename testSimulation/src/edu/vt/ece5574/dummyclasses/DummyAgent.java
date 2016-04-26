package edu.vt.ece5574.dummyclasses;

import edu.vt.ece5574.agents.Agent;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.*;

/**
 * A class to simply inherit the abstract agent to be instatiated.  This agent is awfully dumb
 * @author David Kindel
 *
 */
public class DummyAgent extends Agent {

	private static final long serialVersionUID = 1;
	
	public DummyAgent(String id, String building){
		super(id, building);
	}
	
	@Override
	public void step(SimState state_) {
		Simulation state = (Simulation) state_;
		handleEvents(state);
	}

	public void handleEvents(Simulation state){
		if(!events.isEmpty()){
			for(int i = 0; i < events.size(); i++){
				if(events.get(i).getEventType().toLowerCase().equals("fire")){
					FireEvent fire = (FireEvent) events.get(i);
					System.out.println("Handling fire id " + fire.getEventID());
				}
				else if(events.get(i).getEventType().toLowerCase().equals("water leak")){
					WaterLeakEvent leak = (WaterLeakEvent) events.get(i);
					System.out.println("Handling water leak id " + leak.getEventID());
				}
			}
		}
	}
}
