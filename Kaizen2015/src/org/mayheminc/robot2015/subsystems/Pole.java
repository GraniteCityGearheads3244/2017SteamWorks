package org.mayheminc.robot2015.subsystems;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.RobotPreferences;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Pole extends Subsystem {
	
	public static final int HANDS_CLEARANCE_WITH_TOTE = 20;     
	public static final int HANDS_CLEARANCE_WITHOUT_TOTE = 10;
    
	public final TopElevator topElevator = new TopElevator();
	public final BottomElevator bottomElevator = new BottomElevator();
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private boolean antiCrashingMode = true;

	public boolean getAntiCrashingMode(){					
		return antiCrashingMode;
	}

	public void setAntiCrashingMode(boolean value){			
		antiCrashingMode = value;
	} 

	public void updateSmartDashboard(){
		SmartDashboard.putBoolean("AntiCrashing Mode", antiCrashingMode);
		bottomElevator.updateSmartDashboard();
		topElevator.updateSmartDashboard();
	}
	
	public boolean handsNearCrashing(){						
		return (topElevator.getPositionHand() <= (bottomElevator.getPositionHand() + HANDS_CLEARANCE_WITHOUT_TOTE));
	}
	
	private boolean m_elevatorZeroed = false;
	
	public void clearZeroed() {			
		m_elevatorZeroed = false;

		bottomElevator.setTalonReverseSoftLimit(-99999);    	
		bottomElevator.setTalonForwardSoftLimit(99999);  

		topElevator.setTalonReverseSoftLimit(-99999); 
		topElevator.setTalonForwardSoftLimit(99999); 
	}

	public void setZeroed() {		
		m_elevatorZeroed = true;
		bottomElevator.setTalonReverseSoftLimit(bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MIN);    	
		bottomElevator.setTalonForwardSoftLimit(bottomElevator.ELEVATOR_BOTTOM_HAND_HEIGHT_MAX);

		topElevator.setTalonReverseSoftLimit(topElevator.ELEVATOR_TOP_HAND_HEIGHT_MIN);  	
		topElevator.setTalonForwardSoftLimit(topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX);
	}
	
	public boolean getElevatorZeroed(){
		return m_elevatorZeroed;
	}

	public void stopElevator() {				
		bottomElevator.stopElevatorHand();
		topElevator.stopElevatorHand();
	}
	
	public void setElevatorPIDF() {							
		double elevatorP = RobotPreferences.getElevatorP();
		double elevatorI = RobotPreferences.getElevatorI();
		double elevatorD = RobotPreferences.getElevatorD();
		double elevatorF = RobotPreferences.getElevatorF(); 	

		bottomElevator.setTalonPIDF(elevatorP, elevatorI, elevatorD, elevatorF);
		topElevator.setTalonPIDF(elevatorP, elevatorI, elevatorD, elevatorF);

		DriverStation.reportError("setElevatorPIDF: " + elevatorP + " " + elevatorI + " " + elevatorD + " " + elevatorF + "\n", false);
	}
	
	public void init() {
		setElevatorPIDF();
		clearZeroed();
	}
	
	public void periodic() {
		topElevator.periodic();
		bottomElevator.periodic();
	}
	
	
	//************************************************************************************
	
	
	
	
	public void holdElevatorBottomHand(){
		bottomElevator.holdElevatorHand();
	}
	
	
	
	// getters
	public int getPositionBottomHand(){
		return bottomElevator.getPositionHand();
	}
	public int getPositionTopHand(){
		return topElevator.getPositionHand();
	}
	public double getElevatorBottomHandVelocity(){
		return bottomElevator.getElevatorHandVelocity();
	}
	public double getElevatorTopHandVelocity(){
		return topElevator.getElevatorHandVelocity();
	}
	public double getElevatorBottomHandCurrent(){
		return bottomElevator.getElevatorHandCurrent();
	}
	public double getElevatorTopHandCurrent(){
		return topElevator.getElevatorHandCurrent();
	}
	
	public boolean isBottomHandAtDesiredPosition(){
		return bottomElevator.isHandAtDesiredPosition();
	}
	
	public boolean isTopHandAtDesiredPosition(){
		return topElevator.isHandAtDesiredPosition();
	}
	
	
	
	//manual control
	
	public void lowerElevatorBottomHand(){
		bottomElevator.lowerElevatorHand();
	}
	public void lowerElevatorTopHand(){
		topElevator.lowerElevatorHand();
	}
	public void raiseElevatorBottomHand(){
		bottomElevator.raiseElevatorHand();
	}
	
	public void raiseElevatorTopHand(){
		topElevator.raiseElevatorHand();
	}
	
	
	
	
	
	
	//stop!
	public void stopElevatorBottomHand(){
		bottomElevator.stopElevatorHand();
	}
	public void stopElevatorTopHand(){
		topElevator.stopElevatorHand();
	}
	
	
	
	//indexed mode
	public void moveElevatorBottomHandDownOneIndex(){
		bottomElevator.moveHandDownOneIndex();
	}
	
	public boolean moveElevatorBottomHandToPosition(int position, boolean override){
		return bottomElevator.moveHandToPosition(position, override);
	}
	
	public boolean moveElevatorTopHandToPosition(int position, boolean override){
		return topElevator.moveHandToPosition(position, override);
	}
	
	public boolean moveElevatorTopHandToPosition(int position){
		return topElevator.moveHandToPosition(position);
	}
	
	public void moveElevatorBottomHandUpOneIndex(){
		bottomElevator.moveHandUpOneIndex();
	}
	
	public void moveElevatorTopHandUpOneIndex(){
		topElevator.moveHandUpOneIndex();
	}
	
	public void moveElevatorTopHandDownOneIndex(){
		topElevator.moveHandDownOneIndex();
	}
	
	public boolean moveElevatorTopHandToIndex(int index){
		return topElevator.moveHandToIndex(index);
	}
	
	public void moveElevatorBottomHandToIndex(int index){
		bottomElevator.moveHandToIndex(index);
	}
	
	public void nudgeElevatorBottomHandPosition(int nudgeValue){
		bottomElevator.nudgeHandPosition(nudgeValue);
	}
	
	
	
	
	//permission to lift
	public void addCarryHeight(){
		bottomElevator.addCarryHeight();
	}
	
	public void removeCarryHeight(){
		bottomElevator.removeCarryHeight();
	}
	
	public void moveElevatorBottomHandUpOneIndexPlusCarryOffset(){
		bottomElevator.moveHandUpOneIndexPlusCarryOffset();
	}
	
	
	//setters
	
	public void setElevatorBottomHandManualControlSpeed(double speed){
		bottomElevator.setHandManualControlSpeed(speed);
	}
	
	public void setElevatorTopHandManualControlSpeed(double speed){
		topElevator.setHandManualControlSpeed(speed);
	}
	public void resetPositionBottomHand(int position){
		bottomElevator.resetPositionHand(position);
	}
	public void resetPositionBottomHand(){
		bottomElevator.resetPositionHand();
	}
	public void resetPositionTopHand(int position){
		topElevator.resetPositionHand(position);
	}
	public void resetPositionTopHand(){
		topElevator.resetPositionHand();
	}
	
	
	//zeroing
	public void raiseElevatorTopHandZeroing(){
		topElevator.raiseElevatorHandZeroing();
	}
	public void lowerElevatorBottomHandZeroing(){
		bottomElevator.lowerElevatorHandZeroing();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}