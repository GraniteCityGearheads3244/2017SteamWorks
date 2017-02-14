package org.usfirst.frc3244.SirAntsABot.commands.Auto;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Cycle;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Reset;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_Drive_UnderLowBar_test extends CommandGroup {
    
    public  Auto_Drive_UnderLowBar_test() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//addSequential(new Wrist_To_Setpoint(5));
       // addSequential(new Drive_Robot_Orianted_Distance(120));
        //addSequential(new Auto_SpinFromLowBarForShotReady());
        
        
        // Auto_Score_Ball_In_Goal() Not Tested
        //addSequential(new Auto_Score_Ball_In_Goal()); //NOPE
        //**********TryThis
        //Integer goalChoice = (Integer) Robot.goalChooser.getSelected();
        //addSequential(new Auto_Cycle_Shoot_Ball(goalChoice));
        
        //************This Always Shoots
    	
    	addSequential(new Auto_SetWrist_to_GoalChooser());
        addSequential(new PinBall_Cycle());
        addSequential(new PinBall_Reset());
    }

	
}
