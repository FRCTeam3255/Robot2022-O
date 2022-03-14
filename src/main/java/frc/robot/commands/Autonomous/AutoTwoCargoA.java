// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drivetrain.DriveDistance;
import frc.robot.commands.Intake.CollectCargo;
import frc.robot.commands.Transfer.PushCargoSimple;
import frc.robot.commands.Turret.SetTurretPosition;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Turret;
import static frc.robot.RobotPreferences.AutoPrefs.TwoCargoA.*;

import com.frcteam3255.preferences.SN_DoublePreference;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTwoCargoA extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Shooter shooter;
  Turret turret;
  Hood hood;
  Transfer transfer;
  Intake intake;

  DriveDistance driveToOneOrThree;
  SetShooterRPM setShooterRPM;
  SetTurretPosition setTurretPosition;
  SetHoodPosition setHoodPosition;
  CollectCargo collectCargo;
  PushCargoSimple pushCargoSimple;

  /** Creates a new AutoTwoCargoA. */
  public AutoTwoCargoA(Drivetrain sub_drivetrain, Shooter sub_shooter, Turret sub_turret, Hood sub_hood,
      Transfer sub_transfer, Intake sub_intake, SN_DoublePreference a_inchsToDrive,
      SN_DoublePreference a_peakPercentOutput) {

    drivetrain = sub_drivetrain;
    shooter = sub_shooter;
    turret = sub_turret;
    hood = sub_hood;
    transfer = sub_transfer;
    intake = sub_intake;

    driveToOneOrThree = new DriveDistance(sub_drivetrain, a_inchsToDrive, a_peakPercentOutput);
    setShooterRPM = new SetShooterRPM(shooter, auto1shooterRPM);
    setTurretPosition = new SetTurretPosition(turret, auto1turretAngle);
    setHoodPosition = new SetHoodPosition(hood, auto1hoodSteep);
    collectCargo = new CollectCargo(intake, transfer);
    pushCargoSimple = new PushCargoSimple(shooter, transfer);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        parallel(
            collectCargo.until(transfer::isTopBallCollected),
            driveToOneOrThree,
            setTurretPosition,
            setHoodPosition),

        parallel(
            setShooterRPM, pushCargoSimple));
  }
}