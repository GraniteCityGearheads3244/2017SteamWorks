package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance_Strafe;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_To_Setpoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_03_Strafe_To_Position_Under_LowBar extends CommandGroup {
    
    public  Auto_03_Strafe_To_Position_Under_LowBar() {
      
    	addSequential(new Auto_Cycle_Secure_Ball());
    	addParallel(new Wrist_To_Setpoint(5));
    	addSequential(new Drive_Robot_Orianted_Distance_Strafe(57));
    	addSequential(new Auto_Drive_UnderLowBar());
        
        
    }
}
