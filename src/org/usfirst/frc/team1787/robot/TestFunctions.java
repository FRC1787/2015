package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.Compressor;

/**
 * Contains testing functions.
 * @author Eban, Jeremy, & ry60003333
 */
public class TestFunctions 
{

	/**
	 * The compressor.
	 */
	private final Compressor compressor;

	/**
	 * Creates a new instance of TestFunctions.
	 */
	public TestFunctions()
	{
		compressor = new Compressor();
	}
	
	/**
	 * Test the compressor.
	 */
    public void mainTesting()
    {
    	compressor.start();
    }
    
}
