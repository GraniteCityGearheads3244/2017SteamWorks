package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Vision_Auto_02_Straight_Under_LowBar extends CommandGroup {
    
    public  Vision_Auto_02_Straight_Under_LowBar() {
       
    	 addSequential(new Auto_Cycle_Secure_Ball(),10); //10 second time out
    	 addSequential(new Vision_Auto_Drive_UnderLowBar());
    }
}
