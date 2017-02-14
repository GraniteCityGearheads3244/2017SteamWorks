package org.usfirst.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class MyDualButton extends Button {

	private final GenericHID m_joystick;
	  private final int m_buttonNumber;
	  private final int m_buttonNumber2;
	  
	  /**
	   * Create a joystick button for triggering commands.
	   *
	   * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
	   *                     etc)
	   * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
	   */
	  public MyDualButton(GenericHID joystick, int buttonNumber, int buttonNumber2) {
	    m_joystick = joystick;
	    m_buttonNumber = buttonNumber;
	    m_buttonNumber2 = buttonNumber2;
	  }

	  /**
	   * Gets the value of the joystick button.
	   *
	   * @return The value of the joystick button
	   */
	  public boolean get() {
	    return m_joystick.getRawButton(m_buttonNumber) & m_joystick.getRawButton(m_buttonNumber2);
	  }

}
