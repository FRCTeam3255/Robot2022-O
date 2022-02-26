// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Hood;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Hood;

public class SteepenHood extends SequentialCommandGroup {

  Hood hood;

  /** Creates a new ShallowHood. */
  public SteepenHood(Hood sub_hood) {
    addCommands(new InstantCommand(sub_hood::steepenHood, sub_hood));
  }
}
