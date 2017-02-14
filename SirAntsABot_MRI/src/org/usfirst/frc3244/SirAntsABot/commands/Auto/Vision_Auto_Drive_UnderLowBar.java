package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Orianted_Distance;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Cycle;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Reset;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_To_Setpoint;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Vision_Auto_Drive_UnderLowBar extends CommandGroup {
    
    public  Vision_Auto_Drive_UnderLowBar() {
        
    	addSequential(new Wrist_To_Setpoint(10));
        addSequential(new Drive_Robot_Orianted_Distance(-170));//150));
        addSequential(new Vision_Auto_SpinFromLowBarForShotReady());
        
        
        // Auto_Score_Ball_In_Goal() Not Tested
        //addSequential(new Auto_Score_Ball_In_Goal()); //NOPE
        //**********TryThis
        //Integer goalChoice = (Integer) Robot.goalChooser.getSelected();
        //addSequential(new Auto_Cycle_Shoot_Ball(goalChoice));
        
      
        
        //************This Always Shoots
        //addSequential(new PinBall_Cycle());
        //addSequential(new PinBall_Reset());
    }

	
}
