package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Track_Targets_Set_Wrist extends Command {

    public Auto_Track_Targets_Set_Wrist() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.vision.my_Target_Found()){
    		double w_setpoint = Robot.wristPID.getPosition();
    		double final_Wrist_Deg = Robot.vision.my_GetFinalWristAngle(w_setpoint);
    		if(final_Wrist_Deg!=0){
    			w_setpoint = final_Wrist_Deg; 
    		}else{
    			w_setpoint = w_setpoint; 
    		}
    		Robot.wristPID.setSetpoint(w_setpoint );
    	}
    	Robot.vision.my_CameraLight_Switch(false);
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
