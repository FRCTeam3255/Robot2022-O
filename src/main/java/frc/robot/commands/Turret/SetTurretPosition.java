// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Turret;

public class SetTurretPosition extends CommandBase {
  Turret turret;
  double degrees;
  SN_DoublePreference degreesPref;
  Climber climber;

  int loopsInTol;
  int loopsToFinish;

  /** Creates a new SetTurretPosition. */
  public SetTurretPosition(Turret a_turret, SN_DoublePreference a_degreesPref, Climber a_climber) {
    turret = a_turret;
    degreesPref = a_degreesPref;
    climber = a_climber;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    degrees = degreesPref.getValue();

    loopsInTol = 0;
    loopsToFinish = RobotPreferences.TurretPrefs.turretLoopsToFinish.getValue();

    if (!climber.isPistonAngled()) { // do not let the turret move when the climber is angled
      turret.setTurretAngle(degrees);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(turret.getTurretClosedLoopErrorDegrees()) < RobotPreferences.TurretPrefs.turretMaxAllowableErrorDegrees
        .getValue()) {
      loopsInTol++;
    } else {
      loopsInTol = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.setTurretSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return loopsInTol > loopsToFinish;
  }
}
