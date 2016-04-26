/**
 * 
 */
package edu.vt.ece5574.agents;
import java.util.Calendar;


/**
 * ClockTime - Represents the clock to simulate time based events.
 * 
 * @author vedahari
 *
 */
public class ClockTime {	

	public static enum TIMEPERIOD {
	    UNKNOWN, MORNING, AFTERNOON, EVENING, NIGHT  
	}

	private int hours;
	private int minutes;
	private int seconds;
	
	
	/*
	 * 
	 */
	public int getHours() {
		return hours;
	}
	
	/**
	 * 
	 * @return
	 */

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}
	
	public ClockTime() {
		Calendar calendar = Calendar.getInstance();
		hours = calendar.get(Calendar.HOUR_OF_DAY);
		minutes  = calendar.get(Calendar.MINUTE);
		seconds  = calendar.get(Calendar.SECOND);		
	}
	
	/*
	 * Increments the clock time by the specified seconds.
	 *  
	 * @param 
	 */	
	@Override
	public String toString() {
		return "Clock==> " + String.format("%02d", hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds);
	}
	
	
	
	/**
	 * Increments the time by 'value' seconds
	 * @param value
	 */

	public void incrementTimeBySeconds(int value)
	{
		if (value < 0)
		{
			return;
		}
		seconds = seconds + value;
		if ((seconds/60)>0)
		{
			minutes += seconds/60;
		}
		seconds = seconds %60;
		if ((minutes/60)>0)
		{
			hours += minutes/60;
		}
		minutes = minutes % 60;
		hours = hours%24;
	}
	

	/***
	 * Sets the clock time to specified time.
	 * This function does not handle overflow of time units.
	 * If the value is greater than the maximum limit, the value is simply the modulus.
	 * @param hr
	 * @param min
	 * @param sec
	 */

	public void setTime(int hr, int min, int sec){
		hours = hr % 24;
		minutes = min % 60;
		seconds = sec % 60;
	}
	
	/***
	 * Returns the time period of the day as 
	 * MORNING, AFTERNOON, EVENING, NIGHT
	 * @return
	 */
	
	public TIMEPERIOD getTimePeriod(){
		if(hours >= 5 && hours < 12)
		{
			return TIMEPERIOD.MORNING;
		}
		else if (hours >=12 && hours < 17)
		{
			return TIMEPERIOD.AFTERNOON;
		}
		else if (hours >=17 && hours < 21)
		{
			return TIMEPERIOD.EVENING;
		}
		else if ((hours >= 21 && hours < 24)||((hours>=0 && hours <5)))
		{
			return TIMEPERIOD.NIGHT;
		}			
		return TIMEPERIOD.UNKNOWN;		
	}
	
}
