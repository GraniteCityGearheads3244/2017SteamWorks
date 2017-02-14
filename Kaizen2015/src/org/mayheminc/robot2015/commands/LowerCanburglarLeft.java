package org.mayheminc.robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2015.Robot;

/**
 *
 */
public class LowerCanburglarLeft extends Command {

    public LowerCanburglarLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.leftCanburglar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.leftCanburglar.lowerCanBurglar();
    	
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
