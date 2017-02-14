package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Vision_Auto_Track_Target_XY extends CommandGroup {
    
    public  Vision_Auto_Track_Target_XY() {
       
    	addParallel(new Vision_Auto_Track_Target_Y_Center_PID());
    	addSequential(new Vision_Auto_Track_Target_X_Center_PID());
    	
    }
}
