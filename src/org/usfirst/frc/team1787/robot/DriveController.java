package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * Controls the robot in tele-operated mode.
 * @author jeremystark, Eben Carek, Ryan Rule-Hoffman (sometimes)
 **/
public class DriveController
{
	// All constants should be declared at the top of files :)
	// Variables generally go in order of decreasing accessibility
	// ex. public, then protected, then private
	
	/**
	 * The motor speed for testing.
	 */
	public static final double TEST_MOTOR_SPEED = 0.3;
	
	/**
	 * Constants for different drive modes
	 * @author EbenCarek
	 *
	 */
	public enum DriveMode
	{
		DRIVE_MODE_NORMAL,
		DRIVE_MODE_TEST,
		DRIVE_MODE_TEST_MOTORS,
		DRIVE_MODE_INCREMENTAL
	}
	
	/**
	 * The current drive mode.
	 */
	private DriveMode driveMode;
	
	/**
     * Different possibilities for the driving state of the robot
     * @author EbenCarek
     *
     */
    private enum DriveState
    {
    	FORWARD,
    	BACKWARD,
    	NOT_MOVING
    }
    
    /**
     * The current drive state that the robot is in.
     */
    private DriveState driveState;
	
	/**
	 * The speed at which to multiply input; must be between 0 and 1.
	 */
	public static final double DRIVE_SPEED = 1.0;
	
	/**
	 * The speed that the robot rotates at.
	 */
	public static final double ROTATE_SPEED = 0.5;
	
	/**
	 * The increment size for the motor speed. 
	 */
	public static final double MOTOR_INCREMENT = 0.003333;
	
	/**
	 * The maximum motor speed
	 */
	public static final double MOTOR_MAX = DRIVE_SPEED;
	
	/**
	 * The minimum speed at which the motor moves
	 */
	public static final double MOTOR_MIN = 0.35;
	
	/**
	 * The left motors.
	 */
    private final CANTalon[] leftMotors;
    
    /**
     * The right motors.
     */
    private final CANTalon[] rightMotors;
    
    /**
     * The single instance of the Xbox controller
     * that is connected to the driver's station.
     */
    private final Joystick xboxController;
    
    /**
     * The instance of the drive class that actually
     * sends the movement values to the motors.
     */
    private final RobotDrive robotDrive;
    
    /**
     * Used for incrementing the motors' speed.
     */
    private double moveValue;
    
    /**
     * Used for incrementing the motors' speed.
     */
    private double rotateValue;
    
    /**
     * The encoders measuring the rotation of the motors
     */
    @SuppressWarnings("unused")
	private Encoder leftEncoder, rightEncoder;
   
    /**
     * Constructor for the DriveController class, takes port numbers
     * @param driveMode the mode for driving the robot
     * @param leftMotorPorts int array representing the left motor ports
     * @param rightMotorPorts int array representing the right motor ports
     * @param leftEncoderPorts int array representing the left encoder ports
     * @param rightEncoderPorts int array representing the right encoder ports
     * @param xboxController the shared instance of the xbox controller
     */
    public DriveController(
    		DriveMode driveMode, 
    		int[] leftMotorPorts, 
    		int[] rightMotorPorts, 
    		int[] leftEncoderPorts, 
    		int[] rightEncoderPorts, 
    		Joystick xboxController
    		)
    {
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
    	
    	// Create an instance of the Xbox Controller.
    	this.xboxController = xboxController;
    	
    	// Create instances of the encoders
    	this.leftEncoder = new Encoder(leftEncoderPorts[0], leftEncoderPorts[1], false, EncodingType.k4X);
    	this.rightEncoder = new Encoder(rightEncoderPorts[0], rightEncoderPorts[1], false, EncodingType.k4X);
    	
        robotDrive = new RobotDrive(leftMotors[0], leftMotors[1], rightMotors[0], rightMotors[1]);
        
        // Set driveState and driveMode
        this.driveMode = driveMode;
        this.driveState = DriveState.NOT_MOVING;
    }
    
    /**
     * Takes references to objects, not port numbers
     * @param driveMode
     * @param leftMotors
     * @param rightMotors
     * @param leftEncoder
     * @param rightEncoder
     * @param xboxController
     */
    public DriveController(
    		DriveMode driveMode,
    		CANTalon[] leftMotors,
    		CANTalon[] rightMotors,
    		Encoder leftEncoder,
    		Encoder rightEncoder,
    		Joystick xboxController
    		)
    {
    	this.driveMode = driveMode;
    	this.leftMotors = leftMotors;
    	this.rightMotors = rightMotors;
    	this.leftEncoder = leftEncoder;
    	this.rightEncoder = rightEncoder;
    	this.xboxController = xboxController;
    	
    	robotDrive = new RobotDrive(this.leftMotors[0], this.leftMotors[1], this.rightMotors[0], this.rightMotors[1]);
    	
    	this.driveState = DriveState.NOT_MOVING;
    }
    
    
    /**
     * Called 50 times a second during tele-operated mode.
     */
    public void drivePeriodic() 
    {
    	if (driveMode == DriveMode.DRIVE_MODE_TEST_MOTORS)
    	{
    		testMotors();
    		return;
    	}
    	
    	// Uncomment following line to print joy stick input to console
    	//Utils.printPeriodic("Joystick", "X: " + xboxController.getX() + " Y: " + xboxController.getY());
    	
    	double oldMoveValue = moveValue;
    	//double oldRotateValue = rotateValue;
    	
    	/**
    	 * Normally moveValue should be set to -xboxController.getY().
    	 * There is a wiring problem that causes left motors to spin one direction when set to a positive value
    	 * and right motors to spin in the opposite direction when passed the same value.
    	 * When driven at the same time, positive values cause both motors to drive backwards and negative values
    	 * cause motors to drive forwards. So for the time being, forwards is backwards, and backwards is forwards,
    	 * and moveValue is set to xboxController.getY().
    	 */
    	moveValue = xboxController.getY() * DRIVE_SPEED;
    	rotateValue = xboxController.getX() * ROTATE_SPEED;
    	
    	//Utils.printPeriodic("Drive Before", "moveValue: " + moveValue + " rotateValue: " + rotateValue);
    	
    	/**
    	 * Determines the DriveState of the robot
    	 */
    	if (moveValue > 0)
    	{
    		driveState = DriveState.FORWARD;
    	}
    	else if (moveValue < 0)
    	{
    		driveState = DriveState.BACKWARD;
    	}
    	else
    	{
    		driveState = DriveState.NOT_MOVING;
    	}
    	
    	/**
    	 * Incremental DriveMode
    	 */
    	if (driveMode == DriveMode.DRIVE_MODE_INCREMENTAL)
    	{
	    	if (driveState == DriveState.FORWARD)
	    	{
	    		if (moveValue < MOTOR_MIN)
	    		{
	    			moveValue = MOTOR_MIN;
	    		}
	    		else if (oldMoveValue < moveValue && oldMoveValue + MOTOR_INCREMENT < MOTOR_MAX)
	    		{	
	    			moveValue = oldMoveValue + MOTOR_INCREMENT;
	    		}
	    		// Uncomment the following to allow gradual deceleration
	    		else if (oldMoveValue > moveValue && oldMoveValue - MOTOR_INCREMENT > 0)
	    		{
	    			moveValue = oldMoveValue - MOTOR_INCREMENT;
	    		}
	    	}
	    	else if (driveState == DriveState.BACKWARD)
	    	{	
	    		if (moveValue > -MOTOR_MIN)
	    		{
	    			moveValue = -MOTOR_MIN;
	    		}
	    		else if (oldMoveValue > moveValue && oldMoveValue - MOTOR_INCREMENT > -MOTOR_MAX)
	    		{
	    			moveValue = oldMoveValue - MOTOR_INCREMENT;
	    		}
	    		// Uncomment the following to allow gradual deceleration
	    		else if (oldMoveValue < moveValue && oldMoveValue + MOTOR_INCREMENT < 0)
	    		{
	    			moveValue = oldMoveValue + MOTOR_INCREMENT;
	    		}
	    	}
	    	else if (driveState == DriveState.NOT_MOVING)
	    	{
	    		moveValue = 0;
	    	}
	    	
	    	// Uncomment following line to print move and rotate values to console
	    	//Utils.printPeriodic("Drive After", "moveValue: " + moveValue + " rotateValue: " + rotateValue);
	    	
	    	robotDrive.arcadeDrive(moveValue, rotateValue, true);
	    	
	    	// Do we really need this delay since this code is called 50x a second instead of in a while loop?
	        Timer.delay(0.01);
    	}
    	/**
    	 * Normal DriveMode
    	 */
    	else if (driveMode == DriveMode.DRIVE_MODE_NORMAL)
    	{
        	double adjustedMoveValue = 0.7 * moveValue;
    		
    		robotDrive.arcadeDrive(adjustedMoveValue, rotateValue, true);
            Timer.delay(0.01);
    	}
    }
    
    /* All testing methods should be below here - for Organization*/
    
    /**
     * Test the individual motors.
     */
    private void testMotors()
    {
    	if (xboxController.getX() > 0)
    	{
    		leftMotors[0].set(TEST_MOTOR_SPEED);
    		Utils.printPeriodic("MotorTest", "Testing left motor, index 0.");
    	}
    	else if (xboxController.getX() < 0)
    	{
    		leftMotors[1].set(TEST_MOTOR_SPEED);
    		Utils.printPeriodic("MotorTest", "Testing left motor, index 1.");
    	}
    	
    	if (xboxController.getY() > 0)
    	{
    		rightMotors[0].set(TEST_MOTOR_SPEED);
    		Utils.printPeriodic("MotorTest", "Testing right motor, index 0.");
    	}
    	else if (xboxController.getY() < 0)
    	{
    		rightMotors[1].set(TEST_MOTOR_SPEED);
    		Utils.printPeriodic("MotorTest", "Testing right motor, index 1.");
    	}
    }
}
