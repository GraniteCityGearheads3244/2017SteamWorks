package org.mayheminc.robot2015.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.subsystems.*;

/**
 *
 */
public class PermissionToLift extends Command {

	private boolean m_done = false;
	private boolean m_toteInCarryPosition;
	
    public PermissionToLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole.bottomElevator);
    }

    // Permission to lift has three different stages of operation:
    // 1 - wait for coarse alignment
    // 2 - after coarse alignment 
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	// if we already have 5 totes, don't allow PermissionToLift to run again
    	if (Robot.pole.getPositionBottomHand() >
    		(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_5 + Robot.pole.bottomElevator.CARRY_OFFSET )) {
        	DriverStation.reportError("Skipping PermissionToLift, have 5 totes...\n", false);
        	m_done = true;
    	} else {    	
        	DriverStation.reportError("Starting PermissionToLift...\n", false);
        	m_done = false;
	    	// change tote to carry height
	    	Robot.pole.addCarryHeight();
	    	m_toteInCarryPosition = true;
	    	Robot.drive.clearPriorToteAlignment();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// only change things if there is still work to do...
    	if (!m_done) {
	    	// when we have "coarse" alignment, lower the bottom hand to the pickup position
	    	if (m_toteInCarryPosition &&
	    			Robot.drive.permissionToLiftCheckToteCoarseAligned()) {    		
	    		// lower the bottom hand to the pickup position
	    		Robot.pole.removeCarryHeight();
	    		
	    		// turn off the flag so we don't call Remove.. repeatedly
	    		m_toteInCarryPosition = false;
	    	}
	    	
	    	// when we have proper alignment, lift up the tote and end the command
	    	if (!m_done && Robot.drive.permissionToLiftCheckToteAligned()) {    		
	    		Robot.pole.moveElevatorBottomHandUpOneIndexPlusCarryOffset();
	    		m_done = true;
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_done && Robot.pole.isBottomHandAtDesiredPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Finished PermissionToLift\n", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Aborting PermissionToLift...\n", false);
    }
}
