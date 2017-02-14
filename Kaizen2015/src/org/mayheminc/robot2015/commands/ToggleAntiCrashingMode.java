package org.mayheminc.robot2015.commands;

import org.mayheminc.robot2015.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleAntiCrashingMode extends Command {

	boolean m_completedThisCommand = false;
    public ToggleAntiCrashingMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.pole.getAntiCrashingMode()){
    		Robot.pole.setAntiCrashingMode(false);
    		DriverStation.reportError("AntiCrashing Mode has been turned OFF", false);
    		SmartDashboard.putBoolean("AntiCrashing Mode", false);
    	}else{
    		Robot.pole.setAntiCrashingMode(true);
    		DriverStation.reportError("AntiCrashing Mode has been turned ON", false);
    		SmartDashboard.putBoolean("AntiCrashing Mode", true);
    	}
    	m_completedThisCommand = true;
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_completedThisCommand;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
