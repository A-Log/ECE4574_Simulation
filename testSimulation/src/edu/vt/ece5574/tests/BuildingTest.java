package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import edu.vt.ece5574.sim.Simulation;

import edu.vt.ece5574.agents.Building;
/*import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.WaterLeakEvent;

import edu.vt.ece5574.agents.Sensor;
import edu.vt.ece5574.agents.FireSensor;
import edu.vt.ece5574.agents.WaterLeakSensor;
*/



public class BuildingTest {


	Simulation sim;
	Building bld;
	String bID="0";
	
	@Before
	public void init(){
		sim = new Simulation(1);		
		bld = new Building(bID,sim);  //creates building with default layout
		sim.addAgent(bld);
	}
	

	@Test
	public void checkRoomNumber0() {
	

		int id = bld.getRoomId(5,5);
		assertEquals(id,0);	
		
	}
	@Test
	public void checkRoomNumber1() {
	

		int id = bld.getRoomId(25,5);
		assertEquals(id,1);	
		
	}
	@Test
	public void checkRoomNumber2() {
	

		int id = bld.getRoomId(5,25);
		assertEquals(id,2);	
		
	}
	
	
	@Test
	public void checkRoomNumber3() {
	

		int id = bld.getRoomId(25,25);
		assertEquals(id,3);	
		
	}
	@Test
	public void checkHall(){
		int id = bld.getRoomId(15, 15);
		assertEquals(id,-1);
	}


}
