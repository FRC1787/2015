package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.DriverStation;
import java.util.HashMap;

/**
 * Various utilities.
 * @author jeremystark & ryan & eben
 */
public class Utils
{
	
	/**
	 * The map of IDs to timestamps.
	 */
	private static HashMap timestamps;
	
	/**
	 * Static initializers.
	 */
	static
	{
		timestamps = new HashMap();
	}
	
	/**
	 * Print text to the Driver's Station.
	 * @param text The text to print.
	 */
	public static void print(String text)
	{
		DriverStation.reportError(text + "\n", false);
	}
	
	/**
	 * Print a message periodically.
	 * @param name The name for the message for rate limiting.
	 * @param text The text to print.
	 */
	public static void printPeridoc(String name, String text)
	{
		// Grab the last time that a message with this name was printed
		Object timeObject = timestamps.get(name);
		long time = 0;
		
		// If a message with this name has never been printed, 
		// set the last time to 0.
		if (timeObject == null)
		{
			time = 0;
		}
		else
		{
			time = ((Long)timeObject).longValue();
		}
		
		// If more then 1500 MS have passed since last printing this message, 
		// print out the message.
		if (System.currentTimeMillis() - time >= 1500)
		{
			DriverStation.reportError("[" + name + "]" + text + "\n", false);
			timestamps.put(name, new Long(System.currentTimeMillis()));
		}
	}

}
