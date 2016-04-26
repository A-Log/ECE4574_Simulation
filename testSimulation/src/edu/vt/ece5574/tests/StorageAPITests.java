package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.vt.ece5574.sim.StorageAPI;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ByBuildingApi;
import io.swagger.client.api.ByIDApi;
import io.swagger.client.api.ByTypeApi;
import io.swagger.client.model.Building;
import io.swagger.client.model.Robot;
import io.swagger.client.model.Sensor;
import io.swagger.client.model.User;

/**
 * Test cases for StorageAPI class
 * 
 * @author Owen Nugent
 * */

public class StorageAPITests{
	private static String baseURL;
	private static ApiClient client;
	private static StorageAPI storageAPIInterface;
	
	@BeforeClass
	public static void init()
	{
		baseURL = new String ( "http://localhost:8080/api" );
		client = new ApiClient();
		client.setBasePath(baseURL);
		storageAPIInterface = new StorageAPI();
	}	


	@Test
	public void addUser()
	{
		ByTypeApi byTypeApi = new ByTypeApi(client);
		edu.vt.ece5574.agents.User user_sim = new edu.vt.ece5574.agents.User(null, "userId", "buildingId", false, 3, 5);
		
		try
		{
			byTypeApi.controllersDefaultControllerUsersDelete();
			String user_local_id = storageAPIInterface.addUser(user_sim.getID(), user_sim);

			List<User> users = byTypeApi.controllersDefaultControllerUsersGet();
			assertFalse("Get users should not return an empty list", users.isEmpty());
			assertTrue("Get should only return 1 user", users.size() == 1);
			
			User user_cloud = users.get(0);			
			assertEquals("Assert that the user Xpos is updated", int2BD(user_sim.getLocation().x), user_cloud.getXpos());
			assertEquals("Assert that the user Ypos is updated", int2BD(user_sim.getLocation().y), user_cloud.getYpos());
			
			byTypeApi.controllersDefaultControllerUsersDelete();
		}
		catch(ApiException e)
		{
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
	public void deleteUser()
	{
		ByTypeApi byTypeApi = new ByTypeApi(client);
		edu.vt.ece5574.agents.User user_sim = new edu.vt.ece5574.agents.User(null, "userId", "buildingId", false, 3, 5);
		
		try
		{
			byTypeApi.controllersDefaultControllerUsersDelete();
			String userId = storageAPIInterface.addUser(user_sim.getID(), user_sim);
			storageAPIInterface.deleteUser(userId);
			List<User> users = byTypeApi.controllersDefaultControllerUsersGet();

			assertTrue("Get users should return an empty list", users.isEmpty());
			
		}
		catch(ApiException e)
		{
			Assert.fail(e.getMessage());
		}
	}
	
	//This test
	@Test
	public void updSensorData()
	{
		ByTypeApi byTypeApi = new ByTypeApi(client);
		ByIDApi byIdApi = new ByIDApi(client);
		try
		{
			Sensor sensor = byTypeApi.controllersDefaultControllerSensorsPost();
			String sensorId = sensor.getId();
			
			String data = "Data chunk value is:" + Math.random();
			
			sensor.setData(data);
			storageAPIInterface.updSensorData(sensorId, data);
			Sensor updatedSensor = byIdApi.controllersDefaultControllerSensorsSensorIdGet(sensorId);

			assertEquals("Assert that the sensor data has been updated", sensor.getData(), updatedSensor.getData());
			assertEquals("Assert that the sensor buildingId is unchanged", sensor.getBuildingId(), updatedSensor.getBuildingId());
			assertEquals("Assert that the sensor floor is unchanged", sensor.getFloor(), updatedSensor.getFloor());
			assertEquals("Assert that the sensor room is unchanged", sensor.getRoom(), updatedSensor.getRoom());
			assertEquals("Assert that the sensor Xpos is unchanged", sensor.getXpos(), updatedSensor.getXpos());
			assertEquals("Assert that the sensor Ypos is unchanged", sensor.getYpos(), updatedSensor.getYpos());
			assertEquals("Assert that the sensor type is unchanged", sensor.getType(), updatedSensor.getType());
			
			byTypeApi.controllersDefaultControllerSensorsDelete();
		}
		catch(ApiException e)
		{
			Assert.fail(e.getMessage());
		}

	}
	
	// This test will remove all the buildings from whatever aws storage is being used
	@Test
	public void addBuildingsByTypeGetByTypeDeleteById()
	{
		storageAPIInterface.DeleteBuildings();
		
		String building_id_1 = storageAPIInterface.AddBuilding();
		String building_id_2 = storageAPIInterface.AddBuilding();
		String building_id_3 = storageAPIInterface.AddBuilding();
		
		List<Building> buildings = storageAPIInterface.GetBuildings();
		for(int i = 0; i < buildings.size(); i++)
		{
			assertTrue("Assert that the buildings stored correspond to the IDs sent",
					Objects.equals(buildings.get(i).getId(), building_id_1) || 
					Objects.equals(buildings.get(i).getId(), building_id_2) || 
					Objects.equals(buildings.get(i).getId(), building_id_3));
		}
		
		storageAPIInterface.DeleteBuildings();
		// maybe log that the buildings are deleted
		
		buildings = storageAPIInterface.GetBuildings();
		assertTrue("Asserting that get buildings returns an empty list when it is empty", buildings.isEmpty());
	}
	
    @Test
    public void updRobotPos()
    {	
		ByTypeApi byTypeApi = new ByTypeApi(client);
		ByIDApi byIdApi = new ByIDApi(client);
		ByBuildingApi byBuildingApi = new ByBuildingApi(client);
		
		try
		{
			Building building = byTypeApi.controllersDefaultControllerBuildingsPost();
			Robot robot = byBuildingApi.controllersDefaultControllerBuildingsBuildingIdRobotsPost(building.getId());
			String robotId = robot.getId();
			
			int new_x = (int)(Math.random() * 100); 
			int new_y = (int)(Math.random() * 100);
			
			BigDecimal new_xBD = new BigDecimal("" + new_x);
			BigDecimal new_yBD = new BigDecimal("" + new_y);
			
			robot.setXpos(new_xBD);
			robot.setYpos(new_yBD);
						
			storageAPIInterface.updRobotPos(robotId, new_x, new_y);
			Robot updatedRobot = byIdApi.controllersDefaultControllerRobotsRobotIdGet(robotId);
			assertEquals("Assert that the robot movement is unchanged", robot.getMovement(), updatedRobot.getMovement());
			assertEquals("Assert that the robot buildingId is unchanged", robot.getBuildingId(), updatedRobot.getBuildingId());
			assertEquals("Assert that the robot floor is unchanged", robot.getFloor(), updatedRobot.getFloor());
			assertEquals("Assert that the robot room is unchanged", robot.getRoom(), updatedRobot.getRoom());
			assertEquals("Assert that the robot Xpos is updated", robot.getXpos(), updatedRobot.getXpos());
			assertEquals("Assert that the robot Ypos is updated", robot.getYpos(), updatedRobot.getYpos());
			
			byTypeApi.controllersDefaultControllerRobotsDelete();
			byTypeApi.controllersDefaultControllerBuildingsDelete();
			
		}
		catch(ApiException e)
		{
			Assert.fail(e.getMessage());
		}	
    }
    
    @Test
    public void updUserPos()
    {   	
		ByTypeApi byTypeApi = new ByTypeApi(client);
		ByIDApi byIdApi = new ByIDApi(client);
		try
		{
			User user = byTypeApi.controllersDefaultControllerUsersPost();
			String userId = user.getId();
			
			int new_x = (int)(Math.random() * 100); 
			int new_y = (int)(Math.random() * 100);
			
			BigDecimal new_xBD = new BigDecimal("" + new_x);
			BigDecimal new_yBD = new BigDecimal("" + new_y);
			
			user.setXpos(new_xBD);
			user.setYpos(new_yBD);

			storageAPIInterface.updUserPos(userId, new_x, new_y);
			User updatedUser = byIdApi.controllersDefaultControllerUsersUserIdGet(userId);
			
			assertEquals("Assert that the user buildingId is unchanged", user.getBuildingId(), updatedUser.getBuildingId());
			assertEquals("Assert that the user floor is unchanged", user.getFloor(), updatedUser.getFloor());
			assertEquals("Assert that the user room is unchanged", user.getRoom(), updatedUser.getRoom());
			assertEquals("Assert that the user Xpos is updated", user.getXpos(), updatedUser.getXpos());
			assertEquals("Assert that the user Ypos is updated", user.getYpos(), updatedUser.getYpos());
			
			byTypeApi.controllersDefaultControllerUsersDelete();

		}
		catch(ApiException e)
		{
			Assert.fail(e.getMessage());
		}	
    }
    
    private BigDecimal int2BD(int i)
	{
		return new BigDecimal(i + "");
	}
}
