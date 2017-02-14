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
public class BulldozeToAutoZone extends CommandGroup {
    
    public  BulldozeToAutoZone() {
    	
    	// assumes that the robot's position is the start is having just lifted up a stacked tote
        
        // Bulldoze into the auto zone  (approx 10 feet)
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(0.0, 0.90, 0.0, 30000, 0.0));
    	} else {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 26000, 0.0, 3000));
        }

        // ALL DONE!
    }
}
