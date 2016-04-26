package edu.vt.ece5574.sim;

/**
 * @author Owen Nugent
 *
 */

import io.swagger.client.*;
import io.swagger.client.api.*;
import io.swagger.client.model.*;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

public class StorageAPI {
	
	private String basePath;
	ApiClient client;
	
	public StorageAPI()
	{
		basePath = new String ( "http://localhost:8080/api" );
		client = new ApiClient();
		client.setBasePath(basePath);
	}
	
	public StorageAPI(String basePath)
	{
		this.basePath = basePath;
		client = new ApiClient();
		client.setBasePath(basePath);
	}
		
	public String addUser(String buildingId, edu.vt.ece5574.agents.User userIn)
	{
		User user = new User();
		ByTypeApi byTypeApi = new ByTypeApi(client);
		ByIDApi byIdApi = new ByIDApi(client);
		try
		{
			user = byTypeApi.controllersDefaultControllerUsersPost();
			String userId = user.getId();
			user.setXpos(int2BD(userIn.getLocation().x));
			user.setYpos(int2BD(userIn.getLocation().y));
			
			User userToSend = new User();
			userToSend.setBuildingId(null);//userToSendIn.getBuildingID());
			userToSend.setFloor(null);
			userToSend.setMessage(null);
			userToSend.setOwner(null);
			userToSend.setRoom(null);
			userToSend.setXpos(int2BD(userIn.getLocation().x));
			userToSend.setYpos(int2BD(userIn.getLocation().y));
			userToSend.setId(null);
			byIdApi.controllersDefaultControllerUsersUserIdPut(userId, userToSend);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		return user.getId();
	}
	
	public boolean deleteUser(String userId)
	{
		ByIDApi api = new ByIDApi(client);
		try
		{
			api.controllersDefaultControllerUsersUserIdDelete(userId);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean updSensorData ( String sensorId, String data )
	{
		Sensor sensor = new Sensor();
		sensor.setData(data);
		
		ByIDApi api = new ByIDApi(client);
		try
		{
			api.controllersDefaultControllerSensorsSensorIdPut(sensorId, sensor);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean updRobotPos ( String robotId , int xpos , int ypos )
	{
		Robot robot = new Robot();
		robot.setXpos(int2BD(xpos));
		robot.setYpos(int2BD(ypos));
		
		ByIDApi api = new ByIDApi(client);
		try
		{
			api.controllersDefaultControllerRobotsRobotIdPut(robotId, robot);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean updUserPos ( String userID , int xpos , int ypos ) 
	{
		User user = new User();
		user.setXpos(int2BD(xpos));
		user.setYpos(int2BD(ypos));
		
		ByIDApi api = new ByIDApi(client);
		try
		{
			System.out.println(user.toString());
			api.controllersDefaultControllerUsersUserIdPut(userID, user);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	// returns the building id
	public String AddBuilding()
	{
		ByTypeApi obj1 = new ByTypeApi(client);
		Building b;
		try
		{
			b = obj1.controllersDefaultControllerBuildingsPost();
		}
		catch(ApiException e)
		{
			return e.getMessage();
		}
		return b.getId();
	}
	
	public List<Building> GetBuildings()
	{
		ByTypeApi api = new ByTypeApi(client);
		List<Building> buildings;
		try
		{
			buildings = api.controllersDefaultControllerBuildingsGet();
		}
		catch(ApiException e)
		{
			// log something, maybe we should just throw the exception
			return new ArrayList<Building>();
		}
		return buildings;
	}
	
	// deletes all buildings
	public String DeleteBuildings()
	{
		ByTypeApi api = new ByTypeApi(client);
		String s;
		try
		{
			s = api.controllersDefaultControllerBuildingsDelete();
		}
		catch(ApiException e)
		{
			return e.getMessage();
		}
		return s;
	}

	private BigDecimal int2BD(int i)
	{
		return new BigDecimal(i + "");
	}



}