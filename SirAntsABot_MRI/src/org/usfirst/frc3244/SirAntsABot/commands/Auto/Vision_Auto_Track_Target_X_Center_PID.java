package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision_Auto_Track_Target_X_Center_PID extends PIDCommand {
	 	private static final int image_Width = RobotMap.CAMERA_IMAGE_WIDTH;
	    private static final int image_Height = RobotMap.CAMERA_IMAGE_HEIGHT;
		private double errorX=0;
		private double errorY=0;
		private double errorTarget=0;
		private double image_targetArea = 5000;
		private double w_setpoint=0;
		private boolean target_Found = false;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Vision_Auto_Track_Target_X_Center_PID() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("PID_Spin", .0045, 0.000003, 0.000002, 0.02); //0.00002, 0.000002, 0.02);//, 0.00005, 0.0002, 0.02);
        getPIDController().setContinuous(false);
        getPIDController().setOutputRange(-.15, .15);
        getPIDController().setAbsoluteTolerance(5);
        
      
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        //requires(Robot.driveTrain);
		//needs Robot.vision but so do others
		//requires(Robot.vision);
       
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage
       
        return Robot.vision.my_Get_Xcenter_REAL_PID();

      
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	if(timeSinceInitialized() > Robot.vision.light_Settle_Time){
    		Robot.driveTrain.my_Drive_Mecanum(0,0, output, 0);
    	}
       
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//PIDController pid = getPIDController();
    	//pid.reset();
    	//pid.enable();
    	//System.out.println("Auto_Track_Target_X_Center_PID init");
    	//SmartDashboard.putNumber("Tunning", .005);
    	Robot.vision.my_CameraLight_Switch(true);
    	System.out.println("X Tracking Started");
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
    	System.out.println("Auto_Track_Target_X_CenterPID end()");
    	Robot.driveTrain.my_Drive_Mecanum(0,0,0,0);
    	Robot.vision.my_CameraLight_Switch(false);
    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}