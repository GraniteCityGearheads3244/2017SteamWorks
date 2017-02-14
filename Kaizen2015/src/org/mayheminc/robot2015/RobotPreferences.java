package org.mayheminc.robot2015;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author Ken Streeter
 */
public class RobotPreferences {
    
    // PIDF Settings for Drive Wheel control loops in TalonSRX
	
    static public void putWheelP(double p) {
        Preferences.getInstance().putDouble("WheelP", p);
    }
    static public void putWheelI(double i) {
        Preferences.getInstance().putDouble("WheelI", i);
    }
    static public void putWheelD(double d) {
        Preferences.getInstance().putDouble("WheelD", d);
    }
    static public void putWheelF(double f) {
        Preferences.getInstance().putDouble("WheelF", f);
    }    
    static public double getWheelP() {
        return Preferences.getInstance().getDouble("WheelP", 1.6);
    }
    static public double getWheelI() {
        return Preferences.getInstance().getDouble("WheelI", 0.0);
    }
    static public double getWheelD() {
        return Preferences.getInstance().getDouble("WheelD", 0.0);
    }
    static public double getWheelF() {
        return Preferences.getInstance().getDouble("WheelF", 1.8);
    }
    
    static public void putElevatorP(double p) {
        Preferences.getInstance().putDouble("ElevatorP", p);
    }
    static public void putElevatorI(double i) {
        Preferences.getInstance().putDouble("ElevatorI", i);
    }
    static public void putElevatorD(double d) {
        Preferences.getInstance().putDouble("ElevatorD", d);
    }
    static public void putElevatorF(double f) {
        Preferences.getInstance().putDouble("ElevatorF", f);
    }
    
    static public double getElevatorP() {
        return Preferences.getInstance().getDouble("ElevatorP", 4.8);    // was 4.5 at GSD and UMASS
    }
    static public double getElevatorI() {
        return Preferences.getInstance().getDouble("ElevatorI", 0.0);
    }
    static public double getElevatorD() {
        return Preferences.getInstance().getDouble("ElevatorD", 240.0);  // changed to 240.0 at GSD, was 160.0 prior
    }
    static public double getElevatorF() {
        return Preferences.getInstance().getDouble("ElevatorF", 0.0);
    }
    
    
    static public void putLeftIRValue(double val) {
        Preferences.getInstance().putDouble("LeftIRValue", val);
    }
    static public void putRightIRValue(double val) {
        Preferences.getInstance().putDouble("RightIRValue", val);
    }   
    static public double getLeftIRValue() {
        return Preferences.getInstance().getDouble("LeftIRValue", 2.0);   
    }
    static public double getRightIRValue() {
        return Preferences.getInstance().getDouble("RightIRValue", 2.0);
    }
    static public double getIRThreshold(int IRChannel){
    	switch(IRChannel){
    	case 1: 
    		return getLeftIRValue();
    	case 2: 
			return getRightIRValue();
    	}
    	return 2.0;
    }
}
