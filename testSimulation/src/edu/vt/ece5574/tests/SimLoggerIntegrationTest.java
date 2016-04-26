package edu.vt.ece5574.tests;
import static org.junit.Assert.*;
import java.io.*;
import edu.vt.ece5574.sim.SimLogger;

import org.junit.Test;

public class SimLoggerIntegrationTest {

	private String[] expectedAnswers = {
		"robot1 changed status: idle"
	};
	
	@Test
	public void test() {
		assertEquals(0, 0);
		//fail("Not yet implemented");
	}
	
	@Test
	public void sequentialIntegrationTest() {
		SimLogger logger = new SimLogger("test.txt");	// Generate logger with test log files
		String line = null;
		logger.log("robot1", "idle", "");
		try {
			FileReader fr = new FileReader("test.txt");
			BufferedReader br = new BufferedReader(fr);
			//debug
			line = br.readLine();
			assertEquals(line, expectedAnswers[0]);
			br.close();
			fr.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("resource")
	private Boolean validateFiles(String file1, String file2)
	{
		String line1, line2 = null;
		try {
			FileReader fr1 = new FileReader(file1);
			BufferedReader br1 = new BufferedReader(fr1);
			FileReader fr2 = new FileReader(file2);
			BufferedReader br2 = new BufferedReader(fr2);
			//debug
			while((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null){
				char[] line1char = line1.toCharArray();
				char[] line2char = line2.toCharArray();
				if(line1char.length != line2char.length){
					System.out.println("False: \n" + line1 + "\n" + line2 + "\n");
					return false;
				}
				else {
					for(int i = 0; i < line1char.length; i++){
						if(line1char[i] != line2char[i])
							return false;
					}
				}
			}
			
			br1.close();
			fr1.close();
			br2.close();
			fr2.close();
			
			return true;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	@Test
	public void tabularIntegrationTest() {
		SimLogger LOGGER = new SimLogger("Tabular-Test.txt");
		// Add Robot 1 To the 
		LOGGER.log("Robot1", "Extinguishing Fire", "Position Changed from 1 to 2");
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "step_0_tabular_test.txt"), true);
		LOGGER.log("robot2", "Other Event", "Other Event");
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "step_1_tabular_test.txt"), true);
		LOGGER.log("robot3", "Third Event", "Other Event");	
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "step_2_tabular_test.txt"), true);
		LOGGER.log("Robot1", "Waiting", "Position Changed from 1 to 2");
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "step_3_tabular_test.txt"), true);
		LOGGER.log("robot2", "New Event", "Other Event");
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "step_4_tabular_test.txt"), true);
		LOGGER.log("robot4", "Fourth Robot", "Other Event");
		assertEquals(validateFiles("tabular_Tabular-Test.txt", "final_tabular_test.txt"), true);
	}

}
