package org.usfirst.frc3244.Watson.commands;

import org.usfirst.frc3244.Watson.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Gear_Lift_Jog_Up extends Command {

    public Gear_Lift_Jog_Up() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear_Lift.My_GearLift_Jog(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gear_Lift.My_GearLift_Jog(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
