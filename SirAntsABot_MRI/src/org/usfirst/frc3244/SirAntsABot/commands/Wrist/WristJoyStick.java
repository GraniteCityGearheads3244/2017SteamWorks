package org.usfirst.frc3244.SirAntsABot.commands.Wrist;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristJoyStick extends Command {

    public WristJoyStick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wristPID);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wristPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double stick = Robot.oi.xbox_2.getRawAxis(5);
    	double currentAngle = Robot.wristPID.getPosition();
    	if(Math.abs(stick)>.2){
    		
    		 //System.out.println((stick*5));
    	     Robot.wristPID.setSetpoint(currentAngle+(stick*15));
    	}
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
