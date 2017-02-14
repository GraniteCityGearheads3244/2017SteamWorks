package org.mayheminc.robot2015.autonomousroutines;

import org.mayheminc.robot2015.Robot;
import org.mayheminc.robot2015.commands.Delay;
import org.mayheminc.robot2015.commands.DriveForDistance;
import org.mayheminc.robot2015.commands.DriveForTime;
import org.mayheminc.robot2015.commands.LowerCanburglarLeft;
import org.mayheminc.robot2015.commands.LowerCanburglarRight;
import org.mayheminc.robot2015.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2015.commands.PrintToDriverStation;
import org.mayheminc.robot2015.commands.RaiseCanburglarLeft;
import org.mayheminc.robot2015.commands.RaiseCanburglarRight;
import org.mayheminc.robot2015.commands.RaiseClawLinkage;
import org.mayheminc.robot2015.commands.ZeroElevatorEncodersInAuto;
import org.mayheminc.robot2015.commands.ZeroGyro;
import org.mayheminc.robot2015.commands.TurnOnCompressor;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CanTheftAuto extends CommandGroup {

	public  CanTheftAuto() {
		// Perform needed initialization
		addParallel(new ZeroGyro());
		addSequential(new DriveForDistance(0.0, -0.7, 0.0, 200, 0.0));  // NECMP was -0.3 and 1750
		
		// start driving backward for half second slowly
		addParallel(new DriveForTime(0.0, -0.15, 0.0, 0.5, 0.0));

		addParallel(new LowerCanburglarLeft());
		addSequential(new LowerCanburglarRight());

		// wait for burglars to stop bouncing
		addSequential(new Delay(350));		// was 500 at NECMP; worked at 400
		addSequential(new ZeroElevatorEncodersInAuto(Robot.pole.topElevator.ELEVATOR_TOP_HAND_HIGH_ZERO_POSITION,
				 Robot.pole.bottomElevator.ELEVATOR_BOTTOM_HAND_ZERO_POSITION ));
        addSequential(new PrintAutonomousTimeRemaining());
		
		addSequential(new DriveForDistance(0.0, 0.6, 0.0, 16000, 0.0));
		
		// cans stolen; raise the canburglars and drive back to extract
        addSequential(new RaiseClawLinkage());
        addSequential(new RaiseCanburglarLeft());
        addSequential(new RaiseCanburglarRight());
		addSequential(new DriveForDistance(0.0, 0.4, 0.0, 4000, 0.0));
		
		addSequential(new TurnOnCompressor());

	}
}
