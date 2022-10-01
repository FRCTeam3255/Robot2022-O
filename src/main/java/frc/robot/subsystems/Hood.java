// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.utils.SN_Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.HoodMap;
import frc.robot.RobotPreferences.HoodPrefs;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */

  // Creates Hood Variables
  TalonFX hoodMotor;
  TalonFXConfiguration config;

  // Initializes Hood Variables
  public Hood() {
    hoodMotor = new TalonFX(HoodMap.HOOD_MOTOR_CAN);
    configure();
  }

  public void configure() {
    config = new TalonFXConfiguration();

    hoodMotor.configFactoryDefault();

    config.slot0.kF = HoodPrefs.hoodF.getValue();
    config.slot0.kP = HoodPrefs.hoodP.getValue();
    config.slot0.kI = HoodPrefs.hoodI.getValue();
    config.slot0.kD = HoodPrefs.hoodD.getValue();

    config.slot0.allowableClosedloopError = SN_Math
        .degreesToFalcon(HoodPrefs.hoodAllowableClosedLoopErrorDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue());

    hoodMotor.configAllSettings(config);

    hoodMotor.configClosedLoopPeakOutput(0, HoodPrefs.hoodClosedLoopPeakOutput.getValue());

    hoodMotor.configForwardSoftLimitEnable(true);
    hoodMotor.configForwardSoftLimitThreshold(
        SN_Math.degreesToFalcon(HoodPrefs.hoodMaxDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));

    hoodMotor.configReverseSoftLimitEnable(true);
    hoodMotor.configReverseSoftLimitThreshold(
        SN_Math.degreesToFalcon(HoodPrefs.hoodMinDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));
  }

  public void setAngleDegrees(SN_DoublePreference angle) {
    double encoderCounts = SN_Math.degreesToFalcon(angle.getValue(), HoodPrefs.hoodGearRatio.getValue());
    hoodMotor.set(ControlMode.Position, encoderCounts);
  }

  public double getAngleDegrees() {
    return SN_Math.falconToDegrees(hoodMotor.getSelectedSensorPosition(), HoodPrefs.hoodGearRatio.getValue());
  }

  // Method constantly runs
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Angle Degrees", getAngleDegrees());
  }
}