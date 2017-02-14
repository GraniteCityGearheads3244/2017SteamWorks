
package org.usfirst.frc.team3244.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot implements PIDOutput {
    AHRS ahrs;
    RobotDrive myRobot;
    Joystick stick;
    PIDController turnController;
    double rotateToAngleRate;
    double start_time;
    double currentRotationRate;
    
    /* The following PID Controller coefficients will need to be tuned */
    /* to match the dynamics of your drive system.  Note that the      */
    /* SmartDashboard in Test mode has support for helping you tune    */
    /* controllers by displaying a form where you can enter new P, I,  */
    /* and D constants and test the mechanism.                         */
    
    static final double kP = 0.01;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    
    static final double kToleranceDegrees = 2.0f;

    public static CANTalon driveTrainMotorRight;
    public static CANTalon driveTrainMotorLeft;
    public static Servo sonicScanServo1;
    public static AnalogInput sonicScanUltrasonic1;
    
    public Robot() {
    	sonicScanServo1 = new Servo(0);
        LiveWindow.addActuator("sonicScan", "Servo1", sonicScanServo1);
        
        sonicScanUltrasonic1 = new AnalogInput(0);
        LiveWindow.addSensor("sonicScan", "Ultrasonic1", sonicScanUltrasonic1);
        

    	driveTrainMotorRight = new CANTalon(1);
        LiveWindow.addActuator("Drive Train", "Motor Right", driveTrainMotorRight);
        
        driveTrainMotorLeft = new CANTalon(2);
        LiveWindow.addActuator("Drive Train", "Motor Left", driveTrainMotorLeft);
        myRobot = new RobotDrive(driveTrainMotorLeft,driveTrainMotorRight);
        
        myRobot.setExpiration(0.1);
        stick = new Joystick(0);
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
        
            //ahrs = new AHRS(SPI.Port.kMXP); 
            ahrs = new AHRS(I2C.Port.kMXP); 
            //ahrs = new AHRS(I2C.Port.kOnboard,(byte)200);
            
        } catch (RuntimeException ex ) {
        	System.out.println("Hello from the runtimeExcept");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        LiveWindow.addActuator("DriveSystem", "RotateController", turnController);
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
        myRobot.setSafetyEnabled(false);
        myRobot.drive(0.0, 0.0);    // stop robot
        Timer.delay(2.0);		    //    for 2 seconds
        myRobot.drive(0.0, 0.0);	// stop robot
    }

    /**
     * Runs the motors with onnidirectional drive steering.
     * 
     * Implements Field-centric drive control.
     * 
     * Also implements "rotate to angle", where the angle
     * being rotated to is defined by one of four buttons.
     * 
     * Note that this "rotate to angle" approach can also 
     * be used while driving to implement "straight-line
     * driving".
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            boolean rotateToAngle = false;
            SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
            SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
            SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
            if ( stick.getRawButton(6)) {
                ahrs.reset();
            }
            if ( stick.getRawButton(1)) {
                turnController.setSetpoint(0.0f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(2)) {
                turnController.setSetpoint(-90.0f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(4)) {
                turnController.setSetpoint(179.9f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(3)) {
                turnController.setSetpoint(90.0f);
                rotateToAngle = true;
            }
            
            if ( rotateToAngle ) {
                turnController.enable();
                currentRotationRate = rotateToAngleRate;
            } else {
                turnController.disable();
                currentRotationRate = stick.getRawAxis(0);
            }
            try {
                /* Use the joystick X axis for lateral movement,          */
                /* Y axis for forward movement, and the current           */
                /* calculated rotation rate (or joystick Z axis),         */
                /* depending upon whether "rotate to angle" is active.    */
                //myRobot.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 
                //                               currentRotationRate, ahrs.getAngle());
            	myRobot.arcadeDrive(-stick.getRawAxis(1), -currentRotationRate);
            } catch( RuntimeException ex ) {
                DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
            }
            double r = rescale(stick.getRawAxis(4),-1,1,0,1);
            sonicScanServo1.set(r);
            SmartDashboard.putNumber("eye angle", r);
            Timer.delay(0.005);		// wait for a motor update time
        }
       
    }
    public double rescale(double input, double inputLow, double inputHight, double outputLow, double outputHigh){
    	return ((input-inputLow)-(inputHight-inputLow))*(outputHigh-outputLow)+outputLow+1.5;
    }
    /**
     * Runs during test mode
     */
    public void test() {
    	
    	turnController.enable();
            currentRotationRate = rotateToAngleRate;
    	try {
            /* Use the joystick X axis for lateral movement,          */
            /* Y axis for forward movement, and the current           */
            /* calculated rotation rate (or joystick Z axis),         */
            /* depending upon whether "rotate to angle" is active.    */
            //myRobot.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 
            //                               currentRotationRate, ahrs.getAngle());
        	myRobot.arcadeDrive(0, -currentRotationRate);
        } catch( RuntimeException ex ) {
            DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
        }
        
        Timer.delay(0.005);		// wait for a motor update time
    }

    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
