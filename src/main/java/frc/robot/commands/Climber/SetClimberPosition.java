// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences.ClimberPrefs;
import frc.robot.subsystems.Climber;

public class SetClimberPosition extends CommandBase {
  Climber climber;
  SN_DoublePreference position;

  int loopsInTol = 0;

  /** Creates a new SetClimberPosition. */
  public SetClimberPosition(Climber sub_climber, SN_DoublePreference a_position) {
    climber = sub_climber;
    position = a_position;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    loopsInTol = 0;
    climber.setClimberPosition(position);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (climber.isClimberClosedLoopErrorAcceptable()) {
      loopsInTol++;
    } else {
      loopsInTol = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    loopsInTol = 0;
    climber.setClimberSpeed(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return loopsInTol > ClimberPrefs.climberLoopsToFinish.getValue();
  }
}
