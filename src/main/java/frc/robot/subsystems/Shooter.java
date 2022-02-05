// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

  // CREATES NEW MOTOR
  private TalonFX leftMotor;
  private TalonFX rightMotor;

  // LINKS TO ROBOT MAP
  public Shooter() {
    leftMotor = new TalonFX(RobotMap.ShooterMap.LEFT_MOTOR_CAN);
    rightMotor = new TalonFX(RobotMap.ShooterMap.RIGHT_MOTOR_CAN);

    configure();
  }

  // SET TO FACTORY DEFAULT
  public void configure() {
    leftMotor.configFactoryDefault();
    rightMotor.configFactoryDefault();

    leftMotor.setInverted(false);
    rightMotor.setInverted(true);
  }

  // RESETS COUNT FOR ENCODERS
  public void resetShooterEncoderCounts() {
    leftMotor.setSelectedSensorPosition(0);
    rightMotor.setSelectedSensorPosition(0);
  }

  // GETS AND RETURNS COUNT FOR ENCODERS
  public double getLeftMotorEncoderCount() {
    return leftMotor.getSelectedSensorPosition();
  }

  public double getRightMotorEncoderCount() {
    return rightMotor.getSelectedSensorPosition();
  }

  public void setShooterSpeed(double a_speed) {
    double speed = a_speed;

    leftMotor.set(ControlMode.PercentOutput, speed);
    rightMotor.set(ControlMode.PercentOutput, speed);

  }

  public double getShooterVelocity() {
    return leftMotor.getSelectedSensorVelocity();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Left Motor", getLeftMotorEncoderCount());
    SmartDashboard.putNumber("Shooter Right Motor", getRightMotorEncoderCount());
    SmartDashboard.putNumber("Shooter Velocity", getShooterVelocity());
  }
}