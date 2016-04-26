package edu.vt.ece5574.sim;

import java.awt.Color;

import javax.swing.JFrame;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.agents.TempSensor;
import edu.vt.ece5574.agents.User;
import edu.vt.ece5574.agents.WaterLeakSensor;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.field.grid.IntGrid2D;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;

public class SimulationWithUI extends GUIState
{
	 public Display2D display;
	 public JFrame displayFrame;
	 FastValueGridPortrayal2D buildingPortrayal = new FastValueGridPortrayal2D("building", true);
	 FastValueGridPortrayal2D obstaclePortrayal = new FastValueGridPortrayal2D("Obstacles", true);
	 //FastValueGridPortrayal2D wallPortrayal = new FastValueGridPortrayal2D("Walls", true);
	 //FastValueGridPortrayal2D doorPortrayal = new FastValueGridPortrayal2D("Doors", true);
	 SparseGridPortrayal2D robotPortrayal = new SparseGridPortrayal2D();
	 //SparseGridPortrayal2D userPortrayal = new SparseGridPortrayal2D();
	 //SparseGridPortrayal2D sensorPortrayal = new SparseGridPortrayal2D();*/

	public SimulationWithUI()
	{
		super(new Simulation(System.currentTimeMillis()));
	}

	public SimulationWithUI(SimState state)
	{
		super(state);
	}

	public static String getName()
	{
		return "Smart Building Simulation 1.0.0";
	}


	/*  This is how we can start the simulation: Just "java SimulationWithUI"
		The main method just creates a controller, which by default is the Mason console.
	*/
	public static void main(String[] args)
	{
		new SimulationWithUI().createController();
	}


	 public void setupPortrayals()
     {
		 Simulation sim = (Simulation)state;
		 Building building = (Building)sim.getAgentByID("0");
		 Robot robot0 = building.createRobot(); //robot0 is not being used here. but we may find use for it.
		 Robot robot1 = building.createRobot();
		 TempSensor tempsensor = (TempSensor)building.createSensor("temperature",20,3);
		 WaterLeakSensor waterSensor = (WaterLeakSensor)building.createSensor("waterleak", 15, 4);
		 User user0 = building.createUser();
		//movement gives errors when 2 robots are added.

     // tell the portrayals what to portray and how to portray them
		 buildingPortrayal.setField(building.getTileMap());
		 buildingPortrayal.setMap(new sim.util.gui.SimpleColorMap(
	             0,
	             1,
	             new Color(0,0,0,0),
	             new Color(255,0,0,255) ));

		 obstaclePortrayal.setField(building.getObstacles());
		 obstaclePortrayal.setMap(new sim.util.gui.SimpleColorMap(
				  0,
	                5,
	                new Color(0,0,0,0),
	                new Color(128,64,64,255) ));

		 robotPortrayal.setField(building.getAgents());

		/* doorPortrayal.setField(building.getDoors());
		 doorPortrayal.setMap(new sim.util.gui.SimpleColorMap(
	                0,
	                20,
	                // home pheromones are beneath all, just make them opaque
	                //Color.white,
	                new Color(0,0,0,0),
	                new Color(128,64,64,255) ) ); */


     // reschedule the displayer
     display.reset();

     // redraw the display
     display.repaint();
     }

	 public void start()
     {
     super.start();  // set up everything but replacing the display
     // set up our portrayals
     setupPortrayals();
     }

	 public void load(SimState state)
     {
     super.load(state);
     // we now have new grids.  Set up the portrayals to reflect that
     setupPortrayals();
     }

	public void init(Controller c)
    {
    super.init(c);

    // Make the Display2D.  We'll have it display stuff later.
    display = new Display2D(400,400,this); // at 400x400, we've got 4x4 per array position
    displayFrame = display.createFrame();
    c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
    displayFrame.setVisible(true);

    // attach the portrayals from bottom to top

    display.attach(obstaclePortrayal,"Obstacles");
    display.attach(buildingPortrayal,"Building");
    display.attach(robotPortrayal,"Robot");
  //  display.attach(sensorPortrayal,"Sensors");

    // specify the backdrop color  -- what gets painted behind the displays
    display.setBackdrop(Color.white);
    }

	  public void quit()
      {
      super.quit();

      // disposing the displayFrame automatically calls quit() on the display,
      // so we don't need to do so ourselves here.
      if (displayFrame!=null) displayFrame.dispose();
      displayFrame = null;  // let gc
      display = null;       // let gc
      }
}
