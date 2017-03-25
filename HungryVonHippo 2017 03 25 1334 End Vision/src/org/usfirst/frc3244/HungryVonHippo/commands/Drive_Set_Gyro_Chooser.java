package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive_Set_Gyro_Chooser extends Command {

	private double m_angle;
	
	
    public Drive_Set_Gyro_Chooser() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setTimeout(.5);
    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Integer goalChoice = (Integer) Robot.StartUpChooser.getSelected();
    	m_angle = StartAngel(goalChoice);
    	SmartDashboard.putNumber("Start up Angle", m_angle);
    	Robot.drive.setgyroOffset(m_angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    private double StartAngel(Integer goalChoice) {
		if (goalChoice == 0){ //North
			return 0.0;
		}else if (goalChoice == 180) { //South
			return 180.0;
		}else if (goalChoice == 90) { //East
			return 90.0;
		}else if (goalChoice == -90) { //west
			return -90.0;
		}else{ // (goalChoice == 99) or invalid
			return SmartDashboard.getNumber("Start up Angle", 0);
		}
	}
}
