// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;
import frc.robot.RobotPreferences.TurretPrefs;

public class MoveTurret extends CommandBase {
  Turret turret;
  double speed;

  boolean isSpeedPositive;

  /** Creates a new MoveTurret. */
  public MoveTurret(Turret a_turret) {
    turret = a_turret;
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

    speed = RobotContainer.coDriverStick.getRightStickX();

    if (speed > 0) {
      isSpeedPositive = true;
    } else {
      isSpeedPositive = false;
    }

    speed = speed * speed;

    if (!isSpeedPositive) {
      speed = -speed;
    }

    turret.setTurretSpeed(speed * TurretPrefs.turretOpenLoopSpeed.getValue());
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
