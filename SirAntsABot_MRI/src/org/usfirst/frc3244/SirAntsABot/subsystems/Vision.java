package org.usfirst.frc3244.SirAntsABot.subsystems;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.Drive.Drive_Gyro_FullReset;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {
    
	private int image_Width = RobotMap.CAMERA_IMAGE_WIDTH;
    private int image_Height = RobotMap.CAMERA_IMAGE_HEIGHT;
    public final double light_Settle_Time = 1.0;
	private final Relay camera_Light = RobotMap.cameraLight;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void my_CameraLight_Toggle(){
    	if (camera_Light.get()==Value.kOff){
    		camera_Light.set(Value.kForward);
    	}else{
    		camera_Light.set(Value.kOff);
    	}
    }
    
    /**
     * Control the Light On or off
     * @param Boolean true to turn light on, false to turn it off
     */
    public void my_CameraLight_Switch(boolean on){
    	if(on){
    		camera_Light.set(Value.kForward);
    	}else{
    		camera_Light.set(Value.kOff);
    	}
    	
    }

    
    public double my_Get_Xcenter_PID(){
    	double[] x = Robot.grip.getNumberArray("myContoursReport/centerX", new double[0]);
 
    	double errorX;
    	
    	if(x.length>0){
    		double centerX = x[0];
    		errorX = (centerX-(image_Width/2));
    	}else{
    		double centerX = image_Width/2;
    		errorX = (centerX-(image_Width/2));
    	}
        return errorX;
    }
    
    public double my_Get_Ycenter_PID(){
    	double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
    	double errorY;
    	if(y.length>0){
    		double centerY = y[0];
    		errorY = (centerY-(image_Height/2));
    	}else{
    		double centerY = image_Width/2;
    		errorY = (centerY-(image_Height/2));
    	}
    	
        return errorY;
    }
    
	public boolean my_Target_Found() {
		double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
    	if(y.length>0){
    		return true;
    	}else{
		return false;
    	}
	}

	public double my_GetFinalWristAngle(double w_setpoint) {
		double[] width = Robot.grip.getNumberArray("myContoursReport/width", new double[0]);
		if(width.length>0){
			if(width[0]>120){
				System.out.println("width >120");
				return 70;
			}else if (width[0]>100) {
				System.out.println("width >100");
				return 70;	
			}else if (width[0]>85) {
				System.out.println("width >85");
				return 70;
			}else{
				System.out.println("width >xxx Get Closer");
				return 0;	
			}
		}
		return 0;
	}
    
	
	public double my_Get_Xcenter_REAL_PID(){
    	double[] x = Robot.grip.getNumberArray("myContoursReport/centerX", new double[0]);
    	double errorX;
    	int i =0;
    	
    	if(x.length>0){
    		double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
    		
    		if(y.length>0){//Find Target
    			i = my_Find_Target(y);
    		}
    		
    		double centerX = x[i];
    		errorX = ((image_Width/2)-centerX);
    	}else{
    		
    		errorX = 0;
    	}
    	//Trap negative error
    	if(errorX < -50){
    		errorX=errorX;
    	}
    	if(errorX > 50){
    		errorX=errorX;
    	}
    	if(errorX<10&&errorX>-10){
    		errorX=0;
    	}
    	
       
        return errorX;
    }
    
	 public double my_Get_Ycenter_REAL_PID(){
		 int i =0;
	    	double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
	    	
	    	double errorY;
	    	if(y.length>0){
	    		i = my_Find_Target(y);
	
	    		double centerY = y[i];
	    		errorY = ((image_Height/2)-centerY);
	    	}else{
	    		errorY = 0;//(centerY-(image_Height/2));
	    	}
	    	//Trap negative error
	    	if(errorY<-20){
	    		errorY=-20;
	    	}
	    	if(errorY>15){
	    		errorY=15;
	    	}
	    	//System.out.println("errorY= "+errorY);
	        return errorY;
	    }
	 private int my_Find_Target(double[] t){
		 for (int counter = 0; counter < t.length; counter++) {
				if (t[counter]<150) {
					return counter;
				}
			}
		return 0;
	 }
	 public double my_Get_Xcenter(){
	    	double[] x = Robot.grip.getNumberArray("myContoursReport/centerX", new double[0]);
	    	double centerX = -1;
	    	int i =0;
	    	
	    	if(x.length>0){
	    		if(x.length<2){
		    		double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
		    		
		    		if(y.length>0){//Find Target
		    			
		    			i = my_Find_Target(y);
		    		
		    		}
		    		
		    		centerX = x[i];
	    		}else{
	    			System.out.println("To Many Targets");
	    			centerX = -1;
	    		}
	    	}else{
	    		System.out.println("No Target");
	    		centerX = -1;
	    	}
	        return centerX;
	    }
	 
	 public double my_SpinToTarget_Lookup(double CenterTarget){
		 double VIEW_ANGLE = 47;
		 double CENTER = image_Width/2;
		 if (Robot.DEBUG){
			 System.out.println("CenterTarget = " + CenterTarget);
			 System.out.println("VIEW_ANGLE = " + VIEW_ANGLE);
			 System.out.println("CENTER = " + CENTER);
		 }
		 if(CenterTarget>0 && CenterTarget<image_Width){
			 return (VIEW_ANGLE/2)*((CENTER-CenterTarget)/CENTER);
		 }else{
			 return 0;
		 }
	 }
	 
	 public void my_Vision_Heartbeat(){
		 double[] zero = {0,0,0,0,0};
		 Robot.grip.putNumberArray("myContoursReport/area", zero);
		 
	 }
}

