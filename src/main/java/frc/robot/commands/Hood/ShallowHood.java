// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Hood;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hood;

public class ShallowHood extends InstantCommand {

  Hood hood;

  /** Creates a new ShallowHood. */
  public ShallowHood(Hood sub_hood) {
    hood = sub_hood;
  }

  @Override
  public void initialize() {
    hood.shallowHood();
  }
}
