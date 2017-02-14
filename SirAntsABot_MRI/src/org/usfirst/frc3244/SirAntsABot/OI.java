// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot;

import org.usfirst.frc3244.SirAntsABot.commands.*;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Auto_Cycle_Secure_Ball;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Auto_Ready_Ball;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Auto_Ready_Climb;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Auto_Track_Target_XY;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Vision_Auto_SpinFromLowBarForShotReady;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Vision_Auto_Track_Target_XY;
import org.usfirst.frc3244.SirAntsABot.commands.Auto.Vision_XY;
import org.usfirst.frc3244.SirAntsABot.commands.Claw.Claw_To_Position;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Gyro_FullReset;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Reset_All_Encoders;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Field_Oriented_Mecanum;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Robot_Robot_Oriented_Mecanum;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Snail_NoTimeOut;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Spin_In_Place_PID_TrackTarget;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Turbo_NoTimeOut;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Controled_BackOFF;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Cycle;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Cycle_SOFT;
import org.usfirst.frc3244.SirAntsABot.commands.PinBall.PinBall_Reset;
import org.usfirst.frc3244.SirAntsABot.commands.Scissor.Scissor_To_Position;
import org.usfirst.frc3244.SirAntsABot.commands.Scissor.Scissor_to_Defence_Command_Group;
import org.usfirst.frc3244.SirAntsABot.commands.SeaLegs.SeaLegs_Left_Reference_Cycle;
import org.usfirst.frc3244.SirAntsABot.commands.SeaLegs.SeaLegs_Return_To_TDC;
import org.usfirst.frc3244.SirAntsABot.commands.SeaLegs.SeaLegs_Right_Reference_Cycle;
import org.usfirst.frc3244.SirAntsABot.commands.Vision.Vision_Light_Switch;
import org.usfirst.frc3244.SirAntsABot.commands.Vision.Vision_Light_Toggle;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_Jog_Down;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_Jog_Up;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_To_Setpoint;
import org.usfirst.frc3244.SirAntsABot.commands.Wrist.Wrist_To_Stow;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc3244.SirAntsABot.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    public Joystick xbox_1;
    public JoystickButton a_1;
    public JoystickButton b_1;
    public JoystickButton x_1;
    public JoystickButton y_1;
    public JoystickButton lB_1;
    public JoystickButton rB_1;
    public JoystickButton reset_1;
    public JoystickButton turboStick_1;
    public JoystickButton snailStick_1;
    
    public Joystick xbox_2;
    public JoystickButton a_2;
    public JoystickButton b_2;
    public JoystickButton x_2;
    public JoystickButton y_2;
    public JoystickButton lB_2;
    public JoystickButton rB_2;
    public JoystickButton start_2;
    public JoystickButton reset_2;
    
    
    public Joystick launchPad;
    public JoystickButton toggleSwitch;
    public InternalButton ResetAllDriveEncoders;
    public JoystickButton referenceLeftSeaLeg;
    public JoystickButton referenceRightSeaLeg;
    public JoystickButton returnSeaLeg_to_TDC;
    public JoystickButton returnSeaLeg_to_Deploy;
    public JoystickButton Secure_Ball;
    public JoystickButton Soft_Release;
    public JoystickButton ScissorToClimb;
    public JoystickButton ScissorStartClimb;
    public JoystickButton ScissorToDefence;
    public JoystickButton ScissorToStow;
    public JoystickButton DemoToggle;
 
    
    	
    public OI() {
    	setUp_Controler_Xbox_1();
    	setUp_Controler_Xbox_2();
    	setUp_Controler_LaunchPad();
    	setUp_SmartDashboard_Buttons();
    }
    
    
    
    private void setUp_Controler_Xbox_1(){
    	xbox_1 = new Joystick(0);
        
    	a_1 = new JoystickButton(xbox_1, 1);
        //a_1.whenPressed(new Drive_Spin_In_Place_PID_TrackTarget());
        //a_1.whenPressed(new Drive_Spin_In_Place_PID_TrackTarget());
        //a_1.whenReleased(new Drive_Robot_Robot_Oriented_Mecanum());
        //a_1.whileHeld(new Drive_Spin_In_Place_PID_TrackTarget());
        //a_1.whenPressed(new  Vision_Auto_Track_Target_XY());
        //a_1.whenPressed(new  Vision_Auto_SpinFromLowBarForShotReady() );
        //a_1.whenPressed(new  Vision_XY());
    	a_1.whenPressed(new Vision_Light_Toggle());
        b_1 = new JoystickButton(xbox_1, 2);
        b_1.whileHeld(new Claw_To_Position(13.5));
        
        x_1 = new JoystickButton(xbox_1, 3);
        x_1.whileHeld(new Claw_To_Position(20));
        
        y_1 = new JoystickButton(xbox_1, 4);
        //y_1.whileHeld(new Auto_Track_Target_Center());
        //y_1.whileHeld(new Auto_Track_Target_X_Center_PID());
        //y_1.whileHeld(new Auto_Track_Target_Y_Center_PID());
        y_1.whileHeld(new  Auto_Track_Target_XY());
        
        rB_1 = new JoystickButton(xbox_1, 8);
        rB_1.whenPressed(new Auto_Ready_Climb());
        
        lB_1 = new JoystickButton(xbox_1, 5);
        lB_1.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_STOW_POS));
        
        turboStick_1 = new JoystickButton(xbox_1, 9);
        turboStick_1.whileHeld(new Drive_Turbo_NoTimeOut());
        snailStick_1 = new JoystickButton(xbox_1, 10);
        snailStick_1.whileHeld(new Drive_Snail_NoTimeOut());
        
        reset_1 = new JoystickButton(xbox_1, 7);
        reset_1.whileHeld(new SeaLegs_Return_To_TDC());
    }
    
    private void setUp_Controler_Xbox_2(){
    	xbox_2 = new Joystick(1);
        
    	a_2 = new JoystickButton(xbox_2, 1);
        a_2.whileHeld(new Wrist_To_Setpoint(RobotMap.WRIST_ANGLE_UNDER_LOWBAR+3));//Wrist_Jog_Down());
        
        b_2 = new JoystickButton(xbox_2, 2);
        b_2.whileHeld(new Claw_To_Position(13.5));
        
    	x_2 = new JoystickButton(xbox_2, 3);
        x_2.whileHeld(new Claw_To_Position(20));
       
        y_2 = new JoystickButton(xbox_2, 4);
        y_2.whileHeld(new Wrist_To_Stow(RobotMap.WRIST_STOW_ANGLE));//Wrist_Jog_Up());
        
        rB_2 = new JoystickButton(xbox_2, 6);
        rB_2.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_DEFENCE_POS+2));
        
        lB_2 = new JoystickButton(xbox_2, 5);
        lB_2.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_STOW_POS));
        
        reset_2 = new JoystickButton(xbox_2, 7);
        reset_2.whenPressed(new PinBall_Reset());
        
        start_2 = new JoystickButton(xbox_2, 8);
        start_2.whileHeld(new PinBall_Cycle());
        start_2.whenReleased(new PinBall_Reset());
        
        
        
      
    }
    private void setUp_Controler_LaunchPad(){
    	launchPad = new Joystick(2);
        
    	Secure_Ball = new JoystickButton(launchPad, 1);
    	Secure_Ball.whenPressed(new Auto_Cycle_Secure_Ball());
    	
    	Soft_Release = new JoystickButton(launchPad, 2);
    	Soft_Release.whenPressed(new PinBall_Cycle_SOFT());
    	
    	ScissorToClimb = new JoystickButton(launchPad,3);
    	//ScissorToClimb.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_CLIMB_REACH_BAR));
    	ScissorToClimb.whenPressed(new Auto_Ready_Climb());
    	
    	ScissorStartClimb = new JoystickButton(launchPad,4);
    	/*
    	 * whats best when Pressed or while held
    	 */
    	//ScissorStartClimb.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_CLIMB_LIFT_BOT));
    	ScissorStartClimb.whenPressed(new Scissor_To_Position(RobotMap.SCISSOR_CLIMB_LIFT_BOT));
    	
    	ScissorToDefence = new JoystickButton(launchPad,5);
    	ScissorToDefence.whenPressed(new Scissor_to_Defence_Command_Group());
    	//ScissorToDefence.whenPressed(new Scissor_To_Position(RobotMap.SCISSOR_DEFENCE_POS));
    	
    	ScissorToStow = new JoystickButton(launchPad,6);
    	ScissorToStow.whileHeld(new Scissor_To_Position(RobotMap.SCISSOR_STOW_POS));
    	
    	toggleSwitch = new JoystickButton(launchPad, 7);
        toggleSwitch.whileHeld(new Drive_Robot_Field_Oriented_Mecanum());
        
        referenceLeftSeaLeg = new JoystickButton(launchPad, 8);
     	referenceLeftSeaLeg.whenReleased(new SeaLegs_Left_Reference_Cycle());
     	
     	
     	referenceRightSeaLeg = new JoystickButton(launchPad, 9);
     	referenceRightSeaLeg.whenReleased(new SeaLegs_Right_Reference_Cycle());
     	
     	returnSeaLeg_to_TDC = new JoystickButton(launchPad, 10);
     	returnSeaLeg_to_TDC.whileHeld(new SeaLegs_Return_To_TDC());
     	
     	//returnSeaLeg_to_Deploy = new JoystickButton(launchPad, 11);
     	//returnSeaLeg_to_Deploy.whileHeld(new SeaLegs_Deploy());
     	
    	//DemoToggle = new JoystickButton(launchPad,11);
    	//DemoToggle.whileHeld(new DemoToggle_Command());
    	
        
    }
    private void setUp_SmartDashboard_Buttons(){
    	SmartDashboard.putNumber("Claw Shot Setpoint",15);
    	ResetAllDriveEncoders = new InternalButton();
    	ResetAllDriveEncoders.whileHeld(new Drive_Reset_All_Encoders());
    	//SmartDashboard.putData("Reset All Drive Encoders", ResetAllDriveEncoders);
    	//SmartDashboard.putData("Claw_To_Position: Open", new Claw_To_Position(20));
        //SmartDashboard.putData("Claw_To_Position: Closed", new Claw_To_Position(13.5));
        //SmartDashboard.putData("Claw_To_Position: ShootBall", new Claw_To_Position(15.5));
        //SmartDashboard.putData("Scissor_To_HMI", new Scissor_To_HMI());
        //SmartDashboard.putData("Auto_Ready_Ball", new Auto_Ready_Ball());
        //SmartDashboard.putData("Auto_Cycle_Secure_Ball", new Auto_Cycle_Secure_Ball());
        //SmartDashboard.putData("Auto_00_ReachEdgeOfObstical", new Auto_00_ReachEdgeOfObstical());
        //SmartDashboard.putData("Drive_Robot_Field_Oriented_Mecanum", new Drive_Robot_Field_Oriented_Mecanum());
        //SmartDashboard.putData("Drive_Robot_Robot_Oriented_Mecanum", new Drive_Robot_Robot_Oriented_Mecanum());
        //SmartDashboard.putData("PinBall_Reset", new PinBall_Reset());
        //SmartDashboard.putData("PinBall_Shoot", new PinBall_Shoot());
        //SmartDashboard.putData("PinBall_Cycle", new PinBall_Cycle());
     //SmartDashboard.putData("PinBall_Controled_BackOFF", new PinBall_Controled_BackOFF());
        //SmartDashboard.putData("PinBall_Jog_Reverse", new PinBall_Jog_Reverse());
        
        //SmartDashboard.putData("SeaLegs_Reference_Left", new SeaLegs_Reference_Left());
       // SmartDashboard.putData("SeaLegs_Reference_Right", new SeaLegs_Reference_Right());
        //SmartDashboard.putData("Wrist_To_Lower: Lower", new Wrist_To_Lower(1));
        //SmartDashboard.putData("Wrist_To_Setpoint: Fouty_Five", new Wrist_To_Setpoint(45));
        //SmartDashboard.putData("Wrist_To_Stow: Stow", new Wrist_To_Stow(RobotMap.WRIST_STOW_ANGLE));
        SmartDashboard.putData("Reset Gyro Heading To Current", new Drive_Gyro_FullReset());
        
        //SmartDashboard.putNumber("Claw Shot Setpoint",15.1); //Tested Jun 6 2016 (15.1)

        //SmartDashboard.putData("Shoot", new Wrist_To_Setpoint(Robot.prefs.getDouble("prefs_Shoot_Angle", RobotMap.WRIST_STOW_ANGLE)));//Auto_Drive_UnderLowBar_test());
        //Claw_To_Position c = new Claw_To_Position(20);
        //Drive_Spin_In_Place_PID_TrackTarget c = new Drive_Spin_In_Place_PID_TrackTarget();
        //SmartDashboard.putString("CommandTest_Name", c.getName());
        //SmartDashboard.putData("CommandTest ", c);        
        //SmartDashboard.putData("Camera Light Toggle", new Vision_Light_Toggle());

    }
    
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getXbox_1() {
        return xbox_1;
    }

    public Joystick getXbox_2() {
        return xbox_2;
    }

    public Joystick getLaunchPad() {
        return launchPad;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
