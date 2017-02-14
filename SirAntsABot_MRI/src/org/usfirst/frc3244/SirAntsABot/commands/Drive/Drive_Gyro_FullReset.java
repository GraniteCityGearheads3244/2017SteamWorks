package org.usfirst.frc3244.SirAntsABot.commands.Drive;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_Gyro_FullReset extends Command {

    public Drive_Gyro_FullReset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setTimeout(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.my_zeroHeading(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	end();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
