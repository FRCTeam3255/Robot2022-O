// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

  // Creates the motors
  private TalonFX intakeMotor;
  private DoubleSolenoid intakeSolenoid;

  // Link to Robot Map
  public Intake() {
    intakeMotor = new TalonFX(RobotMap.Intake.INTAKE_MOTOR_CAN);
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.Intake.INTAKE_SOLENOID_PCM_A,
        RobotMap.Intake.INTAKE_SOLENOID_PCM_B);

    configure();
  }

  // Set to factory default (configure it)
  public void configure() {
    intakeMotor.configFactoryDefault();
  }

  // Reset Encoder Count
  public void resetEncoderCount() {
    intakeMotor.setSelectedSensorPosition(0);
  }

  // Get positons
  public double getIntakeMotorCount() {
    return intakeMotor.getSelectedSensorPosition();
  }

  public Value getSolenoidPosition() {
    return intakeSolenoid.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Motor", getIntakeMotorCount());
  }
}