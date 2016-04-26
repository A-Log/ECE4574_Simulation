package edu.vt.ece5574.sim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class SimLogger {
	private final static Boolean DEBUG = false;						// Debug Flag - Set true for console outputs (sequential log only)
	private ArrayList<EntityAndStatus> entities =					// List objects and current status/additional information
			new ArrayList<EntityAndStatus>();
	private String newline = System.getProperty("line.separator");	// Newline character for current system
	private String textLogName = "";								// Name of sequential text log file 
	private String tabularLogName = "";								// Name of tabular log file
	
	// Object to hold entity name and status/additional information. 
	class EntityAndStatus {
		public String name;
		public String status;
		public String additionalInfo;
	}
	
	// Default constructor - this will create log files with the current time.
	public SimLogger() {
		Date date = new Date();
		String dateStr = String.valueOf(date.getTime());
		textLogName = "log_" + dateStr + ".txt";
		tabularLogName = "tabular_log" + dateStr +".txt";
	}
	
	// Log name constructor - this will create log files with the parameter name.
	public SimLogger(String logName){
		textLogName = logName;
		tabularLogName = "tabular_" + logName;
	}
	
	// You can debug outputs to the console by setting DEBUG = true above
	// This will only output the sequential log outputs, not the tabular outputs
	private void consoleDebugMessage(String str){
		System.getProperty("line.separator");
		if(DEBUG)
			System.out.println(str);
	}
	
	// Check to see if the name is represented in the list of entities -- add if not
	private void entityInList(String name, String status, String additionalInfo)
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i).name == name){
				entities.get(i).status = status;
				entities.get(i).additionalInfo = additionalInfo;
				return;
			}
		}
		EntityAndStatus newEntity = new EntityAndStatus();
		newEntity.name = name;
		newEntity.status = status;
		newEntity.additionalInfo = additionalInfo;
		entities.add(newEntity);
	}
	
	// Constructs a message for the text log
	public String constructMessage(String name, String newStatus, String additionalInfo){
		String message = name;
		if(!newStatus.isEmpty() && !additionalInfo.isEmpty())
			message += " changed status: " + newStatus + " (" + additionalInfo + ")" + newline;
		else if(!additionalInfo.isEmpty())
			message += ": " + additionalInfo + newline;
		else if(!newStatus.isEmpty())
			message += " changed status: " + newStatus + newline;
		return message;		
	}
	
	// Adds event to the text log
	public Boolean appendLogEvent(String name, String newStatus, String additionalInfo) {
		try
		{			
		    String message = constructMessage(name, newStatus, additionalInfo);
		    FileWriter fw = new FileWriter(textLogName, true); 
		    fw.write(message);
		    fw.close();
		    consoleDebugMessage(message);
		}
		catch(IOException e)
		{
		    System.err.println("IOException: " + e.getMessage());
		    return false;
		}
		return true;
	}
	
	// Adds header to the tabular log
	public Boolean adjustLogHeader(String name, String newStatus) {
		String newline = System.getProperty("line.separator");
		
		try
		{			
		    FileWriter fw = new FileWriter(tabularLogName, true); 
		    for(int i = 0; i < entities.size(); i++){
		    	fw.write("---------------------");
		    }
		    fw.write("-" + newline);
		    for(int i = 0; i < entities.size(); i++){
		    	fw.write("|");
		    	for(int j = 0; j < 20; j++)
		    	{
		    		if(entities.get(i).name.length() > j)
		    			fw.write(entities.get(i).name.charAt(j));
		    		else
		    			fw.write(" ");
		    	}			    	
		    }		
		    fw.write("|");
		    fw.write(newline);
		    for(int i = 0; i < entities.size(); i++){
		    	fw.write("---------------------");
		    }
		    fw.write("-" + newline);
		    fw.close();
		}
		catch(IOException e)
		{
		    System.err.println("IOException: " + e.getMessage());
		    return false;
		}
		return true;
	}
	
	// Adds events to the tabular log
	public Boolean appendVisualLog(String name, String newStatus) {
		try
		{			
		    FileWriter fw = new FileWriter(tabularLogName, true); 
		    for(int i = 0; i < entities.size(); i++){
		    	fw.write("|");
		    	for(int j = 0; j < 20; j++)
		    	{
		    		if(entities.get(i).status.length() > j)
		    			fw.write(entities.get(i).status.charAt(j));
		    		else
		    			fw.write(" ");
		    	}			    	
		    }		
		    fw.write("|" + newline);
		    fw.close();
		}
		catch(IOException e)
		{
		    System.err.println("IOException: " + e.getMessage());
		    return false;
		}
		return true;
	}
	
	// Public log function, anything that you want to show up in the tabular log should be
	// put into the second parameter (newStatus). Otherwise, construct a string externally
	// and pass additional information as the third parameter (additionalInfo).
	public void log(String name, String newStatus, String additionalInfo){		
		entityInList(name, newStatus, additionalInfo);
		appendLogEvent(name, newStatus, additionalInfo);
		if(newStatus != ""){
			adjustLogHeader(name, newStatus);
			appendVisualLog(name, newStatus);
		}
	}
}
