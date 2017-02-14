package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotMap;


import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.mayheminc.robot2015.RobotPreferences;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public abstract class ElevatorBase extends Subsystem {


	// all of these constants will be inherited by the derived classes

	public static final double ELEVATOR_HIGH_SPEED = 0.5;	
	public static final double ELEVATOR_LOW_SPEED = 0.2;	
	public static final double ELEVATOR_SUPER_SLOW_SPEED = 0.15;
	public final double ELEVATOR_ZEROING_SPEED = 0.20;
	public static final int CLICK_TO_NEXT_POSITION_TOLERANCE = 150;
	public static final int POSITION_TOLERANCE = 85;  // was 25
	

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	protected boolean m_handManualMode = true;  
	protected double m_handSpeed = ELEVATOR_HIGH_SPEED;
	protected double m_handSuperSlow = ELEVATOR_SUPER_SLOW_SPEED;

	protected int m_handDesiredPosition = 0;
	
	private CANTalon m_elevatorHandTalon;

	public void initDefaultCommand() {
		// note that this method is overridden by both BottomElevator AND TopElevator
	}

	// eventually these will all be abstract
	public int ElevatorPositions[];
	public int m_handHeightMin;
	public int m_handHeightMax;
	public int m_manualZeroPosition;

	public ElevatorBase(int talonPort, boolean reverseTalon){
		m_elevatorHandTalon = new CANTalon(talonPort);
		m_elevatorHandTalon.setPosition(0);
		m_elevatorHandTalon.enableBrakeMode(true);
		m_elevatorHandTalon.setVoltageRampRate(96.0);
		m_elevatorHandTalon.reverseSensor(reverseTalon);
	}

	public int getPositionHand() {
		return (int)m_elevatorHandTalon.getPosition();
	}

	public void resetPositionHand(int zeroPosition) {
		m_elevatorHandTalon.setPosition(zeroPosition);
		m_elevatorHandTalon.setReverseSoftLimit(m_handHeightMin);    	
		m_elevatorHandTalon.setForwardSoftLimit(m_handHeightMax);    		
	}

	public void resetPositionHand(){
		resetPositionHand(m_manualZeroPosition);
	}

	public void setHandManualControlSpeed(double speed) {
		m_handSpeed = speed;
	}
	public abstract double calculateSpeedHand();


	public void stopElevatorHand() {
		setManualElevatorHand(0);
	}

	public void raiseElevatorHand() {
		setManualElevatorHand(calculateSpeedHand() * 2);
	}

	public void lowerElevatorHand() {
		setManualElevatorHand(calculateSpeedHand());
//		NO NEGATIVE SIGN...  the value returned from caluclateSpeedHand() will be negative if the joystick is pulled
//		down
	}
	
	public void lowerElevatorHandZeroing(){
		setManualElevatorHand(-ELEVATOR_ZEROING_SPEED);
	}
	
	public void raiseElevatorHandZeroing(){
		setManualElevatorHand(ELEVATOR_ZEROING_SPEED);
	}
	
	private void setManualElevatorHand(double Speed)
	{
		// this command will switch the bottom hand elevator into "manual mode"
		m_elevatorHandTalon.changeControlMode(TalonControlMode.PercentVbus);
		m_elevatorHandTalon.set(Speed);
		m_elevatorHandTalon.enableControl();
		m_handManualMode = true;
	}

		// if we somehow fall through the entire loop without a match, return the highest index
	public boolean moveHandUpOneIndex()
	{
		DriverStation.reportError("checkpoint B", false);
		int destinationIndex;
		destinationIndex = getNextHigherIndexPositionHand();
		
		DriverStation.reportError("Checkpoint C", false);
		return moveHandToIndex(destinationIndex);
	}

	public boolean moveHandUpOneIndexPlusCarryOffset()
	{
		int destinationIndex;
		destinationIndex = getNextHigherIndexPositionHand();
		return moveHandToIndexPlusCarryOffset(destinationIndex);
	}

	public boolean moveHandDownOneIndex()
	{
		int destinationIndex;
		destinationIndex = getNextLowerIndexPositionHand();
		return moveHandToIndex(destinationIndex);
	}


	public boolean moveHandToIndexPlusCarryOffset(int newIndex){
		// If the new index is within bounds...
		
		
		if ( 0 <= newIndex && newIndex < ElevatorPositions.length) {	
			
			return moveHandToPosition(ElevatorPositions[newIndex] + Robot.pole.bottomElevator.CARRY_OFFSET);
			
		} else {
			
			return false;
			
		}
	}

	public boolean moveHandToIndex(int newIndex){
		// If the new index is within bounds...
		
		DriverStation.reportError("Checkpoint D", false);
		
		
		if ( 0 <= newIndex && newIndex < ElevatorPositions.length) {
			DriverStation.reportError("Checkpoint F", false);
			return moveHandToPosition(ElevatorPositions[newIndex]);
		} else {
			DriverStation.reportError("Checkpoint G", false);
			return false;
		}
	}

	public boolean moveHandToPosition(int position){
		if(Robot.pole.getAntiCrashingMode()){
			//    		if we are in anti crashing mode, do NOT override the checks
			return moveHandToPosition(position, false);
		}else{
			//    		if we are NOT in anti crashing mode, DO override the checks
			return moveHandToPosition(position, true);
		}    	
	}


	public boolean moveHandToPosition(int position, boolean override){
		// check to make sure the elevator has previously been zeroed

		if (!Robot.pole.getElevatorZeroed()) {
			DriverStation.reportError("Elevator not yet zeroed; can't moveToPosition.\n", false);
			return true;
		} else if( !override && isThisPositionGoingToMakeTheHandsCrash(position, Robot.pole.HANDS_CLEARANCE_WITH_TOTE)) {
			DriverStation.reportError("Could crash into top hand", false);
			return true;
		}    	
		else {
			if (m_handManualMode) {
				// if still in manual (PercentVbus) mode, change the TalonSRX to Position Mode
				m_elevatorHandTalon.changeControlMode(TalonControlMode.Position);
				m_elevatorHandTalon.enableControl();
				m_handManualMode = false;
			}
			// tell the Talon to go to the position
			m_handDesiredPosition = position;
			m_elevatorHandTalon.set(m_handDesiredPosition);
			return false;
		}
	}
	
	public boolean isThisPositionGoingToMakeTheHandsCrash(int position, int clearance){
		return false;
	}

	public boolean isHandAtDesiredPosition()
	{
		return ((m_handDesiredPosition - POSITION_TOLERANCE < getPositionHand()) &&
				(getPositionHand() < m_handDesiredPosition + POSITION_TOLERANCE));
	}  


	public abstract boolean elevatorHandCurrentOverDraw();


	protected abstract double GetMaxTalonCurrent();
	protected abstract int GetPdpIndex();

	public double getElevatorHandCurrent(){
		return Robot.pdp.getCurrent(GetPdpIndex());
	}

	public double getElevatorHandVelocity(){
		return m_elevatorHandTalon.getSpeed();    	
	}




	

	
	public void setTalonControl(TalonControlMode Mode)
	{
		m_elevatorHandTalon.changeControlMode(Mode);
		m_elevatorHandTalon.enableControl();
	}
	public void periodic(){
		elevatorHandCurrentOverDraw();
	}
	






	public void setTalonPIDF(double elevatorP, double elevatorI, double elevatorD, double elevatorF){
		m_elevatorHandTalon.setPID(elevatorP, elevatorI, elevatorD, elevatorF, 0, 0, 0);
	}

	public void setTalonReverseSoftLimit(int limit){
		m_elevatorHandTalon.setReverseSoftLimit(limit);
	}
	public void setTalonForwardSoftLimit(int limit){
		m_elevatorHandTalon.setForwardSoftLimit(limit);
	}
	public void changeTalonControlMode(TalonControlMode mode){
		m_elevatorHandTalon.changeControlMode(mode);
	}
	public void enableTalonControl(){
		m_elevatorHandTalon.enableControl();
	}
	
	
	
	
	
	
	
	
	
	
	
	public static final int CARRY_OFFSET = 400;  // had initially been 500 during practice before NEU
	public static final int DROP_OFFSET = 1300;
	
	private int getNewAdjustedIndexPositionHand (int adjustment) {
		// adjust the index position by "adjustment"
		int newIndex = getIndexPositionHand() + adjustment;

		// check for overflow or underflow
		if (newIndex < 0) {
			newIndex = 0;
		} else if (newIndex > ElevatorPositions.length - 1) {
			newIndex = ElevatorPositions.length - 1;
		}

		// return the adjusted index position
		return newIndex;
	}
	
	public int getIndexPositionHand() {
		int currentPosition = getPositionHand() - CLICK_TO_NEXT_POSITION_TOLERANCE - CARRY_OFFSET;
		for (int i = 0; i < ElevatorPositions.length; i++){
			if (currentPosition <= ElevatorPositions[i]){
				return i;
			}     		
		}

		// if we somehow fall through the entire loop without a match, return the highest index
		return ElevatorPositions.length-1;
	}
	
	public void addCarryHeight() {
		int currentIndex = getIndexPositionHand();
		moveHandToPosition(ElevatorPositions[currentIndex] + Robot.pole.bottomElevator.CARRY_OFFSET);

	}

	public void removeCarryHeight() {
		int currentIndex = getIndexPositionHand();
		moveHandToPosition(ElevatorPositions[currentIndex]);
	}
	
	
	public int getNextLowerIndexPositionHand(){
		return getNewAdjustedIndexPositionHand(-1);
	}
	
	public int getNextHigherIndexPositionHand(){
		return getNewAdjustedIndexPositionHand(1);
	}
	
	
	
}