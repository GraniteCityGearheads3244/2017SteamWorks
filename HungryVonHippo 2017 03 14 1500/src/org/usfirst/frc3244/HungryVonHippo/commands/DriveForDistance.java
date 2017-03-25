package org.usfirst.frc3244.HungryVonHippo.commands;


import org.usfirst.frc3244.HungryVonHippo.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForDistance extends Command {
	 private double m_x;
	 private double m_y;
	 private double m_rotation;
	 private double m_distance;
	 private double m_heading;
	 Timer m_timer = new Timer();
	 
	 public DriveForDistance(double x, double y, double rotation, double distance, double heading) {
	    	requires(Robot.drive);
	    	m_x = x;
	    	m_y = y;
	    	m_rotation = rotation;
	    	m_distance = distance;
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
        return (Robot.drive.getDistanceTraveled() >= m_distance);
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
