// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotPreferences.ClimberPrefs;
import frc.robot.subsystems.Climber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MagicClimb extends SequentialCommandGroup {
  Climber climber;

  /** Creates a new MagicClimb. */
  public MagicClimb(Climber sub_climber) {

    climber = sub_climber;

    addCommands(
        new SetClimberPosition(sub_climber, ClimberPrefs.climberUpPosition), // extend
        new SetClimberPosition(sub_climber, ClimberPrefs.climberDownPosition), // retract
        new InstantCommand(sub_climber::pivotAngled, sub_climber), // pivot angled
        new SetClimberPosition(sub_climber, ClimberPrefs.climberUpPosition), // extend
        new InstantCommand(sub_climber::pivotPerpendicular, sub_climber), // pivot perpendicular
        new SetClimberPosition(sub_climber, ClimberPrefs.climberDownPosition), // retract
        new InstantCommand(sub_climber::pivotAngled, sub_climber), // pivot angled
        new SetClimberPosition(sub_climber, ClimberPrefs.climberUpPosition), // extend
        new InstantCommand(sub_climber::pivotPerpendicular, sub_climber), // pivot perpendicular
        new SetClimberPosition(sub_climber, ClimberPrefs.climberDownPosition) // retract
    );
  }
}
