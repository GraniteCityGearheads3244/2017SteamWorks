package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_04_Straight_Over_RoughtTerrain extends CommandGroup {
    
    public  Auto_04_Straight_Over_RoughtTerrain() {
       
    	addSequential(new Auto_Cycle_Secure_Ball());
        //addSequential(new Drive_Robot_Orianted_Distance_FullPower(150)); //Up Ramp too
        //addSequential(new Auto_Drive_OverObstical(20,.85));
        
    }
}
