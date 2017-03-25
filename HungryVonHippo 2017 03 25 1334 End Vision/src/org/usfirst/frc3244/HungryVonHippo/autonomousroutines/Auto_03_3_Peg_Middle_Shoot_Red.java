package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveTurnToSetpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Blue Alliance Hopper Autonomous Sequence continued from the Place Left Gear
 */
public class Auto_03_3_Peg_Middle_Shoot_Red extends CommandGroup {

	private static final double ROBOT_TRAVLE_TO_RED_BOILER_ANGLE = 100;
	private static final double ROBOT_TO_RED_BOILER_ANGLE = 130;
	
    public Auto_03_3_Peg_Middle_Shoot_Red() {
    	//Run Peg Left routine
    	addSequential(new Auto_03_1_Peg_Middle()); //Not set
    	//Spin to Boiler
    	addSequential(new DriveTurnToSetpoint(0.0, 0.0, ROBOT_TRAVLE_TO_RED_BOILER_ANGLE));
    	//Drive forward Closer to the Boiler
    	addSequential(new DriveForDistance(0.0, 0.5, 0.0, 10),5);//, ROBOT_TO_RED_BOILER_ANGLE),5); //+x is Left);
    	//Spin to line up with Boiler
    	addSequential(new DriveTurnToSetpoint(0.0, 0.0, ROBOT_TO_RED_BOILER_ANGLE));
    	//Find Target
    	// TO DO
    	//Deliver Fuel for the remainder of the match
    	addSequential(new FlyWheel_Run(FlyWheel_Run.PIN_LEFT_FUEL_POWER),15);
    }
}
