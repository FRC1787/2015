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
    
	DriveController dc = new DriveController(11, 12, 14, 16, 0);
	
	public Robot()
    {
		Utils.print("Mayonnaise");
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
    }
    
    //Called when the robot enters test mode
    public void testPeriodic() 
    {
    }
}
