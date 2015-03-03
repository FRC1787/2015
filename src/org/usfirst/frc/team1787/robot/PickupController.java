package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the pickup controller.
 * @author Jeremy, Eben Carek, & kind of ry60003333
 */
public class PickupController 
{	
	/**
	 * The talon for the pickup motor.
	 */
	private CANTalon pickupMotor;
	
	/**
	 * The Xbox controller instance.
	 */
	private Joystick xboxController;
	
	/**
	 * The bottom and top limit switches.
	 */
	private DigitalInput bottomLimit, topLimit;
	
	/**
	 * flag variables for limit switches
	 */
	private boolean topLimitReached = false, bottomLimitReached = false;
	
	/**
	 * Constructor for the PickupController class, takes port numbers
	 * @param pickupPort the port for the pickup motor.
	 * @param bottomLimitPort the port for the bottom limit switch.
	 * @param topLimitPort the port for the top limit switch.
	 * @param xboxController the Joystick object for the xboxController.
	 */
	public PickupController(int pickupPort, int bottomLimitPort, int topLimitPort, Joystick xboxController)
	{
		this.pickupMotor = new CANTalon(pickupPort);
		this.xboxController = xboxController;
		this.bottomLimit = new DigitalInput(bottomLimitPort);
		this.topLimit = new DigitalInput(topLimitPort);
	}
	
	/**
	 * Takes objects, not port numbers.
	 * @param pickupMotor
	 * @param bottomLimit
	 * @param topLimit
	 * @param xboxController
	 */
	public PickupController(CANTalon pickupMotor, DigitalInput bottomLimit, DigitalInput topLimit, Joystick xboxController)
	{
		this.pickupMotor = pickupMotor;
		this.bottomLimit = bottomLimit;
		this.topLimit = topLimit;
		this.xboxController = xboxController;
	}
	
	/**
	 * Called 50 times a second in tele-operated mode.
	 */
	public void pickupPeriodic()
	{
		this.pickupControl();
		//this.pickupTest();
	}
	
	/**
	 * This controls the raising and lowering of the pickup motor.
	 */
	public void pickupControl()
	{
		// set appropriate flag variable if a limit has been reached
		if (!topLimit.get())
		{
			topLimitReached = true;
		}
		else if (!bottomLimit.get())
		{
			bottomLimitReached = true;
		}
		
		// check if button is pressed, if no limit has been reached, and if the flags have not been set, then set motor
		if (xboxController.getRawButton(4) && topLimit.get() && !topLimitReached) // Y-button raises
		{
			pickupMotor.set(1.0);
			bottomLimitReached = false;
		}
		else if (xboxController.getRawButton(1) && bottomLimit.get() && !bottomLimitReached) // A-button lowers
		{
			pickupMotor.set(-1.0);
			topLimitReached = false;
		}
		else 
		{
			pickupMotor.set(0);
		}
	}
	/**
	 * Prints state of each limit switch
	 */
	/*private void pickupTest()
	{
		if (!topLimit.get())
		{
			Utils.print("top limit active");
		} else {
			Utils.print("top limit not active");
		}
		
		if (!bottomLimit.get())
		{
			Utils.print("bottom limit active");
		} else {
			Utils.print("bottom limit not active");
		}
	}*/
	
	/**
	 * Should raise the pickup mechanism.
	 */
	/*public void raisePickupMechanism()
	{
		// Method needs more work, so far it raises when the button is pressed, 
		// accelerates, stops when top limit is reached
		
		if (!pickupRaising)
		{
			pickupRaising = true;
			pickupLowering = false;
			
			double x = 0;
    	
    		while (!topLimit.get())
    		{
    			pickupSpeed = (Math.pow(1.4, x) - 1) <= 1 ? (Math.pow(1.4, x) - 1) : 1;
    			x += 0.1;
    		
    			pickupMotor.set(pickupSpeed);
    			Timer.delay(0.1);
    		}
    		
    		if (topLimit.get())
    		{
    			pickupMotor.set(0);
    		}
		}
	}*/
	
	/**
	 * Should the lower the pickup mechanism.
	 */
	/*public void lowerPickupMechanism()
	{
		if (!pickupLowering)
		{
			pickupLowering = true;
			pickupRaising = false;
			
			double x = 0;
			
			while (!bottomLimit.get())
			{
				pickupSpeed = -(Math.pow(1.4, x) - 1) >= -1 ? -(Math.pow(1.4, x) - 1) : -1;
				x += 0.1;
				
				pickupMotor.set(pickupSpeed);
				Timer.delay(0.1);
			}
			
			if (topLimit.get())
			{
				pickupMotor.set(0);
			}
		}
	}*/
}

