// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
  /** Creates a new Turret. */

  private TalonSRX TurretMotor;

  // LINKS TO ROBOT MAP
  public Turret() {
    TurretMotor = new TalonSRX(RobotMap.Turret.TURRET_MOTOR_CAN);

    configure();
  }

  // SET TO FACTORY DEFAULT
  public void configure() {
    TurretMotor.configFactoryDefault();
  }

  // RESETS COUNT FOR ENCONDERS
  public void resetEncoderCounts() {
    TurretMotor.setSelectedSensorPosition(0);
  }

  // GETS AND RETURNS COUNT FOR ENCONDERS
  public double getTurretMotorEncoderCount() {
    return TurretMotor.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Turret Motor", getTurretMotorEncoderCount());
  }
}
