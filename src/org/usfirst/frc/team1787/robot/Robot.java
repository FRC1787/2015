/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team1787.robot;

import org.usfirst.frc.team1787.robot.DriveController.DriveMode;

import edu.wpi.first.wpilibj.*;

/**
 * The main robot class
 * @author Eben Carek, Jeremy Stark, Ryan Rule-Hoffman (sometimes)
 *
 */
public class Robot extends IterativeRobot 
{
	
    /**
     * The drive controller.
     */
	private DriveController driveController = null;
	
	/**
	 * The autonomous controller
	 */
	private Autonomous autonomous = null;
	
	/**
	 * The pickup controller.
	 */
	private PickupController pickupController;
	
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
		
		//Utils.print("XboxController: " + xboxController);
		
		// Create the PickupController
		pickupController = new PickupController(15, 0, 1, xboxController);
		
		// Create the Pneumatics
		pneumatics = new Pneumatics(1, xboxController);
		
		// Create the PowerManager
		powerManager = new PowerManager();
		
		/*
		 * Moved the initialization of driveController and autonomous objects to teleopInit() and autonomousInit()
		 * respectively to counter problems where both were being initiated in this constructor.
		 */
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
	 * The autonomous init code.
	 */
	public void autonomousInit()
	{
		// Create Autonomous object
		autonomous = new Autonomous(
				new int[] {13, 14},
				new int[] {12, 11},
				new int[] {6, 7},
				new int[] {8, 9},
				xboxController
				);
	}
	
	/**
	 * Called 50 times a second when the robot is in autonomous mode.
	 */
    public void autonomousPeriodic() 
    {
    	pneumatics.solenoidTest();
    }
    
    /**
     * called before teleop mode begins
     */
    public void teleopInit()
    {
		// Create the DriveController
		driveController = new DriveController(
				DriveMode.DRIVE_MODE_INCREMENTAL,
				new int[] {13, 14}, 
				new int[] {12, 11},
				new int[] {6, 7},
				new int[] {8, 9},
				xboxController
				);
    }
    
    /**
     * Called 50 times a second when the robot is in tele-operated mode.
     */
    public void teleopPeriodic()
    {
    	driveController.drivePeriodic();
    	pickupController.pickupPeriodic();
    	pneumatics.shiftingControls();
    }
    
    /**
     * Called when the robot enters test mode.
     */
    public void testInit() 
    {
    	//pneumatics.startCompressor();
    }
    
    /**
     * periodic testing method.
     */
    public void testPeriodic()
    {
    	//pneumatics.solenoidTest();
    }
}
