// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase {

  private TalonSRX hoodMotor;
  private DigitalInput hoodLimitSwitch;

  /** Creates a new Hood. */

  public Hood() {
    hoodMotor = new TalonSRX(RobotMap.Hood.HOOD_MOTOR_CAN);
    hoodLimitSwitch = new DigitalInput(RobotMap.Hood.HOOD_LIMIT_SWITCH);
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Encoder Count", getHoodEncoderCount());
  }
}