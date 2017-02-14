package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.MoveElevatorTopHandToPosition;
import org.mayheminc.robot2015.commands.ZeroElevatorEncoders;

//import org.mayheminc.robot2015.subsystems.Drive;
//import org.mayheminc.robot2015.autonomousroutines.*;
//import java.math.*;

/**
 *
 */
public class SelfTest extends CommandGroup {
    
    public  SelfTest() {
    	super("SelfTest");
    	
    	// Zero elevator
    	addSequential(new ZeroElevatorEncoders());

    	// Test elevator top hand
    	addSequential(new SelfTestElevatorTopHand());
    	
    	// Move elevator top hand out of the way
    	addSequential(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4));

    	// Test elevator bottom hand
    	addSequential(new SelfTestElevatorBottomHand());
    	
    	addParallel(new SelfTestDrive(SelfTestDrive.k_frontLeft));
    	addParallel(new SelfTestDrive(SelfTestDrive.k_frontRight));
    	addParallel(new SelfTestDrive(SelfTestDrive.k_backLeft));
    	addSequential(new SelfTestDrive(SelfTestDrive.k_backRight));
    	
    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_outerLeft));
    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_innerLeft));
    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_innerRight));
    	addSequential(new SelfTestIRSensor(SelfTestIRSensor.k_outerRight));
    	
    	addSequential(new SelfTestGyro());
    }
}
