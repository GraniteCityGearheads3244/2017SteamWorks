package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveTurnToSetpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Blue Alliance Hopper Autonomous Sequence continued from the Place Left Gear
 */
public class Auto_03_2_Peg_Middle_Shoot_Blue extends CommandGroup {

	private static final double ROBOT_TO_BLUE_BOILER_ANGLE = -90.0;
	
    public Auto_03_2_Peg_Middle_Shoot_Blue() {
    	//Run Peg Left routine
    	addSequential(new Auto_03_1_Peg_Middle()); //Not set
    	//Spin to Boiler
    	//Not Required
    	//Drive forward and square up to Driverstation Wall
    	addSequential(new DriveForDistance(0.6, 0.6, 0.0, 15, ROBOT_TO_BLUE_BOILER_ANGLE),5); //+x is Left);
    	addSequential(new DriveForDistance(0.4, 0.0, 0.0, 10, ROBOT_TO_BLUE_BOILER_ANGLE),2); //+x is Left);
    	//Find Target
    	// TO DO
    	//Deliver Fuel for the remainder of the match
    	addSequential(new FlyWheel_Run(FlyWheel_Run.PIN_LEFT_FUEL_POWER),15);
    }
}
