package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.sim.Simulation;

public class SimulationTests {
	Simulation sim;
	
	@Before
	public void init(){
		sim = new Simulation(1);
		sim.addAgent(new Building("0"));
	}

	@Test
	public void agentAddGoodID(){
		assertTrue(sim.addAgent(new Robot(sim,"1", "0")));
		assertNotNull(sim.getAgentByID("1"));
		assertEquals("1", sim.getAgentByID("1").getID());
	}

	@Test
	public void agentAddNull(){
		assertFalse(sim.addAgent(null));
	}
	
	@Test 
	public void agentAddDuplicate(){
		assertTrue(sim.addAgent(new Robot(sim,"1", "0")));
		assertFalse(sim.addAgent(new Robot(sim,"1", "0")));
	}
	
	@Test
	public void agentAddNullBuilding(){
		assertFalse(sim.addAgent(new Robot(sim,"1", null)));
	}

	@Test
	public void agentAddBadBuilding(){
		assertFalse(sim.addAgent(new Robot(sim,"2", "1")));
	}
	
	@Test
	public void agentAddNullID(){
		assertFalse(sim.addAgent(new Robot(sim,null, "0")));
	}
	
	@Test
	public void agentAddBuildingAgent(){
		assertTrue(sim.addAgent(new Building("1")));
	}
	
	@Test
	public void agentAddIDSameAsBuildingDoesntExist(){
		assertFalse(sim.addAgent(new Robot(sim,"1", "1")));
	}
	
	@Test
	public void agentAddIDSameAsBuildingDoesExist(){
		assertFalse(sim.addAgent(new Robot(sim,"0", "0")));
	}
	
	
	
	@Test
	public void agentRemoveGood(){
		Robot rob = new Robot(sim,"1", "0");
		assertTrue(sim.addAgent(rob));
		assertNotNull(sim.removeAgent(rob));
	}
	
	@Test 
	public void agentRemoveNull(){
		assertNull(sim.removeAgent(null));
	}
	
	@Test
	public void agentRemoveBadID(){
		Robot rob = new Robot(sim,"1", "0");
		assertTrue(sim.addAgent(rob));
		assertNull(sim.removeAgent(new Robot(sim,"2", "0")));
	}
	
	@Test
	public void agentRemoveNullID(){
		assertNull(sim.removeAgent(new Robot(sim,null, "0")));
	}
	
	@Test
	public void agentMessageWaitingNull(){
		assertFalse(sim.agentPushReceived(null));
	}
	
	@Test
	public void agentMessageWaitingBad(){
		assertFalse(sim.agentPushReceived("100"));
	}
	
	@Test
	public void agentMessageWaitingGood(){
		assertTrue(sim.agentPushReceived("0"));
	}
}
