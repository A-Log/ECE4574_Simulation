package edu.vt.ece5574.agents;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.roomconditions.WaterLevel;
import edu.vt.ece5574.roomconditions.Smoke;

import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.field.grid.IntGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;


//Author - Ameya Khandekar
/*
tilemap key :-
0 - empty position
1 - wall
2 - closed door
3 - open door
*/


public class Building extends Agent{

	//private int minRooms = 1;
	//private int maxRooms = 10;

	//#DEFINEs
	private static final int emptyTile = 0;
	private static final int wall = 1;
	private static final int closedDoor = 2;

	private static final int ROOMWIDTH = 10;
	private static final int ROOMHEIGHT = 10;
	private static final int TIME_STEP = 30; //Step time by specified seconds


	private static final long serialVersionUID = 1;
	protected int width;  // building width
	protected int height; // building height
	protected LinkedList<Room> rooms;
	protected LinkedList<Agent> agentsInBld;
	protected LinkedList<Sensor> sensorsInBld;
	protected LinkedList<User> usersInBId;
	private ClockTime clockTime;
	
	
	//protected int[][] tilemap;
	protected IntGrid2D tile_map;
	//protected Continuous2D tilemap;
	protected IntGrid2D obstacles;
	protected SparseGrid2D agents;

	protected Simulation state;
	protected Temperature hallTemperature;
	protected Smoke hallSmoke;
	protected WaterLevel hallWaterLevel;

	public Building(String id){
		super(id, id);
		rooms = new LinkedList<Room>();
		agentsInBld = new LinkedList<Agent>();
		sensorsInBld = new LinkedList<Sensor>();
		usersInBId = new LinkedList<User>();
		clockTime = new ClockTime();
		//Deepak: Take this out..there for initial testing only
		//createRobot();

		//Dummy - needs to be removed later.
	}


	public Building(String id,SimState state_){
		super(id, id);
		state = (Simulation)state_;
		rooms = new LinkedList<Room>();
		agentsInBld = new LinkedList<Agent>();
		sensorsInBld = new LinkedList<Sensor>();
		usersInBId = new LinkedList<User>();
		clockTime = new ClockTime();

		width = 30;
		height = 30;

		//creates a building with a tilemap full of 0s. Thus empty building with plain floor.
		tile_map = new IntGrid2D(width,height,0);
		obstacles = new IntGrid2D(width,height,0);
		agents = new SparseGrid2D(width,height);
		//doors = new IntGrid2D(width,height,0);
		//windows = new IntGrid2D(width,height,0);

		for(int i = 0 ; i < width ; i++){
				//tilemap[i][0] = 1; //1 indicates wall
				//tilemap[i][height - 1] = 1;
			obstacles.field [i][0] = wall;
			obstacles.field [i][height - 1]= wall;
		}

		for(int j = 0 ; j < height ; j++){
				//tilemap[0][j] = 1; //1 indicates wall
				//tilemap[width - 1][j] = 1;
			obstacles.field [0][j]= wall;
			obstacles.field [width - 1][j]= wall;
		}
		hallTemperature = new Temperature(state);
		hallSmoke = new Smoke(state);
		hallWaterLevel = new WaterLevel(state);

		//simple building layout for default use

		Int2D room0Corner = new Int2D(1,1);
		Int2D room1Corner = new Int2D(19,1);
		Int2D room2Corner = new Int2D(1,19);
		Int2D room3Corner = new Int2D(19,19);

		addRoom(room0Corner.getX(), room0Corner.getY(), ROOMWIDTH, ROOMHEIGHT);
		rooms.get(0).addDoor(obstacles,"bottom");
		addRoom(room1Corner.getX(), room1Corner.getY(),ROOMWIDTH,ROOMHEIGHT);
		rooms.get(1).addDoor(obstacles,"right");
		addRoom(room2Corner.getX(), room2Corner.getY(),ROOMWIDTH,ROOMHEIGHT);
		rooms.get(2).addDoor(obstacles,"left");
		addRoom(room3Corner.getX(), room3Corner.getY(),ROOMWIDTH,ROOMHEIGHT);
		rooms.get(3).addDoor(obstacles,"top");


		//add Door to Hallway from outside -
		obstacles.field[0][14] = closedDoor;
		obstacles.field[0][15] = closedDoor;
		obstacles.field[0][16] = closedDoor;
		obstacles.field[0][17] = closedDoor;
		//Deepak: Take this out..there for initial testing only
		//createRobot();

		//state.addAgent(new Robot(id,"2",4,4));
	}

	//constructor
	public Building(String id, int width_, int height_, SimState state_){
				super(id, id);
				state = (Simulation)state_;

				width = width_;
				height = height_;
				//tilemap = new int[width][height]; //creates a building with a tilemap full of 0s. Thus empty building with plain floor.
				tile_map = new IntGrid2D(width, height,0);
				obstacles = new IntGrid2D(width, height,0);
				agents = new SparseGrid2D(width,height);

			hallTemperature = new Temperature(state);
			hallSmoke = new Smoke(state);
			hallWaterLevel = new WaterLevel(state);

			rooms = new LinkedList<Room>();
			agentsInBld = new LinkedList<Agent>();
			sensorsInBld = new LinkedList<Sensor>();
			usersInBId = new LinkedList<User>();
			clockTime = new ClockTime();

			for(int i = 0 ; i < width ; i++){
				//tilemap[i][0] = 1; //1 indicates wall
				//tilemap[i][height - 1] = 1;
				obstacles.field[i][0] = wall;
				obstacles.field[i][height - 1] = wall;
			}

			for(int j = 0 ; j < height ; j++){
				//tilemap[0][j] = 1; //1 indicates wall
				//tilemap[width - 1][j] = 1;
				obstacles.field[0][j] = wall;
				obstacles.field[width - 1][j] = wall;
			}


			//Deepak: Take this out..there for initial testing only

				//createRobot();

	}

	public int getRoomId(int x, int y){

		if( ( x >= 0 ) && (x <= (width - 1)) && ( y >= 0 ) && (y <= (height - 1)) ){

			for(int idx = 0; idx < rooms.size(); idx++)
      		{
      			if((rooms.get(idx)).inRoom(x,y))
      			{
      				return idx; // id of room ranges from 0 to totalRooms - 1.
      			}
      		}

      		return -1; // -1 indicates hall area
		}
		else{
			return -2; // - 2 indicates x,y doesn't belong to the building
		}

	}

	public void displayLayout(){
		System.out.println("Current Layout");
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				System.out.print(obstacles.field[i][j]);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}

	public Temperature getRoomTempById(int idx){

		if(idx == -1){
			return hallTemperature;
		}
		else
			return (rooms.get(idx)).roomTemperature ;

	}

	public WaterLevel getRoomWaterLevelById(int idx) {

	    if(idx == -1) {

	        return hallWaterLevel;
	    } else
	        return (rooms.get(idx).roomWaterLevel);
	}

	public Smoke getRoomSmokeById(int idx){

		if(idx == -1){
			return hallSmoke;
		}
		else
			return (rooms.get(idx)).roomSmoke ;

	}


	public IntGrid2D getTileMap(){

		return tile_map;
	}

	public IntGrid2D getObstacles(){

		return obstacles;
	}

	public SparseGrid2D getAgents(){

		return agents;
	}

/*	public IntGrid2D getDoors(){

		return doors;
	}*/
	public boolean addRoom(int x, int y, int w, int h){

		Room tempRoom = new Room(x,y,w,h,state);

	//check if it crosses another room :-
		for(int idx = 0; idx < rooms.size(); idx++)
      	{
      		if(tempRoom.crossesRoom(rooms.get(idx)))
      		{
      			System.out.println(" can't add room ");
      			return false;
      		}
      	}


		//check if fits main building :-
		if(( x > 0) && ((x < width - 1)) && ( (x + w - 1) > 0) && ((x + w - 1) < (width - 1) ) &&
			( y > 0) && (y < (height - 1)) && ( (y + h - 1) > 0) && ((y + h - 1) < (height - 1 )) ){

			rooms.add(tempRoom);
			//update tilemap
			for(int i = x ; i < x + w ; i++){
				//tilemap[i][y] = 1; //1 indicates wall
				//tilemap[i][y + h - 1] = 1;
				obstacles.field [i][y] = wall;
				obstacles.field [i][y + h - 1] = wall;

			}

			for(int j = y ; j < y + h ; j++){
			//	tilemap[x][j] = 1; //1 indicates wall
			//	tilemap[x + w - 1][j] = 1;
				obstacles.field [x][j] = wall;
				obstacles.field [x + w - 1][j] = wall;

			}
			return true;
		}
		else{
			return false;
		}
		//return true;
	}

	//need to be changed when adding more functionality
	public boolean checkStep(int x, int y){
		if((x>=0) && (y>=0) && (x < width) && (y < height)){
			if(obstacles.field[x][y] == emptyTile){
				return true;
			}
			else if(obstacles.field[x][y] == closedDoor){
				return true;
			}
		}
		return false;

	}

	//Revisit
	public Robot createRobot(){

		//Deepak: need more dynamic way of deciding initial pos
		//Ameya: provided a random initial position
		Int2D pos = genStartPos();
		Robot robot = new Robot(state, String.valueOf(agentsInBld.size()+1),id,pos.getX(),pos.getY());

		agents.setObjectLocation(robot,pos.getX(),pos.getY());
		agentsInBld.add(robot);

		state.addAgent(robot);
		state.schedule.scheduleRepeating(robot);
		return robot;

		//deal with no space for Robot in building later.

	}

	public Sensor createSensor(String type, int x, int y){

		//Int2D pos = genStartPos();
		Sensor newSensor = null;
		boolean repeating = true;

		if(type == "temperature"){

			newSensor = new TempSensor(String.valueOf(agentsInBld.size()), id,state, x,y);

			agents.setObjectLocation(newSensor,x,y);
			agentsInBld.add(newSensor);
			state.addAgent(newSensor);
			sensorsInBld.add(newSensor);

		} else if(type == "waterleak") {

		    newSensor = new WaterLeakSensor(String.valueOf(agentsInBld.size()), id, state, x, y);

            agents.setObjectLocation(newSensor, x, y);
            agentsInBld.add(newSensor);
            state.addAgent(newSensor);
            sensorsInBld.add(newSensor);

		} else if(type == "weight") {

		    newSensor = new WeightSensor(String.valueOf(agentsInBld.size()), id, state, x, y);

            agents.setObjectLocation(newSensor, x, y);
            agentsInBld.add(newSensor);
            state.addAgent(newSensor);
            sensorsInBld.add(newSensor);

            repeating = false;
		}

		if(repeating)
		    state.schedule.scheduleRepeating(newSensor);

		return newSensor;
	}


	public User createUser(){

		Int2D pos = genStartPos();
		User oUser = new Adult(state, String.valueOf(agentsInBld.size()+1),id,true, pos.getX(),pos.getY());
		agents.setObjectLocation(oUser,pos.getX(),pos.getY());
		agentsInBld.add(oUser);
		state.addAgent(oUser);
		state.schedule.scheduleRepeating(oUser);
		return oUser;
	}

	public ClockTime getBuildingTime()
	{
		return clockTime;
	}

	//generates a unique random position unoccupied by any obstacle or other agent (this also includes sensors for now)
	public Int2D genStartPos(){

		int x , y ;
		while(true){
			x = state.random.nextInt()%width;
			y = state.random.nextInt()%height;
			if(x < 0 || y < 0){
				continue;
			}
			boolean flag = false;
			for(int i = 0 ; i < agentsInBld.size(); i++){
				Int2D pos = agents.getObjectLocation(agentsInBld.get(i));
				if(pos.getX() == x && pos.getY() == y){
					flag = true;
					break;
				}
			}

			if(flag == true)
				continue;
			if((obstacles.field[x][y] == emptyTile) && (tile_map.field[x][y] == emptyTile))
				break;
		}

		Int2D position = new Int2D(x,y);
		return position;

	}

	public boolean updateAgentPos(Agent agnt,int x_loc,int y_loc){
		//agents.setObjectPosition(agnt,x_loc, y_loc);
		boolean ret_val = false;
		synchronized (this) {
			if(agents.numObjectsAtLocation(x_loc, y_loc)==0){
				agents.setObjectLocation(agnt,x_loc, y_loc);
				//state.storage.updRobotPos(agnt.getID(), x_loc, y_loc));
				ret_val = true;
			}
	    }
		return ret_val;
	}

	public List<Coordinate> getRoute(Coordinate current,Coordinate destination){
		List<Coordinate> myList = new ArrayList<Coordinate>();
		return myList;
		//Dummy - needs to be changed.

	}


	public void handleBuildingEvents()
	{
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
			if(currentEvent instanceof FireEvent){
				FireEvent fireevent = (FireEvent)currentEvent;
				if(fireevent.is_fireActive()){
				(rooms.get((int)fireevent.getRoom())).roomTemperature.fireTempChange(fireevent.getSeverity());
				}
			}

		}
	}

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		super.step(arg0);

		if(!events.isEmpty()){
			handleBuildingEvents();
		}

	    //Default temperature changes per step in each room :-
		for(int idx = 0; idx < rooms.size(); idx++)
      	{
      		(rooms.get(idx)).roomTemperature.defTempChange();
      		(rooms.get(idx)).roomSmoke.defSmokeChange();
      	}
      	hallTemperature.defTempChange();
      	hallSmoke.defSmokeChange();
      	hallWaterLevel.defWaterLevelChange();
      	//add similar methods for Smoke change per step in each room:-
      	//Increment the simulation clock by TIME_STEP units.
      	clockTime.incrementTimeBySeconds(TIME_STEP);
	}
}
