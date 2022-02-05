// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase {

  private DoubleSolenoid angleHoodSolenoid;
  private DoubleSolenoid.Value shallowAngleHoodValue = Value.kReverse;
  private DoubleSolenoid.Value steepAngleHoodValue = Value.kForward;

  /** Creates a new Hood. */

  public Hood() {
    angleHoodSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
        RobotMap.HoodMap.HOOD_SOLENOID_STEEP_ANGLE_PCM_A,
        RobotMap.HoodMap.HOOD_SOLENOID_SHALLOW_ANGLE_PCM_B);
    // configure is not needed since this is a solenoid
  }

  // check if solenoid is extended
  public boolean isHoodSteep() {
    Value hoodSolenoidStatus = angleHoodSolenoid.get();
    boolean isHoodSteep = false;

    if (hoodSolenoidStatus == DoubleSolenoid.Value.kForward) {
      isHoodSteep = true;
    } else {
      isHoodSteep = false;
    }

    return isHoodSteep;
  }

  // solenoid commands

  public void steepenHood() {
    angleHoodSolenoid.set(steepAngleHoodValue);

  }

  public void shallowHood() {
    angleHoodSolenoid.set(shallowAngleHoodValue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Hood Solenoid", isHoodSteep());
  }
}