// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drivetrain.DriveMotionProfile;
import frc.robot.commands.Intake.CollectCargo;
import frc.robot.commands.Transfer.AutoPushCargoToShooter;
import frc.robot.commands.Turret.SetTurretPosition;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Turret;
import static frc.robot.RobotPreferences.AutoPrefs.FourCargoA.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoFourCargoA extends SequentialCommandGroup {

  Drivetrain drivetrain;
  Shooter shooter;
  Turret turret;
  Hood hood;
  Transfer transfer;
  Intake intake;

  DriveMotionProfile driveForwardsToTwo;
  DriveMotionProfile driveBackwardsFromTwo;
  DriveMotionProfile driveForwardsToTerminal;
  DriveMotionProfile driveBackwardsFromTerminal;

  SetShooterRPM setShooterRPM1;
  SetTurretPosition setTurretPosition1;
  SetHoodPosition setHoodPosition1;

  SetShooterRPM setShooterRPM2;
  SetTurretPosition setTurretPosition2;
  SetHoodPosition setHoodPosition2;

  CollectCargo collectCargo;
  AutoPushCargoToShooter shootBall;

  /** Creates a new AutoTwoCargo. */
  public AutoFourCargoA(Drivetrain sub_drivetrain, Shooter sub_shooter, Turret sub_turret, Hood sub_hood,
      Transfer sub_transfer, Intake sub_intake,
      String a_driveForwardsToTwoLeft, String a_driveForwardsToTwoRight,
      String a_driveBackwardsFromTwoLeft, String a_driveBackwardsFromTwoRight,
      String a_driveForwardsToTerminalLeft, String a_driveForwardsToTerminalRight,
      String a_driveBackwardsFromTerminalLeft, String a_driveBackwardsFromTerminalRight) {

    drivetrain = sub_drivetrain;
    shooter = sub_shooter;
    turret = sub_turret;
    hood = sub_hood;
    transfer = sub_transfer;
    intake = sub_intake;

    driveForwardsToTwo = new DriveMotionProfile(drivetrain, a_driveForwardsToTwoLeft, a_driveForwardsToTwoRight);
    driveBackwardsFromTwo = new DriveMotionProfile(drivetrain, a_driveBackwardsFromTwoLeft,
        a_driveBackwardsFromTwoRight);
    driveForwardsToTerminal = new DriveMotionProfile(drivetrain, a_driveForwardsToTerminalLeft,
        a_driveForwardsToTerminalRight);
    driveBackwardsFromTerminal = new DriveMotionProfile(drivetrain, a_driveBackwardsFromTerminalLeft,
        a_driveBackwardsFromTerminalRight);

    setShooterRPM1 = new SetShooterRPM(shooter, auto3shooterRPM1);
    setTurretPosition1 = new SetTurretPosition(turret, auto3turretAngle1);
    setHoodPosition1 = new SetHoodPosition(hood, auto3hoodSteep1);

    setShooterRPM2 = new SetShooterRPM(shooter, auto3shooterRPM2);
    setTurretPosition2 = new SetTurretPosition(turret, auto3turretAngle2);
    setHoodPosition2 = new SetHoodPosition(hood, auto3hoodSteep2);

    collectCargo = new CollectCargo(intake, transfer);
    shootBall = new AutoPushCargoToShooter(shooter, transfer);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        collectCargo.perpetually(),
        setShooterRPM1.perpetually(),
        parallel(driveForwardsToTwo, setTurretPosition1, setHoodPosition1),
        shootBall,
        setShooterRPM2.perpetually(),
        parallel(driveForwardsToTerminal, setTurretPosition2, setHoodPosition2),
        driveBackwardsFromTerminal,
        shootBall);
  }
}
