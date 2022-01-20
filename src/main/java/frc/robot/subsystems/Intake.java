// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {

  // Creates the motors
  private TalonFX IntakeMotor;
  private DoubleSolenoid IntakeSolenoid;

  // Link to Robot Map
  public Intake() {
    IntakeMotor = new TalonFX(RobotMap.Intake.INTAKE_MOTOR_CAN);

    // why no work?????????????
    // IntakeSolenoid = new DoubleSolenoid(RobotMap.Intake.INTAKE_SOLENOID_CAN_A,
    // RobotMap.Intake.INTAKE_SOLENOID_CAN_B);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}