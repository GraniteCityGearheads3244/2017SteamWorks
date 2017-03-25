package org.usfirst.frc3244.HungryVonHippo.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;


/**
 *
 */
public class IOButtonOrButton extends Button {

	private DigitalInput button1;
	private DigitalInput button2;
	
	public IOButtonOrButton(DigitalInput digitalInput1,DigitalInput digitalInput2){
		button1 = digitalInput1;
		button2 = digitalInput2;
	}
    public boolean get() {
        return !button1.get()||!button2.get();
    }
}
