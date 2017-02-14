package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotMap;
import org.mayheminc.robot2015.RobotPreferences;
import org.mayheminc.util.RangeFinder_GP2D120;
import org.mayheminc.util.Utils;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.AnalogGyro;
//import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 */
public class Drive extends Subsystem {

	private AnalogGyro headingGyro = new AnalogGyro(RobotMap.HEADING_GYRO);

	//IR Sensors
	private static final int kLeftOutbound = 0;
	private static final int kLeftInbound = 1;
	private static final int kRightInbound = 2;
	private static final int kRightOutbound = 3;

	// member variables related to tote pickup
	private int m_loopsAligned = 0;
	private static int LOOPS_REQUIRED_FOR_ALIGNMENT = 3;


	private RangeFinder_GP2D120 rangeFinderLeftOutbound = new RangeFinder_GP2D120(
			RobotMap.RANGE_FINDER_LEFT_OUTBOUND, kLeftOutbound);
	private RangeFinder_GP2D120 rangeFinderLeftInbound = new RangeFinder_GP2D120(
			RobotMap.RANGE_FINDER_LEFT_INBOUND, kLeftInbound);
	private RangeFinder_GP2D120 rangeFinderRightInbound = new RangeFinder_GP2D120(
			RobotMap.RANGE_FINDER_RIGHT_INBOUND, kRightInbound);
	private RangeFinder_GP2D120 rangeFinderRightOutbound = new RangeFinder_GP2D120(
			RobotMap.RANGE_FINDER_RIGHT_OUTBOUND, kRightOutbound);

	// member variables for Mecanum drive
	private static final int kMaxNumberOfMotors = 4;
	private static final int kMaxNumberOfRangeFinders = 4;
	private final int m_invertedMotors[] = new int[kMaxNumberOfMotors];
	private static final int kFrontLeft = 0;
	private static final int kFrontRight = 1;
	private static final int kBackLeft = 2;
	private static final int kBackRight = 3;


	// create objects needed for independent control of each wheel
	private CANTalon[] m_talons = new CANTalon[kMaxNumberOfMotors];
	private RangeFinder_GP2D120[] m_rangeFinders = new RangeFinder_GP2D120[kMaxNumberOfRangeFinders];
	private double m_wheelSpeeds[] = new double[kMaxNumberOfMotors];
	private double m_zeroPositions[] = new double[kMaxNumberOfMotors];

	private boolean m_useVoltageRamp = true;
	private double m_voltageRampRate = 48.0; // in volts/second
	private boolean m_fieldOrientedDrive = false;

	private int m_iterationsSinceRotationCommanded = 0;
	private double m_desiredHeading = 0.0;
	private boolean m_drivingAutoInTeleop = false;
	
	// driving scaling factors
	private static final double FORWARD_BACKWARD_FACTOR = 1.0;
	private static final double ROTATION_FACTOR = 1.25;
	private static final double STRAFE_FACTOR = 2.0;
	private static final double SLOW_FACTOR = 0.35; // scaling factor for (normal) "slow mode"
	private static final double CRAWL_INPUT = 0.30; // "crawl" is a gentle control input
	public static final double ALIGN_SPEED = 0.10;

	// member variables to support closed loop mode
	private boolean m_closedLoopMode = false;
	private double m_maxWheelSpeed = 550.0;     // empirically measured around 560 to 580	

	// "tote aligned" lighting control
	//	private boolean m_toteAligned = false;
	private Solenoid m_lightingSolenoid = new Solenoid(RobotMap.LIGHTING_SOLENOID_0);
	private Solenoid m_lightingSolenoid2 = new Solenoid(RobotMap.LIGHTING_SOLENOID_1);
	private boolean m_forceLightsOn = false;

	public Drive() {

		int talonIndex = 0;

		// construct the talons
		m_talons[kFrontLeft] = new CANTalon(RobotMap.FRONT_LEFT_TALON);
		m_talons[kFrontRight] = new CANTalon(RobotMap.FRONT_RIGHT_TALON);
		m_talons[kBackLeft] = new CANTalon(RobotMap.BACK_LEFT_TALON);
		m_talons[kBackRight] = new CANTalon(RobotMap.BACK_RIGHT_TALON);

		m_rangeFinders[kLeftOutbound] = rangeFinderLeftOutbound;
		m_rangeFinders[kLeftInbound] = rangeFinderLeftInbound;
		m_rangeFinders[kRightInbound] = rangeFinderRightInbound;
		m_rangeFinders[kRightOutbound] = rangeFinderRightOutbound;

		// set all Talon SRX encoder values to zero
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].setPosition(0);
		}

		// put all Talon SRX into brake mode
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].enableBrakeMode(true);
		}

		// ensure ramp rate set accordingly
		if (m_useVoltageRamp) {
			for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
				m_talons[talonIndex].setVoltageRampRate(m_voltageRampRate);
			}
		} else {
			// clear all voltage ramp rates
			for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
				m_talons[talonIndex].setVoltageRampRate(0.0);
			}
		}

		// Also need to set up the "inverted motors" array for the mecanum drive
		// code
		m_invertedMotors[kFrontLeft] = 1;
		m_invertedMotors[kFrontRight] = -1;
		m_invertedMotors[kBackLeft] = 1;
		m_invertedMotors[kBackRight] = -1;
	}

	public void init() {
		// complete initialization here that can't be performed in constructor
		// (some calls can't be made in constructor because other objects don't
		// yet exist)

		// Set up the TalonSRX closed loop / open loop mode for each wheel
		if (m_closedLoopMode) {
			setClosedLoopMode();
		} else {
			setOpenLoopMode();
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new Command());
	}

	public boolean getToteCoarseAligned(){
		return (rangeFinderLeftOutbound.isObjectSeen() && rangeFinderRightOutbound.isObjectSeen());
	}

	public boolean getToteAligned(){
		return (rangeFinderLeftOutbound.isObjectSeen() && rangeFinderRightOutbound.isObjectSeen()
				&& rangeFinderLeftInbound.isObjectFlush() && rangeFinderRightInbound.isObjectFlush());
	}

	public void forceLightsOn(boolean forceOn) {
		m_forceLightsOn = forceOn;
		if (forceOn) {
			m_lightingSolenoid.set(forceOn);
			m_lightingSolenoid2.set(forceOn);
		}
	}
	
	public void setLightingOutput() {
		// this method sets the lighting outputs appropriately.

		boolean ToteAlignedLightsOn = getToteAligned() || m_forceLightsOn;

		m_lightingSolenoid.set(ToteAlignedLightsOn);
		m_lightingSolenoid2.set(ToteAlignedLightsOn);

		SmartDashboard.putBoolean("Red Lights On", ToteAlignedLightsOn);
	}

	public double getHeading() {
		return headingGyro.getAngle();
	}

	public void resetHeadingGyro() {
		headingGyro.reset();
		m_desiredHeading = 0.0;
	}

	public void clearDesiredHeading() {
		m_desiredHeading = getHeading();
	}

	public void recalibrateHeadingGyro() {
		headingGyro.free();
		headingGyro = new AnalogGyro(RobotMap.HEADING_GYRO);
		m_desiredHeading = 0.0;
	}

	public void toggleFieldOrientedDrive() {
		m_fieldOrientedDrive = !m_fieldOrientedDrive;
	}

	public void setClosedLoopMode() {
		int talonIndex = 0;
		m_closedLoopMode = true;
		setWheelPIDF();
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].changeControlMode(TalonControlMode.Speed);
			m_talons[talonIndex].enableControl();
		}
	}

	public void setOpenLoopMode() {
		int talonIndex = 0;
		m_closedLoopMode = false;
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].changeControlMode(TalonControlMode.PercentVbus);
			m_talons[talonIndex].enableControl();
		}
	}

	public void toggleClosedLoopMode() {
		if (!m_closedLoopMode) {
			setClosedLoopMode();
		} else {
			setOpenLoopMode();
		}
	}

	public double getMaxWheelSpeed() {
		return (m_maxWheelSpeed);
	}

	public void zeroDistanceTraveled() {
		int talonIndex = 0;
		// record current positions as "zero" for all of the Talon SRX encoders
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_zeroPositions[talonIndex] = m_talons[talonIndex].getPosition();
		}
	}

	public double getDistanceTraveled() {
		int talonIndex = 0;
		double tempDistance = 0;
		// add up the absolute value of the distances from each individual wheel
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			tempDistance += Math.abs(m_talons[talonIndex].getPosition()
					- m_zeroPositions[talonIndex]);
		}
		return (tempDistance);
	}

	public void setWheelPIDF() {
		int talonIndex = 0;
		double wheelP = RobotPreferences.getWheelP();
		double wheelI = RobotPreferences.getWheelI();
		double wheelD = RobotPreferences.getWheelD();
		double wheelF = RobotPreferences.getWheelF();

		// set the PID values for each individual wheel
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].setPID(wheelP, wheelI, wheelD, wheelF, 0,
					m_voltageRampRate, 0);
		}
		DriverStation.reportError("setWheelPIDF: " + wheelP + " " + wheelI
				+ " " + wheelD + " " + wheelF + "\n", false);
	}

	public void periodic() {
		rangeFinderLeftOutbound.periodic();
		rangeFinderLeftInbound.periodic();
		rangeFinderRightInbound.periodic();
		rangeFinderRightOutbound.periodic();
	}

	public void updateSmartDashboard() {
		setLightingOutput();

		if (Robot.DEBUG) {			
			SmartDashboard.putNumber("Front Left SRX Position",
					m_talons[kFrontLeft].getPosition() - m_zeroPositions[kFrontLeft]);
			SmartDashboard.putNumber("Front Right SRX Position",
					-(m_talons[kFrontRight].getPosition() - m_zeroPositions[kFrontRight]));
			SmartDashboard.putNumber("Back Left SRX Position",
					m_talons[kBackLeft].getPosition() - m_zeroPositions[kBackLeft]);
			SmartDashboard.putNumber("Back Right SRX Position",
					-(m_talons[kBackRight].getPosition() - m_zeroPositions[kBackRight]));

			SmartDashboard.putNumber("Front Left SRX Speed",
					m_talons[kFrontLeft].getSpeed());
			SmartDashboard.putNumber("Front Right SRX Speed",
					-m_talons[kFrontRight].getSpeed());
			SmartDashboard.putNumber("Back Left SRX Speed",
					m_talons[kBackLeft].getSpeed());
			SmartDashboard.putNumber("Back Right SRX Speed",
					-m_talons[kBackRight].getSpeed());

			SmartDashboard.putNumber("FL Desired Speed",
					m_wheelSpeeds[kFrontLeft]);
			SmartDashboard.putNumber("FR Desired Speed",
					-m_wheelSpeeds[kFrontRight]);
			SmartDashboard.putNumber("BL Desired Speed",
					m_wheelSpeeds[kBackLeft]);
			SmartDashboard.putNumber("BR Desired Speed",
					-m_wheelSpeeds[kBackRight]);

			SmartDashboard.putNumber("Front Left Current",
					Robot.pdp.getCurrent(RobotMap.DRIVE_FRONT_LEFT_PDP));
			SmartDashboard.putNumber("Front Right Current",
					Robot.pdp.getCurrent(RobotMap.DRIVE_FRONT_RIGHT_PDP));
			SmartDashboard.putNumber("Back Left Current",
					Robot.pdp.getCurrent(RobotMap.DRIVE_BACK_LEFT_PDP));
			SmartDashboard.putNumber("Back Right Current",
					Robot.pdp.getCurrent(RobotMap.DRIVE_BACK_RIGHT_PDP));

			SmartDashboard.putNumber("Front Left Output Voltage",
					m_talons[kFrontLeft].getOutputVoltage());
			SmartDashboard.putNumber("Front Right Output Voltage",
					-m_talons[kFrontRight].getOutputVoltage());
			SmartDashboard.putNumber("Back Left Output Voltage",
					m_talons[kBackLeft].getOutputVoltage());
			SmartDashboard.putNumber("Back Right Output Voltage",
					-m_talons[kBackRight].getOutputVoltage());

			SmartDashboard.putNumber("Gyro",
					Utils.twoDecimalPlaces(headingGyro.getAngle()));
			SmartDashboard.putNumber("Desired Heading", m_desiredHeading);

			SmartDashboard.putBoolean("Turbo Mode", Robot.oi.driveTurboMode());
			SmartDashboard.putBoolean("Closed Loop Mode", m_closedLoopMode);
			SmartDashboard.putBoolean("Field Oriented Drive",
					m_fieldOrientedDrive);

			SmartDashboard.putNumber("rangeFinder Left Outer",
					rangeFinderLeftOutbound.getFilteredVoltage());
			SmartDashboard.putNumber("rangeFinder Left Inner",
					rangeFinderLeftInbound.getFilteredVoltage());
			SmartDashboard.putNumber("rangeFinder Right Inner",
					rangeFinderRightInbound.getFilteredVoltage());
			SmartDashboard.putNumber("rangeFinder Right Outer",
					rangeFinderRightOutbound.getFilteredVoltage());

			SmartDashboard.putBoolean("LeftHasObject",
					rangeFinderLeftOutbound.isObjectSeen());
			SmartDashboard.putBoolean("RightHasObject",
					rangeFinderRightOutbound.isObjectSeen());

			SmartDashboard.putBoolean("LeftIsFlush",
					rangeFinderLeftInbound.isObjectFlush());
			SmartDashboard.putBoolean("RightIsFlush",
					rangeFinderRightInbound.isObjectFlush());

			SmartDashboard.putBoolean("Tote Is Aligned",
					rangeFinderLeftOutbound.isObjectSeen()
					&& rangeFinderRightOutbound.isObjectSeen());
			SmartDashboard.putBoolean("Tote Is Flush",
					rangeFinderLeftInbound.isObjectFlush()
					&& rangeFinderRightInbound.isObjectFlush());
		}
	}

	/**
	 * Normalize all wheel speeds if the magnitude of any wheel is greater than
	 * 1.0.
	 */
	private void normalizeAndScaleWheelSpeeds() {
		int i;
		double tempMagnitude;
		double maxMagnitude;

		// find maxMagnitude
		maxMagnitude = Math.abs(m_wheelSpeeds[0]);
		for (i = 1; i < kMaxNumberOfMotors; i++) {
			tempMagnitude = Math.abs(m_wheelSpeeds[i]);
			if (tempMagnitude > maxMagnitude) {
				maxMagnitude = tempMagnitude;
			}
		}

		// if any wheel has a magnitude greater than 1.0, reduce all to fit in
		// range
		if (maxMagnitude > 1.0) {
			for (i = 0; i < kMaxNumberOfMotors; i++) {
				m_wheelSpeeds[i] = m_wheelSpeeds[i] / maxMagnitude;
			}
		}

		// if in closedLoopMode, scale wheels to be speeds, rather than power
		// percentage
		if (m_closedLoopMode) {
			for (i = 0; i < kMaxNumberOfMotors; i++) {
				m_wheelSpeeds[i] = m_wheelSpeeds[i] * m_maxWheelSpeed;
			}
		}
	}


	/**
	 * Correct any inverted motors
	 */
	private void correctInvertedMotors() {
		int i;

		for (i = 0; i < kMaxNumberOfMotors; i++) {
			m_wheelSpeeds[i] = m_wheelSpeeds[i] * m_invertedMotors[i];
		}
	}

	/**
	 * Rotate a vector in Cartesian space.
	 */
	protected static double[] rotateVector(double x, double y, double angle) {
		double cosA = Math.cos(angle * (3.14159 / 180.0));
		double sinA = Math.sin(angle * (3.14159 / 180.0));
		double out[] = new double[2];
		out[0] = x * cosA - y * sinA;
		out[1] = x * sinA + y * cosA;
		return out;
	}

	/**
	 * Read the Range Finders. If they are both say there is an object close,
	 * then do don't move. If the left has an object, but the right does not,
	 * strafe left. If the right has an object, but the left does not, strafe
	 * right. If neither has an object, then return the x,y,rotation values.
	 * 
	 * @param vector
	 */
	public boolean determineMovementForToteAlignment(double[] vector) {
		boolean leftHasObject = rangeFinderLeftOutbound.isObjectSeen();
		boolean rightHasObject = rangeFinderRightOutbound.isObjectSeen();

		boolean leftCloseToObject = rangeFinderLeftInbound.isObjectClose();
		boolean rightCloseToObject = rangeFinderRightInbound.isObjectClose();

		boolean leftVeryCloseToObject = rangeFinderLeftInbound.isObjectVeryClose();
		boolean rightVeryCloseToObject = rangeFinderRightInbound.isObjectVeryClose();

		boolean leftIsFlush = rangeFinderLeftInbound.isObjectFlush();
		boolean rightIsFlush = rangeFinderRightInbound.isObjectFlush();

		// both outer sensors see the object, do further checks to get flush and
		// square
		if (leftHasObject && rightHasObject) {
			// check for flush -- if so, command very slow drive forward
			if (leftIsFlush && rightIsFlush) {
				vector[0] = 0.0;
				vector[1] = ALIGN_SPEED * 0.5;
				vector[2] = 0.0;

				return true;

			} else {
				// both sensors see tote, but at least one isn't flush
				vector[0] = 0.0;

				// if neither is flush, drive straight
				if (!leftIsFlush && !rightIsFlush) {
					vector[1] = ALIGN_SPEED * 1.0;    // was 0.6 on 23 Feb 2015
				} else {
					vector[1] = 0.0;
				}

				vector[2] = 0.0;

				// if left is not flush, but right is, rotate clockwise
				if (!leftIsFlush && rightIsFlush) {
					if (Robot.PRACTICE_BOT) {
						vector[1] = -ALIGN_SPEED * 0.40;  // helps do more of a pivot than a rotate
						vector[2] = ALIGN_SPEED * 0.90;
					} else {
						//						vector[2] = ALIGN_SPEED * 0.65;    // this line was 0.65 @ start of NEU; changed to 0.80 below
						vector[2] = ALIGN_SPEED * 0.80;
						//						vector[1] = -ALIGN_SPEED * 0.40;  // helps do more of a pivot than a rotate
						//						vector[2] = ALIGN_SPEED * 0.90;
					}
				}

				// if right is not flush, but left is rotate counterclockwise
				if (!rightIsFlush && leftIsFlush) {
					if (Robot.PRACTICE_BOT) {
						vector[1] = -ALIGN_SPEED * 0.40;  // helps do more of a pivot than a rotate
						vector[2] = -ALIGN_SPEED * 0.90;
					} else {
						//						vector[2] = -ALIGN_SPEED * 0.65;    // this line was 0.65 @ start of NEU; changed to 0.80 below
						vector[2] = -ALIGN_SPEED * 0.80;
						//						vector[1] = -ALIGN_SPEED * 0.40;  // helps do more of a pivot than a rotate
						//						vector[2] = -ALIGN_SPEED * 0.90;
					}
				}
			}
		}

		// left sensor doesn't see the tote, strafe right
		if (!leftHasObject && rightHasObject) {
			if (Robot.PRACTICE_BOT) {
				vector[0] = ALIGN_SPEED * 2.0;
			} else {
				vector[0] = ALIGN_SPEED * 2.5;     // was 1.5 prior to NEU
			}

			// while strafing right, if very close to the object, back away a tiny bit
			if (rightVeryCloseToObject) {
				vector[1] = -ALIGN_SPEED * 1.0;
			}
			// while strafing right, also crawl towards tote if not close to it
			if (!rightCloseToObject) {
				vector[1] = ALIGN_SPEED * 0.2;
			}
			// if strafing right, but right side is already flush, rotate a tiny
			// bit
			if (rightIsFlush) {
				vector[2] = ALIGN_SPEED * 0.3;
			} else {
				vector[2] = 0.0;
			}
		}

		// right sensor doesn't see tote, strafe left
		if (leftHasObject && !rightHasObject) {
			if (Robot.PRACTICE_BOT) {
				vector[0] = -ALIGN_SPEED * 2.0;
			} else {
				vector[0] = -ALIGN_SPEED * 2.5;     // was 1.5 prior to NEU
			}
			// while strafing left, if very close to the object, back away a tiny bit
			if (leftVeryCloseToObject) {
				vector[1] = -ALIGN_SPEED * 1.0;
			}
			// while strafing left, also crawl towards tote if not close to it
			if (!leftCloseToObject) {
				vector[1] = ALIGN_SPEED * 0.2;
			}
			// if strafing left, but left side is already flush, rotate a tiny
			// bit
			if (leftIsFlush) {
				vector[2] = -ALIGN_SPEED * 0.3;
			} else {
				vector[2] = 0.0;
			}
		}

		// in autonomous, drive forward if neither outer sensor sees the tote
		if (DriverStation.getInstance().isAutonomous() && !leftHasObject
				&& !rightHasObject) {
			vector[0] = 0.0;
			vector[1] = ALIGN_SPEED * 1.5;
			vector[2] = 0.0;
		}

		return false;
	}


	/**
	 * Read the Range Finders. If they are both say there is an object close,
	 * then do don't move. If the left has an object, but the right does not,
	 * strafe left. If the right has an object, but the left does not, strafe
	 * right. If neither has an object, then return the x,y,rotation values.
	 * 
	 * @param vector
	 */
	public boolean determineMovementForToteCoarseAlignment(double[] vector) {
		boolean leftHasObject = rangeFinderLeftOutbound.isObjectSeen();
		boolean rightHasObject = rangeFinderRightOutbound.isObjectSeen();

		boolean leftCloseToObject = rangeFinderLeftInbound.isObjectClose();
		boolean rightCloseToObject = rangeFinderRightInbound.isObjectClose();

		boolean leftVeryCloseToObject = rangeFinderLeftInbound.isObjectVeryClose();
		boolean rightVeryCloseToObject = rangeFinderRightInbound.isObjectVeryClose();

		boolean leftIsFlush = rangeFinderLeftInbound.isObjectFlush();
		boolean rightIsFlush = rangeFinderRightInbound.isObjectFlush();

		// both outer sensors see the object, we're done!
		if (leftHasObject && rightHasObject) {
			vector[0] = 0.0;
			vector[1] = 0.0;
			vector[2] = 0.0;
			return true;
		}

		// left sensor doesn't see the tote, strafe right
		if (!leftHasObject && rightHasObject) {
			if (Robot.PRACTICE_BOT) {
				vector[0] = ALIGN_SPEED * 2.5;
			} else {
				vector[0] = ALIGN_SPEED * 2.5;
			}

			// while strafing right, if very close to the object, back away a tiny bit
			if (rightVeryCloseToObject) {
				vector[1] = -ALIGN_SPEED * 1.0;
			}
			// while strafing right, also crawl towards tote if not close to it
			if (!rightCloseToObject) {
				vector[1] = ALIGN_SPEED * 0.2;
			}
			// if strafing right, but right side is already flush, rotate a tiny
			// bit
			if (rightIsFlush) {
				vector[2] = ALIGN_SPEED * 0.3;
			} else {
				vector[2] = 0.0;
			}
		}

		// right sensor doesn't see tote, strafe left
		if (leftHasObject && !rightHasObject) {
			if (Robot.PRACTICE_BOT) {
				vector[0] = -ALIGN_SPEED * 2.5;
			} else {
				vector[0] = -ALIGN_SPEED * 2.5;
			}
			// while strafing left, if very close to the object, back away a tiny bit
			if (leftVeryCloseToObject) {
				vector[1] = -ALIGN_SPEED * 1.0;
			}
			// while strafing left, also crawl towards tote if not close to it
			if (!leftCloseToObject) {
				vector[1] = ALIGN_SPEED * 0.2;
			}
			// if strafing left, but left side is already flush, rotate a tiny
			// bit
			if (leftIsFlush) {
				vector[2] = -ALIGN_SPEED * 0.3;
			} else {
				vector[2] = 0.0;
			}
		}

		// in autonomous, drive forward if neither outer sensor sees the tote
		if (DriverStation.getInstance().isAutonomous() && !leftHasObject
				&& !rightHasObject) {
			vector[0] = 0.0;
			vector[1] = ALIGN_SPEED * 1.5;
			vector[2] = 0.0;
		}

		return false;
	}

	/**
	 * Drive method for Mecanum wheeled robots.
	 *
	 * A method for driving with Mecanum wheeled robots. There are 4 wheels on
	 * the robot, arranged so that the front and back wheels are toed in 45
	 * degrees. When looking at the wheels from the top, the roller axles should
	 * form an X across the robot.
	 *
	 * This is designed to be directly driven by joystick axes.
	 *
	 * @param x
	 *            The speed that the robot should drive in the X direction.
	 *            [-1.0..1.0]
	 * @param y
	 *            The speed that the robot should drive in the Y direction.
	 *            [-1.0..1.0]
	 * @param rotation
	 *            The rate of rotation for the robot that is completely
	 *            independent of the translation. [-1.0..1.0]
	 */
	public void mecanumDriveTeleop(double xIn, double yIn, double rotation) {

		// check for the presence of the special "crawl" commands and do those
		// if commanded
		if (Robot.oi.crawlBackward()) {
			xIn = 0.0;
			yIn = -CRAWL_INPUT;
			rotation = 0.0;
		}

		if (Robot.oi.crawlForward()) {
			xIn = 0.0;
			yIn = CRAWL_INPUT;
			rotation = 0.0;
		}

		if (Robot.oi.crawlRight()) {
			xIn = CRAWL_INPUT;
			yIn = 0.0;
			rotation = 0.0;
		}

		if (Robot.oi.crawlLeft()) {
			xIn = -CRAWL_INPUT;
			yIn = 0.0;
			rotation = 0.0;
		}

		// Compensate for AnalogGyro angle if field-oriented drive is desired
		if (m_fieldOrientedDrive) {
			// rotate the vector to be "robot-centric"
			double rotated[] = rotateVector(xIn, yIn, getHeading());
			xIn = rotated[0];
			yIn = rotated[1];
		}

		// check to see if forward/back, strafe, and rotation are being
		// commanded.
		// values with magnitude < 0.07 are just "centering noise" and set to
		// 0.0
		if ((-0.07 < xIn) && (xIn < 0.07)) {
			xIn = 0.0;
		}
		if ((-0.07 < yIn) && (yIn < 0.07)) {
			yIn = 0.0;
		}
		if ((-0.07 < rotation) && (rotation < 0.07)) {
			rotation = 0.0;
		}

		// scale inputs to compensate for misbalance of speeds in different
		// directions
		xIn = xIn * STRAFE_FACTOR;
		yIn = yIn * FORWARD_BACKWARD_FACTOR;
		rotation = rotation * ROTATION_FACTOR;

		// apply "slowFactor" if not in "Turbo Mode"
		if (!Robot.oi.driveTurboMode()) {
			xIn = xIn * SLOW_FACTOR;
			yIn = yIn * SLOW_FACTOR;
			rotation = rotation * SLOW_FACTOR;
		}

		// check for presence of special "tote alignment" command
		if (Robot.oi.toteAlign()) {
			// do some stuff to figure out how to move
			// end result is that xIn, yIn, and rotation need to be set
			// appropriately
			double vector[] = new double[3];

			vector[0] = xIn;
			vector[1] = yIn;
			vector[2] = rotation;

			determineMovementForToteAlignment(vector);

			xIn = vector[0];
			yIn = vector[1];
			rotation = vector[2];
		}

		// update count of iterations since rotation last commanded
		if ((-0.01 < rotation) && (rotation < 0.01)) {
			// rotation is practically zero, so just set it to zero and
			// increment iterations
			rotation = 0.0;
			m_iterationsSinceRotationCommanded++;
		} else {
			// rotation is being commanded, so clear iteration counter
			m_iterationsSinceRotationCommanded = 0;
		}

		// preserve heading when recently stopped commanding rotations
		if (m_iterationsSinceRotationCommanded == 5) {
			m_desiredHeading = getHeading();
		} else if (m_iterationsSinceRotationCommanded > 5) {
			rotation = (m_desiredHeading - getHeading()) / 40.0;
		}

		// if no directions being commanded and driving in teleop, then let the "AutoInTeleop" take place
		if (m_drivingAutoInTeleop) {
			return;
		}
		
		mecanumDriveCartesian(xIn, yIn, rotation);
	}

	public void mecanumDriveAutonomous(double xIn, double yIn, double rotation,
			double heading) {
		m_desiredHeading = heading;

		// preserve heading if no rotation is commanded
		if ((-0.01 < rotation) && (rotation < 0.01)) {
			rotation = (m_desiredHeading - getHeading()) / 40.0;
		}
		mecanumDriveCartesian(xIn, yIn, rotation);
	}
	
	public void mecanumDriveAutoInTeleopFinished() {
		m_drivingAutoInTeleop = false;
	}
	
	public void mecanumDriveAutoInTeleop(double xIn, double yIn, double rotation) {
		m_drivingAutoInTeleop = true;
		
		// update count of iterations since rotation last commanded
		if ((-0.01 < rotation) && (rotation < 0.01)) {
			// rotation is practically zero, so just set it to zero and
			// increment iterations
			rotation = 0.0;
			m_iterationsSinceRotationCommanded++;
		} else {
			// rotation is being commanded, so clear iteration counter
			m_iterationsSinceRotationCommanded = 0;
		}

		// preserve heading when recently stopped commanding rotations
		if (m_iterationsSinceRotationCommanded == 5) {
			m_desiredHeading = getHeading();
		} else if (m_iterationsSinceRotationCommanded > 5) {
			rotation = (m_desiredHeading - getHeading()) / 40.0;
		}
		
		mecanumDriveCartesian(xIn, yIn, rotation);
	}

	public void mecanumDriveCartesian(double xIn, double yIn, double rotation) {
		int talonIndex = 0;

		m_wheelSpeeds[kFrontLeft] = xIn + yIn + rotation;
		m_wheelSpeeds[kFrontRight] = -xIn + yIn - rotation;
		m_wheelSpeeds[kBackLeft] = -xIn + yIn + rotation;
		m_wheelSpeeds[kBackRight] = xIn + yIn - rotation;

		normalizeAndScaleWheelSpeeds();
		correctInvertedMotors();

		// want to do all the sets immediately after one another to minimize
		// delay between commands
		// set all Talon SRX encoder values to zero

		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].set(m_wheelSpeeds[talonIndex]);
		}
	}

	public int getWheelPosition(int wheelId) {
		return (int) m_talons[wheelId].getPosition();
	}

	public int getWheelSpeed(int wheelId) {
		return (int) m_talons[wheelId].getPosition();
	}

	public double getWheelCurrent(int wheelId) {
		return Robot.pdp.getCurrent(RobotMap.talonPDP[wheelId]);
	}

	public void stopWheel(int wheelId) {
		m_talons[wheelId].set(0);
	}

	public void setSelfTestWheelSpeed(int wheelId, double speed) {
		m_talons[wheelId].set(speed);
	}

	public double getIRVoltage(int sensorId){
		return m_rangeFinders[sensorId].getFilteredVoltage();
	}

	//SHARED FROM ELEVATOR CLASS (OLD)
	public boolean permissionToLiftCheckToteCoarseAligned(){				//SHARED	
		return Robot.drive.getToteCoarseAligned();
	}    

	public void clearPriorToteAlignment() {									//SHARED
		m_loopsAligned = 0;
	}

	public boolean permissionToLiftCheckToteAligned() {						//SHARED
		if (Robot.drive.getToteAligned()) {
			m_loopsAligned++;
		} else {
			m_loopsAligned = 0;
		}
		return (m_loopsAligned >= LOOPS_REQUIRED_FOR_ALIGNMENT);
	}

}
