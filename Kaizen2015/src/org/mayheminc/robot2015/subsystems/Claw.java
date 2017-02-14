package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	
	/*
	 * Claw usage discussion:
	 * 
	 * When is the claw used?
	 * 1: Closed in order to pick up an RC from the floor or step
	 * 2: Opened in order to set an RC on a stack
	 * 3: Opened to set an RC down on the floor (not sure when we would do this...)
	 * 4: Opened in order to "re-align" the claw to be lower on the RC
	 * 5: Closed again at the end of "re-alignment" to hold lower on the RC  
	 */
    
    private Solenoid clawSolenoid = new Solenoid(RobotMap.CLAW_SOLENOID);
    private Solenoid linkageSolenoid = new Solenoid(RobotMap.LINKAGE_SOLENOID);
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	public void closeClaw() {
        // this method closes the claw
        clawSolenoid.set(true);
    }
	
	public void openClaw() {
        // this method opens the claw
        clawSolenoid.set(false);
    }
	public void raiseClaw(){
		linkageSolenoid.set(true);
	}
	public void lowerClaw(){
		linkageSolenoid.set(false);
	}
	
	
}

