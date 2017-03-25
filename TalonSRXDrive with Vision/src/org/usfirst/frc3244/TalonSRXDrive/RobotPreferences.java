package org.usfirst.frc3244.TalonSRXDrive;

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
    
}
