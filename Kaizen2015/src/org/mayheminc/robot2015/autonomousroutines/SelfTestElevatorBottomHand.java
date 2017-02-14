package org.mayheminc.robot2015.autonomousroutines;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.mayheminc.robot2015.subsystems.Elevator;
import org.mayheminc.robot2015.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class SelfTestElevatorBottomHand extends Command {

	public static final int NUMBER_OF_LOOPS = 50;
	public static final int NUMBER_OF_LOOPS_BEFORE_CHECKS = 10;
	
	public static final int ELEVATOR_BOTTOM_HAND_VELOCITY_MIN = 210;
	public static final int ELEVATOR_BOTTOM_HAND_VELOCITY_MAX = 280;

	public static final double ELEVATOR_BOTTOM_HAND_CURRENT_MIN  = 5.25;
	public static final double ELEVATOR_BOTTOM_HAND_CURRENT_MAX = 8.25;

	public static final int ELEVATOR_BOTTOM_HAND_ENCODER_MIN = 2150;
	public static final int ELEVATOR_BOTTOM_HAND_ENCODER_MAX = 2750;
	
	public static final double ELEVATOR_BOTTOM_HAND_TIME_MIN = 0.97 / NUMBER_OF_LOOPS;
	public static final double ELEVATOR_BOTTOM_HAND_TIME_MAX = 1.17 / NUMBER_OF_LOOPS;

	

	boolean m_pass = true;

	int m_count = 0;
	double m_timeRequired = 0;
	Timer m_timer = new Timer();
	
	int m_encoderCount = 0;
	double m_velocity = 0;
	double m_current = 0;

	public SelfTestElevatorBottomHand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
    	// set display of SelfTest status to false
		SmartDashboard.putBoolean("SelfTest Elevator Bottom Hand Pass", false);
    	
    	// initialize needed settings
		m_count = 0;
		m_pass = true;
		m_timer.reset();
    	m_timer.start();
    	
    	// start the test by moving the elevator up
		Robot.pole.raiseElevatorBottomHand();

		//		line beneath this one to help debug a problem where the bottom hand does not lift during the self test auto
		DriverStation.reportError("Starting to lift Elevator Bottom Hand", false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		m_count++;
		// wait for elevator to get moving before performing velocity and current checks
		if (m_count > NUMBER_OF_LOOPS_BEFORE_CHECKS) {
			m_velocity = Robot.pole.getElevatorBottomHandVelocity();
			m_current = Robot.pole.getElevatorBottomHandCurrent();

			if (m_velocity < ELEVATOR_BOTTOM_HAND_VELOCITY_MIN) {
				m_pass = false;
				DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Velocity of " + m_velocity +
						" below threshold of " + ELEVATOR_BOTTOM_HAND_VELOCITY_MIN + "\n", false);
			}
			
			if (m_velocity > ELEVATOR_BOTTOM_HAND_VELOCITY_MAX) {
				m_pass = false;
				DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Velocity of " + m_velocity + 
						" above threshold of " + ELEVATOR_BOTTOM_HAND_VELOCITY_MAX + "\n", false);
			}	
			
			if (m_current < ELEVATOR_BOTTOM_HAND_CURRENT_MIN) {
				m_pass = false;
				DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Current of " + m_current + 
						" below threshold of " + ELEVATOR_BOTTOM_HAND_CURRENT_MIN + "\n", false);
			}
			
			if (m_current > ELEVATOR_BOTTOM_HAND_CURRENT_MAX) {
				m_pass = false;
				DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Current of " + m_current + 
						" above threshold of " + ELEVATOR_BOTTOM_HAND_CURRENT_MAX + "\n", false);
			}
			SmartDashboard.putNumber("ElevatorBottomHand SelfTest Velocity", m_velocity);
			SmartDashboard.putNumber("ElevatorBottomHand SelfTest Current", m_current);
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
    	
    	m_encoderCount = Robot.pole.getPositionBottomHand();

		if (m_encoderCount < ELEVATOR_BOTTOM_HAND_ENCODER_MIN) {
			m_pass = false;
			DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Distance traveled by the Elevator bottom hand: "
										+ m_encoderCount + " is beneath the threshold of " 
										+ ELEVATOR_BOTTOM_HAND_ENCODER_MIN + "\n", false);    		
		}
		
		if (m_encoderCount > ELEVATOR_BOTTOM_HAND_ENCODER_MAX) {
			m_pass = false;
			DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Distance traveled by the Elevator bottom hand: "
										+ m_encoderCount + " is above the threshold of " 
										+ ELEVATOR_BOTTOM_HAND_ENCODER_MAX + "\n", false);
		}
		
		if (m_timeRequired < (ELEVATOR_BOTTOM_HAND_TIME_MIN * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
    									+ m_timeRequired + " is below threshold " 
    									+ (ELEVATOR_BOTTOM_HAND_TIME_MIN * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		if (m_timeRequired > (ELEVATOR_BOTTOM_HAND_TIME_MAX * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
										+ m_timeRequired + " is above threshold " 
    									+ (ELEVATOR_BOTTOM_HAND_TIME_MAX * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		SmartDashboard.putNumber("ElevatorBottomHand SelfTest Encoder", m_encoderCount);
		SmartDashboard.putNumber("ElevatorBottomHand SelfTest Time Required", m_timeRequired);
		SmartDashboard.putBoolean("SelfTest Elevator Bottom Hand Pass", m_pass);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		m_pass = false;
		SmartDashboard.putBoolean("SelfTest Elevator Bottom Hand Pass", m_pass);
		DriverStation.reportError("SelfTest Elevator Bottom Hand Error: Self Test Interrupted", false);
		Robot.pole.stopElevator();
	}
}