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
public class EndOfStackedToteSet extends CommandGroup {
    
    public EndOfStackedToteSet() {
        super("EndOfStackedToteSet");
        
        // Perform needed initialization
        addSequential(new ZeroGyro());
//        addSequential(new ZeroElevatorEncodersInAuto(	Elevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
//        												Elevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
//        
//        // move the RC hand out of the way
//        addParallel(new MoveElevatorTopHandToPosition(Elevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
//        
//        // lift tote #1 just off floor, and then strafe right and back tiny bit to get away from RC
//        addSequential(new MoveElevatorBottomHandToPosition(Elevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1, true));
//        
//        if (Robot.PRACTICE_BOT) {
//    		addParallel(new DriveForDistance(0.50, -0.25, 0.0, 300, 0.0 )); 
//    	} else {
//    		addParallel(new DriveForDistance(0.50, -0.25, 0.0, 400, 0.0 ));  
//    	}
//        
//        // lift the tote high enough to go over RC
//        addSequential(new MoveElevatorBottomHandToPosition((Elevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_3), true));
// 
//        // strafe to tote #2  (go left 81")
//        if (Robot.PRACTICE_BOT) {
//        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 11000, 0.0 ));
//    	} else {
//        	addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 16500, 0.0 ));  // was 16500 at end of GSD
//    	}
//        
//        addParallel(new MoveElevatorBottomHandToPosition(Elevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
//        if (Robot.PRACTICE_BOT) {
//            addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 3000, 0.0 ));  // was 6000 at start of GSD
//    	} else {
//            addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 10500, 0.0 ));  // was 8700 at end of GSD; 6000 at start of GSD
//        }
//  
//        // align to tote #2
//        addSequential(new AlignToTote(5.0));
//        addSequential(new Delay(100));
//        addSequential(new MoveElevatorBottomHandToPosition(Elevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
//        
//        // lift tote #2 just off floor, and then strafe right a tiny bit to get away from RC
//        addSequential(new MoveElevatorBottomHandToPosition(Elevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2, true));
//        if (Robot.PRACTICE_BOT) {
//    		addParallel(new DriveForDistance(0.50, -0.20, 0.0, 300, 0.0 )); 
//    	} else {
//    		addParallel(new DriveForDistance(0.50, -0.15, 0.0, 400, 0.0 )); 
//    	}
//        
//        // lift the tote high enough to go over RC
//        addSequential(new MoveElevatorBottomHandToPosition(Elevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MAX-200, true));
//        
//        // strafe to tote #3  (go left 81")  Note:  prior to GSD, 21800 total = 81"  (~3200 per foot; ~1000 = 4 inches)
//        if (Robot.PRACTICE_BOT) {
//        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 11000, 0.0 ));
//    	} else {
//        	addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 17300, 0.0 ));  // was 15800 at end of GSD
//        }
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 + Robot.pole.bottomElevator.CARRY_OFFSET, true));
//        if (Robot.PRACTICE_BOT) {
//        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 3000, 0.0 ));
//    	} else {
//            addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 10000, 0.0 ));   // was 10000 at end of GSD; 6000 at start of GSD
//        }

        addSequential(new Delay(9000));
        
        // coarse alignment to tote #3
        addSequential(new CoarseAlignToTote(1.0));    // used to be 1.0 seconds
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3, true));

        addSequential(new AlignToTote(true, 3.2));    // need to bail on alignment with 3.2 seconds to go in auto
        addSequential(new Delay(100));

        // bulldoze tote #3 to auto zone  // 30300 is approx 10';   1' is approx 3000 
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 19000, 0.0, 3000));
    	} else {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 27600, 0.0, 3000));
        	// above changed to 27300 for UMASS Elims; was 27000 for quals at UMASS; was 27300 at end of GSD
        	// above should be approx 30000 for totes and robot to fit in autozone;  temporarily reducing for GSD elims
        }
        addSequential(new Delay(100));
        
        // stack totes #2 and #1 on top of tote #3
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2));
        
        // back away from the stack
        addSequential(new DriveForDistance(0.0, -0.4, 0.0, 200, 0.0));
        
        addSequential(new PrintAutonomousTimeRemaining());
        
       // ALL DONE!
    }
}
