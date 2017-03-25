package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator_Jog_Down extends Command {

    public Elevator_Jog_Down() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double newSetpoint = SmartDashboard.getNumber(Robot.elevatorServo.KEY, 0.5) + Robot.elevatorServo.RAMP;
    	
    	if (newSetpoint>1.0){
    		newSetpoint = 1;
    	}else if(newSetpoint<0){
    		newSetpoint = 0;
    	}
    	SmartDashboard.putNumber(Robot.elevatorServo.KEY, newSetpoint);
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
