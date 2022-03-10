// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.frcteam3255.components.SN_DoubleSolenoid;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;
import frc.robot.RobotMap.*;
import static frc.robot.RobotPreferences.*;

public class Climber extends SubsystemBase {

  /** Creates a new Climber. */
  private TalonFX climbMotor;
  private DigitalInput climberBottomSafetySwitch;
  private SN_DoubleSolenoid climberLockPiston;
  private SN_DoubleSolenoid climberPivotPiston;
  // Somebody can rename this solenoid if they can think of a better name
  // ok
  private SN_DoubleSolenoid climberHookPiston;

  public Climber() {

    climberBottomSafetySwitch = new DigitalInput(ClimberMap.BOTTOM_SAFETY_MAG_SWITCH_DIO);
    climbMotor = new TalonFX(ClimberMap.CLIMBER_MOTOR_CAN);

    climberLockPiston = new SN_DoubleSolenoid(RobotMap.CLIMBER_PCM, PneumaticsModuleType.CTREPCM,
        ClimberMap.LOCK_PISTON_PCM_A,
        ClimberMap.LOCK_PISTON_PCM_B);

    climberHookPiston = new SN_DoubleSolenoid(RobotMap.CLIMBER_PCM, PneumaticsModuleType.CTREPCM,
        ClimberMap.STATIONARY_CLIMB_HOOKS_PISTON_A,
        ClimberMap.STATIONARY_CLIMB_HOOKS_PISTON_B);

    climberPivotPiston = new SN_DoubleSolenoid(RobotMap.CLIMBER_PCM, PneumaticsModuleType.CTREPCM,
        ClimberMap.PIVOT_PISTON_PCM_A,
        ClimberMap.PIVOT_PISTON_PCM_B);

    configure();

  }

  public void configure() {
    climbMotor.configFactoryDefault();

    // Set the Soft Limit for Forward Throttle
    climbMotor.configForwardSoftLimitThreshold(ClimberPrefs.climberMaxEncoderCountPerpendicular.getValue());
    climbMotor.configReverseSoftLimitThreshold(ClimberPrefs.climberMinEncoderCount.getValue());
    climbMotor.configForwardSoftLimitEnable(true);
    climbMotor.configReverseSoftLimitEnable(true);

    climberLockPiston.setInverted(ClimberPrefs.climberLockPistonInvert.getValue());
    climberPivotPiston.setInverted(ClimberPrefs.climberPivotPistonInvert.getValue());
    climberHookPiston.setInverted(ClimberPrefs.climberHookPistonInvert.getValue());
  }

  // Method controls CLimb Motor Speed
  public void setClimberSpeed(double a_speed) {
    double speed = a_speed;

    if (isHookDeployed()) {
      climbMotor.set(ControlMode.PercentOutput, speed);
    }
  }

  public void setClimberPosition(SN_DoublePreference a_position) {
    climbMotor.set(ControlMode.Position, a_position.getValue());
  }

  public void resetClimberEncoderCount() {
    climbMotor.setSelectedSensorPosition(0);
  }

  public double getClimberEncoderCount() {
    return climbMotor.getSelectedSensorPosition();
  }

  public boolean isClimberLocked() {
    return climberLockPiston.isDeployed();
  }

  // solenoid commands
  public void lockClimber() {
    climberLockPiston.setDeployed();
  }

  public void unlockClimber() {
    climberLockPiston.setRetracted();
  }

  // Piston Deploy/Retract
  public void pivotPerpendicular() {
    climberPivotPiston.setDeployed();
    climbMotor.configForwardSoftLimitThreshold(ClimberPrefs.climberMaxEncoderCountPerpendicular.getValue());
  }

  public void pivotAngled() {
    climberPivotPiston.setRetracted();
    climbMotor.configForwardSoftLimitThreshold(ClimberPrefs.climberMaxEncoderCountAngled.getValue());
  }

  public boolean isClimberAngled() {
    return climberPivotPiston.isDeployed();
  }

  public void hookForward() {
    climberHookPiston.setDeployed();
  }

  public void hookBackward() {
    climberHookPiston.setRetracted();
  }

  public boolean isHookDeployed() {
    return climberHookPiston.isDeployed();
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
    SmartDashboard.putBoolean("Is Climber Angled", isClimberAngled());
    SmartDashboard.putBoolean("Is Climber Hooked", isHookDeployed());
    SmartDashboard.putNumber("Climber Motor Speed", climbMotor.getMotorOutputPercent());
  }
}