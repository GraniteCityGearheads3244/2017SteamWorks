package org.usfirst.frc3244.SirAntsABot.commands.Drive;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_Turbo_NoTimeOut extends Command {

    public Drive_Turbo_NoTimeOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveVelocity);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveVelocity.myTurbo();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveVelocity.myNormal();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
