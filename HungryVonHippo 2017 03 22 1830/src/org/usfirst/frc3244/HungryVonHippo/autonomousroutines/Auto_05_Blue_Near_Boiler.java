package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistanceInTeleop;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Adjustment;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_05_Blue_Near_Boiler extends CommandGroup {

    public Auto_05_Blue_Near_Boiler() {
    	addSequential(new Drive_Set_Gyro_Chooser(),1); //Not set
    	//addSequential(new Drive_Set_Gyro_Adjustment(-90.0),1);  Replaced with Drive Set Gyro Chooser
    	//addSequential(new FlyWheel_Run(.8),7); //disabled for testing
    	addSequential(new DriveForDistanceInTeleop(-0.5, 0.0, 0, 5),3);
    }
    
}
