// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  private TalonFX leftLead;
  private TalonFX rightLead;
  private TalonFX leftFollow;
  private TalonFX rightFollow;

  public Drivetrain() {
    leftLead = new TalonFX(RobotMap.Drivetrain.LEFT_LEAD_CAN);
    rightLead = new TalonFX(RobotMap.Drivetrain.RIGHT_LEAD_CAN);
    leftFollow = new TalonFX(RobotMap.Drivetrain.LEFT_FOLLOW_CAN);
    rightFollow = new TalonFX(RobotMap.Drivetrain.RIGHT_FOLLOW_CAN);
    configure();
  }

  public void configure() {
    leftLead.configFactoryDefault();
    rightLead.configFactoryDefault();
    leftFollow.configFactoryDefault();
    rightFollow.configFactoryDefault();

    leftFollow.follow(leftLead);
    rightFollow.follow(rightLead);

    leftLead.setInverted(false);
    rightLead.setInverted(true);
    leftFollow.setInverted(false);
    rightFollow.setInverted(true);
    leftLead.setSensorPhase(false);
    rightLead.setSensorPhase(true);
  }

  public void resetEncoderCount() {
    leftLead.setSelectedSensorPosition(0);
    rightLead.setSelectedSensorPosition(0);
  }

  public double getLeftEncoderCount() {
    return leftLead.getSelectedSensorPosition();
  }

  public double getRightEncoderCount() {
    return rightLead.getSelectedSensorPosition();
  }

  public void arcadeDrive(double a_speed, double a_turn) {
    double speed = a_speed;
    double turn = a_turn;

    leftLead.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, turn);
    rightLead.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -turn);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Drivetrain Left Encoder", getLeftEncoderCount());
    SmartDashboard.putNumber("Drivetrain Right Encoder", getRightEncoderCount());
  }
}