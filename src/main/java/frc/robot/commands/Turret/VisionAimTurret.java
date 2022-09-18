// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class VisionAimTurret extends CommandBase {

  Turret turret;
  Shooter shooter;
  Vision vision;

  double target;

  /** Creates a new VisionAimTurret. */
  public VisionAimTurret(Turret sub_turret, Shooter sub_shooter, Vision sub_vision) {
    turret = sub_turret;
    shooter = sub_shooter;
    vision = sub_vision;
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

    if (vision.limelight.hasTarget()) {
      turret.setTurretAngle(target);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.setTurretSpeed(0);
    // vision.limelight.setLEDMode(LEDMode.off);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
