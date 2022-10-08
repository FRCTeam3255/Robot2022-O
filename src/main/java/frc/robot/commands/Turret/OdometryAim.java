// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;

public class OdometryAim extends CommandBase {

  Turret turret;
  Drivetrain drivetrain;

  double hypotenuse;
  double adjacent; // x axis
  double opposite; // y axis

  double angleToHub;

  double robotX;
  double robotY;
  double robotAngle;

  double robotAdjustedAngleToHub;
  double turretAdjustedAngleToHub;

  double goalAngleDegrees;

  double hubX = Units.inchesToMeters(26);
  double hubY = Units.inchesToMeters(13.5);

  /** Creates a new OdometryAim. */
  public OdometryAim(Turret sub_turret, Drivetrain sub_drivetrain) {
    turret = sub_turret;
    drivetrain = sub_drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sub_turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    robotX = drivetrain.getPose().getX();
    robotY = drivetrain.getPose().getY();
    robotAngle = drivetrain.getPose().getRotation().getRadians();

    adjacent = Math.abs(robotX - hubX);
    opposite = Math.abs(robotY - hubY);
    hypotenuse = Math.sqrt((Math.pow(adjacent, 2) + Math.pow(opposite, 2)));

    angleToHub = Math.atan(opposite / adjacent);

    robotAdjustedAngleToHub = angleToHub + robotAngle;
    turretAdjustedAngleToHub = robotAdjustedAngleToHub + Units.degreesToRadians(turret.getTurretAngle());

    goalAngleDegrees = Units.radiansToDegrees(robotAdjustedAngleToHub);

    // turret.setTurretAngle(goalAngleDegrees);

    SmartDashboard.putNumber("odometry aim hypotenuse", hypotenuse);
    SmartDashboard.putNumber("odometry aim adjacent", adjacent);
    SmartDashboard.putNumber("odometry aim opposite", opposite);
    SmartDashboard.putNumber("odometry aim angle to hub (degrees)", Units.radiansToDegrees(angleToHub));
    SmartDashboard.putNumber("odometry aim robotX", robotX);
    SmartDashboard.putNumber("odometry aim robotY", robotY);
    SmartDashboard.putNumber("odometry aim robotAngle (degrees)", Units.radiansToDegrees(robotAngle));
    SmartDashboard.putNumber(
        "odometry aim robotAdjustedAngleToHub (degrees)", Units.radiansToDegrees(robotAdjustedAngleToHub));
    SmartDashboard.putNumber("odometry aim goalAngleDegrees", goalAngleDegrees);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
