// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot.commands.Scissor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3244.SirAntsABot.Robot;

/**
 *
 */
public class Scissor_To_HMI extends Command {
	private double m_setpoint;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Scissor_To_HMI() {
    	
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.scissorPID);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_setpoint = SmartDashboard.getNumber("Scissor_Target");
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// Not Allow movement if Wrist Is up !
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	if(m_setpoint>70 && Robot.wristPID.getSetpoint()>20){
    		end();
    	}else{
    		if(m_setpoint>40 && Robot.wristPID.getSetpoint()>55){
        		end();
        	}else{
        		
        Robot.scissorPID.enable();
        Robot.scissorPID.setSetpoint(m_setpoint);

    
        	}
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.scissorPID.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	 Robot.scissorPID.setSetpoint(Robot.scissorPID.getPosition());
    }
}
