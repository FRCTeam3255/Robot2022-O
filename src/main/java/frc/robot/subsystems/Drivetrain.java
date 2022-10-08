// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.utils.SN_Math;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;
import frc.robot.RobotMap.*;
import frc.robot.RobotPreferences.*;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  // Creates Variables for Drivetrain
  private TalonFX leftLeadMotor;
  private TalonFX rightLeadMotor;
  private TalonFX leftFollowMotor;
  private TalonFX rightFollowMotor;

  private TalonFXConfiguration config;

  public SlewRateLimiter posSlewRateLimiter;
  public SlewRateLimiter negSlewRateLimiter;

  AHRS navx = new AHRS();

  DifferentialDriveOdometry odometry;
  Field2d field = new Field2d();

  String fenderTo1Then2JSON = "pathplanner/generatedJSON/fenderTo1Then2.wpilib.json";
  String ball2ToTerminalJSON = "pathplanner/generatedJSON/2ToTerminal.wpilib.json";
  String terminalTo2JSON = "pathplanner/generatedJSON/TerminalTo2.wpilib.json";

  public Trajectory fenderTo1Then2Traj = new Trajectory();
  public Trajectory ball2ToTerminalTraj = new Trajectory();
  public Trajectory terminalTo2Traj = new Trajectory();

  // Initializes Variables for Drivetrain
  public Drivetrain() {
    leftLeadMotor = new TalonFX(DrivetrainMap.LEFT_LEAD_MOTOR_CAN);
    rightLeadMotor = new TalonFX(DrivetrainMap.RIGHT_LEAD_MOTOR_CAN);
    leftFollowMotor = new TalonFX(DrivetrainMap.LEFT_FOLLOW_MOTOR_CAN);
    rightFollowMotor = new TalonFX(DrivetrainMap.RIGHT_FOLLOW_MOTOR_CAN);

    config = new TalonFXConfiguration();

    posSlewRateLimiter = new SlewRateLimiter(DrivetrainPrefs.drivePosSlewRateLimit.getValue());
    negSlewRateLimiter = new SlewRateLimiter(DrivetrainPrefs.driveNegSlewRateLimit.getValue());

    configure();
    initializeTrajectories();
  }

  // Sets Drivetrain Variable's Default Settings
  public void configure() {

    config.slot0.closedLoopPeakOutput = DrivetrainPrefs.driveClosedLoopPeakOutput.getValue();
    config.slot0.kF = DrivetrainPrefs.driveF.getValue();
    config.slot0.kP = DrivetrainPrefs.driveP.getValue();
    config.slot0.kI = DrivetrainPrefs.driveI.getValue();
    config.slot0.kD = DrivetrainPrefs.driveD.getValue();

    // Left
    leftLeadMotor.configFactoryDefault();
    leftFollowMotor.configFactoryDefault();
    leftLeadMotor.configAllSettings(config);
    leftFollowMotor.configAllSettings(config);
    leftLeadMotor.setInverted(true);
    leftFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    leftLeadMotor.setSensorPhase(true);

    if (DrivetrainPrefs.driveBreakMode.getValue()) {
      leftFollowMotor.setNeutralMode(NeutralMode.Brake);
      leftLeadMotor.setNeutralMode(NeutralMode.Brake);
    } else {
      leftFollowMotor.setNeutralMode(NeutralMode.Coast);
      leftLeadMotor.setNeutralMode(NeutralMode.Coast);
    }

    leftFollowMotor.follow(leftLeadMotor);

    // Right
    rightLeadMotor.configFactoryDefault();
    rightFollowMotor.configFactoryDefault();
    rightLeadMotor.configAllSettings(config);
    rightFollowMotor.configAllSettings(config);
    rightLeadMotor.setInverted(false);
    rightFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    rightLeadMotor.setSensorPhase(false);
    if (DrivetrainPrefs.driveBreakMode.getValue()) {
      rightFollowMotor.setNeutralMode(NeutralMode.Brake);
      rightLeadMotor.setNeutralMode(NeutralMode.Brake);
    } else {
      rightFollowMotor.setNeutralMode(NeutralMode.Coast);
      rightLeadMotor.setNeutralMode(NeutralMode.Coast);
    }
    rightFollowMotor.follow(rightLeadMotor);

    posSlewRateLimiter = new SlewRateLimiter(DrivetrainPrefs.drivePosSlewRateLimit.getValue());
    negSlewRateLimiter = new SlewRateLimiter(DrivetrainPrefs.driveNegSlewRateLimit.getValue());

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

  public double getAverageEncoderCount() {
    return (getLeftEncoderCount() + getRightEncoderCount()) / 2;
  }

  public double getLeftFeetDriven() {
    return getLeftEncoderCount() / DrivetrainPrefs.driveEncoderCountsPerFoot.getValue();
  }

  public double getRightFeetDriven() {
    return getRightEncoderCount() / DrivetrainPrefs.driveEncoderCountsPerFoot.getValue();
  }

  public double getAverageFeetDriven() {
    return getAverageEncoderCount() / DrivetrainPrefs.driveEncoderCountsPerFoot.getValue();
  }

  public double getLeftVelocity() {
    return leftLeadMotor.getSelectedSensorVelocity();
  }

  public double getRightVelocity() {
    return rightLeadMotor.getSelectedSensorVelocity();
  }

  public double getAverageVelocity() {
    return (getLeftVelocity() + getRightVelocity()) / 2;
  }

  public double getLeftFeetPerSecond() {
    double fps = getLeftVelocity(); // encoder counts per 100ms
    fps *= 10; // counts per second
    fps /= DrivetrainPrefs.driveEncoderCountsPerFoot.getValue(); // feet per second
    return fps;
  }

  public double getRightFeetPerSecond() {
    double fps = getRightVelocity(); // encoder counts per 100ms
    fps *= 10; // counts per second
    fps /= DrivetrainPrefs.driveEncoderCountsPerFoot.getValue(); // feet per second
    return fps;
  }

  public double getAverageFeetPerSecond() {
    double fps = getAverageVelocity(); // encoder counts per 100ms
    fps *= 10; // counts per second
    fps /= DrivetrainPrefs.driveEncoderCountsPerFoot.getValue(); // feet per second
    return fps;
  }

  public double getLeftMPS() {
    return SN_Math.falconToMPS(getLeftVelocity(),
        Units.inchesToMeters(DrivetrainPrefs.driveWheelCircumference.getValue()),
        DrivetrainPrefs.driveGearRatio.getValue());
  }

  public double getRightMPS() {
    return SN_Math.falconToMPS(getRightVelocity(),
        Units.inchesToMeters(DrivetrainPrefs.driveWheelCircumference.getValue()),
        DrivetrainPrefs.driveGearRatio.getValue());
  }

  // Method controls Drivetrain Motor speeds
  public void arcadeDrive(double a_speed, double a_turn) {
    double speed = a_speed * DrivetrainPrefs.arcadeSpeed.getValue();
    double turn = a_turn * DrivetrainPrefs.arcadeTurn.getValue();
    double multiplier = 1;

    if (RobotContainer.DriverStick.btn_LBump.get()) {
      multiplier = RobotPreferences.DrivetrainPrefs.arcadeLowSpeed.getValue();
    }

    if (RobotContainer.DriverStick.btn_RBump.get()) {
      multiplier = RobotPreferences.DrivetrainPrefs.arcadeHighSpeed.getValue();
    }

    leftLeadMotor.set(ControlMode.PercentOutput, speed * multiplier, DemandType.ArbitraryFeedForward, turn);
    rightLeadMotor.set(ControlMode.PercentOutput, speed * multiplier, DemandType.ArbitraryFeedForward, -turn);
  }

  public void closedLoopArcadeDrive(double a_speed, double a_turn) {
    double speed = a_speed * DrivetrainPrefs.arcadeClosedLoopMaxSpeed.getValue();

    speed = SN_Math.MPSToFalcon(
        speed, Units.inchesToMeters(DrivetrainPrefs.driveWheelCircumference.getValue()),
        DrivetrainPrefs.driveGearRatio.getValue());
    double turn = a_turn * DrivetrainPrefs.arcadeTurn.getValue();

    leftLeadMotor.set(ControlMode.Velocity, speed, DemandType.ArbitraryFeedForward, turn);
    rightLeadMotor.set(ControlMode.Velocity, speed, DemandType.ArbitraryFeedForward, -turn);
  }

  public void driveDistance(SN_DoublePreference a_inchesToDrive, SN_DoublePreference a_peakPercentOutput) {
    leftLeadMotor.configClosedLoopPeakOutput(0, a_peakPercentOutput.getValue());
    rightLeadMotor.configClosedLoopPeakOutput(0, a_peakPercentOutput.getValue());
    double position = a_inchesToDrive.getValue() * (DrivetrainPrefs.driveEncoderCountsPerFoot.getValue() / 12);
    leftLeadMotor.set(ControlMode.Position, position);
    rightLeadMotor.set(ControlMode.Position, position);
  }

  public void driveSpeed(double a_leftMPS, double a_rightMPS) {
    double leftVelocity = SN_Math.MPSToFalcon(
        a_leftMPS, Units.inchesToMeters(DrivetrainPrefs.driveWheelCircumference.getValue()),
        DrivetrainPrefs.driveGearRatio.getValue());
    double rightVelocity = SN_Math.MPSToFalcon(
        a_rightMPS, Units.inchesToMeters(DrivetrainPrefs.driveWheelCircumference.getValue()),
        DrivetrainPrefs.driveGearRatio.getValue());

    leftLeadMotor.set(ControlMode.Velocity, leftVelocity);
    rightLeadMotor.set(ControlMode.Velocity, rightVelocity);
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d pose) {
    resetDrivetrainEncodersCount();
    odometry.resetPosition(pose, navx.getRotation2d());
  }

  public double getLeftClosedLoopErrorInches() {
    return leftLeadMotor.getClosedLoopError() * (DrivetrainPrefs.driveEncoderCountsPerFoot.getValue() / 12);
  }

  public double getRightClosedLoopErrorInches() {
    return rightLeadMotor.getClosedLoopError() * (DrivetrainPrefs.driveEncoderCountsPerFoot.getValue() / 12);
  }

  public double getAverageClosedLoopErrorInches() {
    return (getLeftClosedLoopErrorInches() + getRightClosedLoopErrorInches()) / 2;
  }

  public void setBrakeMode() {
    leftFollowMotor.setNeutralMode(NeutralMode.Brake);
    leftLeadMotor.setNeutralMode(NeutralMode.Brake);

    rightFollowMotor.setNeutralMode(NeutralMode.Brake);
    rightLeadMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void setCoastMode() {
    leftFollowMotor.setNeutralMode(NeutralMode.Coast);
    leftLeadMotor.setNeutralMode(NeutralMode.Coast);

    rightFollowMotor.setNeutralMode(NeutralMode.Coast);
    rightLeadMotor.setNeutralMode(NeutralMode.Coast);
  }

  public RamseteCommand getRamseteCommand(Trajectory trajectory) {
    field.getObject("traj").setTrajectory(trajectory);

    return new RamseteCommand(
        trajectory,
        this::getPose,
        new RamseteController(),
        DrivetrainPrefs.driveKinematics,
        this::driveSpeed,
        this);
  }

  private void initializeTrajectories() {
    try {
      Path fenderTo1Then2JSONPath = Filesystem.getDeployDirectory().toPath().resolve(fenderTo1Then2JSON);
      Path ball2ToTerminalJSONPath = Filesystem.getDeployDirectory().toPath().resolve(ball2ToTerminalJSON);
      Path terminalTo2JSONPath = Filesystem.getDeployDirectory().toPath().resolve(terminalTo2JSON);

      fenderTo1Then2Traj = TrajectoryUtil.fromPathweaverJson(fenderTo1Then2JSONPath);
      ball2ToTerminalTraj = TrajectoryUtil.fromPathweaverJson(ball2ToTerminalJSONPath);
      terminalTo2Traj = TrajectoryUtil.fromPathweaverJson(terminalTo2JSONPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory. Error: ", ex.getStackTrace());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (RobotContainer.switchBoard.btn_7.get()) {
      // Encoder Counts
      SmartDashboard.putNumber("Drivetrain Left Encoder", getLeftEncoderCount());
      SmartDashboard.putNumber("Drivetrain Right Encoder", getRightEncoderCount());
      SmartDashboard.putNumber("Drivetrain Average Encoder",
          getAverageEncoderCount());

      // Motor Percent Output
      SmartDashboard.putNumber("Drivetrain Left Lead Motor Speed",
          leftLeadMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("Drivetrain Right Lead Motor Speed",
          rightLeadMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("Drivetrain Left Follow Motor Speed",
          leftFollowMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("Drivetrain Right Follow Motor Speed",
          rightFollowMotor.getMotorOutputPercent());

      // Feet Driven
      SmartDashboard.putNumber("Drivetrain Left Feet Driven", getLeftFeetDriven());
      SmartDashboard.putNumber("Drivetrain Right Feet Driven",
          getRightFeetDriven());
      SmartDashboard.putNumber("Drivetrain Average Feet Driven",
          getAverageFeetDriven());

      // Feet Per Second
      SmartDashboard.putNumber("Drivetrain Left Feet Per Second",
          getLeftFeetPerSecond());
      SmartDashboard.putNumber("Drivetrain Right Feet Per Second",
          getRightFeetPerSecond());
      SmartDashboard.putNumber("Drivetrain Average Feet Per Second",
          getAverageFeetPerSecond());

      // Encoder Counts per 100ms
      SmartDashboard.putNumber("Drivetrain Left Velocity", getLeftVelocity());
      SmartDashboard.putNumber("Drivetrain Right Velocity", getRightVelocity());
      SmartDashboard.putNumber("Drivetrain Average Velocity",
          getAverageVelocity());

      // Closed Loop Error
      SmartDashboard.putNumber("Drivetrain Left Closed Loop Error Inches",
          getLeftClosedLoopErrorInches());
      SmartDashboard.putNumber("Drivetrain Right Closed Loop Error Inches",
          getRightClosedLoopErrorInches());
      SmartDashboard.putNumber("Drivetrain Average Closed Loop Error Inches",
          getAverageClosedLoopErrorInches());
    }

  }
}