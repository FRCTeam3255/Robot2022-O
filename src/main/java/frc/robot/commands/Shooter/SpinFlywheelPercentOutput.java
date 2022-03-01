// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.RobotPreferences;

public class SpinFlywheelPercentOutput extends CommandBase {
  /** Creates a new setSpinFlywheelSpeed. */
  // Initializes Shooter Variables
  Shooter shooter;

  public SpinFlywheelPercentOutput(Shooter sub_shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = sub_shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setShooterPercentOutput(RobotPreferences.ShooterPrefs.shooterMotorSpeed.getValue());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterPercentOutput(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
