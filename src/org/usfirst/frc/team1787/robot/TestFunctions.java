package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.Compressor;

public class TestFunctions 
{

	private Compressor comp;

	public TestFunctions()
	{
    	comp = new Compressor();
	}
	
    public void mainTesting()
    {
    	comp.start();
    }
    
}
