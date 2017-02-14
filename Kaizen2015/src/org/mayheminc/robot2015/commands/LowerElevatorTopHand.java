package org.mayheminc.robot2015.commands;
import org.mayheminc.robot2015.Robot;

import org.mayheminc.robot2015.subsystems.ElevatorBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;



/**
 * Lower the elevator until the command is ended or until it reaches the bottom.
 */
public class LowerElevatorTopHand extends Command {

    public LowerElevatorTopHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.topElevator);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.pole.topElevator.getPositionHand() > Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN) {
    	   	if(!Robot.pole.handsNearCrashing() && Robot.pole.getAntiCrashingMode()){
    	   		Robot.pole.lowerElevatorTopHand();
    	   	}else{
    	   		DriverStation.reportError("Cannot lower elevator top hand; would crash into elevator bottom hand", false);
    	   	}
    	} else {
    		DriverStation.reportError("Cannot lower elevator top hand; already at min height.\n", false);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   		Robot.pole.lowerElevatorTopHand();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Robot.pole.getPositionTopHand() <= Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN) ||
    			(Robot.pole.handsNearCrashing() && Robot.pole.getAntiCrashingMode());
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pole.stopElevatorTopHand();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.pole.stopElevatorTopHand();
    }
}
