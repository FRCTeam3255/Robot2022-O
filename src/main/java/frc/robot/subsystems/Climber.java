// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;

public class Climber extends SubsystemBase {

  /** Creates a new Climber. */
  private TalonFX climbMotor;
  private DigitalInput climberBottomSafetySwitch;

  public Climber() {

    climberBottomSafetySwitch = new DigitalInput(RobotMap.ClimberMap.SAFETY_MAG_SWITCH_DIO);
    climbMotor = new TalonFX(RobotMap.ClimberMap.CLIMBER_MOTOR_CAN);
    configure();

  }

  private void configure() {
    climbMotor.configFactoryDefault();

    // Set the Soft Limit for Forward Throttle
    climbMotor.configForwardSoftLimitThreshold(RobotPreferences.ClimberPrefs.climberMaxEncoderCount.getValue(), 0);
    climbMotor.configForwardSoftLimitEnable(true, 0);
  }

  public void resetClimberEncoderCount() {
    climbMotor.configForwardSoftLimitThreshold(RobotPreferences.ClimberPrefs.climberMaxEncoderCount.getValue(), 0);
    climbMotor.setSelectedSensorPosition(0);

  }

  public double getClimberEncoderCount() {
    return climbMotor.getSelectedSensorPosition();
  }

  public void setClimberSpeed(double a_speed) {
    double speed = a_speed;

    if (isClimberAtBottom() == false) {
      climbMotor.set(ControlMode.PercentOutput, 0);

    } else if (isClimberAtBottom() == true) {
      climbMotor.set(ControlMode.PercentOutput, speed);
    }

  }

  // TODO: change when location of mag switch is (ex: isClimberRaised)
  public boolean isClimberAtBottom() {
    return climberBottomSafetySwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber Motor", getClimberEncoderCount());
    SmartDashboard.putBoolean("Climber At Bottom?", isClimberAtBottom());
  }
}