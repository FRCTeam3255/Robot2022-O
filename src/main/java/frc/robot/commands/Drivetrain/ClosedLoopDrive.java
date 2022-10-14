// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences.DrivetrainPrefs;
import frc.robot.subsystems.Drivetrain;

public class ClosedLoopDrive extends CommandBase {
  Drivetrain drivetrain;

  double speed;
  double turn;

  double previousSpeed;
  boolean isSpeedPositive;
  boolean isSpeedIncreasing;

  /** Creates a new ClosedLoopDrive. */
  public ClosedLoopDrive(Drivetrain sub_drivetrain) {
    drivetrain = sub_drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    previousSpeed = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Read direction of the joystick
    speed = RobotContainer.DriverStick.getArcadeMove();

    if (speed > 0) {
      isSpeedPositive = true;
    } else {
      isSpeedPositive = false;
    }

    if (speed - previousSpeed > 0) {
      isSpeedIncreasing = true;
    } else {
      isSpeedIncreasing = false;
    }

    if (DrivetrainPrefs.driveSquaredInputs.getValue()) {
      speed = speed * speed;
      if (!isSpeedPositive) {
        speed = -speed;
      }
    }

    if (isSpeedIncreasing) {
      speed = drivetrain.posSlewRateLimiter.calculate(speed);
    } else {
      speed = drivetrain.negSlewRateLimiter.calculate(speed);
    }

    turn = RobotContainer.DriverStick.getArcadeRotate();

    // Set motors to direction to joystick
    drivetrain.closedLoopArcadeDrive(speed, turn);

    previousSpeed = speed;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
