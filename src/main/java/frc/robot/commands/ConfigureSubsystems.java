// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ConfigureSubsystems extends InstantCommand {
  /** Creates a new ConfigureSubsystems. */

  Drivetrain drivetrain;
  Intake intake;
  Shooter shooter;
  Transfer transfer;
  Turret turret;

  public ConfigureSubsystems(Drivetrain a_drivetrain, Intake a_intake, Shooter a_shooter,
      Transfer a_transfer, Turret a_turret) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    drivetrain = a_drivetrain;
    intake = a_intake;
    shooter = a_shooter;
    transfer = a_transfer;
    turret = a_turret;
  }

  @Override
  public void initialize() {

    drivetrain.configure();
    intake.configure();
    shooter.configure();
    transfer.configure();
    turret.configure();
  }
}