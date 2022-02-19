// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;

public class HoldTurretPosition extends CommandBase {

  Turret turret;
  NavX navx;
  double degrees;
  SN_DoublePreference degreesPref;

  double yawToHold;

  public HoldTurretPosition(Turret a_turret, NavX a_navx, SN_DoublePreference a_degrees) {
    turret = a_turret;
    navx = a_navx;
    degreesPref = a_degrees;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    degrees = degreesPref.getValue();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turret.setTurretAngle(-navx.navx.getYaw() - degrees);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.setTurretSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
