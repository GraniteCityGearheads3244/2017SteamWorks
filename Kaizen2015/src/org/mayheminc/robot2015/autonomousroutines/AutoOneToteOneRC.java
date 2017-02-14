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
public class AutoOneToteOneRC extends CommandGroup {
    
    public  AutoOneToteOneRC() {
    	
        // Perform needed initialization
        addSequential(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_ZERO_POSITION,
														Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // lift up the RC up above the height of the tote
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4));
    	
        // back away from RC
        // lift up tote #1 and then back up a little
        addSequential(new DriveForDistance(0.0, -0.2, 0.0, 200, 0.0));
        addSequential(new Delay(500));
    	
        // drive sideways towards the tote
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1));
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(0.50, -0.10, 0.0, 6000, 0.0 )); 
    	} else {
            addSequential(new DriveForDistance(0.50, -0.10, 0.0, 3700, 0.0 )); 
        }   
    	
        // align to tote
        addSequential(new AlignToTote(3.0));
        addSequential(new Delay(100));
        
        // lift up tote and wait a moment for it to settle
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1));
        addSequential(new Delay(500));
        
        // Bulldoze into the auto zone  (approx 10 feet)
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(0.0, 0.40, 0.0, 29000, 0.0)); 
    	} else {
            addSequential(new DriveForDistance(0.0, 0.40, 0.0, 26000, 0.0));
        }  


        // ALL DONE!
    }
}
