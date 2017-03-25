package org.usfirst.frc3244.HungryVonHippo.commands;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc3244.HungryVonHippo.GripPipelinePin;
import org.usfirst.frc3244.HungryVonHippo.Pipeline;
import org.usfirst.frc3244.HungryVonHippo.Robot;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class Locate_Pin extends Command {

	private double robot_Correction_Y = 0.0;
	private double kP = 0.003;
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private static final double LIFECAMVIEWANGLE = 68.5;
	private UsbCamera videoSource;
	private VisionThread visionThread;
	private Rect[] rectList = null;
	private int rectCount;
	
	NetworkTable table;
	
	private final Object imgLock = new Object();
	
    public Locate_Pin() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("Pin Vision Data");
    	Robot.vision_hardware.My_Right_Light(true);

    	videoSource = Robot.vision_hardware.camera_Right;
    	
	    visionThread = new VisionThread(videoSource, new Pipeline(), pipelneL -> {
	        if (!pipelneL.filterContoursOutput().isEmpty()) {
	        	Rect[] r = new Rect[pipelneL.filterContoursOutput().size()];
	        	int r_Count = pipelneL.filterContoursOutput().size();
	        	for (int i = 0; i < pipelneL.filterContoursOutput().size(); i++) {
	        		r[i] = Imgproc.boundingRect(pipelneL.filterContoursOutput().get(i));
	            
	        		synchronized (imgLock) {
	        			rectCount = r_Count;
	        			rectList = r;
	        		}
	        	}
	        }
	    });
	    visionThread.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Something to Store the Rectangles in
    	Rect[] rectLilst;
    	int rectCount;
    	double x;
    	//Get the list of Rectangles
    	synchronized (imgLock) {
    		rectCount = this.rectCount;
    		rectLilst = this.rectList;	
    	}
    
    	if(rectCount>0){
    		//Make a list of x and y
    		double recs_y[] = new double[rectCount];
    		double recs_x[] = new double[rectCount];
    		for (int i = 0; i < rectCount; i++) {
    			recs_y[i] = rectLilst[i].y;
    			recs_x[i] = rectLilst[i].x;
    		}
    	
    		//Put the list on the NetworkTable
    		table.putNumberArray("Rect y",recs_y);
    		table.putNumberArray("Rect x",recs_x);
    		
    	}else{
    		double recs_y[] = new double[1];
    		double recs_x[] = new double[1];
    		recs_y[0] = 0.0;
			recs_x[0] = 0.0;
			
			//Put the list on the NetworkTable
			table.putNumberArray("Rect y",recs_y);
			table.putNumberArray("Rect x",recs_x);
    	}
    	
    	
		
    	//lets find the two that line up horizontal
    	if(rectCount>1){
    		// A container to store the x alignments
    		double[] alinmentXscore = new double[rectLilst.length-1];
    		
    		for (int i = 0; i < rectCount-1; i++) {
    			alinmentXscore[i] = score_X_Alilnment(((rectLilst[i].x - rectLilst[i+1].x))+1);
    		}
    		
    		//Now Find the best score
    		double largest = 0;
    		int largestIndex = 0;
    		for(int i = 0; i < alinmentXscore.length;i++){
    			if(alinmentXscore[i]>largest){
    				largest = alinmentXscore[i];
    				largestIndex = i;
    			}
    		}
    		robot_Correction_Y = (rectLilst[largestIndex].x - (rectLilst[largestIndex].x - rectLilst[largestIndex+1].x)/2-(IMG_WIDTH/2));
    		
    		table.putNumber("robot_Correction_Y",robot_Correction_Y* kP);
    		x =-0.4;
    	}else{
    		//Only one target found can not track!
    		robot_Correction_Y = 0.0;
    		x=0.0;
    		table.putNumber("robot_Correction_Y",robot_Correction_Y);
    	}
//    	
//    	
//    	double robot_Correction_Y = 0;
    	Robot.drive.mecanumDriveAutoInTeleop(x, -robot_Correction_Y * kP, 0);
    }
    
    private double score_X_Alilnment(double val){
    	return 100-(100*Math.abs(1-val));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//visionThread.interrupt();
    	Robot.vision_hardware.My_Right_Light(false);
    	Robot.drive.mecanumDriveAutoInTeleopFinished();
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	
    }
}
