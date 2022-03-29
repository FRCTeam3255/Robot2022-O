// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences.ShooterPrefs;
import frc.robot.subsystems.Shooter;

public class SetShooterRPM extends CommandBase {
  Shooter shooter;
  SN_DoublePreference RPM;

  /** Creates a new SetShooterGoalRPM. */
  public SetShooterRPM(Shooter sub_shooter, SN_DoublePreference a_RPM) {
    shooter = sub_shooter;
    RPM = a_RPM;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterRPM(RPM.getValue() * ShooterPrefs.shooterGoalRPMMultiplier.getValue());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
