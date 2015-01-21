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
    
    public static Joystick xboxController;
    
    public static RobotDrive robotDrive;
    
    public static double driveSpeed = 0.5;
	
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
    	
        robotDrive = new RobotDrive(leftMotor1, rightMotor1, rightMotor2, leftMotor2);
    	
    	System.out.println("Constructor called");
    }
    
    public void driveControls() 
    {   
    	robotDrive.arcadeDrive
        (
            xboxController.getY() * driveSpeed, 
            -xboxController.getX() * driveSpeed, 
            true
        );
        Timer.delay(0.01);
    }
    
    public void tryDrive()
    {
    	leftMotor1.set(1);
    	leftMotor2.set(1);
    }
   
    public static void shiftingControls() 
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
