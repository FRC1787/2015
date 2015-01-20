/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

public class RobotMain extends IterativeRobot 
{
    
    public RobotMain()
    {
    	
    }
    
    public void robotInit()
    {
        System.out.println("Robot Init.");
        
        
    }
    
    //Called once when the robot enters autonomous mode
    public void autonomousInit() 
    {
        System.out.println("Autonomous Init.");
        
    }

    //Called when the robot enters teleop mode
    public void teleopInit() 
    {
        System.out.println("Teleop Init");
    }
    
    
    
    //Called when the robot enters test mode
    public void testInit() 
    {
        System.out.println("Test Init.");
    }
}
