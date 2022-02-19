// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.frcteam3255.components.SN_Limelight;

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
