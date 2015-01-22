package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Various utilities.
 * @author jeremystark & ryan & eben
 *
 */
public class Utils
{
	
	
	/**
	 * Print text to the Driver's Station.
	 * @param text The text to print.
	 */
	public static void print(String text)
	{
		DriverStation.reportError(text + "\n", false);
	}

}
