// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotMap.*;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */

  // Creates Hood Variables
  private DoubleSolenoid longHoodPiston;
  private DoubleSolenoid shortHoodPiston;
  private DoubleSolenoid.Value shallowAngleHoodValue = Value.kReverse;
  private DoubleSolenoid.Value steepAngleHoodValue = Value.kForward;

  // Initializes Hood Variables
  public Hood() {
    longHoodPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
        HoodMap.LONG_HOOD_SOLENOID_STEEP_ANGLE_PCM_A,
        HoodMap.LONG_HOOD_SOLENOID_SHALLOW_ANGLE_PCM_B);
    shortHoodPiston = new DoubleSolenoid(RobotMap.CLIMBER_PCM, PneumaticsModuleType.CTREPCM,
        HoodMap.SHORT_HOOD_SOLENOID_STEEP_ANGLE_PCM_A,
        HoodMap.SHORT_HOOD_SOLENOID_SHALLOW_ANGLE_PCM_B);
    // configure is not needed since this is a solenoid
  }

  // solenoid methods
  // Sets hood angle to the given value

  // Both are on
  public void hoodHighTilt() {
    longHoodPiston.set(steepAngleHoodValue);
    shortHoodPiston.set(steepAngleHoodValue);
  }

  // Old is on, new is off
  public void hoodMediumTilt() {
    longHoodPiston.set(steepAngleHoodValue);
    shortHoodPiston.set(shallowAngleHoodValue);
  }

  // Old is off, new is on
  public void hoodLowTilt() {
    longHoodPiston.set(shallowAngleHoodValue);
    shortHoodPiston.set(steepAngleHoodValue);
  }

  // Both are off
  public void hoodZeroTilt() {
    longHoodPiston.set(shallowAngleHoodValue);
    shortHoodPiston.set(shallowAngleHoodValue);
  }

  // Method constantly runs
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}