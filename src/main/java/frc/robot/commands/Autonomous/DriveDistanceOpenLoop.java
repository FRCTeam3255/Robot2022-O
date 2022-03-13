// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveDistanceOpenLoop extends CommandBase {

  Drivetrain drivetrain;
  SN_DoublePreference encoderCounts;
  SN_DoublePreference speed;

  /** Creates a new OpenLoopDrive. */
  public DriveDistanceOpenLoop(Drivetrain sub_drivetrain, SN_DoublePreference a_encoderCounts,
      SN_DoublePreference a_speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = sub_drivetrain;
    encoderCounts = a_encoderCounts;
    speed = a_speed;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetDrivetrainEncodersCount();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(speed.getValue(), 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0, 0);
    drivetrain.resetDrivetrainEncodersCount();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getAverageEncoderCount()) > Math.abs(encoderCounts.getValue());
  }
}
