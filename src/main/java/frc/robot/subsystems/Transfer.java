/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Transfer extends SubsystemBase {
  /**
   * Creates a new Transfer.
   */
  // Creates Transfer Variables
  private TalonFX topBeltMotor;
  private TalonFX bottomBeltMotor;
  private TalonFX entranceBeltMotor;
  private DigitalInput transferTopLimitSwitch;
  private DigitalInput transferBottomLimitSwitch;

  // Initializes Transfer Variables
  public Transfer() {
    topBeltMotor = new TalonFX(RobotMap.TransferMap.TOP_BELT_MOTOR_CAN);
    bottomBeltMotor = new TalonFX(RobotMap.TransferMap.BOTTOM_BELT_MOTOR_CAN);
    entranceBeltMotor = new TalonFX(RobotMap.TransferMap.ENTRANCE_BELT_MOTOR_CAN);
    transferTopLimitSwitch = new DigitalInput(RobotMap.TransferMap.TRANSFER_TOP_LIMIT_SWITCH_DIO);
    transferBottomLimitSwitch = new DigitalInput(RobotMap.TransferMap.TRANSFER_BOTTOM_LIMIT_SWITCH_DIO);
  }

  // Sets Transfer variable defaults
  public void configure() {
    topBeltMotor.configFactoryDefault();
    bottomBeltMotor.configFactoryDefault();
    entranceBeltMotor.configFactoryDefault();
  }

  public double getTopBeltMotorEncoderCount() {
    return topBeltMotor.getSelectedSensorPosition();
  }

  public double getBottomBeltMotorEncoderCount() {
    return bottomBeltMotor.getSelectedSensorPosition();

  }

  public void setTopBeltMotorSpeed(double a_speed) {
    double speed = a_speed;
    topBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setBottomBeltMotorSpeed(double a_speed) {
    double speed = a_speed;
    bottomBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setEntranceBeltMotorSpeed(double a_speed) {
    double speed = a_speed;
    entranceBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isTopBallCollected() {
    return !transferTopLimitSwitch.get();
  }

  public boolean isBottomBallCollected() {
    return !transferBottomLimitSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Top Ball Collected", isTopBallCollected());
    SmartDashboard.putBoolean("Bottom Ball Collected", isBottomBallCollected());
  }
}
