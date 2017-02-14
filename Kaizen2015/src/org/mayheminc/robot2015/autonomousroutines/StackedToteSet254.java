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


/**
 *
 * @author Ken Streeter
 */
public class StackedToteSet254 extends CommandGroup {
    
    public StackedToteSet254() {
        
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // move the RC hand out of the way
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
        
        // lift the tote high enough to go over RC
        addParallel(new RaiseClawLinkage());
        addParallel(new MoveElevatorBottomHandToPosition((Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_ONE_TOTE), true));
 
        // move diagonally forward to the left to bulldoze RC out of the staging area
        if (Robot.PRACTICE_BOT) {
    		addSequential(new DriveForDistance(-0.90, 0.10, 0.0, 12000, 55.0 )); 
    	} else {
    		addSequential(new DriveForDistance(-0.90, 0.10, 0.0, 12000, 55.0 ));  
    	}

        // move backward to the left to get around the RC
        if (Robot.PRACTICE_BOT) {
    		addSequential(new DriveForDistance(0.00, -0.50, 0.0, 1500, 45.0 )); 
    	} else {
    		addSequential(new DriveForDistance(0.00, -0.50, 0.0, 1500, 45.0 ));  
    	}
        
        // move backward to the left to get around the RC
        if (Robot.PRACTICE_BOT) {
    		addSequential(new DriveForDistance(0.00, -0.50, 0.0, 2500, 20.0 )); 
    	} else {
    		addSequential(new DriveForDistance(0.00, -0.50, 0.0, 2500, 20.0 ));  
    	}
        
        // straighten out to get back to the wall
        if (Robot.PRACTICE_BOT) {
    		addSequential(new DriveForDistance(0.0, -0.40, 0.0, 2000, 0.0 )); 
    	} else {
    		addSequential(new DriveForDistance(0.0, -0.40, 0.0, 2000, 0.0 ));  
    	}
        
        // delay before strafing
        addSequential(new Delay(250));
        
        // start strafing over to the 2nd tote        
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 8000, 0.0 )); 
    	} else {
            addSequential(new DriveForDistance(-0.90, -0.10, 0.0, 8000, 0.0 ));
        }
        
        // prepare to set tote #1 on top of tote #2 while strafing to tote #2
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(-0.90, -0.12, 0.0, 4000, 0.0 ));  
    	} else {
            addSequential(new DriveForDistance(-0.90, -0.08, 0.0, 4000, 0.0 ));
        }
  
        // align to tote #2
        addSequential(new AlignToTote(5.0));
        addSequential(new Delay(100));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        

        // lift tote #2 just off floor, and then strafe right a tiny bit to get away from RC
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2, true));
        if (Robot.PRACTICE_BOT) {
    		addParallel(new DriveForDistance(0.50, -0.15, 0.0, 400, 0.0 )); 
    	} else {
    		addParallel(new DriveForDistance(0.50, -0.15, 0.0, 400, 0.0 )); 
    	}
        
        // lift the tote high enough to go over RC
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_TWO_TOTES, true));
        
        // strafe to tote #3  (go left 81")  Note:  prior to GSD, 21800 total = 81"  (~3200 per foot; ~1000 = 4 inches)
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 19300, 0.0 ));
    	} else {
        	addSequential(new DriveForDistance(-0.90, -0.08, 0.0, 21000, 0.0 ));  // was 15800 at end of GSD; 17300 at end of UMASS/start of NEU
        }
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 + Robot.pole.bottomElevator.CARRY_OFFSET, true));
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 6500, 0.0 ));
    	} else {
            addSequential(new DriveForDistance(-0.90, -0.08, 0.0, 10200, 0.0 ));   // was 10000 at end of GSD; 10000 at end of UMASS
            //  total at start of NEU was (17300+9000) = 26300; increased to 29200 after first qual match; to 30200 after "field fault" 3rd match
            // increased to 30700 at WPI practice #3; 31,200 at WPI practice #4
        }

        // coarse alignment to tote #3
        addSequential(new CoarseAlignToTote(1.0));    // used to be 1.0 seconds
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3, true));

        // need to bail on alignment with 3.2 seconds to go in auto (will give 0.6 margin)
        // was 3.0 at NE CMP Elims.
        // making it 2.9 after looking at logs.  When the timeout mattered we stil had >0.5 seconds at the end
        addSequential(new AlignToTote(true, 2.3));    
        addSequential(new Delay(100));

        // bulldoze tote #3 to auto zone  // 30300 is approx 10';   1' is approx 3000 
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 20100, 0.0, 3000));
    	} else {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 20100, 0.0, 3000));
        	// above changed to 29400 after Qual 79 at NEU
        	// above changed to 29600 after Qual 58 at NEU
        	// was 27600 for Qual 31 at NEU; lengthened to 28600 after Qual 31 at NEU
        	// above changed to 27300 for UMASS Elims; was 27000 for quals at UMASS; was 27300 at end of GSD
        	// above should be approx 30000 for totes and robot to fit in autozone;  temporarily reducing for GSD elims
        	// was 29400 at NECMP; changed to 25400 for elims
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
