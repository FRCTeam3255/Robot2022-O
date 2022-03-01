// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class RetractIntake extends InstantCommand {

  Intake intake;

  /** Creates a new RetractIntake. */
  public RetractIntake(Intake sub_intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = sub_intake;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.retractIntake();
  }
}