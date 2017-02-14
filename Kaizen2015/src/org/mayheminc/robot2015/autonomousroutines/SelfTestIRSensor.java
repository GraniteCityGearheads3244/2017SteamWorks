package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.Robot;
//import org.mayheminc.util.Utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SelfTestIRSensor extends Command {
	
	public static final double VOLT_MIN = 0.01;
	public static final double VOLT_MAX = 0.55;
	
	public static final double TIME_MIN = 0.00;
	public static final double TIME_MAX = 2.00;
	
	public static final int NUMBER_OF_LOOPS = 50;
	public static final int NUMBER_OF_LOOPS_BEFORE_CHECKS = 0;
	
	public static final String errorReportStrings[] = { "Outer Left", "Inner Left", "Inner Right", "Outer Right"};

	public static final int k_outerLeft = 0;
	public static final int k_innerLeft = 1;
	public static final int k_innerRight = 2;
	public static final int k_outerRight = 3;
	
	private int m_sensorToTest = 0;
	private double m_voltage = 0.00;
	private int m_count = 0;
	private boolean m_pass = true;
	private double m_timeRequired = 0;
	private Timer m_timer = new Timer();
	
    public SelfTestIRSensor(int argument) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_sensorToTest = argument;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("SelfTest IR Sensor " +  errorReportStrings[m_sensorToTest] + " Pass", false);
    	
    	
    	// initialize needed settings
		m_count = 0;
		m_pass = true;
		m_timer.reset();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	m_count++;
    	if (m_count > NUMBER_OF_LOOPS_BEFORE_CHECKS) {
			m_voltage = Robot.drive.getIRVoltage(m_sensorToTest);
			
			if (m_voltage < VOLT_MIN) {
				m_pass = false;
				DriverStation.reportError("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] +  
						" Error: Voltage of " + m_voltage +
						" below threshold of " + VOLT_MIN + "\n", false);
			}
			
			if (m_voltage > VOLT_MAX) {
				m_pass = false;
				DriverStation.reportError("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] +
						" Error: Voltage of " + m_voltage + 
						" above threshold of " + VOLT_MAX + "\n", false);
			}
			SmartDashboard.putNumber("IR Senosr " + errorReportStrings[m_sensorToTest] +
					" SelfTest Voltage", m_voltage);
			
			
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_count >= NUMBER_OF_LOOPS);
    }

    // Called once after isFinished returns true
    protected void end() {
		m_timer.stop();
    	m_timeRequired = m_timer.get();   	
    	m_voltage = Robot.drive.getIRVoltage(m_sensorToTest);
		
		if (m_timeRequired < (TIME_MIN * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest IR Sensor "+ errorReportStrings[m_sensorToTest]
    									+ "Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
    									+ m_timeRequired + " is below threshold " 
    									+ (TIME_MIN * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		if (m_timeRequired > (TIME_MAX * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] 
    									+ " Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
										+ m_timeRequired + " is above threshold " 
    									+ (TIME_MAX * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		
		SmartDashboard.putNumber("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] +
				" Time Required", m_timeRequired);
		SmartDashboard.putBoolean("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] + " Pass", m_pass);
		
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	m_pass = false;
		SmartDashboard.putBoolean("SelfTest IR Sensor " +  errorReportStrings[m_sensorToTest] + " Pass", m_pass);
		DriverStation.reportError("SelfTest IR Sensor " + errorReportStrings[m_sensorToTest] 
				+ " Error: Self Test Interrupted", false);
    }
}
