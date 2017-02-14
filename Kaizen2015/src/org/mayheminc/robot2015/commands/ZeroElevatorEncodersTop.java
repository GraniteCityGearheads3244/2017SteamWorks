package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;

import org.mayheminc.robot2015.subsystems.ElevatorBase;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class ZeroElevatorEncodersTop extends Command {
	int m_lastValueBottomHand = 0;
	int m_numberOfTimesBottomHand = 0;
	boolean m_bottomHandZeroed = false;
	
	int m_topHandZeroValue = 0;
	int m_lastValueTopHand = 0;
	int m_numberOfTimesTopHand = 0;
	boolean m_topHandZeroed = false;	

	int m_numberOfTimesBothZeroed = 0;
	boolean m_bothHandsZeroed = false;
	
    public ZeroElevatorEncodersTop() {
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
    	
    	DriverStation.reportError("Zero Elevator State: Init\n", false);
    	
    	Robot.pole.lowerElevatorBottomHandZeroing();
    	Robot.pole.raiseElevatorTopHandZeroing();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	
    	
    	
    	/**
    	 * This block of code will do one of the following:
    	 * 1) continue to lower the bottom hand and monitor its position
    	 * 2) stop the bottom hand because it is already zeroed
    	 */
    	
    	if (m_bottomHandZeroed == false) {
    		Robot.pole.lowerElevatorBottomHandZeroing();
    		int encoderValue = (int)Robot.pole.getPositionBottomHand();
    		if (encoderValue == m_lastValueBottomHand) {
    			m_numberOfTimesBottomHand++;    		
    		} else {
    			m_numberOfTimesBottomHand = 0;
    		}
    		if (m_numberOfTimesBottomHand >= 5) {
    			m_bottomHandZeroed = true;

    			Robot.pole.resetPositionBottomHand();

    		}
    		m_lastValueBottomHand = encoderValue;
    		
    		DriverStation.reportError("Zero Elevator State: Lowering Bottom\n", false);
    	} else {
    		Robot.pole.stopElevatorBottomHand();
    	}
    	
    	
    	
    	/**
    	 * This block will do one of the following:
    	 * 1) continue to raise the top hand and monitor its position
    	 * 2) stop the top hand because it is already zeroed
    	 */
    	
    	
    	// if the top is moving to zero...
    	if (m_topHandZeroed == false) {
    		
    		Robot.pole.raiseElevatorTopHandZeroing();
    		int encoderValue = (int)Robot.pole.getPositionTopHand();
    		if (encoderValue == m_lastValueTopHand) {
    			m_numberOfTimesTopHand++;    		
    		} else {
    			m_numberOfTimesTopHand = 0;
    		}
    		if (m_numberOfTimesTopHand >= 5) {
    			m_topHandZeroed = true;
    			Robot.pole.resetPositionTopHand(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION);
    		}
    		
    		m_lastValueTopHand = encoderValue;
    		
    		DriverStation.reportError("Zero Elevator State: Raising Top\n", false);   		
			
    	}else{
    		Robot.pole.stopElevatorTopHand();
    	}
    	
    	
    	
    	
    	
    	
    		
    		
    	
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (m_topHandZeroed && m_bottomHandZeroed) {
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
