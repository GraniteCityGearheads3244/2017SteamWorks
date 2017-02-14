package org.mayheminc.robot2015.commands;
import org.mayheminc.robot2015.Robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;



/**
 * Lower the elevator until the command is ended or until it reaches the bottom.
 */
public class LowerElevatorBottomHand extends Command {

    public LowerElevatorBottomHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.bottomElevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.pole.getPositionBottomHand() > Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MIN) {
    	   	Robot.pole.lowerElevatorBottomHand();
    	} else {
    		DriverStation.reportError("Cannot lower elevator bottom hand; already at min height.\n", false);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	   	Robot.pole.lowerElevatorBottomHand();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Robot.pole.getPositionBottomHand() <= Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pole.stopElevatorBottomHand();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.pole.stopElevatorBottomHand();
    }
}
