package org.usfirst.frc3244.Watson.commands;


import org.usfirst.frc3244.Watson.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlyWheel_Jog extends Command {

    public FlyWheel_Jog() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.fuel_Delivery.My_FlywheelJog(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.fuel_Delivery.My_FlywheelJog(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
