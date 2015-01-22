package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * @author jeremystark
 **/

public class DriveController 
{
	//Jaguar Motor Controllers
    private CANJaguar leftMotor1;
    private CANJaguar leftMotor2;
    private CANJaguar rightMotor1;
    private CANJaguar rightMotor2;
    
    private Joystick xboxController;
    
    private RobotDrive robotDrive;
    
    private boolean running = true;
    
    public static double DRIVE_SPEED = 0.5;
    
    //requires the port numbers for all motors and joystick number.
    public DriveController(int leftPort1, int leftPort2, int rightPort1, int rightPort2, int xboxStickNum)
    {
    	//left motors configured
    	leftMotor1 = new CANJaguar(leftPort1);
    	leftMotor2 = new CANJaguar(leftPort2);
    	
    	//right motors configured
    	rightMotor1 = new CANJaguar(rightPort1);
    	rightMotor2 = new CANJaguar(rightPort2);
    	
    	//Xbox Controller configured
    	xboxController = new Joystick(xboxStickNum);
    	
        robotDrive = new RobotDrive(leftMotor2, leftMotor1, rightMotor2, rightMotor1);
    }
    
    public void driveControls() 
    {   
    	Utils.print("X: " + xboxController.getX() + " Y: " + xboxController.getY());
    	
    	robotDrive.arcadeDrive(xboxController.getY() * DRIVE_SPEED, -xboxController.getX() * DRIVE_SPEED, true);
    	
    	/*if(xboxController.getRawButton(1))
    	{
    		leftMotor1.set(1);
    	}
    	else if (!xboxController.getRawButton(1))
    	{
    		leftMotor1.set(0);
    	}
    	
    	if(xboxController.getRawButton(2))
    	{
    		rightMotor1.set(1);
    	}
    	else if (!xboxController.getRawButton(2))
    	{
    		rightMotor1.set(0);
    	}*/
    	
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
