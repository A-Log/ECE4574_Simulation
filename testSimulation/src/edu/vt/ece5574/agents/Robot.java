package edu.vt.ece5574.agents;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.AStar;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.util.Int2D;
import sim.util.MutableInt2D;

/**
 * The logic for robot movement
 * 1-move around the building to sense changes
 * 2-React to an event 
 * @author Deepak Rajendrakumaran
 *
 */
public class Robot extends Agent {

	private static final long serialVersionUID = 1;	
    
    private MutableInt2D robot_loc;
    
    //Was used in earlier random movement algo- currently not used
    /*private Vector<MutableInt2D> lastVisitedLocs;
    private int noOfSavedLocs =0;
    private int toBeSavedLocs =0;*/
    
    private boolean handlingEvent=false;
    private int deadlockedsteps=0;
    private Event currEvent = null;
   // private List<Coordinate> route = null;
  //  private Coordinate nextPoint = null;
    
    Stack<Int2D> routePath;

	private Simulation simState;
	private enum Curr_Direction {
	    North(0), South(1), East(2), West(3), Same(4) ;
	
	    private int posVal;
	
	Curr_Direction(int posVal) {
        this.posVal = posVal;
    }
	public int getCurr_Direction() {
		  return posVal;
		 }
		}
		
		class dirComparator implements Comparator<Curr_Direction> {

		@Override
		 public int compare(Curr_Direction c1, Curr_Direction c2) {
		  return c1.getCurr_Direction() - c2.getCurr_Direction();
		 }
		}
	private Curr_Direction c_dir;
    
	/**
	 * Constructor to initialize position, id.
	 * @param rID : Robot ID
	 * @param bID : Building ID
	 * @param x_loc : x coordinate position of the robot
	 * @param y_loc : y coordinate position of the robot
	 */
	public Robot(Simulation state,String rID, String bID, int x_loc, int y_loc){
		super(Color.blue,true,rID, bID);
		simState = (Simulation)state;
		c_dir = Curr_Direction.Same;
		robot_loc = new MutableInt2D(x_loc,y_loc);
		//Was used in earlier random movement algo- currently not used
		/*lastVisitedLocs = new Vector<MutableInt2D>();
		toBeSavedLocs = 8;*/
		
	}
	
	/**
	 * Not sure if this constructor will be used but it can be for testing and sets
	 * some simple values for what is needed.
	 * @param rID
	 * @param bID
	 */
	public Robot(Simulation state,String rID, String bID){
		super(Color.blue,true,rID, bID);
		simState = (Simulation)state;
		c_dir = Curr_Direction.Same;
		robot_loc = new MutableInt2D(15,15);
	}
	
	/**
   	 * Get current robot x coordinate
   	 */
    public int getX() { return robot_loc.x; }
    
    /**
   	 * Get current robot y coordinate
   	 */
    public int getY() { return robot_loc.y;}
    
    /**
	 * Check if robot is busy dealing with an event
	 */
    public boolean isBusy() { return handlingEvent; }
    
    
  //Was used in earlier random movement algo- currently not used
	/**
	 * Logic to simulate 'normal' movement of robot in the building 
	 * @param state 
	 */
	/*public void randomMovement_old(SimState state){
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		int x_pos = robot_loc.x;
		int y_pos = robot_loc.x;
		MutableInt2D new_loc;
		LinkedList<MutableInt2D> validGridList = new LinkedList<MutableInt2D>();
		SortedSet<Integer> set = new TreeSet<Integer>(); 
		for(int dx = -1; dx < 2; dx++){
			for(int dy = -1; dy < 2; dy++){
				if((dx==0) && (dy==0))
					continue;
				int x = dx+x_pos;
				int y = dy+y_pos;
				
				//temp hack
			//	if((x>9) || (y>9))continue;
				//Hack end
				if(bld.checkStep(x,y)){
				//	System.out.println("Check Pass");
					int weight = checkIfRecentlyVisited(x,y);
					if(weight ==0){
						
						validGridList.add(new MutableInt2D(x,y));	
					}
					else{
						
						set.add(Integer.valueOf(weight));	
					}
						
				}
				
			}
		}
		
		if(!validGridList.isEmpty()){
			int nextLocIndex = state.random.nextInt(validGridList.size());
			new_loc = validGridList.get(nextLocIndex);
			
		}
		else if(set.size()!=0){
			new_loc = lastVisitedLocs.elementAt(set.last()-1);			
		}
		else{
			new_loc=robot_loc;
		}
		
		updateVisitedLocs(new_loc);
		robot_loc.x = new_loc.x;
		robot_loc.y = new_loc.y;
		bld.updateAgentPos(this,robot_loc.x, robot_loc.y);
		//simState.storage.updRobotPos(super.getID(), robot_loc.x, robot_loc.y);
				
	}*/
    
    
    /**
	 * Logic to simulate 'random' movement of robot in the building 
	 * to escape deadlock
	 * @param state 
	 */
	public void randomMovement(){
		
		Building bld = (Building)simState.getAgentByID(buildingID);
		int x_pos = robot_loc.x;
		int y_pos = robot_loc.x;
		MutableInt2D new_loc;
		LinkedList<MutableInt2D> validGridList = new LinkedList<MutableInt2D>();
		for(int dx = -1; dx < 2; dx++){
			for(int dy = -1; dy < 2; dy++){
				if((dx==0) && (dy==0))
					continue;
				int x = dx+x_pos;
				int y = dy+y_pos;
				
		
				if(bld.checkStep(x,y)){
						validGridList.add(new MutableInt2D(x,y));	
				}
			}
		}
		while(true){
			if(!validGridList.isEmpty()){
				int nextLocIndex = simState.random.nextInt(validGridList.size());
				new_loc = validGridList.get(nextLocIndex);
			

				if( bld.updateAgentPos(this,new_loc.x, new_loc.y)==true){
					robot_loc.x = new_loc.x;
					robot_loc.y = new_loc.y;
					c_dir=Curr_Direction.Same;
					deadlockedsteps=0;
					//Deepak:uncomment once storage api works
					/*simState.storage.updRobotPos(super.getID(), 
			 			robot_loc.x, robot_loc.y);*/
					break;
				}
				else{
					validGridList.remove(nextLocIndex);
				}
			}
			else{
				deadlockedsteps++;
				break;
			}
		}
		
				
	}
	
	/**
	 * Logic to simulate 'normal' movement of robot in the building -wall-follower
	 * @param 
	 */
	public void normalMovement(){
		
		Building bld = (Building)simState.getAgentByID(buildingID);
		int x = robot_loc.x;
		int y = robot_loc.y;
		if((c_dir==Curr_Direction.Same)){
			if(bld.checkStep(x,y+1)){
			
			 if(bld.updateAgentPos(this,x, y+1)){
				 robot_loc.x = x;
				 robot_loc.y = y+1;
				 deadlockedsteps=0;
			 }
			 else{
				 deadlockedsteps++;
			 }
			 
			 return;
			}
			else{
				c_dir=Curr_Direction.West;
			}
		}
		
		
		 List<Curr_Direction> dirList = Arrays.asList(Curr_Direction.values());
		 TreeSet<Curr_Direction> dirs = new TreeSet<Curr_Direction>(new dirComparator());
		 dirs.addAll(dirList);
		 for (Curr_Direction dir : dirs) {
			  
			  if(dir == c_dir){
				  switch(c_dir){
				  case Same:
				  case North:
					  if(bld.checkStep(x+1,y)){
						  x=x+1;
						  c_dir = Curr_Direction.East;
						  
					  }
					  else if(bld.checkStep(x,y+1)){
						  y=y+1;
						  c_dir = Curr_Direction.North;
						  
					  }
					  else if(bld.checkStep(x-1,y)){
						  
						  c_dir = Curr_Direction.West;
						  
					  }
					  else if(bld.checkStep(x,y-1)){
						  y=y-1;
						  c_dir = Curr_Direction.South;
						  
					  }
					  break;
				  case South:
					 
					   if(bld.checkStep(x-1,y)){
						  x=x-1;
						  c_dir = Curr_Direction.West;
						  
					  }
					  else if(bld.checkStep(x,y-1)){
						  y=y-1;
						  
					  }
					  else if(bld.checkStep(x+1,y)){
						  x=x+1;
						  c_dir = Curr_Direction.East;
						  
					  }
					  else if(bld.checkStep(x,y+1)){
						  y=y+1;
						  c_dir = Curr_Direction.North;
						 
					  }
					   break;
				  case East:
					  
					   if(bld.checkStep(x,y-1)){
						  y=y-1;
						  c_dir = Curr_Direction.South;
						 
					  }
					  else if(bld.checkStep(x+1,y)){
						  x=x+1;
						  c_dir = Curr_Direction.East;
						 
					  }
					  else if(bld.checkStep(x,y+1)){
						  y=y+1;
						  c_dir = Curr_Direction.North;
						 
					  }
					  else if(bld.checkStep(x-1,y)){
						  x=x-1;
						  c_dir = Curr_Direction.West;
						  
					  }
					   break;
				  case West:
					  
					  if(bld.checkStep(x,y+1)){
						  y=y+1;
						  c_dir = Curr_Direction.North;
						  
					  }
					  else if(bld.checkStep(x-1,y)){
						  x=x-1;
						  c_dir = Curr_Direction.West;
						  
					  }
					  else if(bld.checkStep(x,y-1)){
						  y=y-1;
						  c_dir = Curr_Direction.South;
						  
					  }
					  else if(bld.checkStep(x+1,y)){
						  x=x+1;
						  c_dir = Curr_Direction.East;
						  
					  }
					  break;
				  }
				  break;
			  }
			  
			  
		   
		  }
		
		
		
		
		if( bld.updateAgentPos(this,x, y)){
			 robot_loc.x = x;
			 robot_loc.y = y;
			 deadlockedsteps=0;
			 //Deepak:uncomment once storage api works
				/*simState.storage.updRobotPos(super.getID(), 
			 robot_loc.x, robot_loc.y);*/
		}
		else{
			deadlockedsteps++;
		}
		
	}
	
	//Was used in earlier random movement algo- currently not used	
	/**
	 * Add the latest location visited by the robot and update the visited loc list 
	 * @param new_loc - New location to be added  to the visited location.
	 */
/*	private void updateVisitedLocs(MutableInt2D new_loc){
		lastVisitedLocs.add(new_loc);
		if(toBeSavedLocs == noOfSavedLocs)
			lastVisitedLocs.remove(noOfSavedLocs-1);
		else
			noOfSavedLocs++;
	}
*/
	//Was used in earlier random movement algo- currently not used	
	/**
	 * Check if the given location is in the recently visited location list 
	 * @param x
	 * @param y 
	 */
	/*private int checkIfRecentlyVisited(int x, int y){
		int weight=0;
		int index=0;
	    for ( Iterator<MutableInt2D> iter = this.lastVisitedLocs.iterator(); iter.hasNext(); ) {  
	    	MutableInt2D temp = (MutableInt2D) iter.next();  
	    	index=index+1;
	        if((temp.x==x)&&(temp.y==y)){
	        	weight=index;
	        	break;
	        }
	
	    }  
	    return (weight);
	}*/



	/**
	 * This is where the robot deals with the event after reaching the event location
	 */
	public void addressEvent(){
		
		Building bld = (Building)simState.getAgentByID(buildingID);

		if(currEvent instanceof FireEvent){

			//Robot reducin temperature of room affected by fire. using default rate of 1.
			(bld.getRoomTempById(bld. getRoomId(robot_loc.getX(),robot_loc.getY()))).robotTempChange(1);

			// old Boolean Logic
			//((FireEvent) currEvent).turn_fireOff();			
		}
		
		else if(currEvent instanceof IntruderEvent){
			((IntruderEvent) currEvent).dealWithIntruder();
		}
		else if(currEvent instanceof MoveRobotEvent){
			((MoveRobotEvent) currEvent).RobotReachedLoc();
		}
		else if(currEvent instanceof WaterLeakEvent){
			((WaterLeakEvent) currEvent).turn_LeakOff();
		}
		handlingEvent=false;
	}
	
	/**
	 * Take one step while moving towards an event location
	 * @param state 
	 */
	public void moveToEventSrc(){
	
		Building bld = (Building)simState.getAgentByID(buildingID);
		Int2D nextStep = routePath.pop();
		
		if( bld.updateAgentPos(this,nextStep.x, nextStep.y)){
			 robot_loc.x = nextStep.x;
			 robot_loc.y = nextStep.y;
			 deadlockedsteps=0;
			 //Deepak:uncomment once storage api works
				/*simState.storage.updRobotPos(super.getID(), 
			 robot_loc.x, robot_loc.y);*/
		}
		else{
			deadlockedsteps++;
			
		}
		
			
	}
	
	/**
	 * Obtain route and get started moving towards an event after noticing an event
	 * First step after noticing an event
	 * @param state 
	 * @param bld - building in which the robot is moving
	 */
	public boolean dealWithHouseEvents(SimState state,Building bld){
		currEvent = events.removeFirst();				
		boolean ret_val=false;
		
		if(bld.checkStep(currEvent.getX_pos(), currEvent.getY_pos()) ){
			handlingEvent = true;
			routePath = AStar.findPath(robot_loc.x, robot_loc.y, currEvent.getX_pos(), currEvent.getY_pos(), bld.getObstacles());
			moveToEventSrc();
			ret_val = true;
		}
		return ret_val;
		
	}
	
	@Override
	public void step(SimState state) {
		
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		
		super.step(state);
		
		if(handlingEvent == true){
			if(robot_loc.x == currEvent.getX_pos() && robot_loc.y == currEvent.getY_pos()){
				//System.out.println("Reached: x="+robot_loc.x+"y="+robot_loc.y);
				addressEvent();
			}
			else {
				//System.out.println("Path: x="+robot_loc.x+"y="+robot_loc.y);
				moveToEventSrc();
			
			}
			
		}
		else if(events.isEmpty()){
			//System.out.println("Random: x="+robot_loc.x+"y="+robot_loc.y);
			normalMovement();
		}
		else{
			//System.out.println("Starting: x="+robot_loc.x+"y="+robot_loc.y);
			
			if(dealWithHouseEvents(state, bld)){
				c_dir=Curr_Direction.Same;
			}
			else{
				//invalid event loc
				normalMovement();
			}
		}
		if(deadlockedsteps==2){
			randomMovement();
			if(handlingEvent == true){
				routePath = AStar.findPath(robot_loc.x, robot_loc.y,
						currEvent.getX_pos(), currEvent.getY_pos(), bld.getObstacles());
			}
			
		}
		
		
	}

}
