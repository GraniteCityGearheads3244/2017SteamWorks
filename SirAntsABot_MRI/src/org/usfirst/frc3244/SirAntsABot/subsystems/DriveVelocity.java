package org.usfirst.frc3244.SirAntsABot.subsystems;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveVelocity extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void myTurbo(){
        DriveTrain.velacityFactor = RobotMap.RobotDriveTrainSettings.VELOCITY_TURBO.get();
        
    }
    public void myNormal(){
        DriveTrain.velacityFactor = RobotMap.RobotDriveTrainSettings.VELOCITY_NORMAL.get();
    }
    public void mySnail(){
        DriveTrain.velacityFactor = RobotMap.RobotDriveTrainSettings.VELOCITY_SNAIL.get();
    }
}

