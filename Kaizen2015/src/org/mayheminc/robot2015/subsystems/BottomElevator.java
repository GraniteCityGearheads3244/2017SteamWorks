package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class BottomElevator extends ElevatorBase {
	public static final double ELEVATOR_BOTTOM_HAND_CURRENT_MAX = 60.00;


	public static final int ELEVATOR_BOTTOM_HAND_SPACING = 2300;
	public static final int CARRY_OFFSET = 400;  // had initially been 500 during practice before NEU
	public static final int DROP_OFFSET = 1300;

	public static final int ELEVATOR_BOTTOM_HAND_HEIGHT_MIN = 0;
	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 = 180;
	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + DROP_OFFSET;
	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2 = 2550;   // 2900 as of end of UMASS
	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2 + DROP_OFFSET;
	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 = 4850;   // 5585 as of end of UMASS
	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_3 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 + DROP_OFFSET;
	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4 = 7200;   // 8325 as of end of UMASS
	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_4 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4 + DROP_OFFSET;
	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_5 = 9530;   // was 9450 at end of NEU; 8325 as of end of UMASS
	public static final int ELEVATOR_BOTTOM_HAND_HEIGHT_MAX = 10140;	    // 9250 as of end of UMASS

	public static final int m_handHeightMin = ELEVATOR_BOTTOM_HAND_HEIGHT_MIN;
	public static final int m_handHeightMax = ELEVATOR_BOTTOM_HAND_HEIGHT_MAX;

	public static final int ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_ONE_TOTE = 6350;
	public static final int ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_TWO_TOTES = 8570;

	public static final int ELEVATOR_BOTTOM_HAND_MANUAL_ZERO_POSITION = 0;  // the manual zeroing point
	public static final int ELEVATOR_BOTTOM_HAND_ZERO_POSITION = 450;  // just below the Tote lip
	public static final int ELEVATOR_BOTTOM_HAND_RELEASE_5_HEIGHT = 8150;

	public static final int ElevatorPositionsConst[] = new int[]{
		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1,
		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2,
		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3,
		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4,
		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_5,
		ELEVATOR_BOTTOM_HAND_HEIGHT_MAX - CARRY_OFFSET - 101,  // need to do at least a "minus one"
	};

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}


	public void init(){
//		super.init();		now taken care of by Robot.pole
		if (m_handManualMode) {
			changeTalonControlMode(TalonControlMode.PercentVbus);
		} else {
			changeTalonControlMode(TalonControlMode.Position);
		}


		m_handDesiredPosition = (int) getPositionHand();

		enableTalonControl();
	}
	public BottomElevator(){

		super(RobotMap.ELEVATOR_BOTTOM_HAND_TALON, true);   	
		
		this.ElevatorPositions = BottomElevator.ElevatorPositionsConst.clone();
	}

	public double calculateSpeedHand(){
//		if(Robot.oi.superSlowBottomHand()){
//			return m_handSuperSlow;
//		}else{
//			return m_handSpeed;
//		}
		
		return Robot.oi.getBottomHandManualControl();
	}
	
	public boolean isThisPositionGoingToMakeTheHandsCrash(int position, int clearance){
		return position >= Robot.pole.topElevator.getPositionHand() - clearance;
	}

	protected  double GetMaxTalonCurrent()
	{
		return ELEVATOR_BOTTOM_HAND_CURRENT_MAX;
	}

	protected  int GetPdpIndex()
	{
		return RobotMap.ELEVATOR_BOTTOM_HAND_PDP;
	}

	public void updateSmartDashboard(){
//		super.updateSmartDashboard();		now taken care of by Robot.pole

		// display information about bottom hand
		SmartDashboard.putNumber("Elevator Actual Bottom Hand", getPositionHand());
		if (m_handManualMode) {
			// indicate manual mode by a value of -99999
			SmartDashboard.putNumber("Elevator Desired Bottom Hand", -99999);         		
		} else {
			SmartDashboard.putNumber("Elevator Desired Bottom Hand", m_handDesiredPosition);         		
		}
		SmartDashboard.putNumber("Elevator Current Bottom Hand", Robot.pdp.getCurrent(RobotMap.ELEVATOR_BOTTOM_HAND_PDP));
		SmartDashboard.putBoolean("Bottom Hand in Manual Mode" , m_handManualMode);


	}

	public boolean elevatorHandCurrentOverDraw(){
		double currentDrawn = getElevatorHandCurrent();
		if(currentDrawn >= ELEVATOR_BOTTOM_HAND_CURRENT_MAX){
			stopElevatorHand();
			DriverStation.reportError("*ATTENTION:* ELEVATOR BOTTOM HAND DRAWING " + currentDrawn +
					" IS ABOVE THRESHOLD " + ELEVATOR_BOTTOM_HAND_CURRENT_MAX //+ 
					//   					" n/ **TEMPORARILY DISABLING OPERATOR CONTROL OVER BOTTOM HAND ELEVATOR**"
					, false);
			return true;
		}
		return false;
	}
	public void holdElevatorHand(){
		moveHandToPosition(getPositionHand());    	
	}
	public void fixSlippingHand(int theoreticalEncoder){
		resetPositionHand(theoreticalEncoder);
		moveHandToPosition(theoreticalEncoder, true);
		DriverStation.reportError("Belt Slippage!  Resetting Elevator Encoder Count (Bottom Hand)", false);
	}

	public void nudgeHandPosition(int encoderAdjustment){
		// a positive adjustment moves the position down which will cause the talon to move up.
		// If the talon is at 1000 and we want to go up by 100, pass in 100.  The talon will set the 
		// current position to 900, which will cause the talon to move up by 100 encoder counts to
		// rest at 1000.
		int newPosition = getPositionHand() - encoderAdjustment;
		resetPositionHand(newPosition);
	}
	
	

}

