// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotPreferences;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
  /** Creates a new Turret. */

  private TalonFX turretMotor;

  // LINKS TO ROBOT MAP
  public Turret() {
    turretMotor = new TalonFX(RobotMap.TurretMap.TURRET_MOTOR_CAN);

    configure();
  }

  // SET TO FACTORY DEFAULT
  public void configure() {
    turretMotor.configFactoryDefault();

    // soft limit
    turretMotor.configForwardSoftLimitThreshold(RobotPreferences.TurretPrefs.turretMaxEncoderCount.getValue(), 0);
    turretMotor.configReverseSoftLimitThreshold(RobotPreferences.TurretPrefs.turretMinEncoderCount.getValue(), 0);
    turretMotor.configForwardSoftLimitEnable(true, 0);
    turretMotor.configReverseSoftLimitEnable(true, 0);
  }

  // RESETS COUNT FOR ENCODERS
  public void resetTurretEncoderCounts() {
    turretMotor.setSelectedSensorPosition(0);

    // soft limit
    turretMotor.configForwardSoftLimitThreshold(RobotPreferences.TurretPrefs.turretMaxEncoderCount.getValue(), 0);
    turretMotor.configReverseSoftLimitThreshold(RobotPreferences.TurretPrefs.turretMinEncoderCount.getValue(), 0);
  }

  // GETS AND RETURNS COUNT FOR ENCONDERS
  public double getTurretMotorEncoderCount() {
    return turretMotor.getSelectedSensorPosition();
  }

  public void setTurretSpeed(double a_rotate) {
    double rotate = a_rotate;

    turretMotor.set(ControlMode.PercentOutput, rotate);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Turret Motor", getTurretMotorEncoderCount());
  }
}
