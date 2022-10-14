// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class SetTurretAngle extends CommandBase {

  Turret turret;
  SN_DoublePreference angle;

  /** Creates a new SetTurretAngle. */
  public SetTurretAngle(Turret sub_turret, SN_DoublePreference a_angle) {
    turret = sub_turret;
    angle = a_angle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turret.setTurretAngle(angle.getValue());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
