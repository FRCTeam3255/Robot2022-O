// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Transfer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences.TransferPrefs;
import frc.robot.subsystems.Transfer;
import static frc.robot.RobotPreferences.*;

public class ReverseTransfer extends CommandBase {
  Transfer transfer;

  /** Creates a new ReverseTransfer. */
  public ReverseTransfer(Transfer sub_transfer) {
    transfer = sub_transfer;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    transfer.setTopBeltMotorSpeed(TransferPrefs.transferBeltRejectSpeed);
    transfer.setBottomBeltMotorSpeed(TransferPrefs.transferBeltRejectSpeed);
    transfer.setEntranceBeltMotorSpeed(TransferPrefs.transferEntranceRejectSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    transfer.setTopBeltMotorSpeed(zeroDoublePref);
    transfer.setBottomBeltMotorSpeed(zeroDoublePref);
    transfer.setEntranceBeltMotorSpeed(zeroDoublePref);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
