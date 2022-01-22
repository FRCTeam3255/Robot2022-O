// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Hood;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Hood;

public class AngleHood extends CommandBase {

  Hood hood;
  private SN_DoublePreference degrees;

  /** Creates a new AngleHood. */
  public AngleHood(Hood sub_hood, SN_DoublePreference angleHoodDirection) {
    // Use addRequirements() here to declare subsystem dependencies.
    hood = sub_hood;
    degrees = angleHoodDirection;
    addRequirements(hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hood.moveHoodToDegree(hood.getHoodPosition() + degrees.getValue());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
