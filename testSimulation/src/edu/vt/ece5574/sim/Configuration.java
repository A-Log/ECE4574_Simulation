package edu.vt.ece5574.sim;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
	


//Maybe we shouldn't do json or XML?  There's a java properties file format
//that might be of interest: 
//http://cs.gmu.edu/~eclab/projects/mason/extensions/webtutorial1/#param-file
//Honestly, that seems like a smart decision but it's up to the developer
//
//You should instantiate agents here on loading the config and provided a way 
//to get them back to main through function calls of some sort
//
//Should seed, time and other cmd line args be placed in the config or fed in 
//through the config file?  The config file will take some circumventing of 
//MASON's built in functionality but is possible.  Just annoying. 

/**
 * Basic class for configurations
 * @author Siddharth Bhal
 *
 */
public class Configuration {
	
	
	InputStream inputStream;
	public static File file;
	public static Properties allProp = new Properties();


	public void load(String path) throws InvalidPropertiesFormatException, IOException{
		
	    file = new File(path);
		FileInputStream fileInput = new FileInputStream(file);
		allProp.loadFromXML(fileInput);
		fileInput.close();
		
		
	}
	
	
	
	public String getProp(String propName){
		String property = null;
		if(allProp != null){
			property =  allProp.getProperty(propName);
		}
		if(property == null) {
			System.err.println("Failed to load property named " + propName);
        }
		return property;
	}
}
