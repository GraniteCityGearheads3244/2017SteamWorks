package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.AlignToTote;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.MoveElevatorBottomHandToPosition;
import org.mayheminc.robot2015.commands.MoveElevatorTopHandToPosition;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;
import org.mayheminc.robot2015.subsystems.Drive;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BACKOneTote extends CommandGroup {
    
    public  BACKOneTote() {
    	
        // Perform needed initialization
        addSequential(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_ZERO_POSITION,
														Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
                
        // lift up tote and wait a moment for it to settle
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1));
        addSequential(new Delay(200));
        
        // Back up into the auto zone  (approx 10 feet)
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 19000, 0.0));
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 5000, 90.0));
        // ALL DONE!
        
        // Set down the yellow tote
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1));
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 1000, 90.0));
    }
}
