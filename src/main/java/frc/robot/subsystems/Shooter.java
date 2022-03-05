// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.utils.SN_Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.*;
import static frc.robot.RobotPreferences.*;

public class Shooter extends SubsystemBase {

  // CREATES NEW MOTOR
  private TalonFX leadMotor;
  private TalonFX followMotor;

  private TalonFXConfiguration config = new TalonFXConfiguration();

  double goalRPM;

  /**
   * Creates new shooter
   */
  public Shooter() {
    leadMotor = new TalonFX(ShooterMap.LEFT_MOTOR_CAN);
    followMotor = new TalonFX(ShooterMap.RIGHT_MOTOR_CAN);

    config = new TalonFXConfiguration();

    configure();

    goalRPM = 0;
  }

  /**
   * Configures the shooter's motors
   */
  public void configure() {
    leadMotor.configFactoryDefault();
    followMotor.configFactoryDefault();

    config.slot0.kF = ShooterPrefs.shooterF.getValue();
    config.slot0.kP = ShooterPrefs.shooterP.getValue();
    config.slot0.kI = ShooterPrefs.shooterI.getValue();
    config.slot0.kD = ShooterPrefs.shooterD.getValue();

    leadMotor.configAllSettings(config);
    followMotor.configAllSettings(config);

    followMotor.follow(leadMotor);

    leadMotor.setInverted(false);
    followMotor.setInverted(InvertType.OpposeMaster);

    leadMotor.setSensorPhase(false);
    followMotor.setSensorPhase(true);
  }

  /**
   * Resets the shooter's motors' encoder counts to zero
   */
  public void resetShooterEncoderCounts() {
    leadMotor.setSelectedSensorPosition(0);
    followMotor.setSelectedSensorPosition(0);
  }

  /**
   * 
   * @return Shooter Motor Encoder Counts
   */
  public double getShooterEncoderCount() {
    return leadMotor.getSelectedSensorPosition();
  }

  // Sets/Controls Shooter Motor speeds
  public void setShooterPercentOutput(double a_speed) {
    leadMotor.set(ControlMode.PercentOutput, a_speed);
  }

  /**
   * Sets the shooter RPM
   * 
   * @param a_rpm RPM to set motor to
   */
  public void setShooterRPM(double a_rpm) {
    double rpm = SN_Math.RPMToVelocity(a_rpm, SN_Math.TALONFX_ENCODER_PULSES_PER_COUNT);
    leadMotor.set(ControlMode.Velocity, rpm);
  }

  /**
   * 
   * @return Shooter RPM
   */
  public double getShooterRPM() {
    return SN_Math.velocityToRPM(getShooterVelocity(), SN_Math.TALONFX_ENCODER_PULSES_PER_COUNT);
  }

  /**
   * 
   * @return Shooter velocity (encoder counts per 100ms)
   */
  private double getShooterVelocity() {
    return leadMotor.getSelectedSensorVelocity();
  }

  public boolean isShooterUpToSpeed() {
    return ShooterPrefs.shooterAcceptableErrorRPM.getValue() > Math
        .abs(SN_Math.velocityToRPM(leadMotor.getClosedLoopError(), SN_Math.TALONFX_ENCODER_PULSES_PER_COUNT));

    // return true if the acceptable error (in rpm) is greater than the actual error
    // (converted to rpm)
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Left Motor", getShooterEncoderCount());
    SmartDashboard.putNumber("Shooter Velocity", getShooterVelocity());
    SmartDashboard.putBoolean("Is Shooter Up To Speed", isShooterUpToSpeed());
    SmartDashboard.putNumber("ShooterLeadMotorSpeed", leadMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("ShooterFollowMotorSpeed", followMotor.getMotorOutputPercent());

  }
}
