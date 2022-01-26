// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

  // CREATES NEW MOTOR
  private TalonFX topMotor;
  private TalonFX bottomMotor;

  // LINKS TO ROBOT MAP
  public Shooter() {
    topMotor = new TalonFX(RobotMap.ShooterMap.TOP_MOTOR_CAN);
    bottomMotor = new TalonFX(RobotMap.ShooterMap.BOTTOM_MOTOR_CAN);

    configure();
  }

  // SET TO FACTORY DEFAULT
  public void configure() {
    topMotor.configFactoryDefault();
    bottomMotor.configFactoryDefault();
  }

  // RESETS COUNT FOR ENCODERS
  public void resetEncoderCounts() {
    topMotor.setSelectedSensorPosition(0);
    bottomMotor.setSelectedSensorPosition(0);
  }

  // GETS AND RETURNS COUNT FOR ENCODERS
  public double getTopMotorEncoderCount() {
    return topMotor.getSelectedSensorPosition();
  }

  public double getBottomMotorEncoderCount() {
    return bottomMotor.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Top Motor", getTopMotorEncoderCount());
    SmartDashboard.putNumber("Shooter Bottom Motor", getBottomMotorEncoderCount());
  }
}
