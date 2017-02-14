package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Drive_OverObstical extends Command {
	private double m_seaLegPower;
	private boolean forward = true;
	private double max = 1;
	private double min = -1;
	private double deadband = .2;
    private double m_Distance;
    
    
    public Auto_Drive_OverObstical(double Distance, double SeaLegPower) {
    	m_seaLegPower = -SeaLegPower;
		m_Distance = Distance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.seaLegs);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.my_Reset_Left_Front_Encoder();
    	max = RobotMap.RobotDriveTrainSettings.MAX_DRIVE_SPEED.get();
    	min = RobotMap.RobotDriveTrainSettings.MAX_DRIVE_SPEED.get();
    	deadband = RobotMap.RobotDriveTrainSettings.DEADBAND.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	m_seaLegPower = Robot.seaLegs.target(m_seaLegPower,.1,-0.85,0.85);
    	Robot.seaLegs.my_SeaLegs_drive_Tank(m_seaLegPower, m_seaLegPower);
    	
    	double y_D = 0.0;
		//X = Left/Right
		//y = Forward Back
    	//Power
    	double x = 0.0;
    	double y = 0.5;
    	double r = 0.0;
    	double gyro = 0 ;
    	
    	//Direction?
    	if(m_Distance<0){
    		forward=true;
    		y_D = -1;
    	}else{
    		forward=false;
    		y_D = 1;
    	}
    	
    	x = Robot.driveTrain.target(x,deadband,min,max);
    	y = y_D * Robot.driveTrain.target(y,deadband,min,max);
    	r = Robot.driveTrain.target(r,deadband,min,max);
    	
    	
    	
    	Robot.driveTrain.my_Drive_Mecanum(-x, -y, r, gyro);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (forward && (Robot.driveTrain.my_Get_Left_Front_Encoder() < m_Distance)) || //or
        		(!forward && (Robot.driveTrain.my_Get_Left_Front_Encoder() > m_Distance));
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
