// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;

public class CollectCargo extends CommandBase {
  Intake intake;
  Transfer transfer;

  int timer;

  /** Creates a new Collect. */
  public CollectCargo(Intake sub_intake, Transfer sub_transfer) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = sub_intake;
    transfer = sub_transfer;

    addRequirements(intake, transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Deploy the intake
    intake.deployIntake();
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // if the color sensor sees a non-alliance ball in prox, timer = transferRejectLatchTimeLoops
    if (!intake.ballColorMatchesAlliance() && intake.isProximity()) {
      timer = RobotPreferences.TransferPrefs.transferRejectLatchTimeLoops.getValue();
    } else if (intake.ballColorMatchesAlliance() && intake.isProximity()) {
      timer = 0;
    }

    // If the timer is set, it must be the wrong ball so reverse intake until timer is 0
    if (timer > 0) {
      transfer.setEntranceBeltMotorSpeed(-RobotPreferences.TransferPrefs.transferSpeed.getValue());
      timer--;
    } else {
      transfer.setEntranceBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
    }

    if (!transfer.isTopBallCollected()) {
      transfer.setTopBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
    }

    if (!transfer.isBottomBallCollected()) {
      transfer.setBottomBeltMotorSpeed(RobotPreferences.TransferPrefs.transferSpeed.getValue());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeMotorSpeed(0);
    transfer.setTopBeltMotorSpeed(0);
    transfer.setBottomBeltMotorSpeed(0);
    transfer.setEntranceBeltMotorSpeed(0);
    // Retract Intake
    intake.retractIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
