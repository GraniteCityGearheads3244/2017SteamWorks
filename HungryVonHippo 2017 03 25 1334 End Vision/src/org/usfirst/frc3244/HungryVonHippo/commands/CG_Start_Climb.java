package org.usfirst.frc3244.HungryVonHippo.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_Start_Climb extends CommandGroup {

    public CG_Start_Climb() {
       addParallel(new Drive_Disable_MaintainHeading(),1);
       //addSequential(new );
    }
}
