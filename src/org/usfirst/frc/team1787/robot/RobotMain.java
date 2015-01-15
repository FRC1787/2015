/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;

public class RobotMain extends IterativeRobot 
{
    
    public RobotMain()
    {
        while(isEnabled())
        {
            System.out.println("Robot() isEnabled while called and running");  
        }
    }
    
    public void robotInit()
    {
        System.out.println("Robot Init called.");
        
        Variables.isRunning = true;
        
        /*while(Variables.isRunning)
        {
            //Anything that needs to be init when run
        }*/
        
        Variables.leftMotor1 = new CANJaguar(11);
        Variables.leftMotor2 = new CANJaguar(12);
        Variables.rightMotor1 = new CANJaguar(14);
        Variables.rightMotor2 = new CANJaguar(16);
        Variables.pickupMotor = new CANJaguar(6);
        //Motors set up
        Variables.robotDrive = new RobotDrive
        (
            Variables.leftMotor1, Variables.leftMotor2, 
            Variables.rightMotor1, Variables.rightMotor2
        );              
    }
    
    //Called once when the robot enters autonomous mode
    public void autonomousInit() 
    {
        System.out.println("The robot has entered auton mode");
        
        Autonomous.lowerArms();
        Autonomous.driveForeward();
    }

    //Called when the robot enters teleop mode
    public void teleopInit() 
    {
        while (isOperatorControl() && isEnabled()) 
        {
            if(Variables.shifterPosition == true)
            {
                Variables.shifterPosBoard = "Gear 1";
            } 
            
            else if(Variables.shifterPosition == false)
            {
                Variables.shifterPosBoard = "Gear 2";
            }
        }
    }
    
    //Called when the robot enters test mode
    public void testInit() 
    {
        System.out.println("Robot has entered test mode.");
    }
}
