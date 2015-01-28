/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

public class Robot extends IterativeRobot 
{
    
	DriveController dc = new DriveController(11, 12, 14, 16, 17, 0);
	
	Pneumatics pn = new Pneumatics();
	
	boolean pickupActive = false;
	
	public Robot()
    {
		Utils.print("Mayonnaise");
    }
	
	public void RobotInit()
	{
		
	}
    
    //Called once when the robot enters autonomous mode
    public void autonomousPeriodic() 
    {
    	dc.tryDrive();
    }
    
    //Called 50x a second when the robot enters teleop mode
    public void teleopPeriodic()
    {
    	dc.driveControls();
    	
    	if (dc.getXboxController().getRawButton(1))
    	{
    		if (!pickupActive)
    		{
    			dc.pickupFunction();
    			pickupActive = true;
    		}
    	}
    	else
    	{
    		pickupActive = false;
    	}
    }
    
    //Called when the robot enters test mode
    public void testInit() 
    {
    	pn.startCompressor();
    }
    
    public void testPeriodic()
    {
    	pn.solenoidTest();
    }
}
