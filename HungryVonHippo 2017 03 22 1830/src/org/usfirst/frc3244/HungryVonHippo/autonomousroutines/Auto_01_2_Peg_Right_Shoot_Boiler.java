package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveTurnToSetpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Red Alliance Hopper Autonomous Sequence continued from the Place Left Gear
 */
public class Auto_01_2_Peg_Right_Shoot_Boiler extends CommandGroup {

	private static final double ROBOT_TO_RED_HOPPER_ANGLE = 130;
	
    public Auto_01_2_Peg_Right_Shoot_Boiler() {
    	//Run Peg Right routine
    	addSequential(new Auto_01_1_Peg_Right()); //Not set
    	//Spin to Boiler
    	addSequential(new DriveTurnToSetpoint(0.0, 0.0, ROBOT_TO_RED_HOPPER_ANGLE));
    	//Find Target
    	// TO DO
    	//Deliver Fuel for the remainder of the match
    	addSequential(new FlyWheel_Run(FlyWheel_Run.PIN_LEFT_FUEL_POWER),15);
    }
}
