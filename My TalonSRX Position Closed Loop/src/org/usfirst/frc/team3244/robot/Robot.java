
package org.usfirst.frc.team3244.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	CANTalon _talon = new CANTalon(2);	
	//CANTalon _talonSlave = new CANTalon(1);
	Joystick _joy = new Joystick(0);	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	boolean _lastButton1 = false;
	/** save the target position to servo to */
	double targetPositionRotations;
	
	public void robotInit() {
		/* lets grab the 360 degree position of the MagEncoder's absolute position */
		int absolutePosition = _talon.getPulseWidthPosition() & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
        /* use the low level API to set the quad encoder signal */
        _talon.setEncPosition(absolutePosition);
        
        /* choose the sensor and sensor direction */
        _talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        _talon.reverseSensor(true);
        //_talon.configEncoderCodesPerRev(XXX), // if using FeedbackDevice.QuadEncoder
        //_talon.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        _talon.configNominalOutputVoltage(+0f, -0f);
        _talon.configPeakOutputVoltage(+3f, -3f);
        /* set the allowable closed-loop error,
         * Closed-Loop output will be neutral within this range.
         * See Table in Section 17.2.1 for native units per rotation. 
         */
        _talon.setAllowableClosedLoopErr(1); /* always servo */
        /* set closed loop gains in slot0 */
        _talon.setProfile(0);
        _talon.setF(0.0);
        _talon.setP(0.3);
        _talon.setI(0.0); 
        _talon.setD(0.0);   

        SmartDashboard.putNumber("Set Point", 0);
        
        /*
         * Set Ramp Rate
         */
        _talon.setVoltageRampRate(.1);
        
        //_talonSlave.changeControlMode(TalonControlMode.Follower);
        //_talonSlave.set(_talon.getDeviceID());
        //_talonSlave.reverseOutput(true);
	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	/* get gamepad axis */
    	double leftYstick = _joy.getAxis(AxisType.kY);
    	double motorOutput = _talon.getOutputVoltage() / _talon.getBusVoltage();
    	boolean button1 = _joy.getRawButton(1);
    	boolean button2 = _joy.getRawButton(2);
    	boolean button7 = _joy.getRawButton(7);
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tpos:");
        _sb.append(_talon.getPosition() );
        /* on button1 press enter closed-loop mode on target position */
        if(!_lastButton1 && button1) {
        	/* Position mode - button just pressed */
        	targetPositionRotations =  SmartDashboard.getNumber("Set Point");//leftYstick * 50.0; /* 50 Rotations in either direction */
        	_talon.changeControlMode(TalonControlMode.Position);
        	_talon.set(targetPositionRotations); /* 50 rotations in either direction */

        }
        /* on button2 just straight drive */
        if(button2) {
        	/* Percent voltage mode */
        	_talon.changeControlMode(TalonControlMode.PercentVbus);
        	_talon.set(leftYstick);
        }
        if(button7) {
        	/* Percent voltage mode */
        	_talon.setEncPosition(0);
        }
        /* if Talon is in position closed-loop, print some more info */
        if( _talon.getControlMode() == TalonControlMode.Position) {
        	/* append more signals to print when in speed mode. */
        	_sb.append("\terrNative:");
        	_sb.append(_talon.getClosedLoopError());
        	_sb.append("\ttrg:");
        	_sb.append(targetPositionRotations);
        }
        /* print every ten loops, printing too much too fast is generally bad for performance */ 
        if(++_loops >= 50) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        _sb.setLength(0);
        /* save button state for on press detect */
        _lastButton1 = button1;
    }
}
