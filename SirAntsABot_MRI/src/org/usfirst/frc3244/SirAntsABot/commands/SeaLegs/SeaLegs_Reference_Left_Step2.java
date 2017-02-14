package org.usfirst.frc3244.SirAntsABot.commands.SeaLegs;

import org.usfirst.frc3244.SirAntsABot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SeaLegs_Reference_Left_Step2 extends Command {
	private double searchPower = 0.1;
    public SeaLegs_Reference_Left_Step2() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!Robot.seaLegs.my_SeaLeg_Left_At_TDC()){
    		Robot.seaLegs.my_SeaLeg_Left_initializeCounter();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.seaLegs.SeaLeg_Left_TDC_Found_FWD){
    		Robot.seaLegs.my_SeaLegs_Jog_Left(true,searchPower);
    		
    	}else{
    		Robot.seaLegs.my_SeaLegs_Jog_Left(false,searchPower);
    
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.seaLegs.my_SeaLeg_Left_isSwitchSet() || Robot.seaLegs.my_SeaLeg_Left_At_TDC();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.seaLegs.my_SeaLegs_Jog_Left(true,0);
    	Robot.seaLegs.my_Reset_Left_Encoder();
    	Robot.seaLegs.updateStatus();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.seaLegs.my_SeaLegs_Jog_Left(true,0);
    }
}
