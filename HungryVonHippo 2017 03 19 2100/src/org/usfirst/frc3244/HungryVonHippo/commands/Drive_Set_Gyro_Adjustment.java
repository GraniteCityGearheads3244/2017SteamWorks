package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_Set_Gyro_Adjustment extends Command {

	private double m_adjustment;
    public Drive_Set_Gyro_Adjustment(double adjustment) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_adjustment = adjustment;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setgyroOffset(m_adjustment);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
