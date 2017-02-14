package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;

import org.mayheminc.robot2015.subsystems.ElevatorBase;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class ZeroElevatorEncoders extends Command {
	int m_lastValueBottomHand = 0;
	int m_numberOfTimesBottomHand = 0;
	boolean m_bottomHandZeroed = false;
	
	int m_topHandZeroValue = 0;
	int m_lastValueTopHand = 0;
	int m_numberOfTimesTopHand = 0;
	boolean m_topHandZeroed = false;	

	int m_numberOfTimesBothZeroed = 0;
	boolean m_bothHandsZeroed = false;
	
    public ZeroElevatorEncoders() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pole);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Zero Elevator State", "Init");
    	Robot.pole.clearZeroed();
    	
    	m_lastValueBottomHand = 0;
    	m_numberOfTimesBottomHand = 0;
    	m_bottomHandZeroed = false;
    	
    	m_lastValueTopHand = 0;
    	m_numberOfTimesTopHand = 0;
    	m_topHandZeroed = false;	

    	m_numberOfTimesBothZeroed = 0;
    	m_bothHandsZeroed = false;
    	Robot.pole.setElevatorTopHandManualControlSpeed(Robot.pole.topElevator.ELEVATOR_ZEROING_SPEED);
    	Robot.pole.setElevatorBottomHandManualControlSpeed(Robot.pole.bottomElevator.ELEVATOR_ZEROING_SPEED);
    	DriverStation.reportError("Zero Elevator State: Init\n", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	/**
    	 * This block will only run if the bottom Hand is NOT zeroed.
    	 * This checks the number of times that the bottom hand has been in the same position
    	 * If the bottom hand is in the same position for five consecutive checks, the bottom hand is
    	 * considered zeroed.
    	 */
    	
    	if (m_bottomHandZeroed == false) {
    		Robot.pole.lowerElevatorBottomHand();
    		int encoderValue = (int)Robot.pole.getPositionBottomHand();
    		if (encoderValue == m_lastValueBottomHand) {
    			m_numberOfTimesBottomHand++;    		
    		} else {
    			m_numberOfTimesBottomHand = 0;
    		}
    		if (m_numberOfTimesBottomHand >= 5) {
    			m_bottomHandZeroed = true;
//    			m_topHandZeroed = true;			// WARNING: TEMP HACK ONLY
//    			m_bothHandsZeroed = true;		// WARNING: TEMP HACK ONLY
    			Robot.pole.resetPositionBottomHand();
//    			Robot.elevator.resetPositionTopHand(Elevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION);	
    		}
    		m_lastValueBottomHand = encoderValue;
    		SmartDashboard.putString("Zero Elevator State", "Lowering Bottom");
    		DriverStation.reportError("Zero Elevator State: Lowering Bottom\n", false);
    	} 
    	// if the bottom is zeroed and the top has not started to zero...
    	else if ((m_topHandZeroed == false) && m_topHandZeroValue !=0) {
    		Robot.pole.stopElevatorBottomHand();
			m_topHandZeroed = true;
			Robot.pole.resetPositionTopHand(m_topHandZeroValue);
    	} 
    	// if the top is moving to zero...
    	else if (m_topHandZeroed == false) {
    		Robot.pole.stopElevatorBottomHand();
    		Robot.pole.lowerElevatorTopHand();
    		int encoderValue = (int)Robot.pole.getPositionTopHand();
    		if (encoderValue == m_lastValueTopHand) {
    			m_numberOfTimesTopHand++;    		
    		} else {
    			m_numberOfTimesTopHand = 0;
    		}
    		if (m_numberOfTimesTopHand >= 5) {
    			m_topHandZeroed = true;
    			Robot.pole.resetPositionTopHand();
    		}
    		m_lastValueTopHand = encoderValue;
    		SmartDashboard.putString("Zero Elevator State", "Lowering Top");
    		DriverStation.reportError("Zero Elevator State: Lowering Top\n", false);
    	} 
    	// otherwise both are zeroed.
    	else {
    		m_bothHandsZeroed = true;
    		DriverStation.reportError("Zero Elevator State: Done 1\n", false);
    	}
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (m_topHandZeroed) {
    		m_numberOfTimesBothZeroed++;
    	}
		if (m_numberOfTimesBothZeroed >= 2) {
			m_bothHandsZeroed = true;
		}
        return m_bothHandsZeroed;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pole.stopElevatorBottomHand();
    	Robot.pole.stopElevatorTopHand();
    	Robot.pole.setElevatorTopHandManualControlSpeed(ElevatorBase.ELEVATOR_HIGH_SPEED);
    	Robot.pole.setElevatorBottomHandManualControlSpeed(ElevatorBase.ELEVATOR_HIGH_SPEED);
    	Robot.pole.setZeroed();
    	SmartDashboard.putString("Zero Elevator State", "End");
    	DriverStation.reportError("Zero Elevator State: End\n", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.pole.stopElevator();
    	Robot.pole.setElevatorTopHandManualControlSpeed(ElevatorBase.ELEVATOR_HIGH_SPEED);
    	Robot.pole.setElevatorBottomHandManualControlSpeed(ElevatorBase.ELEVATOR_HIGH_SPEED);
    	SmartDashboard.putString("Zero Elevator State", "Interrupted");
    	DriverStation.reportError("Zero Elevator State: Interrupted\n", false);
	}
}
