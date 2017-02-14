package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

/**
 *
 */
public class Auto_Track_Target_Center extends Command {

    private static final int image_Width = 320;
    private static final int image_Height = 240;
	private double errorX=0;
	private double errorY=0;
	private double image_targetArea = 5000;
	private double w_setpoint=0;
	private boolean target_Found = false;
	
	
	public Auto_Track_Target_Center() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.driveTrain);
		requires(Robot.wristPID);
		requires(Robot.vision);
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	System.out.println("Auto_Track_Target_Center init");
    	//SmartDashboard.putNumber("Tunning", .005);
    	Robot.vision.my_CameraLight_Switch(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timeSinceInitialized() > Robot.vision.light_Settle_Time){
//	    	
	    	errorX = Robot.vision.my_Get_Xcenter_PID();
	    	errorY = Robot.vision.my_Get_Ycenter_PID();
	    	//Turn Robot
	    	Robot.driveTrain.my_Drive_Mecanum(0,0, errorX*0.005, 0);
	    	
	    	//Adjust wrist
	    	w_setpoint = Robot.wristPID.getPosition()-(errorY*0.05);

			Robot.wristPID.setSetpoint(w_setpoint );
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Auto_Track_Target_Center end()");
    	
    	
    	if(Robot.vision.my_Target_Found()){
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

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
