package org.mayheminc.robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
//import org.mayheminc.robot2015.subsystems.Elevator;
import org.mayheminc.robot2015.Robot;

/**
 *
 */
public class SetElevatorSpeed extends Command {

	private double m_speed = 0.0;
	
    public SetElevatorSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.pole.setElevatorTopHandManualControlSpeed(m_speed);
    	Robot.pole.setElevatorBottomHandManualControlSpeed(m_speed);
    	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
