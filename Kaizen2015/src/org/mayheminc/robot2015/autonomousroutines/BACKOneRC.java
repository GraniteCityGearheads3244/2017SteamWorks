package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.commands.AlignToTote;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.MoveElevatorBottomHandToPosition;
import org.mayheminc.robot2015.commands.MoveElevatorTopHandToPosition;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;
import org.mayheminc.robot2015.subsystems.Drive;

import org.mayheminc.robot2015.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BACKOneRC extends CommandGroup {
    
    public  BACKOneRC() {
    	
        // Perform needed initialization
        addSequential(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_ZERO_POSITION,
														Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // lift up the RC just off the ground
        addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4));
        
        // Back up into the auto zone  (approx 10 feet)
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 19000, 0.0));
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 5000, 90.0));
        // ALL DONE!
        
        // Set down the RC
        addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE));
    }
}
