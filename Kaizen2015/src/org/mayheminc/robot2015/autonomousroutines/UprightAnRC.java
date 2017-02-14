/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.*;
import org.mayheminc.robot2015.subsystems.Drive;


/**
 *
 * @author Ken Streeter
 */
public class UprightAnRC extends CommandGroup {
    
    public UprightAnRC() {
        
    	// ensure claw is at correct height
        addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN));

        // close the claw
        addSequential(new CloseClaw());
		addSequential(new Delay(500));
		
    	// lift the RC to the desired height
        addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_UPRIGHT_RC_HEIGHT));
		
    	// wait a moment for the RC to settle
		addSequential(new Delay(500));
    	
    	// lower the claw a tiny bit
        addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_UPRIGHT_RC_HEIGHT-400));
		addSequential(new Delay(200));
		
    	// open the claw to set the RC on the floor
        addSequential(new OpenClaw());
		addSequential(new Delay(10000)); // wait up to 10 seconds for the operator to let go of the button
		
       // ALL DONE!
    }
}
