package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Users;

/**
 * Test cases for Users class
 * 
 * @author Vedahari Narasimhan Ganesan 
 * */

public class UsersTests {
	Users users;
	
	@Before
	public void init()
	{		
		users = new Users();
	}
	
	@Test
	public void isEmptyListAtTheBeginning() {		
		assertEquals(users.getNumberOfUsers(),0);
	}

	@Test
	public void creationOfNewUser() {		
		users.createNewUser();
		assertEquals(users.getNumberOfUsers(),1);
	}
	
	@Test
	public void checkgetAllUsersCount() {		
		users.createNewUser();
		assertEquals(users.getAllUsers().size(),users.getNumberOfUsers());		
	}
	
	@Test
	public void isUserAlreadyExisting() {		
		users.createNewUser();
		String id = users.getAllUsers().get(0).getID();
		assertEquals(users.isUserExisting(id), true);
		users.removeUser(id);
		assertEquals(users.isUserExisting(id), false);		
	}	
	
	@Test
	public void testClearAllUsers() {		
		users.removeAllUsers();
		assertEquals(users.getNumberOfUsers(),0);
	}
	
	@Test
	public void testDefaultAppInstalledUsers() {		
		users.createNewUser();
		users.createNewUser();
		assertEquals(users.getAppInstalledUsersID().size(),0);		
	}
	
	@Test
	public void testAppInstalledUsers() {		
		users.createNewUser();
		users.getAllUsers().get(0).setAppUser(true);
		String id = users.getAllUsers().get(0).getID();
		assertEquals(users.getAppInstalledUsersID().get(0),id);		
	}
	
	
	@Test
	public void testGetUsersInBuilding()
	{
		users.createNewUser();
		String id = users.getAllUsers().get(0).getID();
		String bid = "40";
		users.getAllUsers().get(0).setBuildingID(bid);
		assertEquals(users.getUsersInBuilding(bid).get(0),id);		
	}	
	
	@Test
	public void testRemovalOfUser()
	{
		users.createNewUser();
		String id = users.getAllUsers().get(0).getID();
		users.removeUser(id);
		assertEquals(users.isUserExisting(id), false);		
	}	
	
	
	@Test
	public void testUserCount() {		
		users.createNewUser();
		users.createNewUser();
		assertEquals(users.getNumberOfUsers(),2);
		testClearAllUsers();
	}	

}
