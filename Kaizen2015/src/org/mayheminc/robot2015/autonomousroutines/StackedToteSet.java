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
public class StackedToteSet extends CommandGroup {

	// driveAngles specified in degrees
	public static int STRAIGHT = 0;
	public static int LEFT = -25;
	public static int RIGHT = 25;
	
    public StackedToteSet(int driveAngle) {
        super("StackedToteSet" + driveAngle + "");
        
        double driveAngleMultiplier = 1 / Math.cos(Math.toRadians(driveAngle));
        
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // move the RC hand out of the way
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
        
        // lift tote #1 just off floor, and then strafe right and back tiny bit to get away from RC
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1, true));
        
        if (Robot.PRACTICE_BOT) {
    		addParallel(new DriveForDistance(0.50, -0.25, 0.0, 400, 0.0 )); 
    	} else {
    		addParallel(new DriveForDistance(0.50, -0.25, 0.0, 400, 0.0 ));  
    	}
        
        // lift the tote high enough to go over RC
        addParallel(new RaiseClawLinkage());
        addSequential(new MoveElevatorBottomHandToPosition((Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_ONE_TOTE), true));
 
        // strafe to tote #2  (go left 81"; approx 300 counts per inch)
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 18500, 0.0 ));
    	} else {
			// changed Y to be -0.15 from -0.08 before River Rage
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 21000, 0.0 ));  // was 16500 at end of GSD, UMASS, and start of NEU
    	}
        
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 11000, 0.0 ));  // was 6000 at start of GSD
    	} else {
			// changed Y to be -0.15 from -0.08 before River Rage
            addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 8200, 0.0 ));  // was 8700 at end of GSD; 10500 at end of UMASS; 9500 at start at NEU
            // decreased above to 29700 (from 30000 after QF8 at NEU)
            //  total at start of NEU was (16500+9500) = 26000; increased to 29500 after first qual match; to 30000 after field fault 3rd match
            // increased to 30200 at WPI practice #3; 30700 at WPI practice #4
            // was 30700 at start of River Rage
            // reduced to 30200 after second match of River Rage
            // reduced to 29200 during semifinals of River Rage
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
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 20100, 0.0 ));
    	} else {
			// changed Y to be -0.15 from -0.08 before River Rage
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 21000, 0.0 ));  // was 15800 at end of GSD; 17300 at end of UMASS/start of NEU
        }
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 + Robot.pole.bottomElevator.CARRY_OFFSET, true));
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 7500, 0.0 ));
    	} else {
			// changed Y to be -0.15 from -0.08 before River Rage
            addSequential(new DriveForDistance(-0.90, -0.15, 0.0, 9200, 0.0 ));   // was 10000 at end of GSD; 10000 at end of UMASS
            //  total at start of NEU was (17300+9000) = 26300; increased to 29200 after first qual match; to 30200 after "field fault" 3rd match
            // increased to 30700 at WPI practice #3; 31,200 at WPI practice #4
            // was 31,200 at start of River Rage
            // reduced to 30,700 after our second match at River Rage
            // reduced to 30,400 after our third match at River Rage
            // reduced to 30,200 after our fourth match at River Rage
        }

        // coarse alignment to tote #3
        addSequential(new CoarseAlignToTote(1.0));    // used to be 1.0 seconds
        addParallel(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3, true));

        // need to bail on alignment with 3.2 seconds to go in auto (will give 0.6 margin)
        // was 3.0 at NE CMP Elims.
        // making it 2.8 after looking at logs.  When the timeout mattered we still had >0.5 seconds at the end
        // reduced bailout to 2.7 seconds for River Rage elims
        addSequential(new AlignToTote(true, 2.7));    
        addSequential(new Delay(100));

        // bulldoze tote #3 to auto zone  // 30300 is approx 10';   1' is approx 3000 
        if (Robot.PRACTICE_BOT) {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 28100.0 * driveAngleMultiplier, driveAngle, 3000));
    	} else {
        	addSequential(new DriveForDistanceWithDeceleration(0.0, 0.90, 0.0, 28500.0 * driveAngleMultiplier, driveAngle, 3000));
        	// above changed to 29400 after Qual 79 at NEU
        	// above changed to 29600 after Qual 58 at NEU
        	// was 27600 for Qual 31 at NEU; lengthened to 28600 after Qual 31 at NEU
        	// above changed to 27300 for UMASS Elims; was 27000 for quals at UMASS; was 27300 at end of GSD
        	// above should be approx 30000 for totes and robot to fit in autozone;  temporarily reducing for GSD elims
        	// was 29400 at NECMP; changed to 25400 for elims
        	// was 29400 for Day1 of CMP; reduced to 28500 for Day2 of CMP
        	// was 28500 for River Rage; reduced to 25400 for elims
        }
        addSequential(new Delay(100));
        
        // stack totes #2 and #1 on top of tote #3
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2));
        
        // back away from the stack
        addSequential(new DriveForDistance(0.0, -0.4, 0.0, 200, (double) driveAngle));
        addSequential(new PrintAutonomousTimeRemaining());
        
       // ALL DONE!
    }
}
