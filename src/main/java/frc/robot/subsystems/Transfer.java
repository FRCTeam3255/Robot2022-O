/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Transfer extends SubsystemBase {
  /**
   * Creates a new Transfer.
   */

  private TalonSRX topBeltMotor;
  private TalonSRX bottomBeltMotor;
  // private ColorSensorV3 colorSensor;
  private ColorSensorV3 colorSensor;
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private DigitalInput transferLimitSwitch;

  public Transfer() {
    topBeltMotor = new TalonSRX(RobotMap.Transfer.TOP_BELT_MOTOR_CAN);
    bottomBeltMotor = new TalonSRX(RobotMap.Transfer.BOTTOM_BELT_MOTOR_CAN);
    colorSensor = new ColorSensorV3(i2cPort);
    transferLimitSwitch = new DigitalInput(RobotMap.Transfer.LIMIT_SWITCH_TRANSFER);
  }

  public void configure() {
    topBeltMotor.configFactoryDefault();
    bottomBeltMotor.configFactoryDefault();
  }

  public double getTopBeltMotorEncoderCount() {
    return topBeltMotor.getSelectedSensorPosition();
  }

  public double getBottomBeltMotorEncoderCount() {
    return bottomBeltMotor.getSelectedSensorPosition();
  }

  public void setTransferMotorSpeed(double transfermotor_speed) {
    double speed = transfermotor_speed;

    topBeltMotor.set(ControlMode.PercentOutput, speed);
    bottomBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isBallCollected() {
    return transferLimitSwitch.get();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
