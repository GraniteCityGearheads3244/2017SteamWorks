package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.AlignToTote;
import org.mayheminc.robot2015.commands.CoarseAlignToTote;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.MoveElevatorBottomHandToPosition;
import org.mayheminc.robot2015.commands.MoveElevatorTopHandToPosition;
import org.mayheminc.robot2015.commands.RaiseClawLinkage;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start just outside the landfill infront of either the left or right set of 2 totes.
 * Align, pickup, Align pick up, back up, strafe left/right, align, pickup, align, pickup
 * backup to autozone.
 */
public class LandfillHarvest4Totes extends CommandGroup {
	
	public static final String LEFT_TO_RIGHT = "LeftToRight";
	public static final String RIGHT_TO_LEFT = "RightToLeft";
	       		
	public  LandfillHarvest4Totes(String leftToRight) {
		super("LandFill4Totes" + leftToRight);
		
        // Perform needed initialization
        addParallel(new ZeroGyro());
        addSequential(new ZeroElevatorEncodersInAuto(	Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
        												Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        // move the RC hand out of the way and move the bottom hand to the correct height
        addParallel(new MoveElevatorTopHandToPosition(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HEIGHT_MAX));
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_1, true));
        
        // align to tote #1
        addSequential(new AlignToTote(7.0));
        // pickup tote #1
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2+Robot.pole.bottomElevator.CARRY_OFFSET, true));

        // find tote #2
        addSequential(new CoarseAlignToTote(2.0));
        // lower tote #1 onto tote #2
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_2, true));
        // align to tote #2
        addSequential(new AlignToTote(5.0));
        // pickup tote #2
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3+Robot.pole.bottomElevator.CARRY_OFFSET, true));
        
        // backup to clear the totes to the side
        addParallel(new RaiseClawLinkage());
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 6000, 0.0));
        
        // strafe left or right, depending upon program selection
        if (leftToRight == LEFT_TO_RIGHT) {
        	addSequential(new DriveForDistance(0.4, 0.0, 0.0, 20000, 0.0));        	
        } else {
        	addSequential(new DriveForDistance(-0.4, 0.0, 0.0, 20000, 0.0));  
        }

        // find tote #3
        addSequential(new CoarseAlignToTote(2.0));
        // lower tote #2 onto tote #3
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_3, true));
        // align to tote #3
        addSequential(new AlignToTote(5.0));
        // pickup tote #3
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4+Robot.pole.bottomElevator.CARRY_OFFSET, true));
        addSequential(new Delay(100));

        // find tote #4
        addSequential(new CoarseAlignToTote(2.0));
        // lower tote #3 onto tote #4
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_BELOW_TOTE_4, true));
        // align to tote #4
        addSequential(new AlignToTote(5.0));
        // pickup tote #4
        addSequential(new MoveElevatorBottomHandToPosition(Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ABOVE_TOTE_4, true));
        addSequential(new Delay(100));
        
        // backup to clear the totes and return to the auto zone
        addSequential(new DriveForDistance(0.0, -0.40, 0.0, 15000, 0.0));
    }
}
