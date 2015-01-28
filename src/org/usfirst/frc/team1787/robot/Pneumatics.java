package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

public class Pneumatics 
{
	private Compressor compressor;
	private Solenoid solenoid;
	
	public Pneumatics()
	{
		this(1);
	}
	
	public Pneumatics(int compNum)
	{
		compressor = new Compressor(compNum);
		solenoid = new Solenoid(0);
	}
	
	public void startCompressor()
	{
		compressor.start();
	}
	
	public void solenoidTest()
	{
		solenoid.set(true);
		Timer.delay(3);
		solenoid.set(false);
	}
}
