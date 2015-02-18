package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Controls the Pneumatics.
 * @author Jeremy, Eben, & ry60003333
 *
 */
public class Pneumatics 
{
	/**
	 * The compressor.
	 */
	private final Compressor compressor;
	
	/**
	 * Pneumatics instance of the xboxController
	 */
	private Joystick xboxController;
	
	 /**
     * The solenoid for the gear shifter.
     */
    private Solenoid gearShifter;
    
    /**
     * The boolean showing the position of the shifter position.
     */
    private boolean shifterPosition;

	/**
	 * Creates a new instance of Pneumatics.
	 * @param compressorPort The port that the compressor is attached too.
	 */
	public Pneumatics(int compressorPort, Joystick xboxController)
	{
		compressor = new Compressor(compressorPort);
		gearShifter = new Solenoid(0);
		this.xboxController = xboxController;
	}
	
	/**
	 * Initialize the Pneumatics.
	 */
	public void initPneumatics()
	{
		compressor.clearAllPCMStickyFaults();
	}
	
	/**
	 * Start the compressor.
	 */
	public void startCompressor()
	{
		compressor.start();
	}
	
	/**
	 * Shifting controls for the robot.
	 */
	public void shiftingControls() 
    {
        //Shifting controls
        if (xboxController.getRawButton(5))
        {
            gearShifter.set(true);
            shifterPosition = true;
            Utils.print("Shifter set to " + shifterPosition);
        }

        else if (xboxController.getRawButton(6))
        {
        	gearShifter.set(false);
            shifterPosition = false;
            Utils.print("Shifter set to " + shifterPosition);
        }
    } 
	
	/**
	 * Testing the solenoid.
	 */
	public void solenoidTest()
	{
		gearShifter.set(true);
		Utils.print("gear shifter set true");
		Timer.delay(3);
		gearShifter.set(false);
		Utils.print("gear shifter set false");
	}
}
