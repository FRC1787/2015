package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

public class PickupController 
{
	private Talon pickupMotor;
	private Joystick xboxController;
	private DigitalInput bottomLimit, topLimit;
	
	private static boolean pickupIsRaising = false;
	
	private static double pickupSpeed = 0;
	
	public PickupController(int pickupPort, int bottomLimitPort, int topLimitPort, Joystick xboxController)
	{
		this.pickupMotor = new Talon(pickupPort);
		this.xboxController = xboxController;
		this.bottomLimit = new DigitalInput(bottomLimitPort);
		this.topLimit = new DigitalInput(topLimitPort);
	}
	
	public void pickupPeriodic()
	{
		if (xboxController.getRawButton(1))
		{
			this.pickupRaise();
		}
	}
	
	// Method needs more work, so far it causes pickupMotor to accelerate as long as button is held
	public void pickupRaise()
	{
		if (!pickupIsRaising)
		{
			pickupIsRaising = true;
			
			double x = 0;
    	
    		while (!topLimit.get())
    		{
    			pickupSpeed = (Math.pow(1.4, x) - 1) <= 1 ? (Math.pow(1.4, x) - 1) : 1;
    			x += 0.1;
    		
    			pickupMotor.set(pickupSpeed);
    			
    			Timer.delay(0.1);
    		}
		}
	}
}
