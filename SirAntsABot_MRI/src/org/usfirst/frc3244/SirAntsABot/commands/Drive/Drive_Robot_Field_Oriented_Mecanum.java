// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

/**
 *
 */
public class Drive_Robot_Field_Oriented_Mecanum extends Command {
	
	private double max = 1;
	private double min = -1;
	private double deadband = .2;
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Drive_Robot_Field_Oriented_Mecanum() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	max = RobotMap.RobotDriveTrainSettings.MAX_DRIVE_SPEED.get();
    	min = RobotMap.RobotDriveTrainSettings.MAX_DRIVE_SPEED.get();
    	deadband = RobotMap.RobotDriveTrainSettings.DEADBAND.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double x = -Robot.oi.xbox_1.getRawAxis(0);
    	double y = -Robot.oi.xbox_1.getRawAxis(1);
    	double r = Robot.oi.xbox_1.getRawAxis(4)*.5; //Soften Rotation by half
    	double gyro = Robot.driveTrain.my_Get_Gyro();
    	
    	double RobotStartOriantation = -1; // -1 = Ball Forward
    	
    	x = RobotStartOriantation * Robot.driveTrain.target(x,deadband,min,max);
    	y = RobotStartOriantation * Robot.driveTrain.target(y,deadband,min,max);
    	r = Robot.driveTrain.target(r,deadband,min,max);
    	
    	
    	Robot.driveTrain.my_Drive_Mecanum(x, y, r, gyro);
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
    	end();
    }
}
