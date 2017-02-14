package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 */
public class TopElevator extends ElevatorBase {
	public static final double ELEVATOR_TOP_HAND_CURRENT_MAX = 40.00;

	public static final int ELEVATOR_TOP_HAND_HEIGHT_MIN = 400;
	public static final int ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE = 1786;			// also does "on the step"
	public static final int ELEVATOR_TOP_HAND_UPRIGHT_RC_HEIGHT = 4200;
	public static final int ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4 = 7440;	
	public static final int ELEVATOR_TOP_HAND_HEIGHT_MAX = 10175;	// was 10150 at UMASS
	public static final int ELEVATOR_TOP_HAND_RC_ON_STEP = ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE; 
	// NOTE:  Absolute max giving metal-to-metal contact is 10250
	
	public static final int m_handHeightMin = ELEVATOR_TOP_HAND_HEIGHT_MIN;
	public static final int m_handHeightMax = ELEVATOR_TOP_HAND_HEIGHT_MAX;

	public static final int ELEVATOR_TOP_HAND_ZERO_POSITION = 1067;         // just below the RC handle
	public static final int ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION = 10180;   // smashed up against the hard stop at the top

	public static final int ElevatorPositionsConst[] = new int[]{
		ELEVATOR_TOP_HAND_HEIGHT_MIN,
		ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE,
		ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4,
		ELEVATOR_TOP_HAND_HEIGHT_MAX
	};

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void init(){
//		super.init();		this method has gone away since Robot.pole now takes care of this
		if (m_handManualMode) {
			changeTalonControlMode(TalonControlMode.PercentVbus);
		} else {
			changeTalonControlMode(TalonControlMode.Position);
		}
		m_handDesiredPosition =  (int) getPositionHand();
		enableTalonControl();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public TopElevator(){
		super(RobotMap.ELEVATOR_TOP_HAND_TALON, false);  
		
		this.ElevatorPositions = TopElevator.ElevatorPositionsConst.clone();
	}


	public double calculateSpeedHand(){
//		if(Robot.oi.superSlowTopHand()){
//			return m_handSuperSlow;
//		}else{
//			return m_handSpeed;
//		}
		return Robot.oi.getTopHandManualControl();
	}
	public boolean isThisPositionGoingToMakeTheHandsCrash(int position, int clearance){
		return position <= Robot.pole.bottomElevator.getPositionHand() + clearance;
	}
	


	protected  double GetMaxTalonCurrent(){
		return ELEVATOR_TOP_HAND_CURRENT_MAX;
	}
	
	protected  int GetPdpIndex(){
		return RobotMap.ELEVATOR_BOTTOM_HAND_PDP;
	}




	public boolean moveHandToPosition(int position, boolean override) {
		// check to make sure the elevator has previously been zeroed

		if (!Robot.pole.getElevatorZeroed()) {
			DriverStation.reportError("Elevator not yet zeroed; can't moveToPosition.\n", false);
			return true;
		}

		// if trying to move the top hand all the way to the bottom, just do it
		if (position == ELEVATOR_TOP_HAND_HEIGHT_MIN) {
			// move the bottom hand all the way to the bottom
			Robot.pole.bottomElevator.moveHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);

			// tell the top Talon to go to the position
			super.moveHandToPosition(position, true);  // KBS thinks override should be true here
			return false;
		}

		int bottomHandPosition = Robot.pole.bottomElevator.getPositionHand();

		//  if the current bottom hand position is above the height of the first tote, use the
		//  "HANDS_CLEARANCE_WITH_TOTE" to determine if the hands could crash
		if (!override && (bottomHandPosition > Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + Robot.pole.HANDS_CLEARANCE_WITHOUT_TOTE) &&
				(position <= bottomHandPosition + Robot.pole.HANDS_CLEARANCE_WITH_TOTE )) {
			DriverStation.reportError("Could crash into bottom hand (high case)", false);
			return true;

		} 
		//  if the current bottom hand position is below the height of the first tote, 
		//  there is no way that there could be a tote on the first hand, so allow the top
		//  hand to go within "HANDS_CLEARANCE_WITHOUT_TOTE" of the bottom hand
		else if (!override && (bottomHandPosition <= Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + Robot.pole.HANDS_CLEARANCE_WITHOUT_TOTE) &&
				(position <= bottomHandPosition + Robot.pole.HANDS_CLEARANCE_WITHOUT_TOTE ))
		{ 
			DriverStation.reportError("Could crash into bottom hand (low case)", false);
			return true;
		} else {    		
			if (m_handManualMode) {
				// if still in manual (PercentVbus) mode, change the TalonSRX to Position Mode

				setTalonControl(TalonControlMode.Position);

				m_handManualMode = false;
			}

			// tell the Talon to go to the position
			super.moveHandToPosition(position, override);
			return false;
		}
	}
	
	
	public void updateSmartDashboard(){
//		super.updateSmartDashboard();		Now taken care of by Robot.pole

		// display information about top hand
		SmartDashboard.putNumber("Elevator Actual Top Hand", getPositionHand());
		if (m_handManualMode) {
			// indicate manual mode by a value of -99999
			SmartDashboard.putNumber("Elevator Desired Top Hand", -99999);         		
		} else {
			SmartDashboard.putNumber("Elevator Desired Top Hand", m_handDesiredPosition);         		
		}
		SmartDashboard.putNumber("Elevator Current Top Hand", Robot.pdp.getCurrent(RobotMap.ELEVATOR_TOP_HAND_PDP));
		SmartDashboard.putBoolean("Top Hand in Manual Mode" , m_handManualMode);
	}

	public boolean elevatorHandCurrentOverDraw(){
		double currentDrawn = getElevatorHandCurrent();
		if(currentDrawn >= ELEVATOR_TOP_HAND_CURRENT_MAX){
			//    		m_elevatorTopHandTalon.disableControl();
			stopElevatorHand();
			DriverStation.reportError("*ATTENTION:* ELEVATOR TOP HAND DRAWING " + currentDrawn +
					" IS ABOVE THRESHOLD " + ELEVATOR_TOP_HAND_CURRENT_MAX 
					//					+ " n/ **TEMPORARILY DISABLING OPERATOR CONTROL OVER TOP HAND ELEVATOR**"
					, false);
			return true;

		}
		return false;
	}
	
	public int getNextHigherIndexPositionHand() {
		int currentPosition = getPositionHand() + CLICK_TO_NEXT_POSITION_TOLERANCE;
		for(int i = 0; i < ElevatorPositions.length; i++){
			if(currentPosition <= ElevatorPositions[i]){
				return i;
			}     		
		}
		return ElevatorPositions.length-1;
	}
	
	public int getNextLowerIndexPositionHand(){
		int currentPosition = getPositionHand() - CLICK_TO_NEXT_POSITION_TOLERANCE;
		for(int i = ElevatorPositions.length-1; i >= 0; i--){
			if(currentPosition  >= ElevatorPositions[i]){
				return i;
			}     		
		}
		return 0;
	}
}