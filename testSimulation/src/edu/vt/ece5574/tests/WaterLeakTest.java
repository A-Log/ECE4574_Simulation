package edu.vt.ece5574.tests;

import static org.junit.Assert.*;
import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.TempSensor;
import edu.vt.ece5574.agents.WaterLeakSensor;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import org.junit.Before;
import org.junit.Test;

public class WaterLeakTest
{

    @Test
    public void normalWaterLevel(){

         Simulation sim = new Simulation(0);
         Building building = (Building)sim.getAgentByID("0");

         WaterLeakSensor waterSensor = (WaterLeakSensor)building.createSensor("waterleak", 15, 4);
         assertEquals(false, waterSensor.getWaterLeakStatus());
         assertEquals(building.getRoomWaterLevelById(building.getRoomId(15, 4)).getWaterLevel(), 30);
    }

    @Test
    public void waterLeakEventSensed(){

         Simulation sim = new Simulation(0);
         Building building = (Building)sim.getAgentByID("0");

         WaterLeakSensor waterSensor = (WaterLeakSensor)building.createSensor("waterleak", 15, 4);
         building.addEvent(new WaterLeakEvent());
         waterSensor.handleSensorEvents();

         assertEquals(true, waterSensor.getWaterLeakStatus());
    }

    @Test
    public void waterLevelChanged(){

         Simulation sim = new Simulation(0);
         Building building = (Building)sim.getAgentByID("0");

         WaterLeakSensor waterSensor = (WaterLeakSensor)building.createSensor("waterleak", 15, 4);
         assertEquals(false, waterSensor.getWaterLeakStatus());
         int waterLevel = building.getRoomWaterLevelById(building.getRoomId(15, 4)).getWaterLevel();

         for(int i = 0; i < 100; i++){

             building.step(sim);
         }

         assertTrue(waterLevel != building.getRoomWaterLevelById(building.getRoomId(15, 4)).getWaterLevel());
    }

}
