package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.subsystems.Drive;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveElevatorBothHandsToZero extends CommandGroup {
    
    public  MoveElevatorBothHandsToZero() {
    	
        
    	addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1));
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN));
 
        // ALL DONE!
    }
}
