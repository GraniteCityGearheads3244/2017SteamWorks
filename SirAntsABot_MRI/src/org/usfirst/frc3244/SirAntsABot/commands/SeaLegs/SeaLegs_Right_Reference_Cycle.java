package org.usfirst.frc3244.SirAntsABot.commands.SeaLegs;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SeaLegs_Right_Reference_Cycle extends CommandGroup {
    
    public  SeaLegs_Right_Reference_Cycle() {
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
    	addSequential(new SeaLegs_Reference_Right());
    	addSequential(new SeaLegs_Reference_Right_Step2());
    }
}
