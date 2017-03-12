package org.usfirst.frc3244.Watson.commands;

import org.usfirst.frc3244.Watson.Robot;
import org.usfirst.frc3244.Watson.subsystems.Fuel_Delivery;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Deliver_Fuel extends Command {

	Timer delay = new Timer();
	boolean openloop = true;
	
    public Deliver_Fuel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.fuel_Delivery);
    	SmartDashboard.putNumber("Fuel Elevator", .15);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	delay.reset();
    	delay.start();
    	
		if(openloop ){
    		Robot.fuel_Delivery.changeControlMode(TalonControlMode.PercentVbus);
    	}else{
    		Robot.fuel_Delivery.changeControlMode(TalonControlMode.Speed);
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = SmartDashboard.getNumber("Fuel Elevator", .5);
    	
    	if(openloop){
    		Robot.fuel_Delivery.My_Flywheel_OpenLoop(.8);
    	}else{
    		Robot.fuel_Delivery.My_Flywheel_CloseLoop(300.0);
    	}
    	
    	
    	if(delay.get()> 1.5){
    		Robot.fuel_Delivery.My_Indexer(1);
    		Robot.fuel_Intake.My_Intake_Run(.75);
    	}
    	Robot.fuel_Delivery.diagnostics();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.fuel_Delivery.My_Flywheel_OpenLoop(0);
    	Robot.fuel_Delivery.My_Indexer(0);
    	Robot.fuel_Intake.My_Intake_Run(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
