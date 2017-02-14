//package org.mayheminc.robot2015.subsystems;
//import org.mayheminc.robot2015.Robot;
//import org.mayheminc.robot2015.RobotMap;
//import org.mayheminc.robot2015.RobotPreferences;
//
//import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.CANTalon.ControlMode;
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//
//public class Elevator extends Subsystem {
//
//	//	**************************************************************************
//	//	*********************Initializing several variables***********************
//
////	public static final int HANDS_CLEARANCE_WITH_TOTE = 20;     
////	public static final int HANDS_CLEARANCE_WITHOUT_TOTE = 10;  
////
////
////	// Current maximum thresholds
////	public static final double ELEVATOR_TOP_HAND_CURRENT_MAX = 40.00;
////	public static final double ELEVATOR_BOTTOM_HAND_CURRENT_MAX = 60.00;
////
////	// Elevator positions
////	public static final int ELEVATOR_TOP_HAND_HEIGHT_MIN = 0;
////	public static final int ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE = 1311;			// also does "on the step"
////	public static final int ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4 = 7440;	
////	public static final int ELEVATOR_TOP_HAND_HEIGHT_MAX = 10175;		// was 10150 at UMASS
////	// NOTE:  Absolute max giving metal-to-metal contact is 10250
////
////	public static final int ELEVATOR_TOP_HAND_ZERO_POSITION = 1067;         // just below the RC handle
////	public static final int ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION = 10180;   // smashed up against the hard stop at the top
////
////	public static final int ElevatorTopPositions[] = new int[]{
////		ELEVATOR_TOP_HAND_HEIGHT_MIN,
////		ELEVATOR_TOP_HAND_PICKUP_BELOW_HANDLE,
////		ELEVATOR_TOP_HAND_RC_ABOVE_TOTE_4,
////		ELEVATOR_TOP_HAND_HEIGHT_MAX
////	};
////
////	// spacing between hands on practice robot set to 13 7/8" on 21 March 2015
////	public static final int ELEVATOR_BOTTOM_HAND_SPACING = 2300;
////	public static final int CARRY_OFFSET = 400;  // had initially been 500 during practice before NEU
////	public static final int DROP_OFFSET = 1300;
////
////	public static final int ELEVATOR_BOTTOM_HAND_HEIGHT_MIN = 0;
////	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 = 180;
////	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_1 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + DROP_OFFSET;
////	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2 = 2550;   // 2900 as of end of UMASS
////	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_2 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2 + DROP_OFFSET;
////	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 = 4850;   // 5585 as of end of UMASS
////	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_3 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3 + DROP_OFFSET;
////	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4 = 7200;   // 8325 as of end of UMASS
////	public static final int ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_4 = ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4 + DROP_OFFSET;
////	public static final int ELEVATOR_BOTTOM_HAND_BELOW_TOTE_5 = 9450;   // 8325 as of end of UMASS
////	public static final int ELEVATOR_BOTTOM_HAND_HEIGHT_MAX = 10150;	    // 9250 as of end of UMASS
////
////	public static final int ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_ONE_TOTE = 6350;
////	public static final int ELEVATOR_BOTTOM_HAND_CLEAR_RC_WITH_TWO_TOTES = 8570;
////
////	public static final int ELEVATOR_BOTTOM_HAND_MANUAL_ZERO_POSITION = 0;  // the manual zeroing point
////	public static final int ELEVATOR_BOTTOM_HAND_ZERO_POSITION = 450;  // just below the Tote lip
////	public static final int ELEVATOR_BOTTOM_HAND_RELEASE_5_HEIGHT = 8150;
////
////	public static final int CLICK_TO_NEXT_POSITION_TOLERANCE = 200;	
////
////	public static final int ElevatorBottomPositions[] = new int[]{
////		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1,
////		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2,
////		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3,
////		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4,
////		ELEVATOR_BOTTOM_HAND_BELOW_TOTE_5,
////		ELEVATOR_BOTTOM_HAND_HEIGHT_MAX - CARRY_OFFSET - 201,  // need to do at least a "minus one"
////	};
////
////	//	private int m_BottomHandIndex;
////
////
////	public static final double ELEVATOR_HIGH_SPEED = 0.5;	
////	public static final double ELEVATOR_LOW_SPEED = 0.2;	
////	public static final double ELEVATOR_SUPER_SLOW_SPEED = 0.17; //was 0.15
////	public final double ELEVATOR_ZEROING_SPEED = 0.20;	
////
////	private static boolean antiCrashingMode = true;
////
////	// member variables to support manual modes for elevators
////	// start out in manual mode
////	private boolean m_bottomHandManualMode = true;  
////	private boolean m_topHandManualMode = true;
////	private double m_topHandSpeed = ELEVATOR_HIGH_SPEED;
////	private double m_bottomHandSpeed = ELEVATOR_HIGH_SPEED;
////	private double m_topHandSuperSlow = ELEVATOR_SUPER_SLOW_SPEED;
////	private double m_bottomHandSuperSlow = ELEVATOR_SUPER_SLOW_SPEED;
////
////	// member variables related to encoder positioning
////	private boolean m_elevatorZeroed = false;
////	private int m_topHandDesiredPosition = 0;
////	private int m_bottomHandDesiredPosition = 0;
//
//	
//	//COMMENTED OUT FOR SAFETY
////	private CANTalon m_elevatorBottomHandTalon = new CANTalon(RobotMap.ELEVATOR_BOTTOM_HAND_TALON);
////	private CANTalon m_elevatorTopHandTalon = new CANTalon(RobotMap.ELEVATOR_TOP_HAND_TALON);
//
//	// member variables related to tote pickup
//	private int m_loopsAligned = 0;
//	private static int LOOPS_REQUIRED_FOR_ALIGNMENT = 3;
//
//	//	**************************************************************************
//	//	*********************More initialization stuff****************************
//
//	public Elevator(){
//		// set all Talon SRX encoder positions to zero
////		m_elevatorBottomHandTalon.setPosition(0);
////		m_elevatorTopHandTalon.setPosition(0);
//
//		// ensure brake mode for all Talon SRX
////		m_elevatorBottomHandTalon.enableBrakeMode(true);
////		m_elevatorTopHandTalon.enableBrakeMode(true);
//
//		// clear all voltage ramp rates
////		m_elevatorBottomHandTalon.setVoltageRampRate(96.0);
////		m_elevatorTopHandTalon.setVoltageRampRate(96.0);
//
//		// reverse the sensor on the bottom hand talon, as the output shaft is reversed
////		m_elevatorBottomHandTalon.reverseSensor(true);
//	}
//
//	public void init() {					
//		// complete initialization here that can't be performed in constructor
//		// (some calls can't be made in constructor because other objects don't yet exist)
//
//		// Send the elevator PIDF values to each talon.
//		setElevatorPIDF();	
//
//		// Set up the TalonSRX manual / automatic mode appropriately for each elevator
//		if (m_bottomHandManualMode) {
////			m_elevatorBottomHandTalon.changeControlMode(ControlMode.PercentVbus);
//		} else {
////			m_elevatorBottomHandTalon.changeControlMode(ControlMode.Position);
//		}
//
//		if (m_topHandManualMode) {
////			m_elevatorTopHandTalon.changeControlMode(ControlMode.PercentVbus);
//		} else {
////			m_elevatorTopHandTalon.changeControlMode(ControlMode.Position);
//		}
//
//		// initially clear the "zeroed" soft limits that will exist when "zeroed"
//		clearZeroed();						
//
//		// read back the current positions as desired positions
//		m_topHandDesiredPosition =  (int) getPositionTopHand();
//		m_bottomHandDesiredPosition = (int) getPositionBottomHand();
//
//		// enable control of the Talon
////		m_elevatorBottomHandTalon.enableControl();
////		m_elevatorTopHandTalon.enableControl();
//	}
//
//	public void initDefaultCommand() {
//		// Set the default command for a subsystem here.
//		//setDefaultCommand(new MySpecialCommand());
//	}
//
//
//	//	**************************************************************************
//	//	*********************Misc.  GETters / SETters   **************************
//
//
//	public boolean getAntiCrashingMode(){					
//		return antiCrashingMode;
//	}
//
//	public void setAntiCrashingMode(boolean value){			
//		antiCrashingMode = value;
//	} 
//
//	public void setElevatorPIDF() {							
//		double elevatorP = RobotPreferences.getElevatorP();
//		double elevatorI = RobotPreferences.getElevatorI();
//		double elevatorD = RobotPreferences.getElevatorD();
//		double elevatorF = RobotPreferences.getElevatorF(); 	
//
////		m_elevatorBottomHandTalon.setPID(elevatorP, elevatorI, elevatorD, elevatorF, 0, 0, 0);
////		m_elevatorTopHandTalon.setPID(elevatorP, elevatorI, elevatorD, elevatorF, 0, 0, 0);
//
//		DriverStation.reportError("setElevatorPIDF: " + elevatorP + " " + elevatorI + " " + elevatorD + " " + elevatorF + "\n", false);
//	}
//
//	public int getPositionBottomHand() {
//		return 0;
////		return (int)m_elevatorBottomHandTalon.getPosition();
//	}
//
//	public int getPositionTopHand() {
//		return 0;
////		return (int) m_elevatorTopHandTalon.getPosition();
//	}
//
//	public void resetPositionBottomHand(int zeroPosition) {
////		m_elevatorBottomHandTalon.setPosition(zeroPosition);
////		m_elevatorBottomHandTalon.setReverseSoftLimit(ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);    	
////		m_elevatorBottomHandTalon.setForwardSoftLimit(ELEVATOR_BOTTOM_HAND_HEIGHT_MAX);    		
//	}
//
//	public void resetPositionBottomHand(){
//		resetPositionBottomHand(ELEVATOR_BOTTOM_HAND_MANUAL_ZERO_POSITION);
//	}
//
//	public void resetPositionTopHand(int zeroPosition){
////		m_elevatorTopHandTalon.setPosition(zeroPosition);  	
////		m_elevatorTopHandTalon.setReverseSoftLimit(ELEVATOR_TOP_HAND_HEIGHT_MIN);  	
////		m_elevatorTopHandTalon.setForwardSoftLimit(ELEVATOR_TOP_HAND_HEIGHT_MAX);
//	}
//
//	public void resetPositionTopHand(){
//		resetPositionTopHand(0);
//	}
//
//	public void clearZeroed() {			
//		m_elevatorZeroed = false;
////		m_elevatorBottomHandTalon.setReverseSoftLimit(-99999);    	
////		m_elevatorBottomHandTalon.setForwardSoftLimit(99999);   
////		m_elevatorTopHandTalon.setReverseSoftLimit(-99999); 
////		m_elevatorTopHandTalon.setForwardSoftLimit(99999); 
//	}
//
//	public void setZeroed() {		
//		m_elevatorZeroed = true;
////		m_elevatorBottomHandTalon.setReverseSoftLimit(ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);    	
////		m_elevatorBottomHandTalon.setForwardSoftLimit(ELEVATOR_BOTTOM_HAND_HEIGHT_MAX);
////		m_elevatorTopHandTalon.setReverseSoftLimit(ELEVATOR_TOP_HAND_HEIGHT_MIN);  	
////		m_elevatorTopHandTalon.setForwardSoftLimit(ELEVATOR_TOP_HAND_HEIGHT_MAX);
//
//	}
//
//
//	//  **********************************************************************************
//	//  ********************* Manual Control Methods for Elevator ***********************
//
//	public void setBottomHandManualControlSpeed(double speed) {
//		m_bottomHandSpeed = speed;
//	}
//
//	public void setTopHandManualControlSpeed(double speed) {
//		m_topHandSpeed = speed;
//	}
//
//	public double calculateSpeedTopHand(){
//		if(Robot.oi.superSlowTopHand()){
//			return m_topHandSuperSlow;
//		}
//		else{
//			return m_topHandSpeed;
//		}
//	}
//
//	public double calculateSpeedBottomHand(){
//		if(Robot.oi.superSlowBottomHand()){
//			return m_bottomHandSuperSlow;
//		}else{
//			return m_bottomHandSpeed;
//		}
//	}
//
//	// raise Elevator goes at "double speed"
//	public void raiseElevatorTopHand() {
//		// this command will switch the top hand elevator into "manual mode"
////		m_elevatorTopHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorTopHandTalon.set(calculateSpeedTopHand()*2);
////		m_elevatorTopHandTalon.enableControl();
//		m_topHandManualMode = true;
//	}
//
//	// lower Elevator goes at "single speed" down (backwards)
//	public void lowerElevatorTopHand() {
//		// this command will switch the top hand elevator into "manual mode"
////		m_elevatorTopHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorTopHandTalon.set(-calculateSpeedTopHand());
////		m_elevatorTopHandTalon.enableControl();
//		m_topHandManualMode = true;
//	}
//
//	// stop Elevator (both hands)
//	public void stopElevator() {				
//		stopElevatorBottomHand();
//		stopElevatorTopHand();
//	}
//
//	// stop Elevator
//	public void stopElevatorBottomHand() {
//		// this command will switch the bottom hand elevator into "manual mode"
////		m_elevatorBottomHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorBottomHandTalon.set(0);
////		m_elevatorBottomHandTalon.enableControl();
//		m_bottomHandManualMode = true;
//	}
//
//	// stop Elevator Top Hand
//	public void stopElevatorTopHand() {
//		// this command will switch the top hand elevator into "manual mode"
////		m_elevatorTopHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorTopHandTalon.set(0);
////		m_elevatorTopHandTalon.enableControl();  
//		m_topHandManualMode = true;
//	}
//
//	// raise Elevator goes at "double speed"
//	public void raiseElevatorBottomHand() {
//		// this command will switch the bottom hand elevator into "manual mode"
////		m_elevatorBottomHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorBottomHandTalon.set(calculateSpeedBottomHand()*2);
////		m_elevatorBottomHandTalon.enableControl();
//		m_bottomHandManualMode = true;
//	}
//
//	// lower Elevator goes at "single speed" down (backwards)
//	public void lowerElevatorBottomHand() {
//		// this command will switch the bottom hand elevator into "manual mode"
////		m_elevatorBottomHandTalon.changeControlMode(ControlMode.PercentVbus);
////		m_elevatorBottomHandTalon.set(-calculateSpeedBottomHand());
////		m_elevatorBottomHandTalon.enableControl();
//		m_bottomHandManualMode = true;
//	}
//
//
//	//    *****************************************************************
//	//  ************ Position Control Methods for Elevator ************
//
//
//	private int getNextLowerIndexPositionTopHand(){
//		int currentPosition = getPositionTopHand() - CLICK_TO_NEXT_POSITION_TOLERANCE;
//		for(int i = ElevatorTopPositions.length-1; i >= 0; i--){
//			if(currentPosition  >= ElevatorTopPositions[i]){
//				return i;
//			}     		
//		}
//		return 0;
//	}
//
//	private int getNextHigherIndexPositionTopHand() {
//		int currentPosition = getPositionTopHand() + CLICK_TO_NEXT_POSITION_TOLERANCE;
//		for(int i = 0; i < ElevatorTopPositions.length; i++){
//			if(currentPosition <= ElevatorTopPositions[i]){
//				return i;
//			}     		
//		}
//		return ElevatorTopPositions.length-1;
//	}
//
//	private int getIndexPositionBottomHand() {
//		int currentPosition = getPositionBottomHand() - CLICK_TO_NEXT_POSITION_TOLERANCE - CARRY_OFFSET;
//		for (int i = 0; i < ElevatorBottomPositions.length; i++){
//			if (currentPosition <= ElevatorBottomPositions[i]){
//				return i;
//			}     		
//		}
//
//		// if we somehow fall through the entire loop without a match, return the highest index
//		return ElevatorBottomPositions.length-1;
//	}
//
//	private int getNewAdjustedIndexPositionBottomHand (int adjustment) {
//		// adjust the index position by "adjustment"
//		int newIndex = getIndexPositionBottomHand() + adjustment;
//
//		// check for overflow or underflow
//		if (newIndex < 0) {
//			newIndex = 0;
//		} else if (newIndex > ElevatorBottomPositions.length - 1) {
//			newIndex = ElevatorBottomPositions.length - 1;
//		}
//
//		// return the adjusted index position
//		return newIndex;
//	}
//
//	private int getNextLowerIndexPositionBottomHand(){
//		return getNewAdjustedIndexPositionBottomHand(-1);
//	}
//
//	private int getNextHigherIndexPositionBottomHand(){
//		return getNewAdjustedIndexPositionBottomHand(1);
//	}
//
//	private int getSecondHigherIndexPositionBottomHand(){
//		return getNewAdjustedIndexPositionBottomHand(2);
//	}
//
//	/**
//	 * Move the top hand up by 1 index.
//	 */
//	public boolean moveTopHandUpOneIndex()
//	{
//		return moveTopHandToIndex(getNextHigherIndexPositionTopHand());
//	}    
//
//	/**
//	 * Move the top hand down by 1 index.
//	 */   
//	public boolean moveTopHandDownOneIndex()
//	{
//		return moveTopHandToIndex(getNextLowerIndexPositionTopHand());
//	}
//
//	public boolean moveBottomHandUpOneIndex()
//	{
//		return moveBottomHandToIndex(getNextHigherIndexPositionBottomHand());
//	}
//
//	public boolean moveBottomHandUpOneIndexPlusCarryOffset()
//	{
//		return moveBottomHandToIndexPlusCarryOffset(getNextHigherIndexPositionBottomHand());
//	}
//
//	/**
//	 * Move the bottom hand down by 1 index.
//	 */
//	public boolean moveBottomHandDownOneIndex()
//	{
//		return moveBottomHandToIndex(getNextLowerIndexPositionBottomHand());
//	}
//
//	/**
//	 * Move the top hand to an index.
//	 * @param Index
//	 */
//	public boolean moveTopHandToIndex(int newIndex) {
//		// If the new index is within bounds...
//		if ( 0 <= newIndex && newIndex < ElevatorTopPositions.length) {
//			return moveTopHandToPosition(ElevatorTopPositions[newIndex]);
//		} else {
//			return false;
//		}
//	}    
//
//	public boolean moveBottomHandToIndexPlusCarryOffset(int newIndex){
//		// If the new index is within bounds...
//		if ( 0 <= newIndex && newIndex < ElevatorBottomPositions.length) {	
//			return moveBottomHandToPosition(ElevatorBottomPositions[newIndex] + CARRY_OFFSET);
//		} else {
//			return false;
//		}
//	}
//
//	public boolean moveBottomHandToIndex(int newIndex){
//		// If the new index is within bounds...
//		if ( 0 <= newIndex && newIndex < ElevatorBottomPositions.length) {	
//			return moveBottomHandToPosition(ElevatorBottomPositions[newIndex]);
//		} else {
//			return false;
//		}
//	}
//
//	public boolean moveTopHandToPosition(int position){
//		if(antiCrashingMode) {
//			// if we are in antiCrashing Mode, do NOT override the checks
//			return moveTopHandToPosition(position, false);
//		} else {
//			// if we are NOT in antiCrashing Mode, DO override the checks
//			return moveTopHandToPosition(position, true);
//		}
//	}
//
//	/**
//	 * Move the top hand to a desired encoder position.
//	 * @param position
//	 */
//	// return whether or not there was a problem: problem --> true, no problem --> false
//	public boolean moveTopHandToPosition(int position, boolean override) {
//		// check to make sure the elevator has previously been zeroed
//
//		if (!m_elevatorZeroed) {
//			DriverStation.reportError("Elevator not yet zeroed; can't moveToPosition.\n", false);
//			return true;
//		}
//
//		// if trying to move the top hand all the way to the bottom, just do it
//		if (position == ELEVATOR_TOP_HAND_HEIGHT_MIN) {
//			// move the bottom hand all the way to the bottom
//			moveBottomHandToPosition(ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);
//
//			// tell the top Talon to go to the position
//			m_topHandDesiredPosition = position;
////			m_elevatorTopHandTalon.set(m_topHandDesiredPosition);
//			return false;
//		}
//
//		int bottomHandPosition = Robot.elevator.getPositionBottomHand();
//
//		//  if the current bottom hand position is above the height of the first tote, use the
//		//  "HANDS_CLEARANCE_WITH_TOTE" to determine if the hands could crash
//		if (!override && (bottomHandPosition > ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + HANDS_CLEARANCE_WITHOUT_TOTE) &&
//				(position <= bottomHandPosition + HANDS_CLEARANCE_WITH_TOTE )) {
//			DriverStation.reportError("Could crash into bottom hand (high case)", false);
//			return true;
//
//		} 
//		//  if the current bottom hand position is below the height of the first tote, 
//		//  there is no way that there could be a tote on the first hand, so allow the top
//		//  hand to go within "HANDS_CLEARANCE_WITHOUT_TOTE" of the bottom hand
//		else if (!override && (bottomHandPosition <= ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1 + HANDS_CLEARANCE_WITHOUT_TOTE) &&
//				(position <= bottomHandPosition + HANDS_CLEARANCE_WITHOUT_TOTE ))
//		{ 
//			DriverStation.reportError("Could crash into bottom hand (low case)", false);
//			return true;
//		} else {    		
//			if (m_topHandManualMode) {
//				// if still in manual (PercentVbus) mode, change the TalonSRX to Position Mode
////				m_elevatorTopHandTalon.changeControlMode(ControlMode.Position);
////				m_elevatorTopHandTalon.enableControl();
//				m_topHandManualMode = false;
//			}
//
//			// tell the Talon to go to the position
//			m_topHandDesiredPosition = position;
////			m_elevatorTopHandTalon.set(m_topHandDesiredPosition);
//			return false;
//		}
//	}
//
//
//	public boolean moveBottomHandToPosition(int position){
//		if(antiCrashingMode){
//			//    		if we are in anti crashing mode, do NOT override the checks
//			return moveBottomHandToPosition(position, false);
//		}else{
//			//    		if we are NOT in anti crashing mode, DO override the checks
//			return moveBottomHandToPosition(position, true);
//		}    	
//	}
//
//	//return whether or not there was a problem: problem --> true, no problem --> false
//	public boolean moveBottomHandToPosition(int position, boolean override){
//		// check to make sure the elevator has previously been zeroed
//
//		if (!m_elevatorZeroed) {
//			DriverStation.reportError("Elevator not yet zeroed; can't moveToPosition.\n", false);
//			return true;
//		} else if( !override && (position >= Robot.elevator.getPositionTopHand() - HANDS_CLEARANCE_WITH_TOTE)) {
//			DriverStation.reportError("Could crash into top hand", false);
//			return true;
//		}    	
//		else {
//			if (m_bottomHandManualMode) {
//				// if still in manual (PercentVbus) mode, change the TalonSRX to Position Mode
////				m_elevatorBottomHandTalon.changeControlMode(ControlMode.Position);
////				m_elevatorBottomHandTalon.enableControl();
//				m_bottomHandManualMode = false;
//			}
//			// tell the Talon to go to the position
//			m_bottomHandDesiredPosition = position;
////			m_elevatorBottomHandTalon.set(m_bottomHandDesiredPosition);
//			return false;
//		}
//	}
//
//	public static final int POSITION_TOLERANCE = 100;  // was 25			
//
//	public boolean isTopHandAtDesiredPosition()
//	{
//		return ((m_topHandDesiredPosition - POSITION_TOLERANCE < getPositionTopHand()) &&
//				(getPositionTopHand() < m_topHandDesiredPosition + POSITION_TOLERANCE));
//	}
//
//	public boolean isBottomHandAtDesiredPosition()
//	{
//		return ((m_bottomHandDesiredPosition - POSITION_TOLERANCE < getPositionBottomHand()) &&
//				(getPositionBottomHand() < m_bottomHandDesiredPosition + POSITION_TOLERANCE));
//	}   
//
//
//
//	// *************************update the Smart Dashboard*********************
//	//    *********************************************************************   
//
//
//
//	public void updateSmartDashboard(){							
//
//
//
//		// display information about top hand
//		SmartDashboard.putNumber("Elevator Actual Top Hand", getPositionTopHand());
//		if (m_topHandManualMode) {
//			// indicate manual mode by a value of -99999
//			SmartDashboard.putNumber("Elevator Desired Top Hand", -99999);         		
//		} else {
//			SmartDashboard.putNumber("Elevator Desired Top Hand", m_topHandDesiredPosition);         		
//		}
//		SmartDashboard.putNumber("Elevator Current Top Hand", Robot.pdp.getCurrent(RobotMap.ELEVATOR_TOP_HAND_PDP));
//		SmartDashboard.putBoolean("Top Hand in Manual Mode" , m_topHandManualMode);
//
//
//
//		// display information about bottom hand
//		SmartDashboard.putNumber("Elevator Actual Bottom Hand", getPositionBottomHand());
//		if (m_bottomHandManualMode) {
//			// indicate manual mode by a value of -99999
//			SmartDashboard.putNumber("Elevator Desired Bottom Hand", -99999);         		
//		} else {
//			SmartDashboard.putNumber("Elevator Desired Bottom Hand", m_bottomHandDesiredPosition);         		
//		}
//		SmartDashboard.putNumber("Elevator Current Bottom Hand", Robot.pdp.getCurrent(RobotMap.ELEVATOR_BOTTOM_HAND_PDP));
//		SmartDashboard.putBoolean("Bottom Hand in Manual Mode" , m_bottomHandManualMode);
//
//
//
//
//		SmartDashboard.putBoolean("AntiCrashing Mode", antiCrashingMode);        
//	}    
//
//
//	//    **************************CURRENT MONITORING	*********************************
//
//
//
//
//	public void currentMonitoring(){
//		elevatorTopHandCurrentOverDraw();
//		elevatorBottomHandCurrentOverDraw();
//	}
//
//	public boolean elevatorTopHandCurrentOverDraw(){
//		double currentDrawn = getElevatorTopHandCurrent();
//		if(currentDrawn >= ELEVATOR_TOP_HAND_CURRENT_MAX){
//			//    		m_elevatorTopHandTalon.disableControl();
//			stopElevatorTopHand();
//			DriverStation.reportError("*ATTENTION:* ELEVATOR TOP HAND DRAWING " + currentDrawn +
//					" IS ABOVE THRESHOLD " + ELEVATOR_TOP_HAND_CURRENT_MAX 
//					//					+ " n/ **TEMPORARILY DISABLING OPERATOR CONTROL OVER TOP HAND ELEVATOR**"
//					, false);
//			return true;
//
//		}
//		return false;
//	}
//
//	public boolean elevatorBottomHandCurrentOverDraw(){
//		double currentDrawn = getElevatorBottomHandCurrent();
//		if(currentDrawn >= ELEVATOR_BOTTOM_HAND_CURRENT_MAX){
//			stopElevatorBottomHand();
//			DriverStation.reportError("*ATTENTION:* ELEVATOR BOTTOM HAND DRAWING " + currentDrawn +
//					" IS ABOVE THRESHOLD " + ELEVATOR_BOTTOM_HAND_CURRENT_MAX //+ 
//					//    					" n/ **TEMPORARILY DISABLING OPERATOR CONTROL OVER BOTTOM HAND ELEVATOR**"
//					, false);
//			return true;
//		}
//		return false;
//	}
//
//
//
//
//	//    ***********************************************************************************
//	//    *****************************Self Test*********************************************
//
//
//
//
//	public double getElevatorTopHandCurrent(){
//		return Robot.pdp.getCurrent(RobotMap.ELEVATOR_TOP_HAND_PDP);
//	}
//
//	public double getElevatorBottomHandCurrent(){
//		return Robot.pdp.getCurrent(RobotMap.ELEVATOR_BOTTOM_HAND_PDP);
//	}
//
//	public double getElevatorTopHandVelocity(){
//		return 0.0;
////		return m_elevatorTopHandTalon.getSpeed();    	
//	}
//
//	public double getElevatorBottomHandVelocity(){
//		return 0.0;
////		return m_elevatorBottomHandTalon.getSpeed();    	
//	}
//
//
//
//	//    **********************MISC OTHER************************
//
//
//
//	public boolean handsNearCrashing(){						
//		return (getPositionTopHand() <= (getPositionBottomHand() + HANDS_CLEARANCE_WITHOUT_TOTE));
//	}
//
//
//	public void fixSlippingBottomHand(int theoreticalEncoder){
////		m_elevatorBottomHandTalon.setPosition(theoreticalEncoder);
//		moveBottomHandToPosition(theoreticalEncoder, true);
//		DriverStation.reportError("Belt Slippage!  Resetting Elevator Encoder Count (Bottom Hand)", false);
//	}
//	/**
//	 * Pass in the amount to change the position of the encoder
//	 * @param theoreticalEncoder
//	 */
//	public void nudgeBottomHandPosition(int encoderAdjustment){
//		// a positive adjustment moves the position down which will cause the talon to move up.
//		// If the talon is at 1000 and we want to go up by 100, pass in 100.  The talon will set the 
//		// current position to 900, which will cause the talon to move up by 100 encoder counts to
//		// rest at 1000.
//		int newPosition = getPositionBottomHand() - encoderAdjustment;
////		m_elevatorBottomHandTalon.setPosition(newPosition);
//	}
//
//	public void holdElevatorBottomHand(){
//		moveBottomHandToPosition(getPositionBottomHand());    	
//	}
//
//	public void holdElevatorTopHand(){
//		moveTopHandToPosition(getPositionTopHand());    	
//	}
//
//	public boolean permissionToLiftCheckToteCoarseAligned(){					
//		return Robot.drive.getToteCoarseAligned();
//	}    
//
//	public void clearPriorToteAlignment() {								
//		m_loopsAligned = 0;
//	}
//
//	public boolean permissionToLiftCheckToteAligned() {						
//		if (Robot.drive.getToteAligned()) {
//			m_loopsAligned++;
//		} else {
//			m_loopsAligned = 0;
//		}
//		return (m_loopsAligned >= LOOPS_REQUIRED_FOR_ALIGNMENT);
//	}
//
//	public void periodic(){
//		currentMonitoring();
//	}
//
//	public void addCarryHeight() {
//		int currentIndex = getIndexPositionBottomHand();
//		moveBottomHandToPosition(ElevatorBottomPositions[currentIndex] + CARRY_OFFSET);
//
//	}
//
//	public void removeCarryHeight() {
//		int currentIndex = getIndexPositionBottomHand();
//		moveBottomHandToPosition(ElevatorBottomPositions[currentIndex]);
//	}
//
//	public boolean getElevatorZeroed(){
//		return m_elevatorZeroed;
//	}
//}
//
