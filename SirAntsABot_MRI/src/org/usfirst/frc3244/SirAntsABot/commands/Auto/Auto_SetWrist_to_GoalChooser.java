package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.subsystems.WristPID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_SetWrist_to_GoalChooser extends Command {
	private double angle;
	
    public Auto_SetWrist_to_GoalChooser() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wristPID);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Integer goalChoice = (Integer) Robot.goalChooser.getSelected();
    	angle = goalAngel(goalChoice);
    	Robot.wristPID.enable();
        Robot.wristPID.setSetpoint(angle);
    	
    }

    

	// Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.wristPID.getPosition()<Robot.wristPID.getSetpoint() + 3 &&
        		Robot.wristPID.getPosition()>Robot.wristPID.getSetpoint() - 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private double goalAngel(Integer goalChoice) {
		if (goalChoice == 2){
			return RobotMap.LOW_GOAL_ANGLE;
		}else if (goalChoice == 1) {
			return RobotMap.HIGH_GOAL_ANGLE_FROM_RAMP;
		}else{ // (goalChoice == 0) or invalid
			return RobotMap.WRIST_STOW_ANGLE;
		}
	}
}
