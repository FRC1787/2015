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
	
	private final CANTalon[] leftMotors;
   
	private final CANTalon[] rightMotors;
	
	public Autonomous(
			int[] leftEncoderPorts,
			int[] rightEncoderPorts,
			int[] leftMotorPorts,
			int[] rightMotorPorts)
	{
		this.leftEncoder = new Encoder(leftEncoderPorts[0], leftEncoderPorts[1], false, EncodingType.k4X);
    	this.rightEncoder = new Encoder(rightEncoderPorts[0], rightEncoderPorts[1], false, EncodingType.k4X);
    	
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
	}
}
