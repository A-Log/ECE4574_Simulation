package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.sim.ReadNotifications;
import edu.vt.ece5574.sim.Simulation;

public class RecvMsgSimIntegrationTest {

	static ReadNotifications testNotifications = new ReadNotifications();
	Simulation sim;
	Building bld;
	Robot rob1;
	
	@Before
	public void setUp(){
		sim = new Simulation(10);
		bld = new Building("0");
		rob1 = new Robot(sim,"1", "0");
		sim.addAgent(bld);
		sim.addAgent(rob1);
		testNotifications.deleteAll();
	}
	
	@BeforeClass
	public static void setUpClass() throws Exception {
	    String receiverPassword=new String("password");
	    
        String receiverUserName=new String("simulation.ece5574");
        testNotifications.setAccountDetails(receiverUserName, receiverPassword);
	}

	@Test
	public void testIncomingGmailToAgent() {
		
		//send mail
		String subject="Simulation_ECE5574";
		String text = "1";
		String result[]= new String [1];
		String expected[] = new String [1];
		expected[0]=text;
		
		sendGmail("test.ece5574@gmail.com","simulation.ece5574@gmail.com",subject+":"+text,text);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result= testNotifications.readGmail(sim);
        assertEquals(expected[0].toString(), result[0].toString());
        
        assertTrue(rob1.getMessageWaiting());
	}

	public void sendGmail(String from, String to, String subject, String text){
		 
        // This will send mail from -->sender@gmail.com to -->receiver@gmail.com

     
	   //specify parameters
       String sendingHost="smtp.gmail.com";
       int sendingPort=465;
       String username= "test.ece5574@gmail.com";
       String password= "password";

       Properties props = new Properties();

       props.put("mail.smtp.host", sendingHost);
       props.put("mail.smtp.port", String.valueOf(sendingPort));
       props.put("mail.smtp.user", username);
       props.put("mail.smtp.password", password);

       props.put("mail.smtp.auth", "true");

        Session session1 = Session.getDefaultInstance(props);

        Message simpleMessage = new MimeMessage(session1);

      

       InternetAddress fromAddress = null;
       InternetAddress toAddress = null;

       try {

           fromAddress = new InternetAddress(from);
           toAddress = new InternetAddress(to);

       } catch (AddressException e) {

           e.printStackTrace();

           System.out.println("Email failed");

       }

       try {

           simpleMessage.setFrom(fromAddress);
           simpleMessage.setRecipient(RecipientType.TO, toAddress);
           simpleMessage.setSubject(subject);
           simpleMessage.setText(text);
           Transport transport = session1.getTransport("smtps");
           transport.connect (sendingHost,sendingPort, username, password);
           transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());
           transport.close();
           System.out.println("Sent");

       } catch (MessagingException e) {

           e.printStackTrace();
           System.out.println("Email failed");
                      

       }

   }
}
