// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotPreferences.AutoPrefs;
import frc.robot.RobotPreferences.DrivetrainPrefs;
import frc.robot.commands.Intake.CollectCargo;
import frc.robot.commands.Shooter.PresetShooter;
import frc.robot.commands.Shooter.SpinFlywheelGoalRPM;
import frc.robot.commands.Transfer.PushCargoSimple;
import frc.robot.commands.Turret.SetTurretPosition;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OpenLoopTwoBall extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Shooter shooter;
  Turret turret;
  Hood hood;
  Transfer transfer;
  Intake intake;

  /** Creates a new OpenLoopTwoBall. */
  public OpenLoopTwoBall(Drivetrain sub_drivetrain, Shooter sub_shooter, Turret sub_turret, Hood sub_hood,
      Transfer sub_transfer, Intake sub_intake) {

    drivetrain = sub_drivetrain;
    shooter = sub_shooter;
    turret = sub_turret;
    hood = sub_hood;
    transfer = sub_transfer;
    intake = sub_intake;

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new InstantCommand(drivetrain::resetDrivetrainEncodersCount),
        new InstantCommand(shooter::setGoalUpperHub),
        new PresetShooter(shooter, hood, AutoPrefs.OpenLoopTwoBall.auto4shooterRPM,
            AutoPrefs.OpenLoopTwoBall.auto4hoodSteep, null, null),

        parallel(
            new DriveDistanceOpenLoop(drivetrain, AutoPrefs.OpenLoopTwoBall.auto4dist1,
                DrivetrainPrefs.driveOpenLoopSpeedForward),
            (new CollectCargo(intake, transfer)).perpetually().until(transfer::isBottomBallCollected)),
        new DriveDistanceOpenLoop(drivetrain, AutoPrefs.OpenLoopTwoBall.auto4dist2,
            DrivetrainPrefs.driveOpenLoopSpeedReverse),
        new WaitCommand(2),
        parallel(new SpinFlywheelGoalRPM(shooter), new PushCargoSimple(shooter, transfer)).withTimeout(8)

    );
  }
}
// what this subclass is based off of

// new InstantCommand(sub_drivetrain::resetDrivetrainEncodersCount).andThen(
// new PresetShooter(sub_shooter, sub_hood,
// AutoPrefs.OpenLoopTwoBall.auto4shooterRPM,
// AutoPrefs.OpenLoopTwoBall.auto4hoodSteep, null,
// null).alongWith(com_setUpperHubGoal)
// .andThen(com_driveOpenLoop.alongWith(com_collect.perpetually().until(sub_transfer::isTopBallCollected)))
// .andThen(com_pushCargoSimple).alongWith(com_spinFlywheelGoalRPM).withTimeout(5));
