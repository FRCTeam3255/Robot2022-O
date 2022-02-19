// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.frcteam3255.joystick.SN_DualActionStick;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.Hood.*;
import frc.robot.commands.Turret.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Climber.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // Joysticks
  public static final SN_DualActionStick DriverStick = new SN_DualActionStick(RobotMap.ControllerMap.DRIVER_STICK);
  public static final SN_DualActionStick coDriverStick = new SN_DualActionStick(RobotMap.ControllerMap.CODRIVER_STICK);

  // Subsystems
  private final Drivetrain sub_drivetrain = new Drivetrain();
  private final Hood sub_hood = new Hood();
  private final Turret sub_turret = new Turret();
  private final Intake sub_intake = new Intake();
  private final Shooter sub_shooter = new Shooter();
  private final Climber sub_climber = new Climber();
  private final Transfer sub_transfer = new Transfer();
  public static final NavX sub_navX = new NavX();

  // Drivetrain Commands
  private final Drive com_drive = new Drive(sub_drivetrain);

  private final DriveMotionProfile com_driveTestPath = new DriveMotionProfile(sub_drivetrain,
      "testpath_left.csv", "testpath_right.csv");
  private final DriveMotionProfile com_drive2020Field = new DriveMotionProfile(sub_drivetrain,
      "full2020path_left.csv", "full2020path_right.csv");
  private final DriveMotionProfile com_driveHanger = new DriveMotionProfile(sub_drivetrain,
      "hanger_left.csv", "hanger_right.csv");

  // Hood Commands
  private final ShallowHood com_shallow_hood = new ShallowHood(sub_hood);
  private final SteepenHood com_steepen_hood = new SteepenHood(sub_hood);

  // Turret Commands
  private final ManualRotateTurret com_manualRotateTurret = new ManualRotateTurret(sub_turret);

  // Shooter Commands
  private final PushCargoToShooter com_pushCargoToShooter = new PushCargoToShooter(sub_shooter, sub_transfer);
  private final SpinFlywheel com_spinFlywheel = new SpinFlywheel(sub_shooter);
  // Transfer Commands

  // Intake Commands
  private final CollectCargo com_collect = new CollectCargo(sub_intake, sub_transfer);
  private final RetractIntake com_retractIntake = new RetractIntake(sub_intake);
  private final DeployIntake com_deployIntake = new DeployIntake(sub_intake);

  // Vision Commands

  // Climber Commands

  private final Climb com_climb = new Climb(sub_climber);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDashboardButtons();
    sub_drivetrain.setDefaultCommand(com_drive);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    coDriverStick.btn_RTrig.whileHeld(com_pushCargoToShooter);
    coDriverStick.btn_RTrig.whileHeld(com_spinFlywheel);

    coDriverStick.btn_LTrig.whileHeld(com_collect);
    coDriverStick.btn_Y.whenPressed(com_retractIntake);
    coDriverStick.btn_X.whenPressed(com_deployIntake);
    coDriverStick.POV_West.whileHeld(com_manualRotateTurret);

    coDriverStick.btn_A.whenPressed(com_steepen_hood);
    coDriverStick.btn_B.whenPressed(com_shallow_hood);

    // btn_LStick can become btn_RStick for dominant hand
    coDriverStick.btn_LStick.whileHeld(com_climb);
  }

  /**
   * Use this method to define your dashboard buttons
   */
  private void configureDashboardButtons() {
    SmartDashboard.putData("Reset Climber Encoders",
        new InstantCommand(sub_climber::resetClimberEncoderCount, sub_climber));
    SmartDashboard.putData("Reset Drivetrain Encoders",
        new InstantCommand(sub_drivetrain::resetDrivetrainEncodersCount, sub_drivetrain));
    SmartDashboard.putData("Reset Intake Encoders",
        new InstantCommand(sub_intake::resetIntakeEncoderCount, sub_intake));
    SmartDashboard.putData("Reset Turret Encoders",
        new InstantCommand(sub_turret::resetTurretEncoderCounts, sub_turret));
    SmartDashboard.putData("Reset Shooter Encoders",
        new InstantCommand(sub_shooter::resetShooterEncoderCounts, sub_shooter));
    SmartDashboard.putData("Reset NavX Heading",
        new InstantCommand(sub_navX::resetHeading, sub_navX));
    SmartDashboard.putData("Calibrate NavX",
        new InstantCommand(sub_navX::calibrate, sub_navX));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return com_driveHanger;
  }
}
