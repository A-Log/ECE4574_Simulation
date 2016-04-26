package edu.vt.ece5574.main;

import sim.util.Int2D;

import edu.vt.ece5574.sim.Simulation;
import edu.vt.ece5574.agents.Building;
public class Brain {

    private int isSimulatorRunning;
	private Simulation sim;
    private Building building;

	public void init(){
		System.out.println("Initializing the simulator");


		sim = new Simulation(0);
		building = new Building("0",sim); // creates a building with default layout
	}


	public void start(){

		System.out.println("Starting the Simulation Thread");
		String[] temp = new String[0];
		this.isSimulatorRunning=1;
		sim.run(temp);
		System.out.println("Testing building construction");
		building.displayLayout();
		Int2D position = building.genStartPos();
		System.out.println("Starting position generated is ");
		System.out.println("X = "+position.getX()+", Y = "+position.getY());

	}

	public void stop(){

		System.out.println("Stopping the Simulation Thread");

		this.isSimulatorRunning =0;
		sim.end();

	}


}
