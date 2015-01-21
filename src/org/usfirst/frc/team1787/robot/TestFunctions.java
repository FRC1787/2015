package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class TestFunctions 
{

	private static Solenoid testSole;
	private static Compressor comp;
	
	public TestFunctions()
	{
    	testSole = new Solenoid(0);
    	comp = new Compressor();
	}
	
    public static void mainTesting()
    {
    	comp.start();
    	testSole.set(true);
    }
    
}
