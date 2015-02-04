package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * @author jeremystark, ebencarek
 **/

public class DriveController 
{
	//Jaguar Motor Controllers
    private Talon leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    
    private Joystick xboxController;
    
    private RobotDrive robotDrive;
    
    private double moveValue, rotateValue;
    
    private boolean running = true;
    
    public static final double DRIVE_SPEED = 0.5;
    
    //requires the port numbers for all motors and joystick number.
    public DriveController(int leftPort1, int leftPort2, int rightPort1, int rightPort2, Joystick xboxController)
    {
    	//left motors configured
    	leftMotor1 = new Talon(leftPort1);
    	leftMotor2 = new Talon(leftPort2);
    	
    	//right motors configured
    	rightMotor1 = new Talon(rightPort1);
    	rightMotor2 = new Talon(rightPort2);
    	
    	//Xbox Controller configured
    	this.xboxController = xboxController;
    	
    	/*
    	 * Tested each set of Jaguars individually, determined that both sets work.
    	 * Switched order in constructor (back motors passed in as front motors, and vice versa)
    	 * and all four work correctly
    	 * 
    	 * Tried once more with original configuration, and it works properly. Must be electrical
    	 * error.
    	 */
    	
        robotDrive = new RobotDrive(leftMotor2, leftMotor1, rightMotor2, rightMotor1);
    	//robotDrive = new RobotDrive(leftMotor2, rightMotor2);
    }
    
    public void driveControls() 
    {   
    	// uncomment following line to print joystick input to console
    	//Utils.print("X: " + xboxController.getX() + " Y: " + xboxController.getY());
    	
    	double oldMoveValue = moveValue;
    	//double oldRotateValue = rotateValue;
    	
    	final double MOTOR_INCREMENT = 0.003333;
    	final double MOTOR_MAX = 0.5;
    	
    	moveValue = -xboxController.getY() * DRIVE_SPEED;
    	rotateValue = -xboxController.getX() * DRIVE_SPEED;
    	
    	if (oldMoveValue < moveValue && oldMoveValue + MOTOR_INCREMENT < MOTOR_MAX)
    	{	
    		moveValue = oldMoveValue + MOTOR_INCREMENT;
    	}
    	
    	robotDrive.arcadeDrive(moveValue, rotateValue, true);
    	
        Timer.delay(0.01);
    }
    
    public void tryDrive()
    {	
    	while(running)
    	{
    		leftMotor1.set(1);
    	}
    }
   
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
