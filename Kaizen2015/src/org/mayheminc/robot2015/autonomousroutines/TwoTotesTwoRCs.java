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
public class TwoTotesTwoRCs extends CommandGroup {
    
    public  TwoTotesTwoRCs() {
        // First, pick up the first RC and Tote as in "OneToteOneRc"
        addSequential(new OneToteOneRcPart1());
        
        // Raise RC and Tote to correct height for acquiring 2nd tote
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        
        // Back up towards wall
        if (Robot.PRACTICE_BOT) {
    		addParallel(new DriveForDistance(0.0, -0.15, 0.0, 500, 0.0 ));    // was -0.20
    	} else {
    		addParallel(new DriveForDistance(0.0, -0.10, 0.0, 800, 0.0 ));  
    	}
 
        // strafe to tote #2  (go left 81")
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 25000, 0.0 ));  // was -0.20
    	} else {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 16500, 0.0 ));
    	}
        
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 10000, 0.0 ));
    	} else {
            addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 5000, 0.0 ));
        }
  
        // align to tote #2
        addSequential(new AlignToTote(5.0));
        addSequential(new Delay(100));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        
        // lift tote #2 just off floor
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2, true));
        
        // Back up and to the right a little to get away from RC 
		addSequential(new DriveForDistance(0.20, -0.25, 0.0, 3000, 30.0 )); 
		//		addSequential(new Delay(2000));      // delay to allow observation of where the robot is at this time
		
        // Pivot back to be adjacent to the RC 
		addSequential(new DriveForDistance(0.0, -0.40, 0.0, 5000, 90.0 ));  
        
        // Strafe to the AutoZone (this might not work over the scoring platform)
        addSequential(new StrafeLeftToAutoZone());   
        
        // ALL DONE!
    }
}
