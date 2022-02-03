// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;

public class Hood extends SubsystemBase {

  private DoubleSolenoid hoodSolenoid;

  /** Creates a new Hood. */

  public Hood() {
    hoodSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.HoodMap.HOOD_SOLENOID_PCM_A,
        RobotMap.HoodMap.HOOD_SOLENOID_PCM_B);
    // configure is not needed since this is a solenoid
  }

  // check if solenoid is extended
  public boolean isHoodSteep() {
    Value hoodSolenoidStatus = hoodSolenoid.get();
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
    hoodSolenoid.set(Value.kForward);
  }

  public void shallowHood() {
    hoodSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Hood Solenoid", isHoodSteep());
  }
}