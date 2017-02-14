package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.util.CouldCrashException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevatorBottomHandToPosition extends Command {
    private int m_desiredPosition;
    private int m_tolerance = 60;
    private boolean m_done;
    private boolean m_override;
    private boolean m_couldCrash = false;

    public MoveElevatorBottomHandToPosition(int desiredPosition) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.bottomElevator);
    	m_desiredPosition = desiredPosition;
    	m_override = false;
    	m_done = false;
    }

    public MoveElevatorBottomHandToPosition(int desiredPosition, boolean override) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.bottomElevator);
    	m_desiredPosition = desiredPosition;
    	m_override = override;
    	m_done = false;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	m_couldCrash = false;
    	// tell the Elevator to move to the desired position
    	m_done = false;
    	DriverStation.reportError("Moving Bottom Hand to Position: "+ m_desiredPosition + "\n", false);
    	m_couldCrash = Robot.pole.moveElevatorBottomHandToPosition(m_desiredPosition, m_override);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// done if we are within m_tolerance of m_desiredPosition
    	m_done = (((m_desiredPosition - m_tolerance < Robot.pole.getPositionBottomHand()) &&
   			 (Robot.pole.getPositionBottomHand() < m_desiredPosition + m_tolerance))
   			 || m_couldCrash);  		
    	return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Finished Bottom Hand to Position: "+ m_desiredPosition + "\n", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Interrupted Bottom Hand to Position: "+ m_desiredPosition + "\n", false);
	}
}