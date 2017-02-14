package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.subsystems.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignToTote extends Command {
	
	private double [] m_vector = new double[3];
	private double m_timeout = 0;
	private boolean m_useAutoRemainingTimeout = false;
	private double m_autoRemainingTimeout;
	private boolean m_isAligned = false;
	private int m_loopsAligned = 0;
	private static int LOOPS_REQUIRED = 3;

    public AlignToTote(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_useAutoRemainingTimeout = false;
    	m_timeout = timeout;
    }

    public AlignToTote(boolean useAutoRemainingTimeout, double autoRemainingTimeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_useAutoRemainingTimeout = useAutoRemainingTimeout;
    	m_autoRemainingTimeout = autoRemainingTimeout;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("AlignToTote", "Init");
    	m_isAligned = false;
    	m_loopsAligned = 0;
    	if (m_useAutoRemainingTimeout) {
    		m_timeout = Robot.autonomousTimeRemaining() - m_autoRemainingTimeout;
    		DriverStation.reportError("Timeout = " + m_timeout + "\n", false);    		
    		if (m_timeout <= 0.1 ) {
    			m_timeout = 0.1;
    		}
    		DriverStation.reportError("Using timeout of " + m_timeout + "\n", false);
    	}
        setTimeout(m_timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	m_isAligned = Robot.drive.determineMovementForToteAlignment(m_vector);
    	
    	if (m_isAligned) {
    		m_loopsAligned++;
    	} else {
    		m_loopsAligned = 0;
    	}
    	
    	Robot.drive.mecanumDriveCartesian(m_vector[0], m_vector[1], m_vector[2]);
    	
    	SmartDashboard.putString("AlignToTote", m_isAligned ? "Aligned" : "Not Aligned");
    	SmartDashboard.putNumber("Align X", m_vector[0]);
    	SmartDashboard.putNumber("Align Y", m_vector[1]);
    	SmartDashboard.putNumber("Align R", m_vector[2]);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_loopsAligned >= LOOPS_REQUIRED) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (isTimedOut()) {
    		DriverStation.reportError("AlignToTote Timed Out.\n", false);
    	} else {
    		DriverStation.reportError("AlignToTote Succeeded!!!\n", false);    		
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
