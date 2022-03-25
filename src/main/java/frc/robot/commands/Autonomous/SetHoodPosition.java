// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import com.frcteam3255.preferences.SN_BooleanPreference;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hood;

public class SetHoodPosition extends InstantCommand {
  Hood hood;
  SN_BooleanPreference hoodSteep;

  /** Creates a new SetHoodPosition. */
  public SetHoodPosition(Hood sub_hood, SN_BooleanPreference a_hoodSteep) {
    hood = sub_hood;
    hoodSteep = a_hoodSteep;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (hoodSteep.getValue()) {
      hood.hoodZeroTilt();
    } else {
      hood.hoodHighTilt();
    }
  }
}