package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.AlignToTote;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.DriveForDistanceWithDeceleration;
import org.mayheminc.robot2015.commands.MoveElevatorBottomHandToPosition;
import org.mayheminc.robot2015.commands.MoveElevatorTopHandToPosition;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;
import org.mayheminc.robot2015.subsystems.Drive;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StrafeLeftToAutoZone extends CommandGroup {
    
    public  StrafeLeftToAutoZone() {
        
        // Strafe left into the auto zone  (approx 10 feet)
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(-0.90, 0.0, 0.0, 40000, 90.0));  // untested!
    	} else {
        	addSequential(new DriveForDistance(-0.90, 0.0, 0.0, 40000, 90.0));
        }

        // ALL DONE!
    }
}
