// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import com.frcteam3255.preferences.SN_BooleanPreference;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class PresetShooter extends InstantCommand {

  Shooter shooter;
  Hood hood;
  SN_DoublePreference shooterRPM;
  SN_BooleanPreference hoodSteep;

  public PresetShooter(Shooter sub_shooter, Hood sub_hood, SN_DoublePreference a_shooterRPM,
      SN_BooleanPreference a_hoodSteep) {

    shooter = sub_shooter;
    hood = sub_hood;

    shooterRPM = a_shooterRPM;
    hoodSteep = a_hoodSteep;

    addRequirements(shooter, hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    shooter.setGoalRPM(shooterRPM.getValue());

    if (hoodSteep.getValue()) {
      hood.steepenHood();
    } else {
      hood.shallowHood();
    }

  }
}
