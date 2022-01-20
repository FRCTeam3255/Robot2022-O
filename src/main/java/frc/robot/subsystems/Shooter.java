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
  private TalonFX TopMotor;
  private TalonFX BottomMotor;

  // LINKS TO ROBOT MAP
  public Shooter() {
    TopMotor = new TalonFX(RobotMap.Shooter.TOP_MOTOR);
    BottomMotor = new TalonFX(RobotMap.Shooter.BOTTOM_MOTOR);

    configure();
  }

  // SET TO FACTORY DEFAULT
  private void configure() {
    TopMotor.configFactoryDefault();
    BottomMotor.configFactoryDefault();
  }

  // RESETS COUNT FOR ENCODERS
  public void resetEncoderCounts() {
    TopMotor.setSelectedSensorPosition(0);
    BottomMotor.setSelectedSensorPosition(0);
  }

  // GETS AND RETURNS COUNT FOR ENCODERS
  public double getTopMotorEncoderCount() {
    return TopMotor.getSelectedSensorPosition();
  }

  public double getBottomMotorEncoderCount() {
    return BottomMotor.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Top Motor", getTopMotorEncoderCount());
    SmartDashboard.putNumber("Shooter Bottom Motor", getBottomMotorEncoderCount());
  }
}
