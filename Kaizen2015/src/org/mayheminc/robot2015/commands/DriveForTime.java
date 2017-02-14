package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 */
public class DriveForTime extends Command {
    private double m_x;
    private double m_y;
    private double m_rotation;
    private double m_time; // in seconds
    private double m_heading;
    Timer m_timer = new Timer();

    /**
     * Drive in a direction for an amount of time
     * @param x - left/right
     * @param y - forward/backwards
     * @param rotation 0 
     * @param time - number of seconds to drive
     * @param heading 0 
     */
    public DriveForTime(double x, double y, double rotation, double time, double heading) {
    	requires(Robot.drive);
    	m_x = x;
    	m_y = y;
    	m_rotation = rotation;
    	m_time = time;
    	m_heading = heading;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.zeroDistanceTraveled();
    	m_timer.reset();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.mecanumDriveAutonomous(m_x, m_y, m_rotation, m_heading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_timer.get() >= m_time);
    }

    // Called once after isFinished returns true
    protected void end() {
    	// note:  it is important to call mecanumDriveCartesian here, rather than mecanumDriveAutonomous,
    	// to ensure that "heading preservation" isn't activated for the last instruction
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	SmartDashboard.putNumber("Time", m_timer.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// note:  it is important to call mecanumDriveCartesian here, rather than mecanumDriveAutonomous,
    	// to ensure that "heading preservation" isn't activated for the last instruction
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	SmartDashboard.putNumber("Time", m_timer.get());
    }
}
