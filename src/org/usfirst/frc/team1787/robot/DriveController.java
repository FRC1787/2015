package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the robot in tele-operated mode.
 * @author jeremystark, ebencarek, Ryan Rule-Hoffman (sometimes)
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
	 * The constant for the normal drive mode.
	 */
	public static final int DRIVE_MODE_NORMAL = 0;
	
	/**
	 * The constant for the test drive mode.
	 */
	public static final int DRIVE_MODE_TEST = 1;
	
	/**
	 * The constant for the test motors drive mode.
	 */
	private static final int DRIVE_MODE_TEST_MOTORS = 2;
	
	/**
	 * The current drive mode.
	 */
	public static final int DRIVE_MODE = DRIVE_MODE_TEST_MOTORS;
	
	/**
	 * The speed at which to multiply input; must be between 0 and 1.
	 */
	public static final double DRIVE_SPEED = 0.5;
	
	/**
	 * ??????
	 */
	public static final double MOTOR_INCREMENT = 0.003333;
	
	/**
	 * ??????
	 */
	public static final double MOTOR_MAX = 0.5;
	
	/**
	 * The left motors.
	 */
    private final Talon leftMotors[];
    
    /**
     * The right motors.
     */
    private final Talon rightMotors[];
    
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
     * ???????
     */
    private double moveValue;
    
    /**
     * ???????
     */
    private double rotateValue;
   
    /**
     * Creates a new DriveController.
     * @param leftPorts The left ports for the motors.
     * @param rightPorts The right ports for the motors.
     * @param xboxController The Xbox controller instance.
     */
    public DriveController(int[] leftPorts, int[] rightPorts, Joystick xboxController)
    {
    	// Create instances of the left motor
    	leftMotors = new Talon[leftPorts.length];
    	for (int i = 0; i < leftPorts.length; i++)
    	{
    		leftMotors[i] = new Talon(leftPorts[i]);
    	}
    	
    	// Create instances of the right moors 
    	rightMotors = new Talon[rightPorts.length];
    	for (int i = 0; i < rightPorts.length; i++)
    	{
    		rightMotors[i] = new Talon(rightPorts[i]);
    	}
    	
    	// Create an instance of the Xbox Controller.
    	this.xboxController = xboxController;
    	
    	/*
    	 * Tested each set of Jaguars individually, determined that both sets work.
    	 * Switched order in constructor (back motors passed in as front motors, and vice versa)
    	 * and all four work correctly
    	 * 
    	 * Tried once more with original configuration, and it works properly. Must be electrical
    	 * error.
    	 */
    	
        robotDrive = new RobotDrive(leftMotors[1], leftMotors[0], rightMotors[1], rightMotors[0]);
    }
    
    /**
     * Test the individual motors.
     */
    private void testMotors()
    {
    	if (xboxController.getX() > 0)
    	{
    		leftMotors[0].set(TEST_MOTOR_SPEED);
    		Utils.printPeridoc("MotorTest", "Testing left motor, index 0.");
    	}
    	else if (xboxController.getX() < 0)
    	{
    		leftMotors[1].set(TEST_MOTOR_SPEED);
    		Utils.printPeridoc("MotorTest", "Testing left motor, index 1.");
    	}
    	
    	if (xboxController.getY() > 0)
    	{
    		rightMotors[0].set(TEST_MOTOR_SPEED);
    		Utils.printPeridoc("MotorTest", "Testing right motor, index 0.");
    	}
    	else if (xboxController.getY() < 0)
    	{
    		rightMotors[1].set(TEST_MOTOR_SPEED);
    		Utils.printPeridoc("MotorTest", "Testing right motor, index 1.");
    	}
    }
    
    /**
     * Called 50 times a second during tele-operated mode.
     */
    public void drivePeriodic() 
    {
    	
    	if (DRIVE_MODE == DRIVE_MODE_TEST_MOTORS)
    	{
    		testMotors();
    		return;
    	}
    	
    	// Uncomment following line to print joy stick input to console
    	Utils.printPeridoc("Drive", "X: " + xboxController.getX() + " Y: " + xboxController.getY());
    	
    	double oldMoveValue = moveValue;
    	//double oldRotateValue = rotateValue;
    	
    	moveValue = -xboxController.getY() * DRIVE_SPEED;
    	rotateValue = -xboxController.getX() * DRIVE_SPEED;
    	
    	if (DRIVE_MODE == DRIVE_MODE_NORMAL)
    	{
	    	if (oldMoveValue < moveValue && oldMoveValue + MOTOR_INCREMENT < MOTOR_MAX)
	    	{	
	    		moveValue = oldMoveValue + MOTOR_INCREMENT;
	    	}
    	}
    	
    	robotDrive.arcadeDrive(moveValue, rotateValue, true);
    	
        Timer.delay(0.01);
    }
    
   
    /**
     * Xbox controller reference.
     */
    public void shiftingControls() 
    {
        //Shifting controls
        /*if (xboxController.getRawButton(5))
        {
            gearShifter.set(DoubleSolenoid.Value.kForward);
            shifterPosition = false;
        }

        else if (xboxController.getRawButton(6))
        {
            gearShifter.set(DoubleSolenoid.Value.kReverse);
            shifterPosition = true;
        }*/
    }  
}
