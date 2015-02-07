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
	
    /**
     * The drive controller.
     */
	private DriveController driveController = new DriveController(
			new int[] {11, 12}, 
			new int[] {14, 16}, 
			this.getXboxController()
			);
	
	/**
	 * The pickup controller.
	 */
	private PickupController pickupController = new PickupController(17, 0, 1, this.getXboxController());
	
	/**
	 * The pneumatics.
	 */
	private Pneumatics pneumatics = new Pneumatics();
	
	/**
	 * The Xbox controller.
	 */
    private Joystick xboxController = new Joystick(0);
	
	/**
	 * Creates a new instance of Robot.
	 */
	public Robot()
    {
		Utils.print("Mayonnaise");
    }
	
	/**
	 * Get the Xbox controller.
	 * @return The Xbox controller.
	 */
	public Joystick getXboxController()
	{
		return this.xboxController;
	}
	
	/**
	 * Called when the robot turns on.
	 */
	public void robotInit()
	{
		
	}
    
	/**
	 * Called 50 times a second when the robot is in autonomous mode.
	 */
    public void autonomousPeriodic() 
    {
    	// TODO: Replace me
    	//driveController.tryDrive();
    }
    
    /**
     * Called 50 times a second when the robot is in tele-operated mode.
     */
    public void teleopPeriodic()
    {
    	driveController.driveControls();
    	pickupController.pickupPeriodic();
    }
    
    /**
     * Called when the robot enters test mode.
     */
    public void testInit() 
    {
    	pneumatics.startCompressor();
    }
    
    /**
     * ????
     */
    public void testPeriodic()
    {
    	pneumatics.solenoidTest();
    }
}
