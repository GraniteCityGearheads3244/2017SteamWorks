package org.mayheminc.robot2015.commands;


import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class RaiseElevatorTopHand extends Command {

    public RaiseElevatorTopHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.topElevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.pole.getPositionTopHand() < Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX) {
    		Robot.pole.raiseElevatorTopHand();
    	} else {
    		DriverStation.reportError("Cannot raise elevator top hand; already at max height.\n", false);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.pole.raiseElevatorTopHand();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {  
    	return (Robot.pole.getPositionTopHand() > Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX);
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
