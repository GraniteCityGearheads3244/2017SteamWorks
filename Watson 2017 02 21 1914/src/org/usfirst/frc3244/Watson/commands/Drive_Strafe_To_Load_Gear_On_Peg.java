package org.usfirst.frc3244.Watson.commands;

import org.usfirst.frc3244.Watson.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_Strafe_To_Load_Gear_On_Peg extends Command {

    public Drive_Strafe_To_Load_Gear_On_Peg() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.my_Basic_Drive_Mecanum(.25, 0, 0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.my_Basic_Drive_Mecanum(0, 0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
