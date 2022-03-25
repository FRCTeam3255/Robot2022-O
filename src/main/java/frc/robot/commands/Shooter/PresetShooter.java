// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import com.frcteam3255.preferences.SN_BooleanPreference;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class PresetShooter extends InstantCommand {

  Shooter shooter;
  Hood hood;
  SN_DoublePreference shooterRPMUpper;
  SN_BooleanPreference hoodSteepUpper;
  SN_DoublePreference shooterRPMLower;
  SN_BooleanPreference hoodSteepLower;

  public PresetShooter(Shooter sub_shooter, Hood sub_hood, SN_DoublePreference a_shooterRPMUpper,
      SN_BooleanPreference a_hoodSteepUpper, SN_DoublePreference a_shooterRPMLower,
      SN_BooleanPreference a_hoodSteepLower) {

    shooter = sub_shooter;
    hood = sub_hood;

    shooterRPMUpper = a_shooterRPMUpper;
    hoodSteepUpper = a_hoodSteepUpper;
    shooterRPMLower = a_shooterRPMLower;
    hoodSteepLower = a_hoodSteepLower;

    // addRequirements(shooter, hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (shooter.isGoalHighHub()) {

      shooter.setGoalRPM(shooterRPMUpper.getValue());

      if (hoodSteepUpper.getValue()) {
        hood.hoodHighTilt();
      } else {
        hood.hoodZeroTilt();
      }
    } else {

      shooter.setGoalRPM(shooterRPMLower.getValue());

      if (hoodSteepLower.getValue()) {
        hood.hoodHighTilt();
      } else {
        hood.hoodZeroTilt();
      }

    }

  }
}
