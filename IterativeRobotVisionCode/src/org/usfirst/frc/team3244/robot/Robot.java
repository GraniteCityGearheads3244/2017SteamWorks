package org.usfirst.frc.team3244.robot;


import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3244.util.JoystickAxisButton;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	Relay light;// = new Relay(0);
	Joystick xbox;
	Joystick launchPad;
	
	Talon front_Left;
	Talon front_Right;
	Talon back_Left;
	Talon back_Right;
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	 public static AnalogGyro driveTrainRobot_Gyro;
	 
	private VisionThread visionThread;
	private RobotDrive drive;
	private double centerX = 0.0;
	private int rectCount;
	private int widthRect1;
	private Rect[] rectAll;
	double g = 0;
	JoystickAxisButton l;
	
	private final Object imgLock = new Object();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		driveTrainRobot_Gyro = new AnalogGyro(0);
		
		launchPad = new Joystick(2);
		xbox=new Joystick(0);
		l = new JoystickAxisButton(xbox, 3);
		
		//Do you have a grean Ring light?
		light = new Relay(1);
		
		front_Left = new Talon(0);
		front_Right = new Talon(2);
		back_Left = new Talon(1);
		back_Right = new Talon(3);
		
		drive = new RobotDrive(front_Left, back_Left, front_Right, back_Right);
		LiveWindow.addActuator("Light Subsystem", "Light Relay", light);
		
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            int r_Count = pipeline.filterContoursOutput().size();
	            Rect[] rAll = new Rect[r_Count];
	            
	            //Get all the Rectangles
	            for(int i=0;i<r_Count;i++){
	            	rAll[i]=Imgproc.boundingRect(pipeline.filterContoursOutput().get(i));
	            }
	            	
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	                widthRect1 = r.width;
	                rectCount = r_Count;
	                rectAll = rAll;
	            }
	        }
	    });
	    visionThread.start();
	       
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		light.set(Value.kForward);
		double centerX;
		double widthRect;
		int rectCount;
		Rect[] allRects;
		
		
		synchronized (imgLock) {
			centerX = this.centerX;
			widthRect = this.widthRect1;
			rectCount = this.rectCount;
			
			allRects = this.rectAll;
		}
		//double turn = centerX - (IMG_WIDTH / 2);
		//SmartDashboard.putNumber("Center X = ", centerX);
		SmartDashboard.putNumber("Rect Array Size = ", rectCount);
		if(rectCount == 2 ){
			SmartDashboard.putBoolean("Array Out Of Bounds", false);
			SmartDashboard.putNumber("Rect 0 x = ", allRects[0].x);
			SmartDashboard.putNumber("Rect 1 x = ", allRects[1].x);
		}else{
			SmartDashboard.putBoolean("Array Out Of Bounds", true);
		}
		
		//drive.arcadeDrive(-0.6, turn * 0.005);
	}

	public void teleopInit() {
		System.out.println("Entering TeleopINT");
		driveTrainRobot_Gyro.reset();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double x = deadband(-xbox.getRawAxis(0));
		double y = deadband(-xbox.getRawAxis(1));
		double r = deadband(-xbox.getRawAxis(4));
		if(l.get()){
			light.set(Value.kForward);
		}else{
			light.set(Value.kOff);
		}
		if(launchPad.getRawButton(7)){
			 g = driveTrainRobot_Gyro.getAngle();
		}
		else{
			g = 0.0;//driveTrainRobot_Gyro.getAngle();
		}
		
		
		if (xbox.getRawButton(4)) {
			x = 0.0;
			y = .20;
			r = 0.0;
			g = 0.0;
		}

		if (xbox.getRawButton(1)) {
			x = 0.0;
			y = -0.2;
			r = 0.0;
			g = 0.0;
		}

		if (xbox.getRawButton(3)) {
			x = 0.30;
			y = 0.0;
			r = 0.0;
			g = 0.0;
		}

		if (xbox.getRawButton(2)) {
			x = -0.30;
			y = 0.0;
			r = 0.0;
			g = 0.0;
		}

		drive.mecanumDrive_Cartesian(x, y, r, g);
		//drive.arcadeDrive(xbox.getRawAxis(1), xbox.getRawAxis(0));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	private double deadband(double stick){
		if(Math.abs(stick)>.2){
			return stick;
		}else{
			return 0;
		}
	}
	private double clampValue(double value, double max, double min){
		if(value>max){
			return max;
		}else if(value<min){
			return min;
		}else{
			return value;
		}
	}
}
