// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

  /** Creates a new Climber. */
  private TalonFX climbMotor;
  private DigitalInput safetyMagSwitch;

  public Climber() {

    safetyMagSwitch = new DigitalInput(RobotMap.Climber.SAFETY_MAG_SWITCH_DIO);
    climbMotor = new TalonFX(RobotMap.Climber.CLIMBER_MOTOR_CAN);
    configure();

  }

  private void configure() {
    climbMotor.configFactoryDefault();

  }

  public void resetClimberEncoderCount() {
    climbMotor.setSelectedSensorPosition(0);

  }

  public double getClimberEncoderCount() {
    return climbMotor.getSelectedSensorPosition();

  }

  public boolean isSafetyMagSwitchPressed() {
    return safetyMagSwitch.get();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
