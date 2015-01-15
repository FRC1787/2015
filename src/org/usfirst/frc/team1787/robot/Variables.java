package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import java.io.*;

/**
 * @author jeremystark
 **/

//All variables will be placed in here for use everywhere in the project
public class Variables 
{
    //Public variable for is running;
    public static boolean isRunning;
    
    //Jaguar Motor Controlers
    public static CANJaguar leftMotor1;
    public static CANJaguar leftMotor2;
    public static CANJaguar rightMotor1;
    public static CANJaguar rightMotor2;
    public static CANJaguar pickupMotor;
    
    //General pneumatics
    public static Compressor compressor = new Compressor(1);
                                          //Compressor numbers need to be later
    
    //Gearbox subsystems
    public static DoubleSolenoid gearShifter = new DoubleSolenoid(4, 5);
    public static boolean shifterPosition;
    public static String shifterPosBoard;
    
    //Xbox Controller
    public static Joystick xboxController = new Joystick(1);
    
    //  Xbox controller button
    //  1 = A
    //  2 = B
    //  3 = X
    //  4 = Y
    //  5 = LB
    //  6 = RB
    //  7 = Back
    //  8 = Start
    //  9 = Left Stick Down
    //  10 = Right Stick Down
    //  X and Y axis = Left Stick
    //  X and Y Rotation = Right Stick
    //  Z Axis Positive = Left Trigger
    //  Z Axis Negative = Right Trigger
    
    
    //Robot drive controller
    public static RobotDrive robotDrive;
    
    //Public drive speed for robot. Modifiable from the driver station. <-- soon
    public static double driveSpeed = 0.5;
    
}
