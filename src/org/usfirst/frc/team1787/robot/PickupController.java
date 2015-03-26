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
	private CANTalon[] pickupMotors;
	
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
	private boolean topLimitReached = false, bottomLimitReached = false, automaticActive = false;
	
	private boolean emergencyStop = false;
	
	/**
	 * Constructor for the PickupController class, takes port numbers
	 * @param pickupPort the port for the pickup motor.
	 * @param bottomLimitPort the port for the bottom limit switch.
	 * @param topLimitPort the port for the top limit switch.
	 * @param xboxController the Joystick object for the xboxController.
	 */
	/*public PickupController(int pickupPort, int bottomLimitPort, int topLimitPort, Joystick xboxController)
	{
		this.pickupMotor = new CANTalon(pickupPort);
		this.xboxController = xboxController;
		this.bottomLimit = new DigitalInput(bottomLimitPort);
		this.topLimit = new DigitalInput(topLimitPort);
	}*/
	
	/**
	 * Takes objects, not port numbers.
	 * @param pickupMotors The pickup motors.
	 * @param bottomLimit
	 * @param topLimit
	 * @param xboxController
	 */
	public PickupController(CANTalon[] pickupMotors, DigitalInput bottomLimit, DigitalInput topLimit, Joystick xboxController)
	{
		this.pickupMotors = pickupMotors;
		this.bottomLimit = bottomLimit;
		this.topLimit = topLimit;
		this.xboxController = xboxController;
	}
	
	/**
	 * Set the motor speeds.
	 * @param speed The speed.
	 */
	private void setMotors(double speed)
	{
		pickupMotors[0].set(speed);
		pickupMotors[1].set(speed);
	}
	
	/**
	 * Called 50 times a second in tele-operated mode.
	 */
	public void pickupPeriodic()
	{
		if (!automaticActive) 
		{
			this.pickupControl();
		}
		//this.pickupTest();
	}
	
	/**
	 * This controls the manual raising and lowering of the pickup motor.
	 */
	public void pickupControl()
	{
		this.checkEmergencyStop();
		
		if (emergencyStop)
		{
			setMotors(0);
			return;
		}
		
		// set appropriate flag variable if a limit has been reached
		if (!topLimit.get())
		{
			topLimitReached = true;
		}
		else if (!bottomLimit.get())
		{
			bottomLimitReached = true;
		}
		
		this.pickupAutomatic();
		
		// check if button is pressed, if no limit has been reached, and if the flags have not been set, then set motor
		if (xboxController.getRawButton(4) && topLimit.get() && !topLimitReached) // Y-button raises
		{
			
			setMotors(1.0);
			bottomLimitReached = false;
		}
		else if (xboxController.getRawButton(1) && bottomLimit.get() && !bottomLimitReached) // A-button lowers
		{
			setMotors(-1.0);
			topLimitReached = false;
		}
		else 
		{
			setMotors(0);
		}
	}
	
	public void checkEmergencyStop()
	{
		if (xboxController.getRawButton(8))
		{
			emergencyStop = true;
		}
		else if (xboxController.getRawButton(7))
		{
			emergencyStop = false;
		}
	}
	
	/**
	 * For automatically lowering and then raising the pickup arms
	 */
	public void pickupAutomatic()
	{	
		if (xboxController.getZ() != 0) // If right trigger has been pressed
		{
			automaticActive = true;
		}
		else
		{
			return;
		}
		
		if (automaticActive)
		{
			// Lower
			while (!bottomLimitReached && bottomLimit.get())
			{	
				setMotors(-1.0);
				
				Timer.delay(0.1);
				
				if (!bottomLimit.get())
				{
					bottomLimitReached = true;
				}
			}
			
			topLimitReached = false;
			
			setMotors(0);
			
			Timer.delay(0.3);
			
			// Raise
			while (!topLimitReached && topLimit.get())
			{
				setMotors(1.0);
				
				Timer.delay(0.1);
				
				if (!topLimit.get())
				{
					topLimitReached = true;
				}
			}
			
			bottomLimitReached = false;
			
			setMotors(0);
		}
		
		automaticActive = false;
	}
	
	/**
	 * Prints state of each limit switch
	 */
	private void pickupTest()
	{
		if (xboxController.getRawButton(4))
		{
			pickupMotors[0].set(0.4);
		}
		
		else if (xboxController.getRawButton(1))
		{
			pickupMotors[0].set(-0.4);
		}
		
		else if (xboxController.getRawButton(2))
		{
			pickupMotors[1].set(0.4);
		}
		
		else if (xboxController.getRawButton(3))
		{
			pickupMotors[1].set(-0.4);
		}
		
		else
		{
			pickupMotors[1].set(0);
			pickupMotors[0].set(0);
		}
	}
	
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

