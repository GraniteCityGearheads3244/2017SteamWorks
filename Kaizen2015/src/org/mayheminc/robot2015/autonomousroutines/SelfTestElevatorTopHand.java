package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//import org.mayheminc.robot2015.subsystems.Elevator;
import org.mayheminc.robot2015.Robot;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class SelfTestElevatorTopHand extends Command {
	
	public static final int NUMBER_OF_LOOPS = 50;
	public static final int NUMBER_OF_LOOPS_BEFORE_CHECKS = 15;
	
	public static final int VELOCITY_MIN = 210;
	public static final int VELOCITY_MAX = 280;
	
	public static final double CURRENT_MIN  = 6.25;
	public static final double CURRENT_MAX = 9.25;
	
	public static final int ENCODER_MIN = 2150;
	public static final int ENCODER_MAX = 2850;
	
	public static final double TIME_MIN = 0.97 / NUMBER_OF_LOOPS;
	public static final double TIME_MAX = 1.15 / NUMBER_OF_LOOPS;
	
	boolean m_pass = true;

	int m_count = 0;
	double m_timeRequired = 0;
	Timer m_timer = new Timer();

	int m_encoderCount = 0;
	double m_velocity = 0;
	double m_current = 0;

    public SelfTestElevatorTopHand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// set display of SelfTest status to false
    	SmartDashboard.putBoolean("SelfTest Elevator Top Hand Pass", false);
    	
    	// initialize needed settings
    	m_count = 0;
    	m_pass = true;
    	m_timer.reset();
    	m_timer.start();
    	
    	// start the test by moving the elevator up
    	Robot.pole.raiseElevatorTopHand();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_count++;
    	// wait for elevator to get moving before performing velocity and current checks
    	if (m_count > NUMBER_OF_LOOPS_BEFORE_CHECKS) {
    		m_velocity = Robot.pole.getElevatorTopHandVelocity();
    		m_current = Robot.pole.getElevatorTopHandCurrent();
	    	
    		if (m_velocity < VELOCITY_MIN) {
	    		m_pass = false;
	    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Velocity of " + m_velocity +
	    				                  " below threshold of " + VELOCITY_MIN + "\n", false);
	    	}
    		
	        if (m_velocity > VELOCITY_MAX) {
	        	m_pass = false;
	        	DriverStation.reportError("SelfTest Elevator Top Hand Error: Velocity of " + m_velocity + 
	        								" above threshold of " + VELOCITY_MAX + "\n", false);
	        }
	        
	    	if (m_current < CURRENT_MIN) {
	    		m_pass = false;
	    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Current of " + m_current + 
	    									" below threshold of " + CURRENT_MIN + "\n", false);
	    	}
	    	
	    	if (m_current > CURRENT_MAX) {
	    		m_pass = false;
	    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Current of " + m_current + 
	    									" above threshold of " + CURRENT_MAX + "\n", false);
	    	}
	    	SmartDashboard.putNumber("ElevatorTopHand SelfTest Velocity", m_velocity);
			SmartDashboard.putNumber("ElevatorTopHand SelfTest Current", m_current);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_count > NUMBER_OF_LOOPS);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pole.stopElevator();
    	m_timer.stop();
    	m_timeRequired = m_timer.get();
    	
    	m_encoderCount = Robot.pole.getPositionTopHand();
    	
    	if (m_encoderCount < ENCODER_MIN) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Distance traveled by the Elevator top hand: "
    									+ m_encoderCount + " is beneath the threshold of " 
    									+ ENCODER_MIN + "\n", false);    		
    	}
    	
    	if (m_encoderCount > ENCODER_MAX) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Distance traveled by the Elevator top hand: "
    									+ m_encoderCount + " is above the threshold of " 
    									+ ENCODER_MAX + "\n", false);
    	}
    	
    	if (m_timeRequired < (TIME_MIN * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS + " loops: " 
    									+ m_timeRequired + " is below threshold " 
    									+ (TIME_MIN * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
    	if (m_timeRequired > (TIME_MAX * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Top Hand Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: "
    									+ m_timeRequired + " is above threshold " 
    									+ (TIME_MAX * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	SmartDashboard.putNumber("ElevatorTopHand SelfTest Encoder", m_encoderCount);
		SmartDashboard.putNumber("ElevatorTopHand SelfTest Time Required", m_timeRequired);
    	SmartDashboard.putBoolean("SelfTest Elevator Top Hand Pass", m_pass);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	m_pass = false;
    	SmartDashboard.putBoolean("SelfTest Elevator Top Hand Pass", m_pass);
    	DriverStation.reportError("SelfTest Elevator Top Hand Error: Self Test Interrupted", false);
    	Robot.pole.stopElevator();
    }
}
