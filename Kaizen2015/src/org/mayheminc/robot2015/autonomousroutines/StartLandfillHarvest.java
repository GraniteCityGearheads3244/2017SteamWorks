/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;








import org.mayheminc.robot2015.Robot;
//import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.*;
import org.mayheminc.robot2015.subsystems.Drive;
//import org.mayheminc.robot2015.subsystems.Drive;


/**
 *
 * @author Ken Streeter
 */
public class StartLandfillHarvest extends CommandGroup {
    
    public StartLandfillHarvest() {
        super("StartLandfillHarvest");
        
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // move the RC hand out of the way and move the bottom hand to the correct height
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1, true));
        
        // align to tote #1
        addSequential(new AlignToTote(5.0));
        // pickup tote #1
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2+Robot.pole.bottomElevator.CARRY_OFFSET, true));

        // find tote #2
        addSequential(new CoarseAlignToTote(2.0));
        // lower tote #1 onto tote #2
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        // align to tote #2
        addSequential(new AlignToTote(5.0));
        // pickup tote #2
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3+Robot.pole.bottomElevator.CARRY_OFFSET, true));
        
        // backup to clear the totes to the side
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 6000, 0.0));
        
        // Back up further into the auto zone
        addSequential(new RaiseClawLinkage());
//        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 8000, 0.0));
        
//        // Strafe to the left to be ready for the next totes
//    	addSequential(new DriveForDistance(-0.70, 0.0, 0.0, 14500, 0.0 ));
        
       // ALL DONE!
    }
}
