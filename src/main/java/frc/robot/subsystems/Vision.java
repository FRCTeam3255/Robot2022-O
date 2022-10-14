// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.frcteam3255.components.SN_Limelight;
import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  public SN_Limelight limelight;

  // timer exists because it would flash on and off cause periodic
  private int timer;

  /** Creates a new Vision. */
  public Vision() {
    limelight = new SN_Limelight();
  }

  public void turnLimelightOn() {
    limelight.setLEDMode(LEDMode.on);
  }

  public void turnLimelightOff() {
    limelight.setLEDMode(LEDMode.off);
  }

  // ty, degrees, rpm
  // +23.3, 20, 3000
  // +12.8, 27, 3000
  // +0.7, 35, 3300
  // -5.4, 38, 3600
  // -7.5, 38, 3700
  // -14.3, 38, 4200 (launchpad)
  public double getIdealHoodAngle() {

    double x = limelight.getOffsetY();

    double a = 0.000480075;
    double b = -0.0173149;
    double c = -0.521302;
    double d = 35.4786;

    double angle = (Math.pow(x, 3) * a) + (Math.pow(x, 2) * b) + (x * c) + d;

    return angle;

  }

  public double getIdealShooterRPM() {
    double x = limelight.getOffsetY();

    double a = 1.22666;
    double b = -42.5305;
    double c = 3330.86;

    double rpm = (Math.pow(x, 2) * a) + (x * b) + c;

    return rpm;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("limelight has target", limelight.hasTarget());
    SmartDashboard.putNumber("limelight x error", limelight.getOffsetX());
    SmartDashboard.putNumber("limelight y error", limelight.getOffsetY());
    SmartDashboard.putNumber("limelight target area", limelight.getTargetArea());

    if (RobotController.getUserButton()) {
      if (timer > 25) {
        limelight.toggleLEDs();
      }
    }
    timer++;
  }
}
