// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3244.SirAntsABot.Robot;

/**
 *
 */
public class Wrist_To_Setpoint extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_setpoint;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Wrist_To_Setpoint(double setpoint) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_setpoint = setpoint;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.wristPID);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=INITIALIZE
        Robot.wristPID.enable();
        Robot.wristPID.setSetpoint(m_setpoint);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=INITIALIZE
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if(Robot.scissorPID.getPosition()>35){
    		return Robot.wristPID.my_Get_Wrist_Angle()>24;
    	}else if(Robot.scissorPID.getPosition()>15.5){
    		return Robot.wristPID.my_Get_Wrist_Angle()>85;
    	}else{ 
    		return Robot.wristPID.getPosition()<Robot.wristPID.getSetpoint() + 3 &&
        		Robot.wristPID.getPosition()>Robot.wristPID.getSetpoint() - 3;	
    	}
    	
    	// original statment: 
        //return Robot.wristPID.getPosition()<Robot.wristPID.getSetpoint() + 3 &&
        //		Robot.wristPID.getPosition()>Robot.wristPID.getSetpoint() - 3;

    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
