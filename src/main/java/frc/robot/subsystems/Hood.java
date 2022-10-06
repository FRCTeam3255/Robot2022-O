// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.utils.SN_Math;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.HoodMap;
import frc.robot.RobotPreferences.HoodPrefs;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */

  // Creates Hood Variables
  TalonFX hoodMotor;
  DigitalInput bottomSwitch;
  TalonFXConfiguration config;

  // Initializes Hood Variables
  public Hood() {
    hoodMotor = new TalonFX(HoodMap.HOOD_MOTOR_CAN);
    bottomSwitch = new DigitalInput(HoodMap.HOOD_BOTTOM_SWITCH);
    configure();
  }

  public void configure() {
    config = new TalonFXConfiguration();

    hoodMotor.configFactoryDefault();

    config.slot0.kP = HoodPrefs.hoodP.getValue();
    config.slot0.kI = HoodPrefs.hoodI.getValue();
    config.slot0.kD = HoodPrefs.hoodD.getValue();

    config.slot0.allowableClosedloopError = SN_Math
        .degreesToFalcon(HoodPrefs.hoodAllowableClosedLoopErrorDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue());

    System.out.println(SN_Math
        .degreesToFalcon(HoodPrefs.hoodAllowableClosedLoopErrorDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));

    config.slot0.closedLoopPeakOutput = HoodPrefs.hoodClosedLoopPeakOutput.getValue();

    hoodMotor.configAllSettings(config);

    hoodMotor.setInverted(true);
    hoodMotor.setNeutralMode(NeutralMode.Coast);

    hoodMotor.configForwardSoftLimitEnable(true);
    hoodMotor.configForwardSoftLimitThreshold(
        SN_Math.degreesToFalcon(HoodPrefs.hoodMaxDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));

    hoodMotor.configReverseSoftLimitEnable(true);
    hoodMotor.configReverseSoftLimitThreshold(
        SN_Math.degreesToFalcon(HoodPrefs.hoodMinDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));
  }

  public void setAngleDegrees(SN_DoublePreference angle) {
    double encoderCounts = SN_Math.degreesToFalcon(angle.getValue(), HoodPrefs.hoodGearRatio.getValue());
    hoodMotor.set(ControlMode.Position, encoderCounts, DemandType.ArbitraryFeedForward,
        HoodPrefs.hoodArbitraryFeedForward.getValue());
    SmartDashboard.putNumber("Hood Desired Angle", angle.getValue());
  }

  public void setDoubleAngleDegrees(double angle) {
    double encoderCounts = SN_Math.degreesToFalcon(angle, HoodPrefs.hoodGearRatio.getValue());
    hoodMotor.set(ControlMode.Position, encoderCounts, DemandType.ArbitraryFeedForward,
        HoodPrefs.hoodArbitraryFeedForward.getValue());
    SmartDashboard.putNumber("Hood Desired Angle", angle);
  }

  public double getAngleDegrees() {
    return SN_Math.falconToDegrees(hoodMotor.getSelectedSensorPosition(), HoodPrefs.hoodGearRatio.getValue());
  }

  public void setSpeed(double speed) {
    hoodMotor.set(ControlMode.PercentOutput, speed * HoodPrefs.hoodOpenLoopSpeedMultiplier.getValue());
  }

  public boolean getBottomSwitch() {
    return !bottomSwitch.get();
  }

  public void resetAngleToBottom() {
    hoodMotor.setSelectedSensorPosition(
        SN_Math.degreesToFalcon(HoodPrefs.hoodMinDegrees.getValue(), HoodPrefs.hoodGearRatio.getValue()));
  }

  // Method constantly runs
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Angle Degrees", getAngleDegrees());
    SmartDashboard.putBoolean("Hood Bottom Limit Switch", getBottomSwitch());
    SmartDashboard.putNumber("Hood Encoder Counts", hoodMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("Hood Motor Percent Output", hoodMotor.getMotorOutputPercent());

    if (getBottomSwitch()) {
      resetAngleToBottom();
    }
  }
}