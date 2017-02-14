package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RetrieveRCFromStep extends CommandGroup {
    
    public  RetrieveRCFromStep() {
    	
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ON_STEP));
    	addSequential(new RaiseClawLinkage());
    	
//    	make sure that the RC is not tucked away on the back of the step
//    	addSequential(new CloseClaw());
//    	addSequential(new Delay(200));
//    	
//    	addSequential(new DriveForDistanceInTeleop(0.0, -0.18, 0.0, 750.0));
//    	
//    	addSequential(new OpenClaw());
//    	addSequential(new Delay(200));
//    	
//    	addSequential(new DriveForDistanceInTeleop(0.0, 0.18, 0.0, 1000.0));
 
    	// adjust height of claw and close it
    	addSequential(new CloseClaw());    	
    	addSequential(new Delay(200));
    	
    	// lift the claw and backup
    	addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ON_STEP + 1700));
    	addSequential(new DriveForDistanceInTeleop(0.0, -0.18, 0.0, 4600.0)); // was 4200 at MAYHEM
    	
    	// open claw to set down the RC
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ON_STEP + 1500));
    	addSequential(new OpenClaw());
//    	delay for the pneumatics to work
    	addSequential(new Delay(200));
//    	delay for the RC to settle
    	addSequential(new Delay(500));
    	
    	// lower linkage and drive forward 
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ON_STEP));
    	addSequential(new LowerClawLinkage());
    	addSequential(new Delay(200));
    	addSequential(new DriveForDistanceInTeleop(0.0, 0.25, 0.0, 1700.0));

    	// close claw
    	addSequential(new CloseClaw());
    	addSequential(new Delay(200));
    	
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ON_STEP + 1500));
    	
    	//wait 10 seconds (to account prevent the command from repeating itself)
    	addSequential(new Delay(10000));
    }
}
