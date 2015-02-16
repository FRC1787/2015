package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the pickup controller.
 * @author Jeremy, Eben Carek, & kind of ry60003333
 */
public class PickupController 
{
	
	/**
	 * The pickup speed???
	 */
	private static double pickupSpeed = 0;
	
	/**
	 * The talon for the pickup motor.
	 */
	private CANTalon pickupMotor;
	
	/**
	 * The Xbox controller instance.
	 */
	private Joystick xboxController;
	
	/**
	 * ????
	 */
	private DigitalInput bottomLimit, topLimit;
	
	// We should change this to a state machine?
	
	/**
	 * Set to true when the pickup is raising.
	 */
	private boolean pickupRaising;
	
	/**
	 * Set to true when the pickup is lowering.
	 */
	private boolean pickupLowering;
	
	/**
	 * Flag variable for limit switch
	 */
	public boolean topLimitFlag, bottomLimitFlag;
	
	public PickupController(int pickupPort, int bottomLimitPort, int topLimitPort, Joystick xboxController)
	{
		this.pickupMotor = new CANTalon(pickupPort);
		this.xboxController = xboxController;
		this.bottomLimit = new DigitalInput(bottomLimitPort);
		this.topLimit = new DigitalInput(topLimitPort);
		this.bottomLimitFlag = false;
		this.bottomLimitFlag = false;
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
		if(topLimit.get())
		{
			topLimitFlag = true;
		} else if(bottomLimit.get())
		{
			bottomLimitFlag = true;
		}
		
		if (xboxController.getRawButton(4) && bottomLimit.get() && topLimitFlag) // Y-button raises
		{
			pickupMotor.set(1);
			bottomLimitFlag = false;
		}
		else if (xboxController.getRawButton(1) && topLimit.get() && bottomLimitFlag) // A-button lowers
		{
			pickupMotor.set(-0.75);
			topLimitFlag = false;
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
