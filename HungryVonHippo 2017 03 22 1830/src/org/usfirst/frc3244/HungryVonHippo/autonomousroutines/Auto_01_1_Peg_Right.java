package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.Robot;
import org.usfirst.frc3244.HungryVonHippo.commands.CG_Leave_Gear_On_Peg;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveTurnToSetpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Adjustment;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.Wrist_to_Setpoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_01_1_Peg_Right extends CommandGroup {

	private static final double ROBOT_TO_PEG_ANGLE = -150.0;
    public Auto_01_1_Peg_Right() {
    	//configure Gyro
    	addSequential(new Drive_Set_Gyro_Chooser(),1);
    	//Revere to base line keeping a heading of 180.0deg
    	addSequential(new DriveForDistance(0.0, -0.5, 0.0, 10.25, 179.9));
    	//Set Wrist to present Gear and spin to pin
    	//addParallel(new Wrist_to_Setpoint(Robot.wrist.PRESENT_GEAR_ON_PEG),5);
    	addSequential(new DriveTurnToSetpoint(0.0, 0.0, ROBOT_TO_PEG_ANGLE));
    	//Strife to pin
    	addSequential(new DriveForDistance(-0.4, 0.0, 0.0, 3.5, 360+ROBOT_TO_PEG_ANGLE),5); //+x is Left
    	//Leave the gear and move away
    	addSequential(new CG_Leave_Gear_On_Peg(),5);
    }
}