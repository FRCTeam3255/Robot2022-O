// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class RunSpool extends CommandBase {

  Climber climber;

  /** Creates a new RunSpool. */
  public RunSpool(Climber sub_climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = sub_climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = 0;
    if (climber.isHookDeployed()) {
      if (RobotContainer.DriverStick.getAxisRT() > 0) {
        climber.unlockClimber();
        speed = RobotContainer.DriverStick.getAxisRT();
      }

      if (RobotContainer.DriverStick.getAxisLT() > 0) {
        climber.unlockClimber();
        speed = -RobotContainer.DriverStick.getAxisRT();
      }
    } else {
      speed = 0;
      climber.lockClimber();
    }
    climber.setClimberSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setClimberSpeed(0);
    climber.lockClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
