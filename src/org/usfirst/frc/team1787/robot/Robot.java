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
	private final DriveController driveController;
	
	/**
	 * The pickup controller.
	 */
	private final PickupController pickupController;
	
	/**
	 * The pneumatics.
	 */
	private final Pneumatics pneumatics;
	
	/**
	 * The Xbox controller.
	 */
    private final Joystick xboxController;
    
    /**
     * The manager of power.
     */
    private final PowerManager powerManager;
	
	/**
	 * Creates a new instance of Robot.
	 */
	public Robot()
    {
		// Initialize the XboxController.
		xboxController = new Joystick(0);
		
		Utils.print("XboxController: " + xboxController);
		
		// Create the DriveController
		driveController = new DriveController(
				new int[] {13, 14}, 
				new int[] {12, 11}, 
				xboxController
				);
		
		// Create the PickupController
		pickupController = new PickupController(17, 0, 1, xboxController);
		
		// Create the Pneumatics
		pneumatics = new Pneumatics();
		
		// Create the PowerManager
		powerManager = new PowerManager();
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
		powerManager.initPower();
		pneumatics.initPneumatics();
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
    	driveController.drivePeriodic();
    	//pickupController.pickupPeriodic();
    }
    
    /**
     * Called when the robot enters test mode.
     */
    public void testInit() 
    {
    	//pneumatics.startCompressor();
    }
    
    /**
     * ????
     */
    public void testPeriodic()
    {
    	//pneumatics.solenoidTest();
    }
}
