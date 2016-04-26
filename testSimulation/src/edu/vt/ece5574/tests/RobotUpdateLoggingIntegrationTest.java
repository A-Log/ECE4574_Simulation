package edu.vt.ece5574.tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.sim.ReadNotifications;
import edu.vt.ece5574.sim.Simulation;

public class RobotUpdateLoggingIntegrationTest {
	static ReadNotifications testNotifications = new ReadNotifications();
	Simulation sim;
	Building bld;
	Robot rob1;
	
	@Before
	public void setUp() {
		sim = new Simulation(10);
		bld = new Building("0");
		rob1 = new Robot(sim,"1", "0");
		sim.addAgent(bld);
		sim.addAgent(rob1);
		testNotifications.deleteAll();
	}
	
/*	@Test
	public void robotUpdateLogginTest() {
		//assertTrue(rob1.moveToEventSrc(sim));
	}
*/
}
