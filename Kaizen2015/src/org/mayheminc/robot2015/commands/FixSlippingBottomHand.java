package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FixSlippingBottomHand extends Command {

    public FixSlippingBottomHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriverStation.reportError("In FixSlippingBottomHand.initialize()", false);
//    	Robot.elevator.fixSlippingBottomHand(Elevator.ElevatorBottomPositions[Robot.elevator.getIndexBottomHand()]);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Belt Slipping Reset is complete", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
