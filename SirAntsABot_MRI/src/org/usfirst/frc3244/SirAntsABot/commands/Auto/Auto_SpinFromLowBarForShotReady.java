package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance_Strafe;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Spin_In_Place;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_SpinFromLowBarForShotReady extends CommandGroup {
    
    public  Auto_SpinFromLowBarForShotReady() {
    	
    	//to protect arm do this last//addParallel(new Auto_SetWrist_to_GoalChooser());
    	addSequential(new Drive_Robot_Orianted_Distance_Strafe(10));
    	addSequential(new Drive_Spin_In_Place(-60));
    	addSequential(new Drive_Robot_Orianted_Distance_Strafe(150));
    	addParallel(new Auto_SetWrist_to_GoalChooser());
    	addSequential(new Drive_Robot_Orianted_Distance(42));//,.25)); //Not Tested
    	addSequential(new Drive_Spin_In_Place(-10));
    }
}
