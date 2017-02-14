package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance_Strafe;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Spin_In_Place;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_To_Setpoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Vision_Auto_SpinFromLowBarForShotReady extends CommandGroup {
    
    public  Vision_Auto_SpinFromLowBarForShotReady() {
    	
    	//to protect arm do this last//addParallel(new Auto_SetWrist_to_GoalChooser());
    	
    	addSequential(new Drive_Spin_In_Place(-110));//60));
    	addParallel(new Wrist_To_Setpoint(40),1);
    	addSequential(new Drive_Robot_Orianted_Distance(36,0.5));
    	addSequential(new Vision_XY());
    }
}
