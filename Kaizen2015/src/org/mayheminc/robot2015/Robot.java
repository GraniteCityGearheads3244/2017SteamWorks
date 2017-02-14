
package org.mayheminc.robot2015;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2015.commands.RunAutonomous;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;
import org.mayheminc.robot2015.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory. 
 */
public class Robot extends IterativeRobot {
	public static final boolean DEBUG = true;
	public static final boolean PRACTICE_BOT = false;
	
	// create commands to be invoked for autonomous and teleop
    private Command autonomousCommand;
    //private Command teleopCommand;
    private Runtime runtime = Runtime.getRuntime();
    
    // create the robot subsystems
    public static final Compressor compressor = new Compressor();
	public static final Drive drive = new Drive();
//    public static final Elevator elevator = new Elevator();
    public static final Claw claw = new Claw();
    public static final PowerDistributionPanel pdp = new PowerDistributionPanel();
    public static final Pole pole = new Pole();

    public static final CanBurglar leftCanburglar = new CanBurglar(RobotMap.CANBURGLAR_LEFT_SOLENOID);
    public static final CanBurglar rightCanburglar = new CanBurglar(RobotMap.CANBURGLAR_RIGHT_SOLENOID);
    
    // allocate the "virtual" subsystems; wait to construct these until robotInit()
    public static Autonomous autonomous;
    public static OI oi;

    // autonomous start time monitoring
    private static long autonomousStartTime;
    private static boolean printAutoElapsedTime = false;
    
    // timer for the match, will rumble when twenty seconds are left
    public static final Timer rumbleTimer = new Timer();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
      System.out.println("robotInit");
      SmartDashboard.putString("Auto Prog",  "Initializing...");
      
      System.out.println("About to DriverStation.reportError for 'Initializing...'");
      DriverStation.reportError("Initializing... ", false);
      System.out.println("Done with DriverStation.reportError for 'Initializing...'");

      DriverStation.reportError("About to construct Autonomous Subsystem... \n", false);      
	  autonomous = new Autonomous();
      DriverStation.reportError("Done constructing Autonomous Subsystem.\n", false);
      
      DriverStation.reportError("About to construct OI... \n", false);      
	  oi = new OI();
      DriverStation.reportError("Done constructing OI.\n", false);
      
      // initialize the subsystems that need it
      drive.init();
      pole.init();
      
      // instantiate the command used for the autonomous period
      autonomousCommand = new RunAutonomous();
      DriverStation.reportError("Constructed auto command.\n", false);
      SmartDashboard.putString("Auto Prog",  "Done.");
      Autonomous.updateDashboard();
    }
	
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
        if (printAutoElapsedTime) {
        	double autonomousTimeElapsed = (double) (System.currentTimeMillis() - autonomousStartTime) / 1000.0;
        	DriverStation.reportError ("Autonomous Time Elapsed: " + autonomousTimeElapsed + "\n", false);
            printAutoElapsedTime = false;
        }
    	Robot.leftCanburglar.raiseCanBurglar();
    	Robot.leftCanburglar.raiseCanBurglar();
    }
    
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
        // update sensors in drive that need periodic update
        drive.periodic();
        updateSmartDashboard();
	}

    public void autonomousInit() {
    	// turn off the compressor
    	compressor.stop();
    	
    	// for CanBurgling, we want to start burgling in autonomousInit
    	// check to see if we are running the CanTheftAuto program
    	if (autonomous.getAutonomousCommand().getName().equals("CanTheftAuto") ) {
        	// force the solenoid lights to be on
        	drive.forceLightsOn(true);
    	} else {
    		drive.forceLightsOn(false);
    	}
    	
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();        
        autonomousStartTime = System.currentTimeMillis();
        printAutoElapsedTime = true;
        
		DriverStation.reportError("AutonomousTimeRemaining from autonomousInit = " + Robot.autonomousTimeRemaining() + "\n", false);
    }
    
    public static double autonomousTimeRemaining() {
    	double autonomousTimeElapsed = (double) (System.currentTimeMillis() - autonomousStartTime) / 1000.0;
    	return ( 15.0 - autonomousTimeElapsed );
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        // update sensors in drive that need periodic update
        drive.periodic();
//        elevator.currentMonitoring();
        
        
        updateSmartDashboard();
    }

    public void teleopInit() {
    	// turn off the compressor
    	compressor.start();
    	
    	// remove the "force" for solenoid lights to be on
    	drive.forceLightsOn(false);
    	
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        DriverStation.reportError("Entering Teleop.", false);
        Robot.drive.clearDesiredHeading();
        rumbleTimer.reset();
        rumbleTimer.start();
        
    }

    /**
     * This function is called periodically during operator control
     */
    boolean teleopOnce = false;
    public void teleopPeriodic() {
    	if (!teleopOnce)
    	{
    	  DriverStation.reportError("Teleop Periodic is running!", false);
    	}
    	teleopOnce = true;
        Scheduler.getInstance().run();
            
        // update sensors in drive that need periodic update
        drive.periodic();
//        elevator.periodic();
        
        drive.mecanumDriveTeleop(oi.driveX(), oi.driveY(), oi.driveRotation()); 
        
        updateSmartDashboard();
        
//        if (rumbleTimer.getMatchTime() == 130) {
//        	oi.rumbleOperatorGamePad();
//        	DriverStation.reportError("RUMBLE!!!!!!!!!!!!", false);
//        }
        
        if ((rumbleTimer.get() > 3) && (rumbleTimer.get() < 10)) {
//        	oi.rumbleOperatorGamePad();
//        	DriverStation.reportError("Rumble", false);
        }
        	
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
  
    private long SMART_DASHBOARD_UPDATE_INTERVAL = 250;
    private long nextSmartDashboardUpdate = System.currentTimeMillis();
    
    public void updateSmartDashboard() {
        try {
            if (System.currentTimeMillis() > nextSmartDashboardUpdate) {
                // display free memory for the JVM
            	double freeMemoryInKB = runtime.freeMemory() / 1024;
                SmartDashboard.putNumber("Free Memory", freeMemoryInKB); 
                
                SmartDashboard.putNumber("Battery Voltage", pdp.getVoltage());
                SmartDashboard.putNumber("proportional contorl", oi.getBottomHandManualControl());
                  
            	// display mode information
//                SmartDashboard.putBoolean("Is Teleop", DriverStation.getInstance().isOperatorControl());
//                SmartDashboard.putBoolean("Is Autonomous", DriverStation.getInstance().isAutonomous());
//                SmartDashboard.putBoolean("Is Enabled", DriverStation.getInstance().isEnabled());

            	// display interesting OI information
//                SmartDashboard.putNumber("DriveX", oi.driveX());  
//                SmartDashboard.putNumber("DriveY", oi.driveY());  
//                SmartDashboard.putNumber("DriveRotation", oi.driveRotation());  
                
            	drive.updateSmartDashboard();
//            	elevator.updateSmartDashboard();
            	pole.updateSmartDashboard();
            	
 
                nextSmartDashboardUpdate += SMART_DASHBOARD_UPDATE_INTERVAL;
            }
        } catch (Exception e) {
           return;
        }
    }
    
}
