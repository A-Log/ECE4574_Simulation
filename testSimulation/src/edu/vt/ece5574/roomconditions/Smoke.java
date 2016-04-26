package edu.vt.ece5574.roomconditions;

import java.util.concurrent.atomic.AtomicBoolean;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Author :- Ameya Khandekar

// For now, the smoke is a random boolean. Since it plays the role of a secondary indicator of fire, this should work fine for now.


public class Smoke  {

	protected String buildingID = "0"; //a building will have the same ID as building ID
	protected String roomID;

    private AtomicBoolean smk;  
    private AtomicBoolean fireflag;
    private AtomicBoolean robotflag;
	//protected LinkedList<Event> events;
	private Simulation state;
	
	public Smoke(SimState state_){
		state = (Simulation)state_;
		smk = new AtomicBoolean(false);
/*		fireflag = new AtomicBoolean(false);
		robotflag = new AtomicBoolean(false);*/
	}
	

	public boolean getSmoke(){

		return smk.get();
	}

	public void defSmokeChange(){
		//default Temperature change
		boolean val ;

		int randomInt;
		randomInt = state.random.nextInt() ;
		if(randomInt%2 == 1){
			val = true;
		} 
		else{
			val = false;
		}

		smk.set(val);

	}	

/*	public void fireSmokeChange(){

	}

	public void robotSmokeChange(){

	}
*/
}
