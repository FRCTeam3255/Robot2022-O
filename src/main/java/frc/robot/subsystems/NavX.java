// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NavX extends SubsystemBase {

  public AHRS navx;

  /** Creates a new NavX. */
  public NavX() {
    navx = new AHRS();
  }

  public void resetHeading() {
    navx.reset();
  }

  public void calibrate() {
    navx.calibrate();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("is navx connected", navx.isConnected());
    SmartDashboard.putNumber("navx yaw", navx.getYaw());
  }
}
