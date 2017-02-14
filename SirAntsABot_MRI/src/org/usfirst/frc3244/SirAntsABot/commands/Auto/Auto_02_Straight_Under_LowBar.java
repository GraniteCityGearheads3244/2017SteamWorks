package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_02_Straight_Under_LowBar extends CommandGroup {
    
    public  Auto_02_Straight_Under_LowBar() {
       
    	 addSequential(new Auto_Cycle_Secure_Ball(),10); //10 second time out
    	 addSequential(new Auto_Drive_UnderLowBar());
    }
}
