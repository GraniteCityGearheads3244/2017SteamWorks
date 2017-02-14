package org.mayheminc.robot2015.commands;


import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.subsystems.ElevatorBase;

/**
 *
 */
public class RaiseElevatorBottomHand extends Command {

    public RaiseElevatorBottomHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.bottomElevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.pole.getPositionBottomHand() < Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MAX) {
        	if (!Robot.pole.handsNearCrashing() && Robot.pole.getAntiCrashingMode()){
        		Robot.pole.raiseElevatorBottomHand();
        	} else {
        		DriverStation.reportError("Cannot raise elevator bottom hand; will crash into elevator top hand", false);
        	}
    	} else {
    		DriverStation.reportError("Cannot raise elevator bottom hand; already at max height.\n", false);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.pole.raiseElevatorBottomHand();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {  
    	return (Robot.pole.getPositionBottomHand() > Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MAX) || 
    			(Robot.pole.handsNearCrashing() && Robot.pole.getAntiCrashingMode());
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
