// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;

public class Hood extends SubsystemBase {

  private TalonSRX hoodMotor;
  private DigitalInput hoodLimitSwitch;

  /** Creates a new Hood. */

  public Hood() {
    hoodMotor = new TalonSRX(RobotMap.HoodMap.HOOD_MOTOR_CAN);
    hoodLimitSwitch = new DigitalInput(RobotMap.HoodMap.HOOD_LIMIT_SWITCH);
    configure();
  }

  public void configure() {
    hoodMotor.configFactoryDefault();
  }

  public void resetEncoderCount() {
    hoodMotor.setSelectedSensorPosition(0);
  }

  public double getHoodEncoderCount() {
    return hoodMotor.getSelectedSensorPosition();
  }

  public boolean isHoodOpen() {
    return hoodLimitSwitch.get();
  }

  public double getHoodPosition() {
    return hoodMotor.getSelectedSensorPosition() / RobotPreferences.HoodMap.hoodCountsPerDegree.getValue();
  }

  public void moveHoodToDegree(double degree) {
    hoodMotor.set(ControlMode.Position, (degree * RobotPreferences.HoodMap.hoodCountsPerDegree.getValue()));

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Encoder Count", getHoodEncoderCount());
    SmartDashboard.putBoolean("Hood Open", isHoodOpen());
  }
}