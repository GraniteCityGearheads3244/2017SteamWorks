package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_Track_Target_XY extends CommandGroup {
    
    public  Auto_Track_Target_XY() {
       
    	addParallel(new Auto_Track_Target_Y_Center_PID());
    	addSequential(new Auto_Track_Target_X_Center_PID());
    	
    }
}
