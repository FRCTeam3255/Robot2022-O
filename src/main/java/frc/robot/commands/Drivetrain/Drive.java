// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class Drive extends CommandBase {
  Drivetrain drivetrain;

  /**
   * Creates a new Drive.*
   * sub_drivetrain
   */
  public Drive(Drivetrain sub_drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = sub_drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Read direction of the joystick
    double speed = RobotContainer.DriverStick.getArcadeMove();
    double turn = RobotContainer.DriverStick.getArcadeRotate();

    // Set motors to driection to joywtick
    drivetrain.arcadeDrive(speed, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop spinning the wheels
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
