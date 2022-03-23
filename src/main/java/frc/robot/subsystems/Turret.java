// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.RobotPreferences.*;
import frc.robot.RobotMap.*;

public class Turret extends SubsystemBase {
  /** Creates a new Turret. */

  private TalonFX turretMotor;
  private TalonFXConfiguration config;

  // LINKS TO ROBOT MAP
  public Turret() {
    // Creates Turret Variables
    turretMotor = new TalonFX(TurretMap.TURRET_MOTOR_CAN);
    config = new TalonFXConfiguration();

    configure();
  }

  // Sets Turret Variable Factory Defaults
  public void configure() {
    // first we reset all the settings
    turretMotor.configFactoryDefault();

    // then we set the config settings
    config.slot0.allowableClosedloopError = TurretPrefs.turretMaxAllowableErrorDegrees.getValue();
    config.slot0.closedLoopPeakOutput = TurretPrefs.turretClosedLoopPeakOutput.getValue();
    config.slot0.kF = TurretPrefs.turretF.getValue();
    config.slot0.kP = TurretPrefs.turretP.getValue();
    config.slot0.kI = TurretPrefs.turretI.getValue();
    config.slot0.kD = TurretPrefs.turretD.getValue();

    // then we apply the config settings (which also sets every other setting, not
    // just the ones we set)
    turretMotor.configAllSettings(config);

    // then we set more stuff that wasn't in the config

    // soft limit
    turretMotor.configForwardSoftLimitThreshold(TurretPrefs.turretMaxAngleDegrees.getValue()
        * TurretPrefs.turretEncoderCountsPerDegrees.getValue());
    turretMotor.configReverseSoftLimitThreshold(TurretPrefs.turretMinAngleDegrees.getValue()
        * TurretPrefs.turretEncoderCountsPerDegrees.getValue());
    turretMotor.configForwardSoftLimitEnable(true);
    turretMotor.configReverseSoftLimitEnable(true);

    turretMotor.setInverted(false);
    turretMotor.setSensorPhase(false);

    turretMotor.setNeutralMode(NeutralMode.Brake);
  }

  // Resets Encoder Counts
  public void resetTurretEncoderCounts() {
    turretMotor.setSelectedSensorPosition(0);
  }

  // GETS AND RETURNS COUNT FOR ENCONDERS
  public double getTurretMotorEncoderCounts() {
    return turretMotor.getSelectedSensorPosition();
  }

  public void setTurretSpeed(double a_rotate) {
    double rotate = a_rotate;

    turretMotor.set(ControlMode.PercentOutput, -rotate);
  }

  // gets the turret angle in degrees
  public double getTurretAngle() {
    return getTurretMotorEncoderCounts() / TurretPrefs.turretEncoderCountsPerDegrees.getValue();
  }

  // sets the turret angle in degrees
  public void setTurretAngle(double a_degrees) {
    double position = a_degrees * TurretPrefs.turretEncoderCountsPerDegrees.getValue();
    turretMotor.set(ControlMode.Position, position);
  }

  // gets the difference between where the motor wants to be, and where it is, in
  // encoder counts
  public double getTurretClosedLoopErrorDegrees() {
    return turretMotor.getClosedLoopError() * TurretPrefs.turretEncoderCountsPerDegrees.getValue();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("Turret Encoder", getTurretMotorEncoderCounts());
    // SmartDashboard.putNumber("Turret Angle", getTurretAngle());
    // SmartDashboard.putNumber("Turret Closed Loop Error",
    // getTurretClosedLoopErrorDegrees());
    // SmartDashboard.putNumber("Turret Motor Speed",
    // turretMotor.getMotorOutputPercent());
  }
}
