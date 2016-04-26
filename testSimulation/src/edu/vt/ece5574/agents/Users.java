package edu.vt.ece5574.agents;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * This class provides required information
 * about all the users in the simulated environment.
 * @author Vedahari Narasimhan Ganesan
 * */

public class Users {
	LinkedList<User> userList;
	
	public Users(){
		userList = new LinkedList<User>();
	}
	
	public LinkedList<User> getAllUsers()
	{
		return userList;
	}
	
	public User getUser(String id)
	{		
		for (int i=0; i<userList.size();++i)
		{
			if(userList.get(i).getID().equals(id))
			{
				//The building is found
				return userList.get(i);
			}
		}
		return null;
	}
	
	public void createNewUser()
	{
		
		userList.add(new User("1", "0"));
	}
	
	public boolean isUserExisting(String id)
	{		
		for (int i=0; i<userList.size();++i)
		{
			if(userList.get(i).getID().equals(id))
			{
				//The building is found
				return true;
			}
		}
		return false;
	}
	
	public void removeAllUsers()
	{		
		userList.clear();
	}	
	
	public void removeUser(String id)
	{
		for (int i=0; i<userList.size();++i)
		{
			if(userList.get(i).getID().equals(id))
			{
				//The building is found
				userList.remove(i);
			}
		}		
	}
	
	public ArrayList<String> getAppInstalledUsersID()
	{
		ArrayList<String> arrAppInstalledUsers = new ArrayList<String>();
		for (int i=0;i<userList.size();++i)
		{
			if (userList.get(i).isAppUser())
			{
				String userID = userList.get(i).getID();
				arrAppInstalledUsers.add(userID);
			}
		}		
		return arrAppInstalledUsers;		
	}
	
	public ArrayList<String> getUsersInBuilding(String buildingID)
	{
		ArrayList<String> arrUserID = new ArrayList<String>();
		for (int i=0;i<userList.size();++i)
		{
			if (userList.get(i).getBuildingID().equals(buildingID))
			{
				String userID = userList.get(i).getID();
				arrUserID.add(userID);
			}
		}		
		return arrUserID;		
	}
	
	public int getNumberOfUsers()
	{		
		return userList.size();
	}
}
