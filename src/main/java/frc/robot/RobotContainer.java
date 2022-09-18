// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.frcteam3255.joystick.SN_DualActionStick;
import com.frcteam3255.joystick.SN_F310Gamepad;
import com.frcteam3255.joystick.SN_SwitchboardStick;
import com.frcteam3255.utils.SN_InstantCommand;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.ConfigureSubsystems;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.Turret.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Transfer.*;
import frc.robot.RobotPreferences.TurretPrefs;
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
  public static final SN_F310Gamepad DriverStick = new SN_F310Gamepad(
      RobotMap.ControllerMap.DRIVER_STICK);
  public static final SN_DualActionStick coDriverStick = new SN_DualActionStick(
      RobotMap.ControllerMap.CODRIVER_STICK);
  public static final SN_SwitchboardStick switchBoard = new SN_SwitchboardStick(
      RobotMap.ControllerMap.SWITCH_BOARD);

  // Subsystems
  private final Drivetrain sub_drivetrain = new Drivetrain();
  private final Hood sub_hood = new Hood();
  private final Turret sub_turret = new Turret();
  private final Intake sub_intake = new Intake();
  private final Shooter sub_shooter = new Shooter();
  private final Transfer sub_transfer = new Transfer();
  private final NavX sub_navX = new NavX();
  private final Vision sub_vision = new Vision();

  // Drivetrain Commands
  private final Drive com_drive = new Drive(sub_drivetrain);

  // Hood Commands

  // Turret Commands
  private final MoveTurret com_moveTurret = new MoveTurret(sub_turret);
  private final SetTurretPosition com_setTurretCenter = new SetTurretPosition(sub_turret,
      RobotPreferences.zeroDoublePref);
  private final VisionAimTurret com_visionAimTurret = new VisionAimTurret(sub_turret, sub_shooter, sub_vision);
  private final VisionSpinTurret com_visionSpinTurret = new VisionSpinTurret(sub_turret, sub_shooter, sub_vision);

  // Shooter Commands
  private final PushCargoSimple com_pushCargoSimple = new PushCargoSimple(sub_shooter, sub_transfer);
  private final SpinFlywheelGoalRPM com_spinFlywheelGoalRPM = new SpinFlywheelGoalRPM(sub_shooter);
  private final InstantCommand com_setUpperHubGoal = new InstantCommand(sub_shooter::setGoalUpperHub);

  // Shooter Presets

  private final SetTurretPosition com_presetToIntake = new SetTurretPosition(sub_turret,
      TurretPrefs.turretSnapToIntake);
  private final SetTurretPosition com_presetAwayIntake = new SetTurretPosition(sub_turret,
      TurretPrefs.turretSnapAwayIntake);

  // Transfer Commands
  private final ReverseTransfer com_reverseTransfer = new ReverseTransfer(sub_transfer, sub_intake);

  // Intake Commands
  private final CollectCargo com_collect = new CollectCargo(sub_intake,
      sub_transfer);
  private final RetractIntake com_retractIntake = new RetractIntake(sub_intake);
  private final DeployIntake com_deployIntake = new DeployIntake(sub_intake);

  // Vision Commands

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDashboardButtons();
    sub_drivetrain.setDefaultCommand(com_drive);
    com_setUpperHubGoal.initialize(); // upper hub needs to be set as goal
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver Stick (there's nothing)

    // coDriver Stick

    coDriverStick.btn_RTrig.whileHeld(com_pushCargoSimple);
    coDriverStick.btn_RTrig.whileHeld(com_spinFlywheelGoalRPM);
    coDriverStick.btn_LTrig.whileHeld(com_collect);

    coDriverStick.btn_RBump.whenPressed(com_spinFlywheelGoalRPM);
    coDriverStick.btn_LBump.whileHeld(com_moveTurret);

    // Limelight Commands
    coDriverStick.btn_A.whileHeld(com_visionAimTurret);

    coDriverStick.btn_B.whileHeld(com_reverseTransfer);
    // Just Setting Angle (X Axis)
    coDriverStick.btn_X.whileHeld(com_visionSpinTurret);

    coDriverStick.btn_Back.whenPressed(com_retractIntake);

    // Foward = Facing Intake
    coDriverStick.btn_RStick.whenPressed(com_presetToIntake);
    coDriverStick.btn_LStick.whenPressed(com_presetAwayIntake);
  }

  /**
   * Use this method to define your dashboard buttons
   */
  private void configureDashboardButtons() {
    // Reset Encoders
    SmartDashboard.putData("Reset Drivetrain Encoders",
        new SN_InstantCommand(sub_drivetrain::resetDrivetrainEncodersCount, true,
            sub_drivetrain));
    SmartDashboard.putData("Reset Intake Encoders",
        new SN_InstantCommand(sub_intake::resetIntakeEncoderCount, true, sub_intake));
    SmartDashboard.putData("Reset Turret Encoders",
        new SN_InstantCommand(sub_turret::resetTurretEncoderCounts, true, sub_turret));
    SmartDashboard.putData("Reset Shooter Encoders",
        new SN_InstantCommand(sub_shooter::resetShooterEncoderCounts, true, sub_shooter));
    SmartDashboard.putData("Reset NavX Heading",
        new InstantCommand(sub_navX::resetHeading, sub_navX));

    // Calibration
    SmartDashboard.putData("Calibrate NavX",
        new InstantCommand(sub_navX::calibrate, sub_navX));

    // Configure Resets (Each Subsystem & All Subsystems at once)

    SmartDashboard.putData("Configure Drivetrain",
        new InstantCommand(sub_drivetrain::configure, sub_drivetrain));

    SmartDashboard.putData("Configure Intake",
        new InstantCommand(sub_intake::configure, sub_intake));

    SmartDashboard.putData("Configure Shooter",
        new InstantCommand(sub_shooter::configure, sub_shooter));

    SmartDashboard.putData("Configure Transfer",
        new InstantCommand(sub_transfer::configure, sub_shooter));

    SmartDashboard.putData("Configure Turret",
        new InstantCommand(sub_turret::configure, sub_turret));

    SmartDashboard.putData("Configure All Subsystems",
        new ConfigureSubsystems(sub_drivetrain, sub_intake, sub_shooter, sub_transfer, sub_turret));

    SmartDashboard.putData("Deplay Intake", com_deployIntake);
    SmartDashboard.putData("Retract Intake", com_retractIntake);

    SmartDashboard.putData("Set Turret Zero", com_setTurretCenter);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    if (switchBoard.btn_1.get()) {
      return null;
    } else {
      return null;
    }
  }
}
