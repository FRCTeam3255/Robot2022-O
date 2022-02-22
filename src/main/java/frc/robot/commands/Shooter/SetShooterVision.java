// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class SetShooterVision extends CommandBase {
  Shooter shooter;
  Vision vision;

  double goalRPM;

  /** Creates a new SetShooterVision. */
  public SetShooterVision(Vision sub_vision, Shooter sub_shooter) {
    shooter = sub_shooter;
    vision = sub_vision;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // math here for finding the thing
    goalRPM = (vision.limelight.getOffsetY());
    shooter.setShooterRPM(goalRPM);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
