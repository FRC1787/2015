package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the Robot in autonomous mode.
 * @author jeremystark, ebencarek
 **/
public class Autonomous 
{
	/**
	 * The left and right encoders.
	 */
	private Encoder leftEncoder, rightEncoder;
	
	/**
	 * The array of both the left and right encoders.
	 */
	private Encoder[] encoders;
	
	/**
	 * The array of left motors.
	 */
	private final CANTalon[] leftMotors;
   
	/**
	 * The array of right motors.
	 */
	private final CANTalon[] rightMotors;
	
	/**
     * The instance of the drive class that actually
     * sends the movement values to the motors.
     */
    private final RobotDrive robotDrive;
	
	/**
	 * The array of pickup motors.
	 */
	private final CANTalon[] pickupMotors;
	
	/**
	 * The inputs for when the mechanism has reached its lower and upper limit.
	 */
	private DigitalInput bottomLimit, topLimit;
	
	/**
	 * Boolean values for when the mechanism has reached its lower and upper limit.
	 */
	private boolean bottomLimitReached = false, topLimitReached = false;
	
	/**
	 * Is anonymous mode active.
	 */
	private boolean active = false;
	
	/**
	 * The current speed.
	 */
	private double currentSpeed = 0;
	
	/**
	 * The current rotation.
	 */
	private double currentRotation = 0;
	
	/**
	 * Constructor for the Autonomous class, takes the port numbers instead of objects
	 * @param pickupMotorPort port for the pickup motor
	 * @param leftMotorPorts int array representing the ports of the left motors
	 * @param rightMotorPorts int array representing the ports of the right motors
	 * @param leftEncoderPorts int array representing the ports of the left encoders
	 * @param rightEncoderPorts int array representing the ports of the right encoders
	 * @param xboxController the shared instance of the xbox controller
	 */
	/*public Autonomous
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
    	
    	bottomLimit = new DigitalInput(0);
    	topLimit = new DigitalInput(1);
	}*/
	
	/**
	 * This constructor takes references to the actual objects used for driving instead
	 * of the ports used for instantiating them.
	 * @param pickupMotor
	 * @param leftMotors
	 * @param rightMotors
	 * @param leftEncoder
	 * @param rightEncoder
	 * @param bottomLimit
	 * @param topLimit
	 * @param xboxController
	 */
	public Autonomous(
			CANTalon[] pickupMotors,
			CANTalon[] leftMotors,
			CANTalon[] rightMotors,
			Encoder leftEncoder,
			Encoder rightEncoder,
			DigitalInput bottomLimit,
			DigitalInput topLimit,
			Joystick xboxController, 
			RobotDrive robotDrive
			)
	{
		this.pickupMotors = pickupMotors;
		this.leftMotors = leftMotors;
		this.rightMotors = rightMotors;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		this.bottomLimit = bottomLimit;
		this.topLimit = topLimit;
		
		encoders = new Encoder[] {this.leftEncoder, this.rightEncoder};
		
		this.robotDrive = robotDrive;
	}
	
	/**
	 * Set the pickup motor speeds.
	 * @param speed The speed.
	 */
	private void setPickupMotors(double speed)
	{
		pickupMotors[0].set(speed);
		pickupMotors[1].set(speed);
	}
	
	/**
	 * The periodic autonomous method for the robot that performs to specified autonomous actions
	 */
	public void autonomousPeriodic()
	{
		if (!active)
		{
			autonomousOptionTwoWithTimer();
			active = true;
		}
		
		// Drive
		robotDrive.arcadeDrive(currentSpeed, currentRotation, true);
	}
	
	/**
	 * Pick up can, drive forward, pick up tote, turn 90 degrees, drive to auto zone, set down tote and can
	 */
	@SuppressWarnings("unused")
	private void autonomousOptionOne()
	{
		pickupArmsRaise();
		driveForDistanceInInches(18.0); // 1.5 feet
		pickupArmsLower();
		Timer.delay(0.2);
		pickupArmsRaise();
		turnWithEncoders(false); // turns left
		driveForDistanceInInches(120.0); // 10 feet, change when real distance is determined
		pickupArmsLower();
	}
	
	/**
	 * Same as autonomousOptionOne() but uses timer delays instead of encoders
	 */
	private void autonomousOptionOneWithTimer()
	{
		Runnable runnable = new Runnable() {
			
			public void run()
			{
				pickupArmsRaise();
				driveForTimeInSeconds(2);
				pickupArmsLower();
				//Timer.delay(0.2);
				//pickupArmsRaise();
				turnWithTimerDelay(false);
				driveForTimeInSeconds(6);
				//pickupArmsLower();
			}
			
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	/**
	 * Push tote and can forward into auto zone without picking up either object
	 */
	@SuppressWarnings("unused")
	private void autonomousOptionTwo()
	{
		driveForDistanceInInches(120.0); // 10 feet, change when real distance is determined
	}
	
	private void autonomousOptionTwoWithTimer()
	{
		Runnable runnable = new Runnable() {
			
			public void run()
			{
				pickupArmsRaise();
				driveForTimeInSeconds(6.0);
				pickupArmsLower();
			}
			
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	/**
	 * Drive for a specified amount of time.
	 * @param seconds the amount of time before stopping
	 */
	private void driveForTimeInSeconds(double seconds)
	{
		driveMotorsWithMoveAndRotateValue(0.6, 0);
		Timer.delay(seconds);
		driveMotorsWithMoveAndRotateValue(0, 0);
	}
	
	/**
	 * Turns the robot left or right.
	 * @param right whether or not the robot should turn right
	 */
	private void turnWithTimerDelay(boolean right)
	{
		/*double rightMoveValue = right ? 0.5 : 0.1;
		double leftMoveValue = right ? 0.1 : 0.5;
		
		driveMotorsWithLeftAndRightValue(rightMoveValue, leftMoveValue);
		Timer.delay(1.5);
		driveMotorsWithLeftAndRightValue(0, 0);*/
		
		double moveValue = 0.4;
		double rotateValue = right ? 0.5 : -0.5;
		
		driveMotorsWithMoveAndRotateValue(moveValue, rotateValue);
		Timer.delay(3);
		driveMotorsWithMoveAndRotateValue(0, 0);
	}
	
	/**
	 * Raise pickup arms
	 */
	private void pickupArmsRaise()
	{
		while (!topLimitReached && topLimit.get())
		{
			setPickupMotors(1.0);
			
			Timer.delay(0.05);
			
			if (!topLimit.get())
			{
				topLimitReached = true;
			}
		}
		
		bottomLimitReached = false;
		
		setPickupMotors(0);
	}
	
	/**
	 * Raise pickup arms
	 */
	private void pickupArmsLower()
	{
		while (!bottomLimitReached && bottomLimit.get())
		{	
			setPickupMotors(-1.0);
			
			Timer.delay(0.05);
			
			if (!bottomLimit.get())
			{
				bottomLimitReached = true;
			}
		}
		
		topLimitReached = false;
		
		setPickupMotors(0);
	}
	
	/**
	 * Sets a move and rotate value for the motors
	 * @param moveValue
	 * @param rotateValue
	 */
	private void driveMotorsWithMoveAndRotateValue(double moveValue, double rotateValue)
	{
		//Utils.printPeriodic("Drive", "MoveValue: " + moveValue + " Rotate: " + rotateValue);
		//robotDrive.arcadeDrive(-moveValue, rotateValue, true);
		
		// Set the variables
		currentSpeed = -moveValue;
		currentRotation = rotateValue;
	}
	
	/**
	 * Set values for the left and right motors.
	 * @param leftMotorsValue the assumed speed of the left motors.
	 * @param rightMotorsValue the assumed speed of the right motors.
	 */
	private void driveMotorsWithLeftAndRightValue(double leftMotorsValue, double rightMotorsValue)
	{	
		robotDrive.tankDrive(-leftMotorsValue, -rightMotorsValue, true);
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
			driveMotorsWithLeftAndRightValue(0.75, 0.75);
			
			leftDistance = Math.abs(leftEncoder.getDistance());
			rightDistance = Math.abs(rightEncoder.getDistance());
			
			Timer.delay(0.1);
		}
		
		driveMotorsWithLeftAndRightValue(0, 0);
	}
	
	/**
	 * Turn 90 degrees in either direction, based on direction passed in
	 * @param direction true for right, false for left
	 */
	private void turnWithEncoders(boolean right)
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
			
			driveMotorsWithLeftAndRightValue(rightMoveValue, leftMoveValue);
			
			leftDistance = Math.abs(leftEncoder.getDistance());
			rightDistance = Math.abs(leftEncoder.getDistance());
			
			Timer.delay(0.1);
		}
		
		driveMotorsWithLeftAndRightValue(0, 0);
	}
}
