package org.mayheminc.robot2015;

import org.mayheminc.robot2015.commands.*;

import org.mayheminc.util.DisabledOnlyJoystickButton;
import org.mayheminc.util.EnabledOnlyJoystickButton;
import org.mayheminc.util.JoystickAxisButton;
import org.mayheminc.util.JoystickPOVButton;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.mayheminc.robot2015.autonomousroutines.UprightAnRC;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	//driver pad and stick
	public static final Joystick DRIVER_PAD = new Joystick(RobotMap.DRIVER_GAMEPAD);
	
	
	
	public static final Joystick DRIVER_STICK = new Joystick(RobotMap.DRIVER_JOYSTICK);
	private static final Button DRIVER_STICK_BUTTON_ONE = new EnabledOnlyJoystickButton(DRIVER_STICK, 1);
	private static final Button DRIVER_STICK_BUTTON_TWO = new EnabledOnlyJoystickButton(DRIVER_STICK, 2);
	private static final Button DRIVER_STICK_BUTTON_THREE = new EnabledOnlyJoystickButton(DRIVER_STICK, 3);
	private static final Button DRIVER_STICK_BUTTON_FOUR = new DisabledOnlyJoystickButton(DRIVER_STICK, 4);
	private static final Button DRIVER_STICK_BUTTON_FIVE = new DisabledOnlyJoystickButton(DRIVER_STICK, 5);
	private static final Button DRIVER_STICK_BUTTON_SIX = new DisabledOnlyJoystickButton(DRIVER_STICK, 6);
	private static final Button DRIVER_STICK_BUTTON_SEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 7);
	private static final Button DRIVER_STICK_BUTTON_EIGHT= new DisabledOnlyJoystickButton(DRIVER_STICK, 8);
	private static final Button DRIVER_STICK_BUTTON_NINE = new DisabledOnlyJoystickButton(DRIVER_STICK, 9);
	private static final Button DRIVER_STICK_BUTTON_TEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 10);
	private static final Button DRIVER_STICK_BUTTON_ELEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 11);
	
	//operator pad and stick
	public static final Joystick OPERATOR_PAD = new Joystick(RobotMap.OPERATOR_GAMEPAD);
	private static final Button OPERATOR_PAD_BUTTON_ONE = new JoystickButton(OPERATOR_PAD, 1);
	private static final Button OPERATOR_PAD_BUTTON_TWO = new JoystickButton(OPERATOR_PAD, 2);
	private static final Button OPERATOR_PAD_BUTTON_THREE = new JoystickButton(OPERATOR_PAD, 3);
	private static final Button OPERATOR_PAD_BUTTON_FOUR = new JoystickButton(OPERATOR_PAD, 4);
	private static final Button OPERATOR_PAD_BUTTON_FIVE = new JoystickButton(OPERATOR_PAD, 5);
	private static final Button OPERATOR_PAD_BUTTON_SIX = new JoystickButton(OPERATOR_PAD, 6);

	private static final Button OPERATOR_PAD_BUTTON_SEVEN = new JoystickButton(OPERATOR_PAD, 7);
	private static final Button OPERATOR_PAD_BUTTON_EIGHT = new JoystickButton(OPERATOR_PAD, 8);
	private static final Button OPERATOR_PAD_BUTTON_NINE = new JoystickButton(OPERATOR_PAD, 9);
	private static final Button OPERATOR_PAD_BUTTON_TEN = new JoystickButton(OPERATOR_PAD, 10);
	
	private static final int OPERATOR_PAD_BUTTON_ELEVEN = 11;
	private static final int OPERATOR_PAD_BUTTON_TWELVE = 12;
	
	public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
	public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
	public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
	public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;
	
//	Operator Control Buttons
	private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
	private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);
	private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
	private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);
	
//	public static final Joystick OPERATOR_STICK = new Joystick(RobotMap.OPERATOR_JOYSTICK);
//	private static final Button OPERATOR_STICK_BUTTON_ONE = new JoystickButton(OPERATOR_STICK, 1);
//	private static final Button OPERATOR_STICK_BUTTON_TWO = new JoystickButton(OPERATOR_STICK, 2);
//	private static final Button OPERATOR_STICK_BUTTON_THREE = new JoystickButton(OPERATOR_STICK, 3);
//	private static final Button OPERATOR_STICK_BUTTON_FOUR = new JoystickButton(OPERATOR_STICK, 4);
//	private static final Button OPERATOR_STICK_BUTTON_FIVE = new JoystickButton(OPERATOR_STICK, 5);
//	private static final Button OPERATOR_STICK_BUTTON_SIX = new JoystickButton(OPERATOR_STICK, 6);
//	private static final Button OPERATOR_STICK_BUTTON_SEVEN = new JoystickButton(OPERATOR_STICK, 7);
//	private static final Button OPERATOR_STICK_BUTTON_EIGHT= new JoystickButton(OPERATOR_STICK, 8);
//	private static final Button OPERATOR_STICK_BUTTON_NINE = new JoystickButton(OPERATOR_STICK, 9);
//	private static final Button OPERATOR_STICK_BUTTON_TEN = new JoystickButton(OPERATOR_STICK, 10);
//	private static final Button OPERATOR_STICK_BUTTON_ELEVEN = new JoystickButton(OPERATOR_STICK, 11);
	
	
	//	Axis Definitions for the F310 gamepad
	//	Axis 0 - Left X Axis (-1.0 left to +1.0 right)
	//	Axis 1 - Left Y Axis (-1.0 forward to +1.0 backward)
	//	Axis 2 - Left Trigger (0.0 unpressed to +1.0 fully pressed)
	//	Axis 3 - Right Trigger (0.0 unpressed to +1.0 fully pressed)
	//	Axis 4 - Right X Axis (-1.0 left to +1.0 right)
	//	Axis 5 - Right Y axis (-1.0 forward to +1.0 backward)
	//  Empirical testing on 23 Jan 2015 shows that +/-0.07 is a reasonable threshold for "centered"
	//   (in other words, intentional non-zero values will have magnitude >= 0.07;
	//    values with a magnitude of < 0.07 should probably be treated as zero.)
	public static final int GAMEPAD_F310_LEFT_X_AXIS = 0;
	public static final int GAMEPAD_F310_LEFT_Y_AXIS = 1;
	public static final int GAMEPAD_F310_LEFT_TRIGGER = 2;
	public static final int GAMEPAD_F310_RIGHT_TRIGGER = 3;
	public static final int GAMEPAD_F310_RIGHT_X_AXIS = 4;
	public static final int GAMEPAD_F310_RIGHT_Y_AXIS = 5;

	public static final int GAMEPAD_F310_A_BUTTON = 1;
	public static final int GAMEPAD_F310_B_BUTTON = 2;
	public static final int GAMEPAD_F310_X_BUTTON = 3;
	public static final int GAMEPAD_F310_Y_BUTTON = 4;
	public static final int GAMEPAD_F310_LEFT_BUTTON = 5;
	public static final int GAMEPAD_F310_RIGHT_BUTTON = 6;
	public static final int GAMEPAD_F310_BACK_BUTTON = 7;
	public static final int GAMEPAD_F310_START_BUTTON = 8;
	public static final int GAMEPAD_F310_LEFT_STICK_BUTTON = 9;
	public static final int GAMEPAD_F310_RIGHT_STICK_BUTTON = 10;
	
	// Driver Control Modes
    private static final Button TOGGLE_CLOSED_LOOP_MODE_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 7); 
    private static final Button TOGGLE_FOD_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 8);  
    
	private static final JoystickAxisButton DRIVER_PAD_LEFT_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD, GAMEPAD_F310_LEFT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);
	private static final JoystickAxisButton DRIVER_PAD_RIGHT_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD, GAMEPAD_F310_RIGHT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);
	
	private static final Button DRIVER_PAD_START_BUTTON = new JoystickButton(DRIVER_PAD, GAMEPAD_F310_START_BUTTON);
    
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_UP = new JoystickPOVButton(DRIVER_PAD, 0);
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_RIGHT = new JoystickPOVButton(DRIVER_PAD, 90);
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_DOWN = new JoystickPOVButton(DRIVER_PAD, 180);
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_LEFT = new JoystickPOVButton(DRIVER_PAD, 270);
		
    private static final JoystickPOVButton OPERATOR_PAD_D_PAD_LEFT = new JoystickPOVButton(OPERATOR_PAD, 270);
    private static final JoystickPOVButton OPERATOR_PAD_D_PAD_RIGHT = new JoystickPOVButton(OPERATOR_PAD, 90);
    private static final JoystickPOVButton OPERATOR_PAD_D_PAD_UP = new JoystickPOVButton(OPERATOR_PAD, 0);
    private static final JoystickPOVButton OPERATOR_PAD_D_PAD_DOWN = new JoystickPOVButton(OPERATOR_PAD, 180);
    
    
	public OI() {
    	DriverStation.reportError("OI constructor.\n", false);
        // Re-initialization buttons
        DRIVER_STICK_BUTTON_ONE.whenPressed(new ZeroElevatorEncodersTop());
        DRIVER_STICK_BUTTON_EIGHT.whenPressed(new CalibrateGyro());
        DRIVER_STICK_BUTTON_NINE.whenPressed(new SetPIDF()); 
    	
        // Autonomous Program Selection Buttons
    	DRIVER_STICK_BUTTON_FIVE.whenPressed(new SelectAutonomousDelay(1));
    	DRIVER_STICK_BUTTON_FOUR.whenPressed(new SelectAutonomousDelay(-1));
    	DRIVER_STICK_BUTTON_ELEVEN.whenPressed(new SelectAutonomousProgram(1));
    	DRIVER_STICK_BUTTON_TEN.whenPressed(new SelectAutonomousProgram(-1));
    	DRIVER_STICK_BUTTON_SIX.whenPressed(new SelectAutonomousProgram(10));
    	DRIVER_STICK_BUTTON_SEVEN.whenPressed(new SelectAutonomousProgram(-10));
        
    	// Set up Elevator Controls        
     	DRIVER_STICK_BUTTON_TWO.whileHeld(new NudgeElevatorBottomHandDown());
     	DRIVER_STICK_BUTTON_THREE.whileHeld(new NudgeElevatorBottomHandUp());
  	     	
//     	**********************DRIVER BUTTONS AND CONTROLS***************************
     	DRIVER_PAD_LEFT_TRIGGER_BUTTON.whenPressed(new OpenClaw());
     	DRIVER_PAD_RIGHT_TRIGGER_BUTTON.whenPressed(new CloseClaw());
     	
     	DRIVER_PAD_D_PAD_UP.whenPressed(new RaiseCanburglarsBoth());
     	DRIVER_PAD_D_PAD_DOWN.whenPressed(new LowerCanburglarsBoth());
     	
     	DRIVER_PAD_START_BUTTON.whileHeld(new RetrieveRCFromStep());
     	
//     	**********************OPERATOR BUTTONS AND CONTROLS***************************
//     	.whenPressed(new FixSlippingBottomHand());
     	OPERATOR_PAD_BUTTON_FIVE.whenPressed(new MoveElevatorTopHandUpOneIndex());
     	OPERATOR_PAD_BUTTON_SEVEN.whenPressed(new MoveElevatorTopHandDownOneIndex());
     	OPERATOR_PAD_D_PAD_UP.whenPressed(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
     	OPERATOR_PAD_D_PAD_DOWN.whenPressed(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN));
     	OPERATOR_PAD_D_PAD_LEFT.whenPressed(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE));
     	OPERATOR_PAD_D_PAD_RIGHT.whileHeld(new PermissionToLift());
     	
     	OPERATOR_PAD_BUTTON_SIX.whenPressed(new MoveElevatorBottomHandUpOneIndex());
     	OPERATOR_PAD_BUTTON_EIGHT.whenPressed(new MoveElevatorBottomHandDownOneIndex());
 
     	OPERATOR_PAD_BUTTON_ONE.whenPressed(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1));
     	
     	
     	OPERATOR_PAD_BUTTON_TWO.whenPressed(new LowerClawLinkage());
     	OPERATOR_PAD_BUTTON_FOUR.whenPressed(new RaiseClawLinkage());
     	
     	OPERATOR_PAD_BUTTON_NINE.whenPressed(new HoldElevatorBottomHand());
//     	OPERATOR_PAD_BUTTON_TEN.whileHeld(new ToggleAntiCrashingMode());
     	OPERATOR_PAD_BUTTON_TEN.whileHeld(new UprightAnRC());

     	
     	// Operator Axis Buttons:
        OPERATOR_PAD_LEFT_Y_AXIS_UP.whileHeld(new RaiseElevatorTopHand());
        OPERATOR_PAD_LEFT_Y_AXIS_DOWN.whileHeld(new LowerElevatorTopHand());
        
        OPERATOR_PAD_RIGHT_Y_AXIS_UP.whileHeld(new RaiseElevatorBottomHand());
        OPERATOR_PAD_RIGHT_Y_AXIS_DOWN.whileHeld(new LowerElevatorBottomHand());

    	// Mode initialization
        TOGGLE_FOD_BUTTON.whenPressed(new ToggleFieldOrientedDrive());
        TOGGLE_CLOSED_LOOP_MODE_BUTTON.whenPressed(new ToggleClosedLoopMode());
        
	}
	
	public double driveX() {
		return(DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_LEFT_X_AXIS));
	}
	
	public double driveY() {
		// The driveY is the "Y" Axis of the Driver Gamepad.
		// However, the joysticks give -1.0 on the Y axis when pushed forward
		// This method reverses that, so that positive numbers are forward
		return (-DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_LEFT_Y_AXIS));
	}
	
	public double driveRotation() {
		return DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_RIGHT_X_AXIS);
	}
	
    public boolean driveTurboMode() {
        return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_LEFT_BUTTON));
    }
    
    public boolean toteAlign(){
    	return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_RIGHT_BUTTON));
    }
    
    public boolean crawlBackward() {
        return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_A_BUTTON));
    }
    
    public boolean crawlForward() {
        return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_Y_BUTTON));
    }
    
    public boolean crawlLeft() {
        return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_X_BUTTON));
    }
    
    public boolean crawlRight() {
        return(DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_B_BUTTON));
    }
    
    public boolean superSlowTopHand(){
    	return OPERATOR_PAD.getRawButton(OI.OPERATOR_PAD_BUTTON_ELEVEN);
    }
    
    public boolean superSlowBottomHand(){
    	return OPERATOR_PAD.getRawButton(OI.OPERATOR_PAD_BUTTON_TWELVE);
    }
    
    public void rumbleOperatorGamePad() {
    	DRIVER_PAD.setRumble(Joystick.RumbleType.kLeftRumble, 0);
    	DRIVER_PAD.setRumble(Joystick.RumbleType.kRightRumble, 0);
    }
        
    /**
     * This method will return a positive value when the right joystick is pushed up, 
     * and it will return a negative value when the right joystick is pushed down.
     * @return
     */
    public double getBottomHandManualControl(){
    	return (OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_Y_AXIS)) * -1;
    }

    /**
     * This method will return a positive value when the left joystick is pushed up, 
     * and it will return a negative value when the left joystick is pushed down.
     * @return
     */
    
    public double getTopHandManualControl(){
    	return (OPERATOR_PAD.getRawAxis(OPERATOR_PAD_LEFT_Y_AXIS)) * -1;
    }
}

