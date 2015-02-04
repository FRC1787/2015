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
    Joystick xboxController = new Joystick(0);
	
	DriveController driveController = new DriveController(11, 12, 14, 16, this.getXboxController());
	PickupController pickupController = new PickupController(17, 0, 1, this.getXboxController());
	Pneumatics pneumatics = new Pneumatics();
	
	boolean pickupActive = false;
	
	public Joystick getXboxController()
	{
		return this.xboxController;
	}
	
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
    	driveController.tryDrive();
    }
    
    //Called 50x a second when the robot enters teleop mode
    public void teleopPeriodic()
    {
    	driveController.driveControls();
    	pickupController.pickupPeriodic();
    }
    
    //Called when the robot enters test mode
    public void testInit() 
    {
    	pneumatics.startCompressor();
    }
    
    public void testPeriodic()
    {
    	pneumatics.solenoidTest();
    }
}
