package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.DriveForDistanceInTeleop;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro_Adjustment;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_05_Blue_Near_Boiler extends CommandGroup {

    public Auto_05_Blue_Near_Boiler() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new Drive_Set_Gyro_Adjustment(-90.0),1);
    	//addSequential(new FlyWheel_Run(.8),7);
    	addSequential(new DriveForDistanceInTeleop(-0.5, 0.0, 0, 5),3);
    }
    
}
