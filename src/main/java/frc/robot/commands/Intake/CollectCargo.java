// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Transfer.TransferState;

import static frc.robot.RobotPreferences.*;

import com.frcteam3255.preferences.SN_DoublePreference;

public class CollectCargo extends CommandBase {
  Intake intake;
  Transfer transfer;

  // When a wrong colored ball comes into the robot, you can't just run in reverse
  // while you see the ball, because as soon as you don't see a ball anymore you
  // run the motor forward again and just recollect the same wrong colored ball.
  // So what we do is when we see a wrong colored ball we remember it for enough
  // time to make sure it's completely out of the robot. If we see a correct
  // colored ball, we know that we don't want to spit it out, so we stop the
  // entrance motor. But, the intake is further away from the color sensor, so the
  // time it will take for the ball to actually exit the robot and not just the
  // contact of the entrance, so we have a seperate, longer timer for the intake.

  int intakeRejectTimer;
  int transferRejectTimer;

  SN_DoublePreference outputIntakeSpeed;
  SN_DoublePreference outputEntranceSpeed;
  SN_DoublePreference outputBottomBeltSpeed;
  SN_DoublePreference outputTopBeltSpeed;

  boolean stateOverride;

  /** Creates a new CollectBall. */
  public CollectCargo(Intake sub_intake, Transfer sub_transfer) {
    intake = sub_intake;
    transfer = sub_transfer;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.deployIntake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    outputIntakeSpeed = IntakePrefs.intakeCollectSpeed;
    outputEntranceSpeed = TransferPrefs.transferEntranceSpeed;
    outputBottomBeltSpeed = TransferPrefs.transferBeltSpeed;
    outputTopBeltSpeed = TransferPrefs.transferBeltSpeed;

    if (transfer.isTopBallCollected()) {
      outputTopBeltSpeed = RobotPreferences.zeroDoublePref;
      // If top AND bottom are collected, turn everything else off as well
      if (transfer.isBottomBallCollected()) {
        outputIntakeSpeed = RobotPreferences.zeroDoublePref;
        outputEntranceSpeed = RobotPreferences.zeroDoublePref;
        outputBottomBeltSpeed = RobotPreferences.zeroDoublePref;
      }
    }

    // // If there is a ball
    // if (intake.isBallNearIntake()) {
    // // And it's the correct color
    // if (intake.ballColorMatchesAlliance()) {

    // // the entrance motor should run
    // transferRejectTimer = 0;

    // // but not the intake, since the incorrect color ball may still be there
    // intakeRejectTimer -= IntakePrefs.intakeRejectTimerLoops.getValue();

    // // but if it's the wrong color
    // } else {

    // // the intake should go in reverse
    // intakeRejectTimer = IntakePrefs.intakeRejectTimerLoops.getValue();

    // // and the entrance motor should also go in reverse
    // transferRejectTimer = TransferPrefs.transferRejectTimerLoops.getValue();
    // }
    // }

    // here we set the output speeds of the robot, and count down the timers

    // if (intakeRejectTimer > 0) {
    // outputIntakeSpeed = IntakePrefs.intakeRejectSpeed;
    // intakeRejectTimer--;
    // }
    // if (transferRejectTimer > 0) {
    // outputEntranceSpeed = TransferPrefs.transferEntranceRejectSpeed;
    // transferRejectTimer--;
    // }
    // If the Intake and/or the entrance timer is greater than zero then the
    // transfer state will show Shooting to be true
    switch (transfer.getTransferState()) {
      case SHOOTING:
        stateOverride = true;
        break;
      case PROCESSING:
        stateOverride = false;
        break;
      case OFF:
        transfer.setTransferState(TransferState.PROCESSING);
        stateOverride = false;
        break;
    }

    if (!stateOverride) {
      transfer.setEntranceBeltMotorSpeed(outputEntranceSpeed);
      transfer.setBottomBeltMotorSpeed(outputBottomBeltSpeed);
      transfer.setTopBeltMotorSpeed(outputTopBeltSpeed);
    }

    intake.setIntakeMotorSpeed(outputIntakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeMotorSpeed(RobotPreferences.zeroDoublePref);
    transfer.setEntranceBeltMotorSpeed(RobotPreferences.zeroDoublePref);
    transfer.setBottomBeltMotorSpeed(RobotPreferences.zeroDoublePref);
    transfer.setTopBeltMotorSpeed(RobotPreferences.zeroDoublePref);

    if (!stateOverride) {
      transfer.setTransferState(TransferState.OFF);
    }

    intakeRejectTimer = 0;
    transferRejectTimer = 0;

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
