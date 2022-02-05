// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;

public class PushCargoToShooter extends CommandBase {
  Shooter shooter;
  Transfer transfer;

  /** Creates a new ShootCargoIf. */
  public PushCargoToShooter(Shooter sub_shooter, Transfer sub_transfer) {
    // Use addRequirements() here to declare subsystem dependencies.

    shooter = sub_shooter;
    transfer = sub_transfer;

    addRequirements(transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooter.getShooterVelocity() >= RobotPreferences.ShooterPrefs.shooterMotorTargetVelocity.getValue()) {
      // transfer.setTransferMotorSpeed(RobotPreferences.TransferPrefs.transferMotorSpeed);
      transfer.setBottomBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
      transfer.setTopBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
    } else {
      transfer.setBottomBeltMotorSpeed(0);
      transfer.setTopBeltMotorSpeed(0);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    transfer.setTopBeltMotorSpeed(0);
    transfer.setBottomBeltMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
