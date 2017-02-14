package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CanBurglar extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private Solenoid m_canBurlgarSolenoid;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public CanBurglar(int PCMPort){
    	m_canBurlgarSolenoid = new Solenoid(PCMPort);
    }
    
    public void lowerCanBurglar() {
    	m_canBurlgarSolenoid.set(true);
    }
    
    public void raiseCanBurglar() {
    	m_canBurlgarSolenoid.set(false);
    	
    }
}

