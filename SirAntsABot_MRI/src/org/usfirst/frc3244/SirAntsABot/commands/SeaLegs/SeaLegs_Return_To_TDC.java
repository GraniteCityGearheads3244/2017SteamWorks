package org.usfirst.frc3244.SirAntsABot.commands.SeaLegs;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SeaLegs_Return_To_TDC extends Command {

    private double m_SeaLeg_Left_Pos;
	private double m_SeaLeg_Right_Pos;
	private double m_SeaLeg_Left_delta_to_TDC;
	private double m_SeaLeg_Right_delta_to_TDC;

	public SeaLegs_Return_To_TDC() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.seaLegs);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_SeaLeg_Left_Pos = Robot.seaLegs.my_Get_Left_Encoder();
    	m_SeaLeg_Right_Pos = Robot.seaLegs.my_Get_Right_Encoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	m_SeaLeg_Left_Pos = Robot.seaLegs.my_Get_Left_Encoder();
    	m_SeaLeg_Right_Pos = Robot.seaLegs.my_Get_Right_Encoder();
    	
    	m_SeaLeg_Left_delta_to_TDC = m_SeaLeg_Left_Pos%360;
    	m_SeaLeg_Right_delta_to_TDC = m_SeaLeg_Right_Pos%360;

    	double left_kp = 0.2;
    	double right_kp = 0.15;
    	
    	double powerLeft = Robot.seaLegs.target(m_SeaLeg_Left_delta_to_TDC * left_kp, 0, -0.75, 0.75);
    	double powerRight = Robot.seaLegs.target(m_SeaLeg_Right_delta_to_TDC * right_kp, 0, -0.75, 0.75);
    	if(Robot.seaLegs.my_SeaLeg_Left_At_TDC()){
    		powerLeft=0.0;
    	}
    	if(Robot.seaLegs.my_SeaLeg_Right_At_TDC()){
    		powerRight=0.0;
    	}
    	Robot.seaLegs.my_SeaLegs_drive_Tank(-powerLeft, -powerRight);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.seaLegs.my_SeaLeg_Left_At_TDC() && Robot.seaLegs.my_SeaLeg_Right_At_TDC();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.seaLegs.my_SeaLegs_drive_Tank(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
