// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

//TODO: Add RPM functionality so that this command isn't exactly the same as VisionSpinTurret
public class VisionAimTurret extends CommandBase {

  Turret turret;
  Shooter shooter;
  Vision vision;
  NavX navX;

  double limelightTarget;
  double oldTargetPosition = 0;
  double oldNavXPosition = 0;
  double newTargetPosition = 0;
  double changeInNavx = 0;
  double oppositePosition = 0;

  /** Creates a new VisionAimTurret. */
  public VisionAimTurret(Turret sub_turret, Shooter sub_shooter, Vision sub_vision, NavX sub_navX) {
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
    oldTargetPosition = turret.getTurretAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelightTarget = -vision.limelight.getOffsetX() + turret.getTurretAngle();

    changeInNavx = navX.navx.getYaw() - oldNavXPosition;
    newTargetPosition = oldTargetPosition + changeInNavx;
    SmartDashboard.putNumber("newTargetPosition", newTargetPosition);
    SmartDashboard.putNumber("oldTargetPosition", oldTargetPosition);

    boolean isPressed = RobotContainer.coDriverStick.btn_A.get();

    if (isPressed && vision.limelight.hasTarget()) {
      turret.setTurretAngle(limelightTarget);
      oldNavXPosition = navX.navx.getYaw();
      oldTargetPosition = limelightTarget;
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
