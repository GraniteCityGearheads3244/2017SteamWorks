/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;





import org.mayheminc.robot2015.Robot;
//import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.*;
import org.mayheminc.robot2015.subsystems.Drive;
//import org.mayheminc.robot2015.subsystems.Drive;


/**
 *
 * @author Ken Streeter
 */
public class DriveCornerToZone extends CommandGroup {
    
    public DriveCornerToZone() {
        
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));

        // bulldoze to auto zone at medium speed
        if (Robot.PRACTICE_BOT) {
            addSequential(new DriveForDistance(0.0, 0.50, 0.0, 12000, 0.0));
    	} else {
            addSequential(new DriveForDistance(0.0, 0.50, 0.0, 10000, 0.0));
        }
        addSequential(new RaiseClawLinkage());
        
       // ALL DONE!
    }
}
