// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotPreferences;
import frc.robot.commands.Hood.ShallowHood;
import frc.robot.commands.Turret.SetTurretPosition;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PrepClimb extends SequentialCommandGroup {
  /** Creates a new PrepClimb. */
  public PrepClimb(Turret sub_turret, Hood sub_hood, Climber sub_climber) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        // Zero Turret
        new SetTurretPosition(sub_turret, RobotPreferences.zeroDoublePref),
        // Retract hood
        new ShallowHood(sub_hood),
        // Deploy stationary hooks
        new InstantCommand(sub_climber::hookUp, sub_climber),
        // Angle climber
        new InstantCommand(sub_climber::pivotAngled, sub_climber));
  }
}