// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.RobotContainer;

public class ManualRotateTurret extends CommandBase {
  Turret turret;

  /** Creates a new ManualRotate. */
  public ManualRotateTurret(Turret sub_turret) {
    // Use addRequirements() here to declare subsystem dependencies.
    turret = sub_turret;
    addRequirements(sub_turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Direction that turret rotates depends on codriverstick rightstick input
    double rotate = RobotContainer.coDriverStick.getRightStickX();

    turret.setTurretSpeed(rotate);
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
