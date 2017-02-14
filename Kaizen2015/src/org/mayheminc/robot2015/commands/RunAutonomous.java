
package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Noel
 */
public class RunAutonomous extends Command {
    private long startTime;
    Command command;
    
    public RunAutonomous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//        requires(Robot.autonomous);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = System.currentTimeMillis() + Robot.autonomous.getDelay() * 1000;
        command = Robot.autonomous.getAutonomousCommand();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (System.currentTimeMillis() >= startTime) {
            command.start();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= startTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
