// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.TalonSRXDrive.commands;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc3244.TalonSRXDrive.PipelineX;
import org.usfirst.frc3244.TalonSRXDrive.Robot;
import org.usfirst.frc3244.TalonSRXDrive.subsystems.Drive;

/**
 *
 */
public class AutonomousCommand extends Command {
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX0 = 0.0;
	private double centY = 0.0;
	private Rect[] rectList = null;
	
	NetworkTable table;
	
	private final Object imgLock = new Object();
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public AutonomousCommand() {
    	
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	table = NetworkTable.getTable("Vision Data");
    	
    	 UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
 	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
 	    
 	    visionThread = new VisionThread(camera, new PipelineX(), pipelineL -> {
 	        if (!pipelineL.filterContoursOutput().isEmpty()) {
 	        	Rect[] r = new Rect[pipelineL.filterContoursOutput().size()];
 	        	for (int i = 0; i < pipelineL.filterContoursOutput().size(); i++) {
 	        		r[i] = Imgproc.boundingRect(pipelineL.filterContoursOutput().get(i));
 	            
 	        		synchronized (imgLock) {
 	        			rectList = new Rect[pipelineL.filterContoursOutput().size()];
 	        			rectList = r;
 	        			centerX0 = r[0].x + (r[0].width / 2);
 	        			
 	        		}
 	        	}
 	        }
 	    });
 	    visionThread.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	Rect[] rL = null;
    	double centerX;
    	
    	synchronized (imgLock) {
    		rL = new Rect[this.rectList.length];
    		rL = this.rectList;
    		centerX = this.centerX0;
    		double cY = this.centY;
    	}
    	table.putNumber("centY: ", centY);
    	table.putNumber("centerX0: ", centerX);
    	double rLx[] = new double[rL.length];
    	for (int i = 0; i < rL.length; i++) {
    		rLx[i] = rL[i].x;
    		table.putNumberArray("Rect rL",rLx);
    	}
    	double turn = centerX - (IMG_WIDTH / 2);
    	
    	//Robot.drive.mecanumDriveAutonomous(0, 0, turn * 0.005, 0);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	visionThread.interrupt();
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}