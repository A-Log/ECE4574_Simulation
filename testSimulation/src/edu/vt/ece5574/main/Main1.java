package edu.vt.ece5574.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


public class Main1 {

	public static void main(String[] args) {
		Brain brain = new Brain();
		//System.out.println(System.getProperty("user.dir"));
		//put user interface hooks here
		brain.init();
		brain.start();
		
		
		//brain.stop();

	}

}
