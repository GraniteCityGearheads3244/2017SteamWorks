package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Vision_XY extends Command {
	Vision_Auto_Track_Target_Y_Center_PID y = null;
	Vision_Auto_Track_Target_X_Center_PID x = null;
	
	Timer targetTime = new Timer();
	double thisScan = 0;
	double delayshotTime = 1;
	
    public Vision_XY() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	 requires(Robot.wristPID);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	y = new Vision_Auto_Track_Target_Y_Center_PID();
    	x = new Vision_Auto_Track_Target_X_Center_PID();
    	
    	y.start();
    	x.start();
    	//
    	targetTime.start();
    	targetTime.reset();   
    	thisScan = targetTime.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double abs_error = Math.abs(Robot.vision.my_Get_Xcenter_PID());
    	System.out.println("abs_Error = " +abs_error);
    	if(abs_error<8 && Robot.vision.my_Target_Found()){
    		thisScan = targetTime.get();
    	}else{
    		targetTime.reset();
    	}
        return thisScan > delayshotTime;//isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	y.cancel();
    	x.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
