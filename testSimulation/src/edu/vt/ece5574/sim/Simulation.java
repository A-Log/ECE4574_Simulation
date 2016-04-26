package edu.vt.ece5574.sim;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Map;

import edu.vt.ece5574.agents.Agent;
import edu.vt.ece5574.agents.Building;
import sim.engine.*;

/**
 * The root of the simulation.  This is where things get started and the magic happens.
 * @author David Kindel
 *
 */
public class Simulation extends SimState {

	public Configuration config;

	private static final long serialVersionUID = 1;
    private int numRobots;
    private int numBuildings;

    private static final String password = "fill_in_password";

    public HashMap<String, Agent> agents; //map the agent id to the agent itself

    public boolean connectStorage;
    public StorageAPI storage;
    public PushAPICaller pushOutgoing;
    public ReadNotifications pushIncoming;

    public Simulation(long seed){
    	super(seed); //needs to be first line, can't just set seed here
    	boolean debug ;

    	config = new Configuration();
    	try {
			config.load(System.getProperty("user.dir") + File.separator+"configuration.xml");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	if(config != null){
    		numRobots = Integer.parseInt(config.getProp("numRobots"));
    		debug = Boolean.parseBoolean(config.getProp("debug")); //will be read in from config.  If it's a debug, we'll set seed manually
    		seed = Long.parseLong(config.getProp("seedValue"));
    		numBuildings = Integer.parseInt(config.getProp("numBuildings"));
    		connectStorage = Boolean.parseBoolean(config.getProp("connectStorage")); // ConnectStorage
    		System.out.println("Seed="+ seed + " Number Robots:" + numRobots);

    	}

    	else{
    		numRobots = 0;
    		debug = false;
    		seed = 5;
    		numBuildings = 0;
    	}

    	//



        if(debug){
        	if(seed == 0){
        		System.err.println("Seed cannot be set to 0. Running sim with default seed.");
        	}
        	else{
        		setSeed(seed);
            	System.out.println("seed modified to: " + seed());
            	System.out.println("Ignore other message noting the job number and seed value.");
        	}
        }

        agents = new HashMap<String, Agent>();
        storage = new StorageAPI();
        pushOutgoing = new PushAPICaller();
        pushIncoming = new ReadNotifications();
        // add robots and building statically

          for(int i = 0; i < numBuildings; i++){
         	agents.put(new Integer(i).toString(), new Building(new Integer(i).toString(),this));
         	Building building= new Building(new Integer(i).toString(),this);
         	agents.put(new Integer(i).toString(), building);
        	 for(int j= 1; j<= numRobots;j++){

                building.createRobot();
              	System.out.println("Robot created with id:" + Integer.toString(i)+"@"+Integer.toString(j));
               }
          }



    }

	public void run(String[] args) {

		doLoop(Simulation.class, args);
		System.exit(0);
	}

	public void end(){
		this.finish();
	}


    public void start() {
        super.start();  // very important!  This resets and cleans out the Schedule.

        //clear the room of previous actors
        /*room.clear();
        int numBuildings = Integer.parseInt(config.getProp("numBuildings"));

        for(int i = 0; i < numBuildings; i++){
        	agents.put(new Integer(i).toString(), new Building(new Integer(i).toString()));
        }*/
        pushIncoming.setAccountDetails("simulation.ece5574", password);
        schedule.scheduleRepeating(pushIncoming);
      //  public HashMap<String, Agent> agents;
        Iterator it = agents.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            schedule.scheduleRepeating((Agent)pair.getValue());
        }


    }


    /**
     * Add an agent into the environment. It gets placed into the global agent list if
     * there wasn't an agent with that ID previously.  If there was, false is returned.
     *
     * This will certainly be called by a building agent but not necessarily by anything else.
     * The building will receive an "add robot" event and will then have to call this method.
     *
     * This may also be used in testing
     *
     * This function should never be called if the building hasn't already been added
     * unless you're adding a building itself.
     * Furthermore, every agent must be assigned to a building prior to calling this.
     *
     * @param agent The agent to place into the simulation
     * @return True if the agent is placed in with a unique ID.  False if it couldn't be added
     * due to a bad ID or no building ID set
     */
    public boolean addAgent(Agent agent){
    	if(agent == null){
    		return false;
    	}
    	System.out.println();
    	if(agent.getBuildingID() == null || agent.getID() == null ||
    			(agents.get(agent.getBuildingID()) == null
    			&& agent.getClass() != Building.class)){
			return false;
    	}

    	if(agents.get(agent.getID()) != null){
    		return false;
    	}
    	agents.put(agent.getID(), agent);
    	//Moving this to the create<agent> method in building, since for some reason,
    	// the first agent created does not move when adding here-will check later
    	//schedule.scheduleRepeating(agent);
    	return true;
    }

    /**
     * Remove the agent from the simulation environment by removing it from the global agent list.
     *
     * @param agent The agent to remove
     * @return Returns the agent that was removed if it was in the list, null if it wasn't.
     */
    public Agent removeAgent(Agent agent){
    	if(agent == null){
    		return null;
    	}
    	return agents.remove(agent.getID());
    }

    /**
     * Get the agent by the provided string ID
     * @param id the id of the agent
     * @return the agent object itself or null if not found
     */
	public Agent getAgentByID(String id) {
		return agents.get(id);
	}

	/**
	 * Notifies the agent that a message is waiting on the server side.
	 * @param id The ID of the agent for a waiting message
	 * @return True if the agent is found and its message waiting flag set
	 */
	public boolean agentPushReceived(String id){
		Agent agent = agents.get(id);
		if(agent == null){
			return false;
		}
		agent.setMessageWaiting();
		return true;
	}
}
