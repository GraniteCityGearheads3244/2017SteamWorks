// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.Watson;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon driveTrainMotor_Left_Front;
    public static CANTalon driveTrainMotor_Left_Rear;
    public static CANTalon driveTrainMotor_Right_Front;
    public static CANTalon driveTrainMotor_Right_Rear;
    public static RobotDrive driveTrainRobotDrive41;
    public static SpeedController fuel_DeliveryMotor_Fuel_Indexer;
    public static CANTalon fuel_DeliveryMotor_Fuel_Flywheel;
    public static Servo fuel_DeliveryElevator;
    public static SpeedController winchMotor_Winch;
    public static SpeedController fuel_IntakeMotor_Intake_Roler;
    public static AnalogPotentiometer gear_Lift1Pot_Gear_Lift;
    public static SpeedController gear_Lift1Motor_Gear_Lift;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static AHRS ahrs;
    

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainMotor_Left_Front = new CANTalon(1);
        LiveWindow.addActuator("DriveTrain", "Motor_Left_Front", driveTrainMotor_Left_Front);
        
        driveTrainMotor_Left_Rear = new CANTalon(2);
        LiveWindow.addActuator("DriveTrain", "Motor_Left_Rear", driveTrainMotor_Left_Rear);
        
        driveTrainMotor_Right_Front = new CANTalon(3);
        LiveWindow.addActuator("DriveTrain", "Motor_Right_Front", driveTrainMotor_Right_Front);
        
        driveTrainMotor_Right_Rear = new CANTalon(4);
        LiveWindow.addActuator("DriveTrain", "Motor_Right_Rear", driveTrainMotor_Right_Rear);
        
        driveTrainRobotDrive41 = new RobotDrive(driveTrainMotor_Left_Front, driveTrainMotor_Left_Rear,
              driveTrainMotor_Right_Front, driveTrainMotor_Right_Rear);
        
        driveTrainRobotDrive41.setSafetyEnabled(true);
        driveTrainRobotDrive41.setExpiration(0.1);
        driveTrainRobotDrive41.setSensitivity(0.5);
        driveTrainRobotDrive41.setMaxOutput(1.0);
        driveTrainRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrainRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        fuel_DeliveryMotor_Fuel_Indexer = new VictorSP(5);
        LiveWindow.addActuator("Fuel_Delivery", "Motor_Fuel_Indexer", (VictorSP) fuel_DeliveryMotor_Fuel_Indexer);
        
        fuel_DeliveryMotor_Fuel_Flywheel = new CANTalon(5);
        LiveWindow.addActuator("Fuel_Delivery", "Motor_Fuel_Flywheel", fuel_DeliveryMotor_Fuel_Flywheel);
        
        fuel_DeliveryElevator = new Servo(4);
        LiveWindow.addActuator("Fuel_Delivery", "Elevator", fuel_DeliveryElevator);
        
        winchMotor_Winch = new VictorSP(1);
        LiveWindow.addActuator("Winch", "Motor_Winch", (VictorSP) winchMotor_Winch);
        
        fuel_IntakeMotor_Intake_Roler = new Spark(2);
        LiveWindow.addActuator("Fuel_Intake", "Motor_Intake_Roler", (Spark) fuel_IntakeMotor_Intake_Roler);
        
        gear_Lift1Pot_Gear_Lift = new AnalogPotentiometer(2, 270.0, -93.0); //Larger Moves up
        LiveWindow.addSensor("Gear_Lift1", "Pot_Gear_Lift", gear_Lift1Pot_Gear_Lift);
        
        gear_Lift1Motor_Gear_Lift = new Spark(3);
        LiveWindow.addActuator("Gear_Lift1", "Motor_Gear_Lift", (Spark) gear_Lift1Motor_Gear_Lift);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        // Invert Motors
        fuel_IntakeMotor_Intake_Roler.setInverted(true);
        winchMotor_Winch.setInverted(true);
        fuel_DeliveryMotor_Fuel_Indexer.setInverted(true);
        
        //Create Gyro
        try {
        	System.out.println("Hello Tying to INIT Navx");
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
        
            //ahrs = new AHRS(SPI.Port.kMXP); 
        	ahrs = new AHRS(I2C.Port.kMXP);
            //ahrs = new AHRS(I2C.Port.kOnboard); 
        	//ahrs = new AHRS(I2C.Port.kOnboard,(byte)200);
            
        } catch (RuntimeException ex ) {
        	System.out.println("Hello from the runtimeExcept");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }
}