package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Agent;
import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.*;
import edu.vt.ece5574.sim.Simulation;

/**
 * Test events and see that they get set properly, get added to the event list, etc.
 * @author David Kindel
 *
 */
public class EventTests {

	Simulation sim;

	Building bld;
	Robot rob1;
	Robot rob10;
	Robot rob5;
	Robot rob2;
	
	@Before
	public void init(){
		/*String[] args = new String[2];
		args[0] = "-seed";
		args[1] = "1";
		
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
				Simulation.main(args);
		    }
		            
		});
		        
		thread.start();

		System.out.println("after main");
		try {
			Thread.sleep(2000); //wait for simulation to be set up
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		sim = new Simulation(1);
		bld = new Building("0");
		rob1 = new Robot(sim,"1", "0");
		rob10 = new Robot(sim,"10", "0");
		rob5 = new Robot(sim,"5", "0");
		rob2 = new Robot(sim,"2", "0");
		sim.addAgent(bld);
		sim.addAgent(rob1);
		sim.addAgent(rob10);
		sim.addAgent(rob5);
		sim.addAgent(rob2);
	}
	
	
	
	private FireEvent createFire() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("fire", Event.getEventType(details));
		FireEvent event = new FireEvent();
		assertTrue(event.init(details));
		assertEquals("fire", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("extinguish", event.getAction());
		return event;
	}
	
	private FireEvent createFireBadRobotID(){
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("fire", Event.getEventType(details));
		FireEvent event = new FireEvent();
		assertTrue(event.init(details));
		assertEquals("fire", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("extinguish", event.getAction());
		return event;
	}
	
	
	private FireEvent createFireBadAndGoodRobotID(){
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("fire", Event.getEventType(details));
		FireEvent event = new FireEvent();
		assertTrue(event.init(details));
		assertEquals("fire", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("extinguish", event.getAction());
		return event;
	}
	
	private WaterLeakEvent createWaterLeak() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"water leak\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"fix plumbing\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("water leak", Event.getEventType(details));
		WaterLeakEvent event = new WaterLeakEvent();
		assertTrue(event.init(details));
		assertEquals("water leak", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("fix plumbing", event.getAction());
		return event;
	}
	
	private IntruderEvent createIntruder() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"intruder\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"defend\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("intruder", Event.getEventType(details));
		IntruderEvent event = new IntruderEvent();
		assertTrue(event.init(details));
		assertEquals("intruder", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("defend", event.getAction());
		return event;
	}
	
	private MoveRobotEvent createMoveRobot() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("move robot", Event.getEventType(details));
		MoveRobotEvent event = new MoveRobotEvent();
		assertTrue(event.init(details));
		assertEquals("move robot", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("move", event.getAction());
		return event;
	}
	
	private WaterLeakEvent createWaterLeakForUser() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"water leak\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"message\": \"There was a water leak in the building\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("water leak", Event.getEventType(details));
		WaterLeakEvent event = new WaterLeakEvent();
		assertTrue(event.init(details));
		assertEquals("water leak", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals(1, event.getRoom());
		assertEquals(2, event.getFloor());
		assertEquals(3, event.getX_pos());
		assertEquals(4, event.getY_pos());
		assertEquals(5, event.getSeverity());
		assertEquals("There was a water leak in the building", event.getMessage());
		return event;
	}
	
	private UserMessageEvent createUserMessage() {
		//Simulate what we'd get on the response of the push request
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"message\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"message\": \"Electricity will be off between 9am-5pm.\""
						+ "}"
					+ "}"
				+ "}";
		assertEquals("message", Event.getEventType(details));
		UserMessageEvent event = new UserMessageEvent();
		assertTrue(event.init(details));
		assertEquals("message", event.getEventType());
		assertEquals("0", event.getBuilding());
		assertEquals("Electricity will be off between 9am-5pm.", event.getMessage());
		return event;
	}
	
	
	@Test
	public void insertFire(){
		FireEvent event = createFire();
		rob1.addEvent(event);
		rob2.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		assertEquals("0", agent.getEventList().get(0).getEventID());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void insertFireGetOOBEvent(){
		FireEvent event = createFire();
		rob1.addEvent(event);
		rob2.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		LinkedList<Event> events = agent.getEventList();
		assertEquals("0", events.get(0).getEventID());
		agent = sim.getAgentByID("5");
		assertEquals("0", events.get(0).getEventID());
		events.get(1);
	}
	
	
	@Test
	public void testFireCreation(){
		createFire();
	}
	
	
	@Test
	public void testWaterLeakCreation(){
		createWaterLeak();
	}
	
	@Test
	public void insertWaterLeak(){
		WaterLeakEvent event = createWaterLeak();
		rob1.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		LinkedList<Event> events = agent.getEventList();
		assertEquals("0", events.get(0).getEventID());
	}
	
	@Test
	public void checkNoEventsBadRobotID(){
		WaterLeakEvent event = createWaterLeak();
		rob1.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		LinkedList<Event> events = agent.getEventList();
		assertEquals(1, events.size());
		agent = sim.getAgentByID("10");
		events = agent.getEventList();
		assertEquals(0, events.size());
	}
	
	@Test
	public void testIntruderCreation(){
		createIntruder();
	}
	
	@Test
	public void insertIntruder(){
		IntruderEvent event = createIntruder();
		rob1.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		LinkedList<Event> events = agent.getEventList();
		assertEquals("0", events.get(0).getEventID());
	}
	
	@Test
	public void testMoveRobotCreation(){
		createMoveRobot();
	}
	
	@Test
	public void insertMoveRobot(){
		MoveRobotEvent event = createMoveRobot();
		rob1.addEvent(event);
		Agent agent = sim.getAgentByID("1");
		LinkedList<Event> events = agent.getEventList();
		assertEquals("0", events.get(0).getEventID());
	}
	
	@Test
	public void testUserWaterLeak(){
		createWaterLeakForUser();
	}
	
	@Test
	public void insertUserWaterLeakNoRobots(){
		WaterLeakEvent event = createWaterLeakForUser();
		rob1.addEvent(event);
		rob10.addEvent(event);
		rob5.addEvent(event);
		Agent agent = sim.getAgentByID("0");
		LinkedList<Event> events = agent.getEventList();
		assertEquals(0, events.size());
	}
	
	@Test
	public void insertUserWaterLeak(){
		WaterLeakEvent event = createWaterLeakForUser();
		rob1.addEvent(event);
		rob5.addEvent(event);
		rob10.addEvent(event);
		Agent agent = sim.getAgentByID("5");
		LinkedList<Event> events = agent.getEventList();
		assertEquals(1, events.size());
		agent = sim.getAgentByID("10");
		events = agent.getEventList();
		assertEquals(1, events.size());
		agent = sim.getAgentByID("1");
		events = agent.getEventList();
		assertEquals(1, events.size());
		agent = sim.getAgentByID("0");
		events = agent.getEventList();
		assertEquals(0, events.size());
	}
	
	@Test
	public void testUserMessage(){
		createUserMessage();
	}
	
	@Test
	public void agentAddedButRequestingByBadID(){
		UserMessageEvent event = createUserMessage();
		Robot rob4 = new Robot(sim,"4", "0");
		sim.addAgent(rob4);
		rob1.addEvent(event);
		rob4.addEvent(event);
		rob10.addEvent(event);
		Agent agent = sim.getAgentByID("5");
		LinkedList<Event> events = agent.getEventList();
		assertEquals(0, events.size());
	}

	@Test
	public void agentNotAddedButRequestedByID(){
		assertNull(sim.getAgentByID("4"));
	}
	
	
	
	@Test
	public void malformedJSONObject(){
		String details =  //missing ending "}"
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}";
		MoveRobotEvent event = new MoveRobotEvent();
		assertFalse(event.init(details));
	}
	
	@Test 
	public void misspelledField(){
		String details =  
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"rom\": 1," //misspell room to rom
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		MoveRobotEvent event = new MoveRobotEvent();
		assertFalse(event.init(details));
	}
	
	@Test
	public void missingRequiredField(){
		String details =  
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\"," //missing room number
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		MoveRobotEvent event = new MoveRobotEvent();
		assertFalse(event.init(details));
	}
	
	@Test
	public void badIDRequested(){
		FireEvent event = createFireBadRobotID();
		Robot rob11 = new Robot(sim,"11", "0");
		sim.addAgent(rob11);
		rob11.addEvent(event); //add it to 11 just because (and to get rid of warning)
		
		//check it wasn't added to the event list of any of our active agents
		Agent agent = sim.getAgentByID("1");
		assertNotNull(agent);
		LinkedList<Event> events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		agent = sim.getAgentByID("10");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		agent = sim.getAgentByID("5");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		agent = sim.getAgentByID("2");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		
		//check it didn't create a new agent ID 3
		assertNull(sim.getAgentByID("3"));
	}
	
	@Test
	public void badAndGoodIDsRequested(){
		FireEvent event = createFireBadAndGoodRobotID();

		rob1.addEvent(event);
		
		//Check that the event was still added to ID 1
		Agent agent = sim.getAgentByID("1");
		assertNotNull(agent);
		LinkedList<Event> events = agent.getEventList();
		assertNotNull(events);
		assertEquals(1, events.size());
		
		//check it wasn't added to the event list of any of our other active agents
		agent = sim.getAgentByID("10");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		agent = sim.getAgentByID("5");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		agent = sim.getAgentByID("2");
		assertNotNull(agent);
		events = agent.getEventList();
		assertNotNull(events);
		assertEquals(0, events.size());
		
		//check it didn't create a new agent ID 3
		assertNull(sim.getAgentByID("3"));
	}
	
	/**
	 * Test simple mechanism to start and stop events
	 * @author Deepak Rajendrakumaran
	 *
	 */
	@Test
	public void testFireCreated(){
		FireEvent event = createFire();
		assertTrue(event.is_fireActive());
	}
	
	
	@Test
	public void testWaterLeakCreated(){
		WaterLeakEvent event = createWaterLeak();
		assertTrue(event.is_WaterLeakActive());
	}
	
	
	@Test
	public void testIntruderCreated(){
		IntruderEvent event = createIntruder();
		assertTrue(event.is_intruderActive());
	}
	
	
	
	@Test
	public void testMoveRobotCreated(){
		MoveRobotEvent event = createMoveRobot();
		assertFalse(event.hasRobotReachedLoc());
	}
	@Test
	public void testFireRemoved(){
		FireEvent event = createFire();
		event.turn_fireOff();
		assertFalse(event.is_fireActive());
	}
	
	
	@Test
	public void testWaterLeakRemoved(){
		WaterLeakEvent event = createWaterLeak();
		event.turn_LeakOff();
		assertFalse(event.is_WaterLeakActive());
	}
	
	
	@Test
	public void testIntruderRemoved(){
		IntruderEvent event = createIntruder();
		event.dealWithIntruder();
		assertFalse(event.is_intruderActive());
	}
	
	
	
	@Test
	public void testMoveRobotRemoved(){
		MoveRobotEvent event = createMoveRobot();
		event.RobotReachedLoc();
		assertTrue(event.hasRobotReachedLoc());
	}
}
