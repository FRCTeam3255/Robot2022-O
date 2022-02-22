// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Transfer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;

public class PushCargoToShooter extends CommandBase {
  // Creates Shooter and Transfer Variables in PushCargotoShooter
  Shooter shooter;
  Transfer transfer;

  /** Creates a new ShootCargoIf. */
  public PushCargoToShooter(Shooter sub_shooter, Transfer sub_transfer) {
    // Use addRequirements() here to declare subsystem dependencies.
    // Initializes PushCargoToShooter Variables
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
    // If the Shooter speed is greater than or equal to the Targeted velocity then
    // Transfer BeltMotors turn on
    if (shooter.getShooterVelocity() >= RobotPreferences.ShooterPrefs.shooterMotorTargetVelocity.getValue()) {
      // transfer.setTransferMotorSpeed(RobotPreferences.TransferPrefs.transferMotorSpeed);
      transfer.setBottomBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
      transfer.setTopBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());

      // If the Shooter speed is less than the targeted velocity then Transfer
      // beltMotors Stop
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
