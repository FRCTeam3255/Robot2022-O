// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences.DrivetrainPrefs;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {

  Drivetrain drivetrain;
  SN_DoublePreference inchesToDrive;
  SN_DoublePreference peakPercentOutput;

  int loopsWithAcceptableError;

  /** Creates a new DriveDistance. */
  public DriveDistance(Drivetrain sub_drivetrain, SN_DoublePreference a_inchsToDrive,
      SN_DoublePreference a_peakPercentOutput) {
    drivetrain = sub_drivetrain;
    inchesToDrive = a_inchsToDrive;
    peakPercentOutput = a_peakPercentOutput;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.driveDistance(inchesToDrive, peakPercentOutput);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (drivetrain.getAverageClosedLoopErrorInches() <= DrivetrainPrefs.driveAllowableClosedLoopErrorInches.getValue()) {
      loopsWithAcceptableError++;
    } else {
      loopsWithAcceptableError = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return loopsWithAcceptableError > DrivetrainPrefs.driveLoopsToFinish.getValue();
  }
}
