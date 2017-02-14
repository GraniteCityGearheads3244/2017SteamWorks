/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.mayheminc.robot2015.commands.*;
//import org.mayheminc.robot2015.subsystems.Drive;

/**
 *
 * @author Ken Streeter
 */
public class SquareTest extends CommandGroup {
    
    public SquareTest() {
        super("SquareTest");
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

		// have the robot "translate" in a square for testing
        addSequential(new ZeroGyro());
        addSequential(new DriveForDistance(0.70, 0.0, 0.0, 5000, 0.0));
        addSequential(new Delay(1000));
        addSequential(new DriveForDistance(0.0, -0.35, 0.0, 3000, 0.0));
        addSequential(new Delay(1000));
        addSequential(new DriveForDistance(-0.70, 0.0, 0.0, 5000, 0.0));
        addSequential(new Delay(1000));
        addSequential(new DriveForDistance(0.0, 0.35, 0.0, 3000, 0.0));
       
    }
}
