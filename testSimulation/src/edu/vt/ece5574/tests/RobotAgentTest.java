package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;


/**
 * Test Robot Agent and see that they respond to events.
 * @author Deepak Rajendrakumaran
 *
 */
public class RobotAgentTest {

	Simulation sim;
	Building bld;
	String bID="0";
	
	@Before
	public void init(){
		sim = new Simulation(1);		
		bld = new Building(bID,sim);
		sim.addAgent(bld);
	}
	
	@Test(timeout=1000)
	public void addRobot(){
		Robot rob = new Robot(sim,"2",bID,5,100);
		assertTrue(sim.addAgent(rob));			
		
	}
	
	
	@Test(timeout=1000)
	public void chckRobotID(){
		Robot rob = new Robot(sim,"3",bID,9,90);
		sim.addAgent(rob);
		assertTrue(rob.getID().compareTo("3")==0);
			
		
	}
	
	@Test(timeout=1000)
	public void chckRobotLoc(){
		int x_loc=55;
		int y_loc=19;
		Robot rob = new Robot(sim,"3",bID,x_loc,y_loc);
		sim.addAgent(rob);
		assertTrue((rob.getX()==x_loc)&&(rob.getY()==y_loc));
			
		
	}
	
	@Test(timeout=1000)
	public void NormalMovement(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,1,1);
		Robot rob = bld.createRobot();
		//sim.addAgent(rob);
		double initial_x= rob.getX();
		double initial_y= rob.getY();
		rob.normalMovement();
		rob.normalMovement();
		rob.normalMovement();
		assertFalse((rob.getX()==initial_x)&&(rob.getY()==initial_y));	
	}
	
	@Test(timeout=1000)
	public void randomMovement(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,1,1);
		Robot rob = bld.createRobot();
		//sim.addAgent(rob);
		double initial_x= rob.getX();
		double initial_y= rob.getY();
		rob.randomMovement();
		assertFalse((rob.getX()==initial_x)&&(rob.getY()==initial_y));	
	}
	
	@Test(timeout=1000)
	public void collisionMovement(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,1,1);
		int n =0;
		Robot rob = bld.createRobot();
		Robot rob1 = bld.createRobot();
		Robot rob2 = bld.createRobot();
		
		
		
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 2,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		
		MoveRobotEvent event = new MoveRobotEvent();
		event.init(details);
		rob.addEvent(event);
		while(n<500){
			rob.step(sim);
			rob2.randomMovement();
			rob1.normalMovement();
			assertFalse((rob2.getX()==rob.getX())&&(rob2.getY()==rob.getY()));	
			assertFalse((rob1.getX()==rob.getX())&&(rob1.getY()==rob.getY()));
			assertFalse((rob2.getX()==rob1.getX())&&(rob2.getY()==rob1.getY()));

			n++;
		}
		
			
	}
	
	@Test(timeout=1000)
	public void continuousNormalMovement(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,3,2);
		//sim.addAgent(rob);
		Robot rob = bld.createRobot();
	//	assertTrue(bld.checkStep(9, 9));	
		int steps=100;
		while(steps!=0){
			rob.normalMovement();;
			bld.checkStep(rob.getX(), rob.getY());
			steps--;
		}
		assertTrue(bld.checkStep(rob.getX(), rob.getY()));			
		
	}
	
	@Test(timeout=1000)
	public void respondtoNoEvent(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,2,2);
		//sim.addAgent(rob);
		Robot rob = bld.createRobot();
		rob.step(sim);
		assertFalse(rob.isBusy());
		
	}
	
	@Test(timeout=1000)
	public void moveToaPoint(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,2,5);
		//sim.addAgent(rob);
		Robot rob = bld.createRobot();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 2,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		
		MoveRobotEvent event = new MoveRobotEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertEquals((int)rob.getX(),event.getX_pos());
		assertEquals((int)rob.getY(),event.getY_pos());
		
	}
	
	@Test(timeout=1000)
	public void respondtoFireEvent(){
	//	String rID ="1";
	//	Robot rob = new Robot(sim,rID,bID,2,5);
		Robot rob = bld.createRobot();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 3,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
	
		FireEvent event = new FireEvent();
		event.init(details);
		int orig_temp = bld.getRoomTempById(bld. getRoomId(
				2,3)).getTemperature();
		bld.getRoomTempById(bld. getRoomId(
				rob.getX(),rob.getY())).fireTempChange(5);
		bld.step(sim);
		bld.step(sim);
		bld.step(sim);
		bld.step(sim);
		bld.step(sim);
		int orig_temp_latest= bld.getRoomTempById(bld. getRoomId(
				2,3)).getTemperature();
//		assertEquals(orig_temp_latest,orig_temp);
		assertNotSame(orig_temp_latest,orig_temp);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		while(orig_temp_latest>orig_temp){
			bld.step(sim);
			orig_temp_latest= bld.getRoomTempById(bld. getRoomId(
				2,3)).getTemperature();
		}
	
		assertEquals(orig_temp_latest,orig_temp);
		//assertFalse(event.is_fireActive());
		
	}
	
	@Test(timeout=1000)
	public void respondtoWaterEvent(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,2,4);
		Robot rob = bld.createRobot();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"water leak\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 7,"
						+ "\"ypos\": 9,"
						+ "\"severity\": 5,"
						+ "\"action\": \"fix plumbing\""
						+ "}"
					+ "}"
				+ "}";
		
		WaterLeakEvent event = new WaterLeakEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertFalse(event.is_WaterLeakActive());
		
	}
	
	
	@Test(timeout=1000)
	public void respondtoMoveRobotEvent(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,2,4);
		Robot rob = bld.createRobot();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 8,"
						+ "\"ypos\": 7,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		
		MoveRobotEvent event = new MoveRobotEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertTrue(event.hasRobotReachedLoc());
		
	}
	
	
	@Test(timeout=1000)
	public void respondtoIntruderEvent(){
		//String rID ="1";
		//Robot rob = new Robot(sim,rID,bID,3,4);
		Robot rob = bld.createRobot();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"intruder\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 3,"
						+ "\"severity\": 5,"
						+ "\"action\": \"defend\""
						+ "}"
					+ "}"
				+ "}";
		
		IntruderEvent event = new IntruderEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertFalse(event.is_intruderActive());
		
	}
}
