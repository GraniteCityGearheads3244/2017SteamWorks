package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevatorTopHandDownOneIndex extends Command {

    public MoveElevatorTopHandDownOneIndex() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.topElevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.pole.moveElevatorTopHandDownOneIndex();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.pole.isTopHandAtDesiredPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
