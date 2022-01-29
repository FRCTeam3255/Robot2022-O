// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
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
    intakeMotor = new TalonFX(RobotMap.IntakeMap.INTAKE_MOTOR_CAN);
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.IntakeMap.INTAKE_SOLENOID_PCM_A,
        RobotMap.IntakeMap.INTAKE_SOLENOID_PCM_B);

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

  public void setIntakeMotorSpeed(double a_speed) {
    double speed = a_speed;

    intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  // Get positons
  public double getIntakeMotorCount() {
    return intakeMotor.getSelectedSensorPosition();
  }

  public boolean isIntakeDeployed() {
    Value intakeSolenoidStatus = intakeSolenoid.get();
    boolean isIntakeDeployed = false;

    if (intakeSolenoidStatus == DoubleSolenoid.Value.kForward) {
      isIntakeDeployed = true;
    } else {
      isIntakeDeployed = false;
    }

    return isIntakeDeployed;

  }

  // solenoid do solenoid thing!!
  public void deployIntake() {
    intakeSolenoid.set(Value.kForward);
  }

  public void retractIntake() {
    intakeSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Motor", getIntakeMotorCount());
    SmartDashboard.putBoolean("Intake Solenoid", isIntakeDeployed());
  }
}