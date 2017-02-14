package org.usfirst.frc3244.SirAntsABot.commands;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DemoToggle_Command extends Command {

    public DemoToggle_Command() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before thisCommand runs the first time
    protected void initialize() {
    	if(RobotMap.isCompetitionBot){
    		RobotMap.isCompetitionBot=false;
    	}else{
    		RobotMap.isCompetitionBot=true;
    	}
    	Robot.driveVelocity.myNormal();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
