// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.HungryVonHippo.commands;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3244.HungryVonHippo.Robot;

/**
 *
 */
public class Winch_Jog extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_Power;
    private boolean m_ls1_2_at_Start;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Winch_Jog(double Power) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_Power = Power;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.winch.initializeLS_Counters();
    	m_ls1_2_at_Start = Robot.winch.isSwitchSet_Before_Start();
    	Robot.drive.set_PreserveHeading(false);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.winch.My_Winch_Jog_Up(m_Power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.winch.isSwitchSet() || m_ls1_2_at_Start;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.winch.My_Winch_Jog_Up(0);
    	Robot.drive.set_PreserveHeading(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
