package edu.vt.ece5574.tests;

import static org.junit.Assert.*;
import edu.vt.ece5574.sim.SimLogger;

import org.junit.Test;

public class SimLoggerUnitTest extends SimLogger {

	SimLogger LOGGER = new SimLogger();
	
	@Test
	public void testConstructMessage(){
		String result = LOGGER.constructMessage("Robot1", "Exstinguishing", "Moving to location (1,2)");
		result = result.substring(0, result.length()-2);	// remove newline character
		System.out.println(result);
		assertEquals(result, "Robot1 changed status: Exstinguishing (Moving to location (1,2))");
	}
	
	@Test
	public void testAppendLogEvent(){
		Boolean functioning = LOGGER.appendLogEvent("Robot1", "Idle", "Stopped at location (1,2)");
		assertEquals(functioning, true);
	}

	@Test
	public void testAdjustLogHeader(){
		Boolean functioning = LOGGER.adjustLogHeader("Robot1", "Idle");
		assertEquals(functioning, true);
	}
	
	@Test
	public void testAppendVisualLog(){
		Boolean functioning = LOGGER.appendVisualLog("Robot1", "Idle");
		assertEquals(functioning, true);
	}
}
