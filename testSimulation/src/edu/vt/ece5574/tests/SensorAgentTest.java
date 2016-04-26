package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;

import edu.vt.ece5574.agents.Sensor;
import edu.vt.ece5574.agents.TempSensor;
import edu.vt.ece5574.agents.WaterLeakSensor;




public class SensorAgentTest {


//simple test for checking if fire is burning.
//need to write test for where Robot extinguishes fire.
	@Test
	public void normalTemperature(){
		 Simulation sim = new Simulation(0);
		 Building building = (Building)sim.getAgentByID("0");
		// Robot robot0 = building.createRobot(); 
		 TempSensor tempsensor = (TempSensor)building.createSensor("temperature",20,3);
		 assertEquals(tempsensor.getTempValue(),10);
	}
	
	@Test
	public void temperatureChanges(){
		 Simulation sim = new Simulation(0);
		 Building building = (Building)sim.getAgentByID("0");
		// Robot robot0 = building.createRobot(); 
		 TempSensor tempsensor = (TempSensor)building.createSensor("temperature",20,3);
		 int original = tempsensor.getTempValue();
		System.out.println("temperatureChanges test originalTemp ="+tempsensor.getTempValue());
		for(int i = 0; i < 10; i++){
			building.step(sim);
			//System.out.println("temperatureChanges test Intermediate Values ="+tempsensor.getTempValue());
		}
		System.out.println("temperatureChanges test finalTemp ="+tempsensor.getTempValue());
		assertTrue(tempsensor.getTempValue() != original);

	}
	
	@Test
	public void longertemperatureChanges(){
		 Simulation sim = new Simulation(0);
		 Building building = (Building)sim.getAgentByID("0");
		// Robot robot0 = building.createRobot(); 
		 TempSensor tempsensor = (TempSensor)building.createSensor("temperature",20,3);
		 int original = tempsensor.getTempValue();
		System.out.println("Longer temperatureChanges test originalTemp ="+tempsensor.getTempValue());
		for(int i = 0; i < 1000; i++){
			building.step(sim);
			//System.out.println("temperatureChanges test Intermediate Values ="+tempsensor.getTempValue());
		}
		System.out.println("Longer temperatureChanges test finalTemp ="+tempsensor.getTempValue());
		assertTrue(tempsensor.getTempValue() != original);

	}
	
	
	@Test
	public void burningFire(){

	//	TempSensor tempsensor = new TempSensor("1","0");
		 Simulation sim = new Simulation(0);
		 Building building = (Building)sim.getAgentByID("0");
		// Robot robot0 = building.createRobot(); 
		 TempSensor tempsensor = (TempSensor)building.createSensor("temperature",20,3);
		 
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
		
		

		
		
		System.out.println("burningFire test original temp "+tempsensor.getTempValue());
		for(int i = 0; i < 10; i++){
			building.step(sim);
		}
		
		FireEvent event = new FireEvent();
		event.init(details);
		building.addEvent(event);
		building.step(sim);
		building.step(sim);
		building.step(sim);
		System.out.println("burningFire final temp:- "+tempsensor.getTempValue());
		assertTrue(tempsensor.getTempValue() > 400);
	
	}

}


//old code tests.

/*Simulation sim;
Building bld;
String bID="0";

@Before
public void init(){
	sim = new Simulation(1);		
	bld = new Building(bID);
	sim.addAgent(bld);
}*/


//@Test
/*	public void checkFireSensor() {


	Sensor firesensor = new FireSensor("1","0");
	assertEquals(firesensor.getSensorType(),"fire");	
	
}
*/
/*@Test
public void checkWaterLeakSensor() {


	Sensor waterleaksensor = new WaterLeakSensor("1","0");
	assertEquals(waterleaksensor.getSensorType(),"waterleak");	
	
}

/*	@Test
public void checkWaterLeakSensor() {


	Sensor firesensor = new Sensor("WATERLEAK", "1", "0");
	assertEquals(firesensor.getSensorType(),"WATERLEAK");	
	
}

*/


/*@Test(timeout=1000)
public void douseFire(){
	String rID ="1";
	Robot rob = new Robot(rID,bID,2,5);
	FireSensor firesensor = new FireSensor("1","0");

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
	rob.addEvent(event);
	firesensor.addEvent(event);
	while(true){
		rob.step(sim);
		//firesensor.step(sim);

		if(rob.isBusy()==false)
			break;
	}
	firesensor.step(sim);
	assertFalse(firesensor.getFireStatus());
	
}

@Test(timeout=1000)
public void leakingWater(){
	WaterLeakSensor waterleaksensor = new WaterLeakSensor("1","0");

	String details = 
			"{"
			+ "\"messageId\": \"0\","
			+ "\"message\": {"
				+ "\"msg_type\": \"water leak\","
				+ "\"body\": {"
					+ "\"building\": \"0\","
					+ "\"room\": 1,"
					+ "\"floor\": 1,"
					+ "\"xpos\": 2,"
					+ "\"ypos\": 3,"
					+ "\"severity\": 5,"
					+ "\"action\": \"fix plumbing\""
					+ "}"
				+ "}"
			+ "}";
	
	WaterLeakEvent event = new WaterLeakEvent();
	event.init(details);
	waterleaksensor.addEvent(event);
	waterleaksensor.step(sim);
	assertTrue(waterleaksensor.getWaterLeakStatus());
}
*/


