// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import com.frcteam3255.utils.SN_MotionProfile;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Drivetrain;

public class DriveMotionProfile extends CommandBase {
  Drivetrain drivetrain;
  String leftPath;
  String rightPath;
  SN_MotionProfile motionProfile;

  /** Creates a new DriveMotionProfile. */
  public DriveMotionProfile(Drivetrain a_drivetrain, String a_leftPath, String a_rightPath) {
    drivetrain = a_drivetrain;
    leftPath = a_leftPath;
    rightPath = a_rightPath;

    motionProfile = new SN_MotionProfile(leftPath, rightPath);
    motionProfile.reload();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // this is kinda just copied from 2020 code as SN_MotionProfile has no
    // documentation
    SN_MotionProfile.setSensorUnitsPerTick(RobotPreferences.DrivetrainPrefs.driveEncoderCountsPerFoot.getValue());
    motionProfile.reload();
    drivetrain.resetDrivetrainEncodersCount();
    drivetrain.startMotionProfile(motionProfile.pointsLeft, motionProfile.pointsRight);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.resetMotionProfile();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.isMotionProfileFinished();
  }
}
