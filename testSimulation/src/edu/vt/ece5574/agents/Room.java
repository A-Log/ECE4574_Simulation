package edu.vt.ece5574.agents;

import java.util.LinkedList;
import java.util.ArrayList;
import sim.engine.SimState;
import sim.field.grid.IntGrid2D;
import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.roomconditions.WaterLevel;
import edu.vt.ece5574.roomconditions.Smoke;
import edu.vt.ece5574.sim.Simulation;
public class Room {


	private static final long serialVersionUID = 1;

	public int x;
	public int y;
	public int width;
	public int height;
	public Temperature roomTemperature;
	public Smoke roomSmoke;
	public WaterLevel roomWaterLevel;
	protected Simulation state;

	private static final int closedDoor = 2;


	//constructor
	public Room(int x_, int y_, int width_, int height_,SimState state_){
			x = x_;
			y = y_;
			width = width_;
			height = height_;
			state = (Simulation)state_;
			roomTemperature = new Temperature(state);
			roomSmoke = new Smoke(state);
			roomWaterLevel = new WaterLevel(state);
	}

	public boolean inRoom(int x1, int y1){
		if( ( x1 >= x ) && (x1 <= (x + width - 1)) && ( y1 >= y ) && (y1 <= (y + height - 1)) ){
			return true;
		}
		return false;
	}

	public boolean crossesRoom(Room b){

		//check for each corners :-

		if( ( x >= b.x ) && (x <= (b.x + b.width - 1)) && ( y >= b.y ) && (y <= (b.y + b.height - 1)) ){
			return true;
		}

		if( ( (x + width - 1) >= b.x ) && ( (x + width - 1) <= (b.x + b.width - 1)) && ( y >= b.y ) && (y <= (b.y + b.height - 1)) ){
			return true;
		}

		if( ( x >= b.x ) && (x <= (b.x + b.width - 1)) && ( (y + height - 1) >= b.y ) && ((y + height - 1) <= (b.y + b.height - 1)) ){
			return true;
		}

		if( (  (x + width - 1) >= b.x ) && ( (x + width - 1) <= (b.x + b.width - 1)) &&
			( (y + height - 1) >= b.y ) && ((y + height - 1) <= (b.y + b.height - 1)) ){
			return true;
		}

		return false;
	}

	public void addDoor(IntGrid2D doors, String side){

		if(width < 5 || height < 5){
			return;
		}
		int position ;
		//adds closed door
		if(side == "bottom"){
			position = y + height/2 ;
			doors.field[x + width-1][position] = closedDoor;
			doors.field[x + width-1][position + 1] = closedDoor ;
			doors.field[x + width-1][position + 2] = closedDoor;


		}
		else if(side == "left"){
			position = x + width/2 ;
			doors.field[position][y] = closedDoor ;
			doors.field[position + 1][y] = closedDoor ;
			doors.field[position + 2][y] = closedDoor ;


		}
		else if(side == "right"){
			position = x + width/2 ;
			doors.field[position][y + height - 1] = closedDoor ;
			doors.field[position + 1][y + height - 1] = closedDoor ;
			doors.field[position + 2][y + height - 1] = closedDoor ;


		}
		else if(side == "top"){
			position = y + height/2 ;
			doors.field[x ][position] = closedDoor ;
			doors.field[x ][position + 1] = closedDoor ;
			doors.field[x ][position + 2] = closedDoor ;


		}
	}

}