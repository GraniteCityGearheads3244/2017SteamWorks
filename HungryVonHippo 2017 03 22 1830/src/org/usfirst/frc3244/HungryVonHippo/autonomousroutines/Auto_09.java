package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Chooser;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_09 extends CommandGroup {

    public Auto_09() {
    	addSequential(new Drive_Set_Gyro_Chooser(),1); //Not set
    }
}
