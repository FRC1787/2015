package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the Pneumatics.
 * @author Jeremy, Eban, & ry60003333
 *
 */
public class Pneumatics 
{
	/**
	 * The compressor.
	 */
	private final Compressor compressor;
	
	/**
	 * The solenoid.
	 */
	private final Solenoid solenoid;
	
	/**
	 * Creates a new instance of Pneumatics.
	 */
	public Pneumatics()
	{
		this(1);
	}
	
	/**
	 * Creates a new instance of Pneumatics.
	 * @param compressorPort The port that the compressor is attached too.
	 */
	public Pneumatics(int compressorPort)
	{
		compressor = new Compressor(compressorPort);
		solenoid = new Solenoid(0);
	}
	
	/**
	 * Start the compressor.
	 */
	public void startCompressor()
	{
		compressor.start();
	}
	
	/**
	 * Test the solenoid.
	 */
	public void solenoidTest()
	{
		solenoid.set(true);
		Timer.delay(3);
		solenoid.set(false);
	}
}
