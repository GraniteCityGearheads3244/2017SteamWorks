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
public class StayStill extends CommandGroup {
    
    public StayStill() {
        
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        
        // keep the RC hand out of the way and move the bottom hand to the correct height
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1, true));
        
        addSequential(new RaiseClawLinkage());
       // ALL DONE!
    }
}
