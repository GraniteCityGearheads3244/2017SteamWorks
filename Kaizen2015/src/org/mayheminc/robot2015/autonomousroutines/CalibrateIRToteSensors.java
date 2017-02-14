package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.Command;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotPreferences;

/**
 *
 */
public class CalibrateIRToteSensors extends Command {
	private int m_count = 0;
	private double m_leftBucket = 0.0;
	private double m_rightBucket = 0.0;
	private final int NUMBER_OF_TESTS = 10;
	

    public CalibrateIRToteSensors() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_count = 0;
    	m_leftBucket = 0;
    	m_rightBucket = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_count++;
    	m_leftBucket += Robot.drive.getIRVoltage(1);
    	m_rightBucket += Robot.drive.getIRVoltage(2);
    	   	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_count >= NUMBER_OF_TESTS;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotPreferences.putLeftIRValue(m_leftBucket/m_count);
    	RobotPreferences.putRightIRValue(m_rightBucket/m_count);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
}
