/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2015.autonomousroutines.*;
import org.mayheminc.robot2015.commands.AlignToTote;
import org.mayheminc.robot2015.commands.CoarseAlignToTote;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.DriveForDistanceWithDeceleration;
import org.mayheminc.robot2015.commands.RetrieveRCFromStep;



/**
 *
 * @author Noel
 */
public class Autonomous extends Subsystem {

    private static Command autonomousCommands[] = {          
        /* 0 */ new StayStill()                            		// do nothing								
        /* 1 */ , new CanTheftAuto()	
        /* 2 */ , new StackedToteSet(StackedToteSet.STRAIGHT)
        /* 3 */ , new StackedToteSet(StackedToteSet.LEFT)
        /* 4 */ , new StackedToteSet254()
        /* 5 */ , new DriveRearWallToZone() 
        /* 6 */ , new DriveCornerToZone()
        /* 7 */ , new DrivePlatformToZone()
        /* 8 */ , new DriveLineToZone()
        /* 9 */ , new StartLandfillHarvest()
        /*10 */ , new LandfillHarvest4Totes(LandfillHarvest4Totes.LEFT_TO_RIGHT)
        /*11 */ , new LandfillHarvest4Totes(LandfillHarvest4Totes.RIGHT_TO_LEFT)
        /*12 */ , new CanTheftAutoShort()
        
//        /* 10*/ , new SelfTest()
//        /* 11*/	, new CalibrateIRToteSensors()
        
//        /* 11*/ , new TwoTotesTwoRCs() 
//        /* 12*/ , new AlignNPickupTote()

        };
        
    private static Command[] initialTeleopCommands = {
    	/* 0 */   null
        /* 1 */ , null
        /* 2 */ , null
        /* 3 */ , null
        /* 4 */ , null
        /* 5 */ , null
        /* 6 */ , null
        /* 7 */ , null
        /* 8 */ , null
        /* 9 */ , null
        /*10 */ , null
        /*11 */ , null
        /*12 */ , null		// new DriveStraight(true, -1.0, (18*12)),
	};

    // set number of default program to run
    private static int programNumber = 2;
    private static int delay = 0;
    
    public Autonomous() {
        updateDashboard();
    }
    
    public void initDefaultCommand() {
    }
    
    public Command getAutonomousCommand() {
        return autonomousCommands[programNumber];
    }

	public Command getInitialTeleopCommand(){
		return initialTeleopCommands[programNumber];
	}
    
    public int getDelay() {
        return delay;
    }
    
    public void adjustProgramNumber(int delta) {
        programNumber += delta;
        if (programNumber < 0) {
            programNumber = autonomousCommands.length - 1;
        }
        else if (programNumber >= autonomousCommands.length) {
            programNumber = 0;
        }
        
        updateDashboard();
    }

    private static final int MAX_DELAY = 9;
    
    public void adjustDelay(int delta) {
        delay += delta;
        if (delay < 0) {
            delay = 0;
        }
        else if (delay > MAX_DELAY) {
            delay = MAX_DELAY;
        }
        
        updateDashboard();
    }
    
    private static StringBuffer sb = new StringBuffer();
    
    public static void updateDashboard() {
        sb.setLength(0);
        sb.append("Auto: ");
        sb.append(programNumber + " " + autonomousCommands[programNumber].getName());
        sb.append("         ");
        SmartDashboard.putString("Auto Prog", sb.toString());
        SmartDashboard.putNumber("Auto Delay", delay);
    }
    
    public String toString(){
        return autonomousCommands[programNumber].getName();
    }
}
