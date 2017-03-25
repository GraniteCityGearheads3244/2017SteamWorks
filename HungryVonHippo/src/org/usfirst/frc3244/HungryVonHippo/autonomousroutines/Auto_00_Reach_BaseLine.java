package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistance;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Adjustment;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_00_Reach_BaseLine extends CommandGroup {

    public Auto_00_Reach_BaseLine() {
    	addSequential(new Drive_Set_Gyro_Chooser(),1); //Not set
    	addSequential(new DriveForDistance(0.0, 0.5, 0.0, 10, 0.0));
    }
}
