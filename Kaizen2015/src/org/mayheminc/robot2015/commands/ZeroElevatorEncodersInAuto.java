package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class ZeroElevatorEncodersInAuto extends Command {
	int m_bottomHandZeroValue = 0;
	int m_lastValueBottomHand = 0;
	int m_numberOfTimesBottomHand = 0;
	boolean m_bottomHandZeroed = false;
	
	int m_topHandZeroValue = 0;
	int m_lastValueTopHand = 0;
	int m_numberOfTimesTopHand = 0;
	boolean m_topHandZeroed = false;	

	int m_numberOfTimesBothZeroed = 0;
	boolean m_bothHandsZeroed = false;
	
    public ZeroElevatorEncodersInAuto(int topHandZeroValue, int bottomHandZeroValue) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole);
    	
    	// this method will use the current position as the zero position with the value below
    	m_topHandZeroValue = topHandZeroValue;
    	m_bottomHandZeroValue = bottomHandZeroValue;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.pole.clearZeroed();
    	DriverStation.reportError("Zero Elevator State: Init\n", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriverStation.reportError("Zero Elevator State: Execute\n", false);
    	Robot.pole.resetPositionBottomHand(m_bottomHandZeroValue);
		Robot.pole.resetPositionTopHand(m_topHandZeroValue);		
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	DriverStation.reportError("Zero Elevator State: isFinished\n", false);
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pole.setZeroed();
    	Robot.pole.moveElevatorBottomHandToPosition(m_bottomHandZeroValue, false);
		Robot.pole.moveElevatorTopHandToPosition(m_topHandZeroValue);
    	DriverStation.reportError("Zero Elevator State: End\n", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Zero Elevator State: Interrupted\n", false);
	}
}
