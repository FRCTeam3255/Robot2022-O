// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import frc.robot.RobotPreferences;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class VisionSpinTurret extends CommandBase {

  Turret turret;
  Shooter shooter;
  Vision vision;
  NavX navX;

  double target;
  double oldTargetPosition = 0;
  double oldNavXPosition = 0;
  double newTargetPosition = 0;
  double changeInNavx = 0;
  double oppositePosition = 0;

  /** Creates a new VisionSpinTurret. */
  public VisionSpinTurret(Turret sub_turret, Shooter sub_shooter, Vision sub_vision, NavX sub_navX) {
    turret = sub_turret;
    shooter = sub_shooter;
    vision = sub_vision;
    navX = sub_navX;

    // Use addRequirements() here to declare subsystem dependencies.
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
    target = -vision.limelight.getOffsetX() + turret.getTurretAngle();

    changeInNavx = navX.navx.getYaw() - oldNavXPosition;
    newTargetPosition = oldTargetPosition + changeInNavx;

    if (vision.limelight.hasTarget()) {
      turret.setTurretAngle(target);
      oldNavXPosition = navX.navx.getYaw();
      oldTargetPosition = target;
    } else {
      if (newTargetPosition < RobotPreferences.TurretPrefs.turretMinAngleDegrees.getValue()) {
        oppositePosition = RobotPreferences.TurretPrefs.turretMinAngleDegrees.getValue() - newTargetPosition;
        turret.setTurretAngle(RobotPreferences.TurretPrefs.turretMaxAngleDegrees.getValue() - oppositePosition);
      } else if (newTargetPosition > RobotPreferences.TurretPrefs.turretMaxAngleDegrees.getValue()) {
        oppositePosition = newTargetPosition - RobotPreferences.TurretPrefs.turretMaxAngleDegrees.getValue();
        turret.setTurretAngle(RobotPreferences.TurretPrefs.turretMinAngleDegrees.getValue() + oppositePosition);
      } else {
        turret.setTurretAngle(newTargetPosition);
      }
    }
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
