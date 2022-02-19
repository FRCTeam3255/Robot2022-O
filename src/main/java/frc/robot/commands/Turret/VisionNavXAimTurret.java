// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class VisionNavXAimTurret extends CommandBase {
  Turret turret;
  Vision vision;
  NavX navx;

  double currentAngle;
  double lastAngle;

  /** Creates a new VisionNavXAimTurret. */
  public VisionNavXAimTurret(Turret a_turret, Vision a_vision, NavX a_navx) {
    turret = a_turret;
    vision = a_vision;
    navx = a_navx;

    currentAngle = 0;
    lastAngle = 0;

    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    vision.limelight.setLEDMode(LEDMode.on);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // currentAngle still has the value from the last loop
    lastAngle = currentAngle;

    if (vision.limelight.hasTarget()) {
      // just aim using limelight if it has a target
      currentAngle = vision.limelight.getOffsetX() + turret.getTurretAngle();
      turret.setTurretAngle(currentAngle);
    } else {
      // if the limelight doesn't have a target, then use the last angle
      // the navx lets you use lastAngle by giving an external point of reference
      // using the limelight, the limelight is the external point of reference
      turret.setTurretAngle(lastAngle - navx.navx.getYaw());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    vision.limelight.setLEDMode(LEDMode.off);
    turret.setTurretSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
