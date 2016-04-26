
/**@author Saket
/*
 This class implements methods to read push notification from email:simulation.ece5574@gmail.com
 The format of the email expected is as follows:
 Subject: Simulation_ECE5574
 Content:
 <userID>

 Please refer TestReceiveNotifications for sending sample emails

 Usage:
 ReadNotifications notifications = new ReadNotifications();
 notifications.setAccountDetails("simulation.ece5574@gmail.com", "password");
 result= testNotifications.readGmail(sim);

 sim is the object of class Simulation which this API uses
 Please talk to the team for the password of the user account
  */
package edu.vt.ece5574.sim;

import java.util.*;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import sim.engine.SimState;
import sim.engine.Steppable;

import javax.mail.Flags;

public class ReadNotifications implements Steppable {

	private static final long serialVersionUID = 1;
	private String userName;
	private String password;
	private String receivingHost;

	public void setAccountDetails(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public void deleteAll() {

		this.receivingHost = "imap.gmail.com";
		Properties props2 = System.getProperties();
		props2.setProperty("mail.store.protocol", "imaps");

		Session session2 = Session.getDefaultInstance(props2, null);

		try {
			Store store = session2.getStore("imaps");
			store.connect(this.receivingHost, this.userName, this.password);

			Folder folder = store.getFolder("INBOX");// get inbox
			folder.open(Folder.READ_WRITE);// open folder

			Message message[] = folder.getMessages();
			for (int i = 0; i < message.length; i++) {
				// go through all mails
				message[i].setFlag(Flags.Flag.DELETED, true); // delete messages
			}
			folder.close(true);

			store.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String[] readGmail(Simulation sim) {
		this.receivingHost = "imap.gmail.com";
		Properties props2 = System.getProperties();
		props2.setProperty("mail.store.protocol", "imaps");

		Session session2 = Session.getDefaultInstance(props2, null);

		try {
			Store store = session2.getStore("imaps");
			store.connect(this.receivingHost, this.userName, this.password);

			Folder folder = store.getFolder("INBOX");// get inbox
			folder.open(Folder.READ_WRITE);// open folder

			Message message[] = folder.getMessages();
			String result[] = new String[message.length];
			for (int i = 0; i < message.length; i++) {

				// go through all mails

				String[] subject = message[i].getSubject().split(":");
				if (subject.length == 2) {
					if (subject[0].equals("Simulation_ECE5574")) {

						if (subject[1] != null) {
							// //delete message
							if (sim.agentPushReceived(subject[1])) {
								System.out.println("push notification received..querying back to server");
								message[i].setFlag(Flags.Flag.DELETED, true); // delete message
							}
						}
					}
					result[i] = subject[1];
				}
			}
			folder.close(true);

			store.close();
			return result;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// return null if no results
		return null;
	}

	@Override
	public void step(SimState state) {
		readGmail((Simulation) state);
	}

}
