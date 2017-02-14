
package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Noel
 */
public class SelectAutonomousProgram extends Command {
    
    private int delta;

    public SelectAutonomousProgram(int delta) {
        requires(Robot.autonomous);
        setRunWhenDisabled(true);
        this.delta = delta;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.autonomous.adjustProgramNumber(delta);
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
