// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  // Creates Variables for Drivetrain
  private TalonFX leftLeadMotor;
  private TalonFX rightLeadMotor;
  private TalonFX leftFollowMotor;
  private TalonFX rightFollowMotor;

  private TalonFXConfiguration config;

  // Initializes Variables for Drivetrain
  public Drivetrain() {
    leftLeadMotor = new TalonFX(RobotMap.DrivetrainMap.LEFT_LEAD_MOTOR_CAN);
    rightLeadMotor = new TalonFX(RobotMap.DrivetrainMap.RIGHT_LEAD_MOTOR_CAN);
    leftFollowMotor = new TalonFX(RobotMap.DrivetrainMap.LEFT_FOLLOW_MOTOR_CAN);
    rightFollowMotor = new TalonFX(RobotMap.DrivetrainMap.RIGHT_FOLLOW_MOTOR_CAN);

    config = new TalonFXConfiguration();

    configure();
  }

  // Sets Drivetrain Variable's Default Settings
  public void configure() {

    config.slot0.allowableClosedloopError = RobotPreferences.DrivetrainPrefs.driveAllowableClosedLoopError.getValue();
    config.slot0.closedLoopPeakOutput = RobotPreferences.DrivetrainPrefs.driveClosedLoopPeakOutput.getValue();
    config.slot0.kF = RobotPreferences.DrivetrainPrefs.driveF.getValue();
    config.slot0.kP = RobotPreferences.DrivetrainPrefs.driveP.getValue();
    config.slot0.kI = RobotPreferences.DrivetrainPrefs.driveI.getValue();
    config.slot0.kD = RobotPreferences.DrivetrainPrefs.driveD.getValue();

    // Left
    leftLeadMotor.configFactoryDefault();
    leftFollowMotor.configFactoryDefault();
    leftLeadMotor.configAllSettings(config);
    leftFollowMotor.configAllSettings(config);
    leftLeadMotor.setInverted(true);
    leftFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    leftLeadMotor.setSensorPhase(true);
    leftFollowMotor.setNeutralMode(NeutralMode.Brake);
    leftLeadMotor.setNeutralMode(NeutralMode.Brake);

    leftFollowMotor.follow(leftLeadMotor);

    // Right
    rightLeadMotor.configFactoryDefault();
    rightFollowMotor.configFactoryDefault();
    rightLeadMotor.configAllSettings(config);
    rightFollowMotor.configAllSettings(config);
    rightLeadMotor.setInverted(false);
    rightFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    rightLeadMotor.setSensorPhase(false);
    rightFollowMotor.setNeutralMode(NeutralMode.Brake);
    rightLeadMotor.setNeutralMode(NeutralMode.Brake);
    rightFollowMotor.follow(rightLeadMotor);

  }

  public void resetDrivetrainEncodersCount() {
    leftLeadMotor.setSelectedSensorPosition(0);
    rightLeadMotor.setSelectedSensorPosition(0);
  }

  public double getLeftEncoderCount() {
    return leftLeadMotor.getSelectedSensorPosition();
  }

  public double getRightEncoderCount() {
    return rightLeadMotor.getSelectedSensorPosition();
  }

  // Method controls Drivetrain Motor speeds
  public void arcadeDrive(double a_speed, double a_turn) {
    double speed = a_speed * RobotPreferences.DrivetrainPrefs.arcadeSpeed.getValue();
    double turn = a_turn * RobotPreferences.DrivetrainPrefs.arcadeTurn.getValue();

    leftLeadMotor.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, turn);
    rightLeadMotor.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -turn);
  }

  // starts motion profile using seperate left and right trajectories, and ctre
  // ControlMode.MotionProfile
  public void startMotionProfile(BufferedTrajectoryPointStream pointsLeft, BufferedTrajectoryPointStream pointsRight) {
    configure();
    resetDrivetrainEncodersCount();

    leftLeadMotor.startMotionProfile(pointsLeft,
        RobotPreferences.DrivetrainPrefs.motionProfileMinBufferedPoints.getValue(), ControlMode.MotionProfile);
    rightLeadMotor.startMotionProfile(pointsRight,
        RobotPreferences.DrivetrainPrefs.motionProfileMinBufferedPoints.getValue(), ControlMode.MotionProfile);
  }

  public boolean isMotionProfileFinished() {
    return leftLeadMotor.isMotionProfileFinished() && rightLeadMotor.isMotionProfileFinished();
  }

  public void resetMotionProfile() {
    rightLeadMotor.set(ControlMode.PercentOutput, 0.0);
    leftLeadMotor.set(ControlMode.PercentOutput, 0.0);
    rightLeadMotor.clearMotionProfileTrajectories();
    leftLeadMotor.clearMotionProfileTrajectories();
    resetDrivetrainEncodersCount();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Drivetrain Left Encoder", getLeftEncoderCount());
    SmartDashboard.putNumber("Drivetrain Right Encoder", getRightEncoderCount());
    SmartDashboard.putBoolean("Drivetrain Motion Profile Finished", isMotionProfileFinished());
    SmartDashboard.putNumber("DrivetrainLeftLeadMotorSpeed", leftLeadMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("DrivetrainRightLeadMotorSpeed", rightLeadMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("DrivetrainLeftFollowMotorSpeed", leftFollowMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("DrivetrainRightFollowMotorSpeed", rightFollowMotor.getMotorOutputPercent());

  }

}