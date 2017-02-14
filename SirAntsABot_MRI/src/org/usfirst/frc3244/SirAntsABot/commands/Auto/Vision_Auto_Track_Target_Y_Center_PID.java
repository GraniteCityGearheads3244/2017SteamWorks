package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Cycle_w_Reset;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision_Auto_Track_Target_Y_Center_PID extends PIDCommand {
	 private static final int image_Width = RobotMap.CAMERA_IMAGE_WIDTH;
	    private static final int image_Height = RobotMap.CAMERA_IMAGE_HEIGHT;
		private double w_setpoint=0;
		private double PID_Deg_Multiplier = 1;
		private boolean target_Found = false;
		
  
    public Vision_Auto_Track_Target_Y_Center_PID() {

  
        super("PID_Wrist", .004, 0.000000, 0.0000, 0.02);//, 0.00005, 0.0002, 0.02);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(0.2);
        
        //requires(Robot.wristPID);
		
       
    }

    protected double returnPIDInput() {
       
        return Robot.vision.my_Get_Ycenter_REAL_PID();

    }

    protected void usePIDOutput(double output) {
        
    	if(timeSinceInitialized() > Robot.vision.light_Settle_Time){
    	w_setpoint = Robot.wristPID.getSetpoint()-(output*PID_Deg_Multiplier);
    	}else{
    		w_setpoint = Robot.wristPID.getSetpoint();
    	}
    	//System.out.println("w_setpoint=" +  w_setpoint);
		Robot.wristPID.setSetpoint(w_setpoint);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Auto_Track_Target_Y_Center_PID init");
    	//SmartDashboard.putNumber("Tunning", .005);
    	Robot.vision.my_CameraLight_Switch(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Auto_Track_Target_Y_CenterPID end()");
    	if(Robot.vision.my_Target_Found()){
    		double w_setpoint = Robot.wristPID.getPosition();
    		double final_Wrist_Deg = Robot.vision.my_GetFinalWristAngle(w_setpoint);
    		if(final_Wrist_Deg!=0){
    			w_setpoint = final_Wrist_Deg; 
    		}else{
    			w_setpoint = w_setpoint; 
    		}
    		System.out.println("Wrist = " + w_setpoint);
    		Robot.wristPID.setSetpoint(w_setpoint );
    		System.out.println("FIRE");
    		PinBall_Cycle_w_Reset makeShot = new PinBall_Cycle_w_Reset();
    		makeShot.start();
    	}
    	Robot.vision.my_CameraLight_Switch(false);
    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}