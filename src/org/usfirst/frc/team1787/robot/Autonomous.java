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
	
	private final Joystick xboxController;
	
	private boolean active = false;
	
	/**
	 * The default constructor for the Autonomous class
	 * @param leftEncoderPorts int array containing the ports for the left encoder.
	 * @param rightEncoderPorts int array containing the ports for the right encoder.
	 * @param leftMotorPorts int array for the left motor ports.
	 * @param rightMotorPorts int array for the right motor ports.
	 * @param xboxController the xboxController object instance.
	 */
	public Autonomous
		(
			int[] leftEncoderPorts,
			int[] rightEncoderPorts,
			int[] leftMotorPorts,
			int[] rightMotorPorts,
			Joystick xboxController
		)
	{
		this.leftEncoder = new Encoder(leftEncoderPorts[0], leftEncoderPorts[1], false, EncodingType.k4X);
    	this.rightEncoder = new Encoder(rightEncoderPorts[0], rightEncoderPorts[1], false, EncodingType.k4X);
    	
    	// set the distance per pulse of encoder in inches
    	this.leftEncoder.setDistancePerPulse(2.5133);
    	this.rightEncoder.setDistancePerPulse(2.5133);
    	
    	// Create instances of the left motor
    	leftMotors = new CANTalon[leftMotorPorts.length];
    	for (int i = 0; i < leftMotorPorts.length; i++)
    	{
    		leftMotors[i] = new CANTalon(leftMotorPorts[i]);
    	}
    	
    	// Create instances of the right moors 
    	rightMotors = new CANTalon[rightMotorPorts.length];
    	for (int i = 0; i < rightMotorPorts.length; i++)
    	{
    		rightMotors[i] = new CANTalon(rightMotorPorts[i]);
    	}
    	
    	this.xboxController = xboxController;
	}
	
	/**
	 * The periodic autonomous method for the robot that
	 * will drive forward when active.
	 */
	public void autonomousPeriodic()
	{
		if (xboxController.getRawButton(1) && !active)
		{
			driveOneFoot();
			active = true;
		}
	}
	
	/**
	 * Drive one foot as measured by the encoders.
	 * @author ebencarek
	 */
	public void driveOneFoot()
	{
		// resets the count of each encoder
		for (Encoder e : encoders)
		{
			e.reset();
		}
		
		// get initial distance of each encoder (should be 0)
		double leftDistance = Math.max(leftEncoder.getDistance(), -leftEncoder.getDistance());
		double rightDistance = Math.max(rightEncoder.getDistance(), -rightEncoder.getDistance());
		
		// while the distances are under 12 inches, drive the motors forward and update the distance values
		while (leftDistance < 12 && rightDistance < 12)
		{
			driveMotors(0.5, 0.5);
			
			leftDistance = Math.max(leftEncoder.getDistance(), -leftEncoder.getDistance());
			rightDistance = Math.max(rightEncoder.getDistance(), -rightEncoder.getDistance());
			
			Timer.delay(0.1);
		}
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
