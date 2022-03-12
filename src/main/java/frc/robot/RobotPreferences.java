package frc.robot;

import com.frcteam3255.preferences.SN_BooleanPreference;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.preferences.SN_IntPreference;
import com.frcteam3255.preferences.SN_ZeroDoublePreference;
import com.frcteam3255.preferences.SN_ZeroIntPreference;

public final class RobotPreferences {

  // when mechanical builds stuff, it's almost always (and should be) designed to
  // where the motors can run at full speed. unless there is some actual real
  // issue, we shouldn't make the motors run slower than they should. for example,
  // in 2020, the turret was set to run half speed for no real reason. this made
  // our bot worse than it could have been. this year, the turret is (unless this
  // is unsafe for some reason) going to run at full speed under closed loop
  // control

  // The one situation where this doesn't apply is when the motor is under open
  // loop, user control. it may not be practical for a user to be controlling a
  // mechanism at full speed, so it's fine to slow it down then. Usually though,
  // mechanisms that users control will not need to have granular control, and
  // instead should use presets (closed loop, full speed control). Eg 2020 hood,
  // 2019 cascade, etc.

  public static final SN_ZeroIntPreference zeroIntPref = new SN_ZeroIntPreference();
  public static final SN_ZeroDoublePreference zeroDoublePref = new SN_ZeroDoublePreference();
  public static final SN_DoublePreference encoderCountsPerTalonFXRotation = new SN_DoublePreference(
      "encoderCountsPerTalonFXRotation", 2048);

  public static final class DrivetrainPrefs {
    public static final SN_DoublePreference arcadeSpeed = new SN_DoublePreference("arcadeSpeed", 0.75);
    public static final SN_DoublePreference arcadeLowSpeed = new SN_DoublePreference("arcadeLowSpeed", 0.5);
    public static final SN_DoublePreference arcadeHighSpeed = new SN_DoublePreference("arcadeHighSpeed", 1);
    public static final SN_DoublePreference arcadeTurn = new SN_DoublePreference("arcadeTurn", 1);

    public static final SN_DoublePreference driveF = new SN_DoublePreference("driveF", 0);
    public static final SN_DoublePreference driveP = new SN_DoublePreference("driveP", 1);
    public static final SN_DoublePreference driveI = new SN_DoublePreference("driveI", 0);
    public static final SN_DoublePreference driveD = new SN_DoublePreference("driveD", 0);

    public static final SN_DoublePreference driveAllowableClosedLoopErrorInches = new SN_DoublePreference(
        "driveAllowableClosedLoopErrorInches", 2);
    public static final SN_DoublePreference driveClosedLoopPeakOutput = new SN_DoublePreference(
        "driveClosedLoopPeakOutput", 1);
    public static final SN_DoublePreference driveLoopsToFinish = new SN_DoublePreference("driveLoopsToFinish", 25);

    // drivetrain gear ratio: 10:60 aka motor rotates once, wheel rotates 1/6
    // 2048 counts per motor rotation, * 6 is 12288 counts per wheel rotation
    // 4 inch wheel * pi = inches per rot: 12.56637
    // 12288 counts per rot / 12.56637 inches per rot = 978 counts per inch
    // 978 counts per inch * 12 = 11734 counts per foot
    public static final SN_IntPreference driveEncoderCountsPerFoot = new SN_IntPreference(
        "driveEncoderCountsPerFoot", 11734);

    public static final SN_IntPreference motionProfileMinBufferedPoints = new SN_IntPreference(
        "motionProfileMinBufferedPoints", 10);
    public static final SN_DoublePreference driveOpenLoopRampTimeSeconds = new SN_DoublePreference(
        "driveOpenLoopRampTimeSeconds", .25);

    public static final SN_BooleanPreference driveBreakMode = new SN_BooleanPreference("driveBreakMode", false);

    public static final SN_DoublePreference driveMaximumArcadeSpeedDelta = new SN_DoublePreference(
        "driveMaximumArcadeSpeedDelta", .01);

    // value is in controller input delta per second, eg 0.3 means that the
    // controller can only change by 0.3 in a second
    public static final SN_DoublePreference drivePosSlewRateLimit = new SN_DoublePreference(
        "drivePosSlewRateLimit", .7);
    public static final SN_DoublePreference driveNegSlewRateLimit = new SN_DoublePreference(
        "driveNegSlewRateLimit", .7);
    public static final SN_BooleanPreference driveSquaredInputs = new SN_BooleanPreference("driveSquaredInputs", true);

  }

  public static final class HoodPrefs {
    public static final SN_BooleanPreference hoodPresetUpperFenderSteep = new SN_BooleanPreference(
        "hoodPresetUpperFenderSteep", false);
    public static final SN_BooleanPreference hoodPresetUpperTarmacSteep = new SN_BooleanPreference(
        "hoodPresetUpperTarmacSteep", true);
    public static final SN_BooleanPreference hoodPresetUpperLaunchpadSteep = new SN_BooleanPreference(
        "hoodPresetUpperLaunchpadSteep", true);
    public static final SN_BooleanPreference hoodPresetUpperTerminalSteep = new SN_BooleanPreference(
        "hoodPresetUpperTerminalSteep", true);

    public static final SN_BooleanPreference hoodPresetLowerFenderSteep = new SN_BooleanPreference(
        "hoodPresetLowerFenderSteep", true);
    public static final SN_BooleanPreference hoodPresetLowerTarmacSteep = new SN_BooleanPreference(
        "hoodPresetLowerTarmacSteep", true);
    public static final SN_BooleanPreference hoodPresetLowerLaunchpadSteep = new SN_BooleanPreference(
        "hoodPresetLowerLaunchpadSteep", true);
    public static final SN_BooleanPreference hoodPresetLowerTerminalSteep = new SN_BooleanPreference(
        "hoodPresetLowerTerminalSteep", true);
  }

  public static final class ShooterPrefs {
    public static final SN_DoublePreference shooterPercentOutput = new SN_DoublePreference(
        "shooterPercentOutput", 1);
    public static final SN_DoublePreference shooterTargetRPM = new SN_DoublePreference(
        "shooterTargetRPM", 5000);
    public static final SN_DoublePreference shooterAcceptableErrorRPM = new SN_DoublePreference(
        "shooterAcceptableErrorRPM", 100);

    public static final SN_BooleanPreference shooterInvert = new SN_BooleanPreference(
        "shooterInvert", false);

    public static final SN_DoublePreference shooterEncoderCountsPerDegrees = new SN_DoublePreference(
        "shooterEncoderCountsPerDegrees", 84);

    public static final SN_DoublePreference shooterF = new SN_DoublePreference("shooterF", 0.05);
    public static final SN_DoublePreference shooterP = new SN_DoublePreference("shooterP", 0.005);
    public static final SN_DoublePreference shooterI = new SN_DoublePreference("shooterI", 0.0001);
    public static final SN_DoublePreference shooterD = new SN_DoublePreference("shooterD", 0);

    public static final SN_DoublePreference shooterPresetUpperFenderRPM = new SN_DoublePreference(
        "shooterPresetUpperFenderRPM", 3100);
    public static final SN_DoublePreference shooterPresetUpperTarmacRPM = new SN_DoublePreference(
        "shooterPresetUpperTarmacRPM", 4000);
    public static final SN_DoublePreference shooterPresetUpperLaunchpadRPM = new SN_DoublePreference(
        "shooterPresetUpperLaunchpadRPM", 4500);
    public static final SN_DoublePreference shooterPresetUpperTerminalRPM = new SN_DoublePreference(
        "shooterPresetUpperTerminalRPM", 5000);

    public static final SN_DoublePreference shooterPresetLowerFenderRPM = new SN_DoublePreference(
        "shooterPresetLowerFenderRPM", 2000);
    public static final SN_DoublePreference shooterPresetLowerTarmacRPM = new SN_DoublePreference(
        "shooterPresetLowerTarmacRPM", 2500);
    public static final SN_DoublePreference shooterPresetLowerLaunchpadRPM = new SN_DoublePreference(
        "shooterPresetLowerLaunchpadRPM", 3000);
    public static final SN_DoublePreference shooterPresetLowerTerminalRPM = new SN_DoublePreference(
        "shooterPresetLowerTerminalRPM", 3500);

    public static final SN_IntPreference shooterIgnoreRPMTimeAfterShotLoops = new SN_IntPreference(
        "shooterIgnoreRPMTimeAfterShotLoops", 25);
  }

  public static final class TurretPrefs {
    public static final SN_DoublePreference turretMaxAngleDegrees = new SN_DoublePreference("turretMaxAngleDegrees",
        360);
    public static final SN_DoublePreference turretMinAngleDegrees = new SN_DoublePreference("turretMinAngleDegrees",
        0);

    // 2048 encoder counts per rotation * 65 (gr) = 133120
    // 133120 / 360 = 370
    public static final SN_DoublePreference turretEncoderCountsPerDegrees = new SN_DoublePreference(
        "turretEncoderCountsPerDegrees", 370);
    public static final SN_DoublePreference turretMaxAllowableErrorDegrees = new SN_DoublePreference(
        "turretMaxAllowableErrorDegrees",
        1);
    public static final SN_DoublePreference turretClosedLoopPeakOutput = new SN_DoublePreference(
        "turretClosedLoopPeakOutput", 1);
    public static final SN_DoublePreference turretF = new SN_DoublePreference("turretF", 0);
    public static final SN_DoublePreference turretP = new SN_DoublePreference("turretP", 1);
    public static final SN_DoublePreference turretI = new SN_DoublePreference("turretI", 0);
    public static final SN_DoublePreference turretD = new SN_DoublePreference("turretD", 0);

    public static final SN_IntPreference turretLoopsToFinish = new SN_IntPreference("turretLoopsToFinish", 25);

    public static final SN_DoublePreference turretPresetPos1 = new SN_DoublePreference("turretTestPos", 45);
    public static final SN_DoublePreference turretOpenLoopSpeed = new SN_DoublePreference("turretOpenLoopSpeed", .3);
  }

  public static final class TransferPrefs {
    public final static SN_DoublePreference transferEntranceSpeed = new SN_DoublePreference(
        "transferEntranceSpeed", .8);
    public final static SN_DoublePreference transferEntranceRejectSpeed = new SN_DoublePreference(
        "transferEntranceRejectSpeed", -.8);
    public final static SN_DoublePreference transferBeltSpeed = new SN_DoublePreference(
        "transferBeltSpeed", 1);

    public final static SN_BooleanPreference transferEntranceInvert = new SN_BooleanPreference(
        "transferEntranceInvert", true);
    public final static SN_BooleanPreference transferBottomBeltInvert = new SN_BooleanPreference(
        "transferBottomBeltInvert", false);
    public final static SN_BooleanPreference transferTopBeltInvert = new SN_BooleanPreference(
        "transferTopBeltInvert", false);

    // one loop is 20ms
    public final static SN_IntPreference transferRejectTimerLoops = new SN_IntPreference(
        "transferRejectLatchTimeLoops", 25);

    // Transfer ramping
    public final static SN_DoublePreference transferRampTime = new SN_DoublePreference("transferRampTime", 0);

  }

  public static final class IntakePrefs {
    public final static SN_BooleanPreference intakeMotorInvert = new SN_BooleanPreference(
        "intakeMotorInvert", false);
    public final static SN_BooleanPreference intakePistonInvert = new SN_BooleanPreference(
        "intakePistonInvert", false);

    public final static SN_DoublePreference intakeCollectSpeed = new SN_DoublePreference(
        "intakeCollectSpeed", 0.80);
    public final static SN_DoublePreference intakeRejectSpeed = new SN_DoublePreference(
        "intakeRejectSpeed", -0.80);
    // one loop is 20ms
    public final static SN_IntPreference intakeRejectTimerLoops = new SN_IntPreference(
        "intakeRejectLatchTimeLoops", 50);
    public final static SN_IntPreference colorSensorMinProximity = new SN_IntPreference(
        "colorSensorMinProximity", 1000);
  }

  public static final class VisionPrefs {
  }

  public static final class ClimberPrefs {
    public static final SN_DoublePreference climberMotorSpeed = new SN_DoublePreference("climberMotorSpeed", 0.5);
    public static final SN_DoublePreference climberMaxEncoderCountPerpendicular = new SN_DoublePreference(
        "climberMaxEncoderCount", 150000);
    public static final SN_DoublePreference climberMaxEncoderCountAngled = new SN_DoublePreference(
        "climberMaxEncoderCountPivoted", 200000);
    public static final SN_DoublePreference climberMinEncoderCount = new SN_DoublePreference(
        "climberMinEncoderCount", 0);

    // Climbing Up/Down Positions
    public static final SN_DoublePreference climberUpPosition = new SN_DoublePreference("climberUpPosition", 32555);
    public static final SN_DoublePreference climberDownPosition = new SN_DoublePreference("climberDownPosition", 0);

    public static final SN_BooleanPreference climberLockPistonInvert = new SN_BooleanPreference(
        "climberLockPistonInvert", false);
    public static final SN_BooleanPreference climberPivotPistonInvert = new SN_BooleanPreference(
        "climberPivotPistonInvert", false);
    public static final SN_BooleanPreference climberHookPistonInvert = new SN_BooleanPreference(
        "climberHookPistonInvert", false);

    public static final SN_IntPreference climberLoopsToFinish = new SN_IntPreference("climberLoopsToFinish", 25);
    public static final SN_DoublePreference climberAcceptableClosedLoopError = new SN_DoublePreference(
        "climberAcceptableClosedLoopError", 300);
  }
}