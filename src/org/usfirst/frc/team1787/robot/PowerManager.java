package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Manages the power distribution board.
 * @author ry60003333
 *
 */
public class PowerManager
{
	
	/**
	 * The power distribution panel.
	 */
	private final PowerDistributionPanel powerDistributionPanel;
	
	/**
	 * Creates a new PowerManager.
	 */
	public PowerManager()
	{
		powerDistributionPanel = new PowerDistributionPanel();
	}
	
	/**
	 * Initialize the power manager.
	 */
	public void initPower()
	{
		powerDistributionPanel.clearStickyFaults();
	}

}
