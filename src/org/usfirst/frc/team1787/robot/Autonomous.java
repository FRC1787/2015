package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * Controls the Robot in autonomous mode.
 * @author jeremystark, ebencarek
 **/
public class Autonomous 
{
	private Encoder leftEncoder, rightEncoder;
	private Encoder[] encoders = {leftEncoder, rightEncoder};
	
	private final CANTalon[] leftMotors;
   
	private final CANTalon[] rightMotors;
	
	private final CANTalon pickupMotor;
	
	private final DigitalInput bottomLimit = new DigitalInput(0), topLimit = new DigitalInput(1);
	
	private boolean bottomLimitReached = false, topLimitReached = false;
	
	private final Joystick xboxController;
	
	private boolean active = false;
	
	/**
	 * Constructor for the Autonomous class
	 * @param pickupMotorPort port for the pickup motor
	 * @param leftMotorPorts int array representing the ports of the left motors
	 * @param rightMotorPorts int array representing the ports of the right motors
	 * @param leftEncoderPorts int array representing the ports of the left encoders
	 * @param rightEncoderPorts int array representing the ports of the right encoders
	 * @param xboxController the shared instance of the xbox controller
	 */
	public Autonomous
		(
			int pickupMotorPort,
			int[] leftMotorPorts,
			int[] rightMotorPorts,
			int[] leftEncoderPorts,
			int[] rightEncoderPorts,
			Joystick xboxController
		)
	{
		this.leftEncoder = new Encoder(leftEncoderPorts[0], leftEncoderPorts[1], false, EncodingType.k4X);
    	this.rightEncoder = new Encoder(rightEncoderPorts[0], rightEncoderPorts[1], false, EncodingType.k4X);
    	
    	// set the distance per pulse of encoder in inches
    	this.leftEncoder.setDistancePerPulse(2.5133);
    	this.rightEncoder.setDistancePerPulse(2.5133);
    	
    	// Create instances of the left motors
    	leftMotors = new CANTalon[leftMotorPorts.length];
    	for (int i = 0; i < leftMotorPorts.length; i++)
    	{
    		leftMotors[i] = new CANTalon(leftMotorPorts[i]);
    	}
    	
    	// Create instances of the right motors 
    	rightMotors = new CANTalon[rightMotorPorts.length];
    	for (int i = 0; i < rightMotorPorts.length; i++)
    	{
    		rightMotors[i] = new CANTalon(rightMotorPorts[i]);
    	}
    	
    	// Create instances of the pickup motor
    	this.pickupMotor = new CANTalon(pickupMotorPort);
    	
    	this.xboxController = xboxController;
	}
	
	/**
	 * The periodic autonomous method for the robot that
	 * will drive forward when active.
	 */
	public void autonomousPeriodic()
	{
		if (!active)
		{
			driveForDistanceInInches(12);
			active = true;
		}
	}
	
	/**
	 * Pick up can, drive forward, pick up tote, turn 90 degrees, drive to auto zone, set down tote and can
	 */
	public void autonomousOptionOne()
	{
		pickupArmsRaise();
		driveForDistanceInInches(18.0); // 1.5 feet
		pickupArmsLower();
		pickupArmsRaise();
		turn(false); // turns left
		driveForDistanceInInches(120.0); // 10 feet, change when real distance is determined
		pickupArmsLower();
	}
	
	/**
	 * Push tote and can forward into auto zone without picking either up
	 */
	public void autonomousOptionTwo()
	{
		// 10 feet, change when real distance is determined
		driveForDistanceInInches(120.0);
	}
	
	/**
	 * Drive a set number of inches as measured by the encoders
	 * @param inches the distance in inches to be driven
	 */
	private void driveForDistanceInInches(double inches)
	{
		// resets the count of each encoder
		for (Encoder e : encoders)
		{
			e.reset();
		}
		
		// get initial distance of each encoder (should be 0)
		double leftDistance = Math.abs(leftEncoder.getDistance());
		double rightDistance = Math.abs(rightEncoder.getDistance());
		
		// while the distances are under the given number of inches, drive the motors forward and update the distance values
		while (leftDistance < inches && rightDistance < inches)
		{
			driveMotors(0.75, 0.75);
			
			leftDistance = Math.abs(leftEncoder.getDistance());
			rightDistance = Math.abs(rightEncoder.getDistance());
			
			Timer.delay(0.1);
		}
		
		driveMotors(0, 0);
	}
	
	/**
	 * turn 90 degrees in either direction, based on direction passed in
	 * @param direction true for right, false for left
	 */
	private void turn(boolean right)
	{
		for (Encoder e : encoders)
		{
			e.reset();
		}
		
		double leftDistance = Math.abs(leftEncoder.getDistance());
		double rightDistance = Math.abs(rightEncoder.getDistance());
		
		while (leftDistance < 18 && rightDistance < 18) // drive each side for 1.5 feet in opposite directions, needs testing
		{
			double rightMoveValue = right ? 0.5 : -0.5;
			double leftMoveValue = -rightMoveValue;
			
			driveMotors(rightMoveValue, leftMoveValue);
			
			leftDistance = Math.abs(leftEncoder.getDistance());
			rightDistance = Math.abs(leftEncoder.getDistance());
			
			Timer.delay(0.1);
		}
		
		driveMotors(0, 0);
	}
	
	private void pickupArmsRaise()
	{
		while (!topLimitReached && topLimit.get())
		{
			pickupMotor.set(1.0);
			
			Timer.delay(0.1);
			
			if (!topLimit.get())
			{
				topLimitReached = true;
			}
		}
		
		pickupMotor.set(0);
	}
	
	private void pickupArmsLower()
	{
		while (!bottomLimitReached && bottomLimit.get())
		{	
			pickupMotor.set(1.0);
			
			Timer.delay(0.1);
			
			if (!bottomLimit.get())
			{
				bottomLimitReached = true;
			}
		}
		
		pickupMotor.set(0);
	}
	
	/**
	 * Set values for the left and right motors.
	 * @param leftMotorsValue the assumed speed of the left motors.
	 * @param rightMotorsValue the assumed speed of the right motors.
	 */
	private void driveMotors(double leftMotorsValue, double rightMotorsValue)
	{	
		// make sure drive values are between -1 and 1 inclusive
		leftMotorsValue = leftMotorsValue > 0 ? Math.min(leftMotorsValue, 1) : Math.max(leftMotorsValue, -1);
		rightMotorsValue = rightMotorsValue > 0 ? Math.min(rightMotorsValue, 1) : Math.max(rightMotorsValue, -1);
		
		// set each motor
		for (CANTalon t : leftMotors)
		{
			t.set(leftMotorsValue);
		}
		
		for (CANTalon t : rightMotors)
		{
			t.set(rightMotorsValue);
		}
	}
}
