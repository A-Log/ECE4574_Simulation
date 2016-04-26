package edu.vt.ece5574.dummyclasses;

import edu.vt.ece5574.agents.Agent;
import sim.engine.SimState;

/**
 * A building that has just basic properties that we might use. 
 * @author David Kindel
 *
 */
public class DummyBuilding extends Agent{
	private static final long serialVersionUID = 1;
	private int numRooms;
	private int numFloors;
	
	public DummyBuilding(){
		super("0", "0");
	}
	


	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * @return the numFloors
	 */
	public int getNumFloors() {
		return numFloors;
	}

	/**
	 * @param numFloors the numFloors to set
	 */
	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}



	/**
	 * @return the numRooms
	 */
	public int getNumRooms() {
		return numRooms;
	}



	/**
	 * @param numRooms the numRooms to set
	 */
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}


}
