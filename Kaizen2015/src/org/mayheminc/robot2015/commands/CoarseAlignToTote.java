package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.subsystems.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CoarseAlignToTote extends Command {
	
	double [] m_vector = new double[3];
	double m_timeout = 0;
	boolean m_isAligned = false;
	int m_loopsAligned = 0;
	private static int LOOPS_REQUIRED = 3;

    public CoarseAlignToTote(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_timeout = timeout;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("AlignToTote", "CoarseInit");
    	m_isAligned = false;
    	m_loopsAligned = 0;
        setTimeout(m_timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	m_isAligned = Robot.drive.determineMovementForToteCoarseAlignment(m_vector);
    	
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
    	Robot.drive.mecanumDriveCartesian(0,0,0);
    	if (isTimedOut()) {
    		DriverStation.reportError("CoarseAlignToTote Timed Out\n", false);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.mecanumDriveCartesian(0,0,0);
    }
}
