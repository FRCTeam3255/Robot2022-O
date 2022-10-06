// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.frcteam3255.components.SN_Limelight;
import com.frcteam3255.components.SN_Limelight.LEDMode;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotPreferences;

public class Vision extends SubsystemBase {

  public SN_Limelight limelight;

  // timer exists because it would flash on and off cause periodic
  private int timer;

  /** Creates a new Vision. */
  public Vision() {
    limelight = new SN_Limelight();
  }

  // public double getIdealUpperHubRPM() {
  // double a = 10.998;
  // double b = 34.0879;
  // double c = 3600;
  // double x = limelight.getOffsetY();
  // return (a * (x * x)) + (b * x) + c;
  // }

  public double getIdealMediumHoodRPM() {
    double a = 3338.9172;
    double b = 0.9929;
    double x = limelight.getOffsetY();
    return a * Math.pow(b, x);
  }

  public double getIdealLowerHubRPM() {
    return /* different regression */ limelight.getOffsetY(); // TODO: find regression
  }

  public void turnLimelightOn() {
    limelight.setLEDMode(LEDMode.on);
  }

  public void turnLimelightOff() {
    limelight.setLEDMode(LEDMode.off);
  }

  public double limelightDistanceFromGoal() {
    double goalAngleDegrees = RobotPreferences.VisionPrefs.limelightMountAngle.getValue() + limelight.getOffsetY();
    double goalAngleRadians = goalAngleDegrees * (3.14159 / 180.0);

    double limelightDistanceFromGoal = (RobotPreferences.VisionPrefs.highHubHeight.getValue()
        - RobotPreferences.VisionPrefs.limelightMountHeight.getValue()) / Math.tan(goalAngleRadians);
    return limelightDistanceFromGoal;
  }

  public double limelightDistanceRPM() {
    double limelightDistanceFromGoal = limelightDistanceFromGoal();
    double calculatedRPM = 0; // TODO: get actual datapoints for this calculation
    return calculatedRPM;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("limelight has target", limelight.hasTarget());
    SmartDashboard.putNumber("limelight x error", limelight.getOffsetX());
    SmartDashboard.putNumber("limelight y error", limelight.getOffsetY());
    SmartDashboard.putNumber("limelight target area", limelight.getTargetArea());
    SmartDashboard.putNumber("limelight Ideal Upper Hub RPM", getIdealMediumHoodRPM());
    SmartDashboard.putNumber("limelight Idead Lower Hub RPM", getIdealLowerHubRPM());
    SmartDashboard.putNumber("limelight distance from hub", limelightDistanceFromGoal());

    if (RobotController.getUserButton()) {
      if (timer > 25) {
        limelight.toggleLEDs();
      }
    }
    timer++;
  }
}
