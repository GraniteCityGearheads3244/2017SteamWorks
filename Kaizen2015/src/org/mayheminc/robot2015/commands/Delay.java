
package org.mayheminc.robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Noel
 */
public class Delay extends Command {
    private long delay;
    private long endTime;
    
    public Delay(long delay) {
        super("Delay");
        this.delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        endTime = System.currentTimeMillis() + delay;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}