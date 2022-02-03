// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hood;

public class SteepenHood extends CommandBase {

  Hood hood;

  /** Creates a new SteepenHood. */
  public SteepenHood(Hood sub_hood) {
    // Use addRequirements() here to declare subsystem dependencies.
    hood = sub_hood;
    addRequirements(hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hood.steepenHood();
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
    return true;
  }
}
