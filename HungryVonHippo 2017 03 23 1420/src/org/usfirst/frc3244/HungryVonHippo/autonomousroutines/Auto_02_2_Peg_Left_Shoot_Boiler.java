package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.DriveTurnToSetpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Blue Alliance Hopper Autonomous Sequence continued from the Place Left Gear
 */
public class Auto_02_2_Peg_Left_Shoot_Boiler extends CommandGroup {

	private static final double ROBOT_TO_BLUE_BOILER_ANGLE = -130.0;
	
    public Auto_02_2_Peg_Left_Shoot_Boiler() {
    	//Run Peg Left routine
    	addSequential(new Auto_02_1_Peg_Left()); //Not set
    	//Spin to Boiler
    	addSequential(new DriveTurnToSetpoint(0.0, 0.0, ROBOT_TO_BLUE_BOILER_ANGLE));
    	//addSequential(new DriveForDistance(0.0, 0.0, 0.0, 3, -130.0),5); //+x is Left);
    	//Find Target
    	// TO DO
    	//Deliver Fuel for the remainder of the match
    	addSequential(new FlyWheel_Run(FlyWheel_Run.PIN_LEFT_FUEL_POWER),15);
    }
}
