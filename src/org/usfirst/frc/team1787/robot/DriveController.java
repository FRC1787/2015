package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author jeremystark
 **/

public class DriveController 
{

    public static void driveControls() 
    {
        double lastTime, timeDelta = 0.0;
        double time = Timer.getFPGATimestamp();
            
        lastTime = time;
        time = Timer.getFPGATimestamp();
        timeDelta = time - lastTime;
            
        Variables.robotDrive.arcadeDrive
        (
            Variables.xboxController.getY() * Variables.driveSpeed, 
            -Variables.xboxController.getX() * Variables.driveSpeed, 
            true
        );
    }
   
   
    public static void shiftingControls() 
    {
        //Shifting controls
        if (Variables.xboxController.getRawButton(5))
        {
            Variables.gearShifter.set(DoubleSolenoid.Value.kForward);
            Variables.shifterPosition = false;
        }

        else if (Variables.xboxController.getRawButton(6))
        {
            Variables.gearShifter.set(DoubleSolenoid.Value.kReverse);
            Variables.shifterPosition = true;
        }
    }  
}
