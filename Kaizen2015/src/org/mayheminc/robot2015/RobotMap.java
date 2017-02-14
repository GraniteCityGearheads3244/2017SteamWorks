package org.mayheminc.robot2015;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// CAN Talons
	public static final int FRONT_LEFT_TALON = 1;
	public static final int FRONT_RIGHT_TALON = 2;
	public static final int BACK_LEFT_TALON = 3;
	public static final int BACK_RIGHT_TALON = 4;
	
	public static final int ELEVATOR_TOP_HAND_TALON = 5;
	public static final int ELEVATOR_BOTTOM_HAND_TALON = 6;
	
	// digital inputs / outputs
	public static final int ELEVATOR_BOTTOM_HAND_ENCODER_A = 0;
	public static final int ELEVATOR_BOTTOM_HAND_ENCODER_B = 1;
	public static final int ELEVATOR_TOP_HAND_ENCODER_A = 2;
	public static final int ELEVATOR_TOP_HAND_ENCODER_B = 3;
	public static final int ELEVATOR_DOWN_LIMIT = 4;
	
    // Analog inputs
    public static final int HEADING_GYRO = 0;
    public static final int RANGE_FINDER_LEFT_OUTBOUND = 4;
    public static final int RANGE_FINDER_LEFT_INBOUND = 5;
    public static final int RANGE_FINDER_RIGHT_INBOUND = 6;
    public static final int RANGE_FINDER_RIGHT_OUTBOUND = 7;

	// Joysticks
	public static final int DRIVER_GAMEPAD = 0;
	public static final int DRIVER_JOYSTICK = 1;
	public static final int OPERATOR_GAMEPAD = 2;
	public static final int OPERATOR_JOYSTICK = 3;
	
	//Solenoids:
	public static final int LIGHTING_SOLENOID_0 = 0;
	public static final int LIGHTING_SOLENOID_1 = 1;
	public static final int CANBURGLAR_RIGHT_SOLENOID = 2;	
	public static final int CLAW_SOLENOID = 3;
	public static final int LINKAGE_SOLENOID = 4; 	
	public static final int CANBURGLAR_LEFT_SOLENOID = 5;
	
	// PDP Channels = 
	public static final int DRIVE_FRONT_LEFT_PDP = 13;
	public static final int DRIVE_FRONT_RIGHT_PDP = 2;
	public static final int DRIVE_BACK_LEFT_PDP = 14;
	public static final int DRIVE_BACK_RIGHT_PDP = 1;
	
	public static final int ELEVATOR_TOP_HAND_PDP = 12;
	public static final int ELEVATOR_BOTTOM_HAND_PDP = 3;
	
	public static final int talonPDP[] = { DRIVE_FRONT_LEFT_PDP, DRIVE_FRONT_RIGHT_PDP,
								    DRIVE_BACK_LEFT_PDP, DRIVE_BACK_RIGHT_PDP };
	
}

