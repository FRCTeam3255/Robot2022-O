// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  private TalonFX leftLeadMotor;
  private TalonFX rightLeadMotor;
  private TalonFX leftFollowMotor;
  private TalonFX rightFollowMotor;

  public Drivetrain() {
    leftLeadMotor = new TalonFX(RobotMap.DrivetrainMap.LEFT_LEAD_MOTOR_CAN);
    rightLeadMotor = new TalonFX(RobotMap.DrivetrainMap.RIGHT_LEAD_MOTOR_CAN);
    leftFollowMotor = new TalonFX(RobotMap.DrivetrainMap.LEFT_FOLLOW_MOTOR_CAN);
    rightFollowMotor = new TalonFX(RobotMap.DrivetrainMap.RIGHT_FOLLOW_MOTOR_CAN);
    configure();
  }

  public void configure() {

    // Left
    leftLeadMotor.configFactoryDefault();
    leftFollowMotor.configFactoryDefault();
    leftLeadMotor.setInverted(false);
    leftFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    leftLeadMotor.setSensorPhase(false);
    leftFollowMotor.setNeutralMode(NeutralMode.Brake);
    leftLeadMotor.setNeutralMode(NeutralMode.Brake);

    leftFollowMotor.follow(leftLeadMotor);

    // Right
    rightLeadMotor.configFactoryDefault();
    rightFollowMotor.configFactoryDefault();
    rightFollowMotor.follow(rightLeadMotor);

    rightLeadMotor.setInverted(true);
    rightFollowMotor.setInverted(TalonFXInvertType.FollowMaster);
    rightLeadMotor.setSensorPhase(true);
    rightFollowMotor.setNeutralMode(NeutralMode.Brake);
    rightLeadMotor.setNeutralMode(NeutralMode.Brake);

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

  public void arcadeDrive(double a_speed, double a_turn) {
    double speed = a_speed;
    double turn = a_turn;

    leftLeadMotor.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, turn);
    rightLeadMotor.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -turn);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Drivetrain Left Encoder", getLeftEncoderCount());
    SmartDashboard.putNumber("Drivetrain Right Encoder", getRightEncoderCount());
  }
}