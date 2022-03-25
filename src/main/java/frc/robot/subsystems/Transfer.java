/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap.*;

import static frc.robot.RobotPreferences.*;

public class Transfer extends SubsystemBase {
  /**
   * Creates a new Transfer.
   */
  // Creates Transfer Variables
  private TalonFX topBeltMotor;
  private TalonFX bottomBeltMotor;
  private TalonFX entranceBeltMotor;

  private DigitalInput transferTopLeftLimitSwitch;
  private DigitalInput transferBottomLeftLimitSwitch;
  private DigitalInput transferTopRightLimitSwitch;
  private DigitalInput transferBottomRightLimitSwitch;

  TransferState state;

  // Initializes Transfer Variables
  public Transfer() {

    topBeltMotor = new TalonFX(TransferMap.TOP_BELT_MOTOR_CAN);
    bottomBeltMotor = new TalonFX(TransferMap.BOTTOM_BELT_MOTOR_CAN);
    entranceBeltMotor = new TalonFX(TransferMap.ENTRANCE_BELT_MOTOR_CAN);

    transferTopLeftLimitSwitch = new DigitalInput(TransferMap.TRANSFER_TOP_LEFT_LIMIT_SWITCH_DIO);
    transferBottomLeftLimitSwitch = new DigitalInput(TransferMap.TRANSFER_BOTTOM_LEFT_LIMIT_SWITCH_DIO);
    transferTopRightLimitSwitch = new DigitalInput(TransferMap.TRANSFER_TOP_RIGHT_LIMIT_SWITCH_DIO);
    transferBottomRightLimitSwitch = new DigitalInput(TransferMap.TRANSFER_BOTTOM_RIGHT_LIMIT_SWITCH_DIO);
    configure();

    state = TransferState.OFF;
  }

  // Sets Transfer variable defaults
  public void configure() {
    topBeltMotor.configFactoryDefault();
    bottomBeltMotor.configFactoryDefault();
    entranceBeltMotor.configFactoryDefault();

    topBeltMotor.setNeutralMode(NeutralMode.Brake);
    bottomBeltMotor.setNeutralMode(NeutralMode.Brake);
    entranceBeltMotor.setNeutralMode(NeutralMode.Brake);

    entranceBeltMotor.setInverted(true);

    // Ramping
    topBeltMotor.configOpenloopRamp(TransferPrefs.transferRampTime.getValue());
    bottomBeltMotor.configOpenloopRamp(TransferPrefs.transferRampTime.getValue());
  }

  public enum TransferState {
    SHOOTING, PROCESSING, OFF
  }

  public void setTransferState(TransferState a_state) {
    state = a_state;
  }

  public TransferState getTransferState() {
    return state;
  }

  public double getTopBeltMotorEncoderCount() {
    return topBeltMotor.getSelectedSensorPosition();
  }

  public double getBottomBeltMotorEncoderCount() {
    return bottomBeltMotor.getSelectedSensorPosition();
  }

  public void setTopBeltMotorSpeed(SN_DoublePreference a_speed) {
    double speed = a_speed.getValue();
    topBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setBottomBeltMotorSpeed(SN_DoublePreference a_speed) {
    double speed = a_speed.getValue();
    bottomBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setEntranceBeltMotorSpeed(SN_DoublePreference a_speed) {
    double speed = a_speed.getValue();
    entranceBeltMotor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isTopBallCollected() {
    return !transferTopRightLimitSwitch.get() || !transferTopLeftLimitSwitch.get();
  }

  public boolean isBottomBallCollected() {
    return !transferBottomRightLimitSwitch.get() || !transferBottomLeftLimitSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Top Ball Collected", isTopBallCollected());
    SmartDashboard.putBoolean("Bottom Ball Collected", isBottomBallCollected());
    if (RobotContainer.switchBoard.btn_7.get()) {
      SmartDashboard.getNumber("Top Belt Motor Speed",
          topBeltMotor.getMotorOutputPercent());
      SmartDashboard.getNumber("Bottom Belt Motor Speed",
          bottomBeltMotor.getMotorOutputPercent());
      SmartDashboard.getNumber("Entrance Belt Motor Speed",
          entranceBeltMotor.getMotorOutputPercent());
    }

  }
}
