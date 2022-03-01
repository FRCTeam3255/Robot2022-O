// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.*;
import static frc.robot.RobotPreferences.*;

public class Climber extends SubsystemBase {

  /** Creates a new Climber. */
  private TalonFX climbMotor;
  private DigitalInput climberBottomSafetySwitch;
  private DoubleSolenoid climberLockPiston;
  private DoubleSolenoid climberPivotPiston;

  // Solenoid Variables
  // Lock Piston
  private DoubleSolenoid.Value lockDeploy = Value.kForward;
  private DoubleSolenoid.Value lockRetract = Value.kReverse;

  // Pivot Piston
  private DoubleSolenoid.Value pivotDeploy = Value.kForward;
  private DoubleSolenoid.Value pivotRetract = Value.kReverse;

  public Climber() {

    climberBottomSafetySwitch = new DigitalInput(ClimberMap.BOTTOM_SAFETY_MAG_SWITCH_DIO);
    climbMotor = new TalonFX(ClimberMap.CLIMBER_MOTOR_CAN);
    climberLockPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ClimberMap.LOCK_PISTON_PCM_A,
        ClimberMap.LOCK_PISTON_PCM_B);
    configure();
    climberPivotPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ClimberMap.PIVOT_PISTON_PCM_A,
        ClimberMap.PIVOT_PISTON_PCM_B);
  }

  public boolean isClimberLocked() {
    Value climberLockStatus = climberLockPiston.get();
    boolean isClimberLocked = false;

    if (climberLockStatus == lockDeploy) {
      isClimberLocked = true;
    } else {
      isClimberLocked = false;
    }

    return isClimberLocked;
  }

  public boolean isClimberPivoted() {
    Value climberPivotStatus = climberPivotPiston.get();
    boolean isClimberPivoted = false;

    if (climberPivotStatus == lockDeploy) {
      isClimberPivoted = true;
    } else {
      isClimberPivoted = false;
    }

    return isClimberPivoted;
  }

  // solenoid commands
  public void lockClimber() {
    climberLockPiston.set(lockDeploy);
  }

  public void unlockClimber() {
    climberLockPiston.set(lockRetract);
  }

  public void configure() {
    climbMotor.configFactoryDefault();

    // Set the Soft Limit for Forward Throttle
    climbMotor.configForwardSoftLimitThreshold(ClimberPrefs.climberMaxEncoderCount.getValue());
    climbMotor.configForwardSoftLimitEnable(true);
  }

  public void resetClimberEncoderCount() {
    climbMotor.configForwardSoftLimitThreshold(ClimberPrefs.climberMaxEncoderCount.getValue());
    climbMotor.setSelectedSensorPosition(0);

  }

  public double getClimberEncoderCount() {
    return climbMotor.getSelectedSensorPosition();
  }

  // Method controls CLimb Motor Speed
  public void setClimberSpeed(double a_speed) {
    double speed = a_speed;
    // If the Climber is at the bottom climber cannot go any lower
    if (isClimberAtBottom() == true && speed < 0) {
      climbMotor.set(ControlMode.PercentOutput, 0);
      // If the Climber is anywhere other than the bottom the climber will move either
      // up or down
    } else {
      climbMotor.set(ControlMode.PercentOutput, ClimberPrefs.climberMotorSpeed.getValue() * speed);
    }

  }

  // Climbing Up/Down
  public void extendClimber() {
    climbMotor.set(ControlMode.Position, ClimberPrefs.climberUpPosition.getValue());
  }

  public void retractClimber() {
    climbMotor.set(ControlMode.Position, ClimberPrefs.climberDownPosition.getValue());
  }

  // Piston Deploy/Retract
  public void pivotForward() {
    climberPivotPiston.set(pivotDeploy);
  }

  public void pivotBackward() {
    climberPivotPiston.set(pivotRetract);
  }

  // TODO: change when location of mag switch is (ex: isClimberRaised)
  public boolean isClimberAtBottom() {
    return !climberBottomSafetySwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber Encoder Counts", getClimberEncoderCount());
    SmartDashboard.putBoolean("Is Climber At Bottom", isClimberAtBottom());
    SmartDashboard.putBoolean("Is Climber Locked", isClimberLocked());
    SmartDashboard.putBoolean("Is Climber Pivoted", isClimberPivoted());
    SmartDashboard.putNumber("Climber Motor Speed", climbMotor.getMotorOutputPercent());
  }
}