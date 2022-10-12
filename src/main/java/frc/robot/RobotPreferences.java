package frc.robot;

import com.frcteam3255.preferences.SN_BooleanPreference;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.preferences.SN_IntPreference;
import com.frcteam3255.preferences.SN_ZeroDoublePreference;
import com.frcteam3255.preferences.SN_ZeroIntPreference;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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

  public static final class ClimberPrefs {
    public static final SN_DoublePreference climbOpenLoopSpeed = new SN_DoublePreference("climbOpenLoopSpeed", 1);
    public static final SN_DoublePreference climbClosedLoopSpeed = new SN_DoublePreference("climbClosedLoopSpeed", 1);

    public static final SN_DoublePreference climbF = new SN_DoublePreference("climbF", 0);
    public static final SN_DoublePreference climbP = new SN_DoublePreference("climbP", 1);
    public static final SN_DoublePreference climbI = new SN_DoublePreference("climbI", 0);
    public static final SN_DoublePreference climbD = new SN_DoublePreference("climbD", 0);
    public static final SN_DoublePreference climbAllowableClosedLoopError = new SN_DoublePreference(
        "climbAllowableClosedLoopError", 0);

    // encoder counts position of "up" position or able reach the next bar
    public static final SN_DoublePreference climbUpPosition = new SN_DoublePreference("climbUpPosition", 42069);

    // if the climber is too low and you try to angle the climber will hit turret
    public static final SN_DoublePreference climbMinAnglePosition = new SN_DoublePreference(
        "climbMinAnglePosition", 6942);
  }

  public static final class DrivetrainPrefs {
    public static final SN_DoublePreference arcadeSpeed = new SN_DoublePreference("arcadeSpeed", 0.65);
    public static final SN_DoublePreference arcadeTurn = new SN_DoublePreference("arcadeTurn", .5);
    public static final SN_DoublePreference arcadeLowSpeed = new SN_DoublePreference("arcadeLowSpeed", 0.4);
    public static final SN_DoublePreference arcadeHighSpeed = new SN_DoublePreference("arcadeHighSpeed", 1.5);
    public static final SN_DoublePreference arcadeClosedLoopMaxSpeed = new SN_DoublePreference(
        "arcadeClosedLoopMaxSpeed", 3);

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

    public static final SN_BooleanPreference driveBreakMode = new SN_BooleanPreference("driveBreakMode", true);

    // value is in controller input delta per second, eg 0.3 means that the
    // controller can only change by 0.3 in a second
    public static final SN_DoublePreference drivePosSlewRateLimit = new SN_DoublePreference(
        "drivePosSlewRateLimit", 2);
    public static final SN_DoublePreference driveNegSlewRateLimit = new SN_DoublePreference(
        "driveNegSlewRateLimit", 2);
    public static final SN_BooleanPreference driveSquaredInputs = new SN_BooleanPreference("driveSquaredInputs", true);

    public static final SN_DoublePreference driveOpenLoopSpeedForward = new SN_DoublePreference(
        "driveOpenLoopSpeedForward", .3);
    public static final SN_DoublePreference driveOpenLoopSpeedReverse = new SN_DoublePreference(
        "driveOpenLoopSpeedReverse", -.3);

    public static final SN_DoublePreference driveOpenLoopCounts = new SN_DoublePreference("driveOpenLoopCounts", 44444);

    public static final SN_DoublePreference driveWheelCircumference = new SN_DoublePreference(
        "driveWheelCircumference", 4 * Math.PI);
    public static final SN_DoublePreference driveGearRatio = new SN_DoublePreference("driveGearRatio", 6);

    public static final SN_DoublePreference driveWidth = new SN_DoublePreference("driveWidth", 0.55); // meters
    public static final SN_DoublePreference driveLength = new SN_DoublePreference("driveLength", 0.67); // meters
    public static final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(
        driveWidth.getValue());

  }

  public static final class HoodPrefs {
    public static final SN_DoublePreference hoodArbitraryFeedForward = new SN_DoublePreference(
        "hoodArbitraryFeedForward", 0.040078);
    public static final SN_DoublePreference hoodP = new SN_DoublePreference("hoodP", 0.09);
    public static final SN_DoublePreference hoodI = new SN_DoublePreference("hoodI", 0);
    public static final SN_DoublePreference hoodD = new SN_DoublePreference("hoodD", 0);

    public static final SN_DoublePreference hoodAllowableClosedLoopErrorDegrees = new SN_DoublePreference(
        "hoodAllowableClosedLoopErrorDegrees", 0.0001);
    public static final SN_DoublePreference hoodClosedLoopPeakOutput = new SN_DoublePreference(
        "hoodClosedLoopPeakOutput", .25);
    public static final SN_DoublePreference hoodOpenLoopSpeedMultiplier = new SN_DoublePreference(
        "hoodOpenLoopSpeedMultiplier", .25);
    public static final SN_DoublePreference hoodGearRatio = new SN_DoublePreference("hoodGearRatio", 69.33333);
    public static final SN_DoublePreference hoodMinDegrees = new SN_DoublePreference("hoodMinDegrees", 4.89);
    public static final SN_DoublePreference hoodMaxDegrees = new SN_DoublePreference("hoodMaxDegrees", 38);

    public static final SN_DoublePreference hoodNudgeDegrees = new SN_DoublePreference("hoodNudgeDegrees", 1);

    // TODO: get ideal positions
    public static final SN_DoublePreference hoodFender = new SN_DoublePreference("hoodFender", 7);
    public static final SN_DoublePreference hoodTerminal = new SN_DoublePreference("hoodTerminal", 35);
    public static final SN_DoublePreference hoodLaunchpad = new SN_DoublePreference("hoodLaunchpad", 38);
    public static final SN_DoublePreference hoodTarmac = new SN_DoublePreference("hoodTarmac", 15);

    public static final SN_DoublePreference hoodOldZero = new SN_DoublePreference("hoodOldZero", 10.8);
    public static final SN_DoublePreference hoodOldLow = new SN_DoublePreference("hoodOldLow", 16.4);
    public static final SN_DoublePreference hoodOldMid = new SN_DoublePreference("hoodOldMid", 25.3);
    public static final SN_DoublePreference hoodOldHigh = new SN_DoublePreference("hoodOldHigh", 35.4);

  }

  public static final class ShooterPrefs {
    public static final SN_DoublePreference shooterPercentOutput = new SN_DoublePreference(
        "shooterPercentOutput", 1);
    public static final SN_DoublePreference shooterTargetRPM = new SN_DoublePreference(
        "shooterTargetRPM", 5000);
    public static final SN_DoublePreference shooterAcceptableErrorRPM = new SN_DoublePreference(
        "shooterAcceptableErrorRPM", 15);

    public static final SN_BooleanPreference shooterInvert = new SN_BooleanPreference(
        "shooterInvert", false);

    public static final SN_DoublePreference shooterEncoderCountsPerDegrees = new SN_DoublePreference(
        "shooterEncoderCountsPerDegrees", 84);

    public static final SN_DoublePreference shooterF = new SN_DoublePreference("shooterF", 0);
    public static final SN_DoublePreference shooterP = new SN_DoublePreference("shooterP", 0.02);
    public static final SN_DoublePreference shooterI = new SN_DoublePreference("shooterI", 0.0002);
    public static final SN_DoublePreference shooterD = new SN_DoublePreference("shooterD", 0.0088);

    public static final SN_DoublePreference shooterPresetUpperFenderRPM = new SN_DoublePreference(
        "shooterPresetUpperFenderRPM", 2700);
    public static final SN_DoublePreference shooterPresetUpperTarmacRPM = new SN_DoublePreference(
        "shooterPresetUpperTarmacRPM", 3255);
    public static final SN_DoublePreference shooterPresetUpperLaunchpadRPM = new SN_DoublePreference(
        "shooterPresetUpperLaunchpadRPM", 4200);
    public static final SN_DoublePreference shooterPresetUpperTerminalRPM = new SN_DoublePreference(
        "shooterPresetUpperTerminalRPM", 4550);

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

    public static final SN_IntPreference shooterDelayLoops = new SN_IntPreference(
        "shooterDelayLoops", 20);
    public static final SN_IntPreference shooterBufferLoops = new SN_IntPreference("shooterBufferLoops", 10);

    public static final SN_DoublePreference shooterGoalRPMMultiplier = new SN_DoublePreference(
        "shooterGoalRPMMultiplier", 1);

  }

  public static final class TurretPrefs {

    public static final SN_DoublePreference turretTwoBallAutoDegrees = new SN_DoublePreference(
        "turretTwoBallAutoDegrees", 96);
    public static final SN_DoublePreference turretThreeBallAutoDegrees = new SN_DoublePreference(
        "turretThreeBallAutoDegrees", -8);

    public static final SN_DoublePreference turretMaxAngleDegrees = new SN_DoublePreference("turretMaxAngleDegrees",
        110);
    public static final SN_DoublePreference turretMinAngleDegrees = new SN_DoublePreference("turretMinAngleDegrees",
        -270);
    public static final SN_DoublePreference turretSnapAwayIntake = new SN_DoublePreference("turretSnapAwayIntake", 90);
    public static final SN_DoublePreference turretSnapToIntake = new SN_DoublePreference("turretSnapToIntake", -90);

    // 2048 encoder counts per rotation * 65 (gr) = 133120
    // 133120 / 360 = 370
    public static final SN_DoublePreference turretEncoderCountsPerDegrees = new SN_DoublePreference(
        "turretEncoderCountsPerDegrees", 370);
    public static final SN_DoublePreference turretMaxAllowableErrorDegrees = new SN_DoublePreference(
        "turretMaxAllowableErrorDegrees", 3);
    public static final SN_DoublePreference turretClosedLoopPeakOutput = new SN_DoublePreference(
        "turretClosedLoopPeakOutput", 1);
    public static final SN_DoublePreference turretF = new SN_DoublePreference("turretF", 0);
    public static final SN_DoublePreference turretP = new SN_DoublePreference("turretP", 0.15);
    public static final SN_DoublePreference turretI = new SN_DoublePreference("turretI", 0.00001);
    public static final SN_DoublePreference turretD = new SN_DoublePreference("turretD", 8);

    public static final SN_IntPreference turretLoopsToFinish = new SN_IntPreference("turretLoopsToFinish", 25);

    public static final SN_DoublePreference turretPresetPos1 = new SN_DoublePreference("turretTestPos", 90);
    public static final SN_DoublePreference turretOpenLoopSpeed = new SN_DoublePreference("turretOpenLoopSpeed", .3);
  }

  public static final class TransferPrefs {
    public final static SN_DoublePreference transferEntranceSpeed = new SN_DoublePreference(
        "transferEntranceSpeed", .8);
    public final static SN_DoublePreference transferEntranceRejectSpeed = new SN_DoublePreference(
        "transferEntranceRejectSpeed", -.8);
    public final static SN_DoublePreference transferBeltSpeed = new SN_DoublePreference(
        "transferBeltSpeed", .5);
    public final static SN_DoublePreference transferBeltRejectSpeed = new SN_DoublePreference(
        "transferBeltRejectSpeed", -.5);

    public final static SN_BooleanPreference transferEntranceInvert = new SN_BooleanPreference(
        "transferEntranceInvert", true);
    public final static SN_BooleanPreference transferBottomBeltInvert = new SN_BooleanPreference(
        "transferBottomBeltInvert", false);
    public final static SN_BooleanPreference transferTopBeltInvert = new SN_BooleanPreference(
        "transferTopBeltInvert", false);

    // one loop is 20ms
    public final static SN_IntPreference transferRejectTimerLoops = new SN_IntPreference(
        "transferRejectTimerLoops", 25);

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
        "intakeRejectTimerLoops", 50);
    public final static SN_IntPreference colorSensorMinProximity = new SN_IntPreference(
        "colorSensorMinProximity", 1000);
  }

  public static final class VisionPrefs {
  }

  public static final class AutoPrefs {

    public static final SN_IntPreference autoPushToShooterLatchTimeLoops = new SN_IntPreference(
        "autoPushToShooterLatchTimeLoops", 20);

    // auto1
    public static final class TwoCargoA {
      public static final SN_DoublePreference auto1shooterRPM = new SN_DoublePreference("auto1shooterRPM", 3255);
      public static final SN_DoublePreference auto1turretAngle = new SN_DoublePreference("auto1turretAngle", 0);
      public static final SN_BooleanPreference auto1hoodSteep = new SN_BooleanPreference("auto1hoodSteep", true);
    }

    // auto2
    public static final class FiveCargoA {
      public static final SN_DoublePreference auto2shooterRPM1 = new SN_DoublePreference("auto2shooterRPM1", 3000);
      public static final SN_DoublePreference auto2turretAngle1 = new SN_DoublePreference("auto2turretAngle1", 0);
      public static final SN_BooleanPreference auto2hoodSteep1 = new SN_BooleanPreference("auto2hoodSteep1", true);

      public static final SN_DoublePreference auto2shooterRPM2 = new SN_DoublePreference("auto2shooterRPM2", 3000);
      public static final SN_DoublePreference auto2turretAngle2 = new SN_DoublePreference("auto2turretAngle2", 0);
      public static final SN_BooleanPreference auto2hoodSteep2 = new SN_BooleanPreference("auto2hoodSteep2", true);

      public static final SN_DoublePreference auto2shooterRPM3 = new SN_DoublePreference("auto2shooterRPM3", 3000);
      public static final SN_DoublePreference auto2turretAngle3 = new SN_DoublePreference("auto2turretAngle3", 0);
      public static final SN_BooleanPreference auto2hoodSteep3 = new SN_BooleanPreference("auto2hoodSteep3", true);
    }

    // auto3
    public static final class FourCargoA {
      public static final SN_DoublePreference auto3shooterRPM1 = new SN_DoublePreference("auto3shooterRPM1", 3000);
      public static final SN_DoublePreference auto3turretAngle1 = new SN_DoublePreference("auto3turretAngle1", 0);
      public static final SN_BooleanPreference auto3hoodSteep1 = new SN_BooleanPreference("auto3hoodSteep1", true);

      public static final SN_DoublePreference auto3shooterRPM2 = new SN_DoublePreference("auto3shooterRPM2", 3000);
      public static final SN_DoublePreference auto3turretAngle2 = new SN_DoublePreference("auto3turretAngle2", 0);
      public static final SN_BooleanPreference auto3hoodSteep2 = new SN_BooleanPreference("auto3hoodSteep2", true);

    }

    // auto4
    public static final class OpenLoopTwoBall {
      public static final SN_DoublePreference auto4shooterRPM = new SN_DoublePreference("auto4shooterRPM", 3255);
      public static final SN_BooleanPreference auto4hoodSteep = new SN_BooleanPreference("auto4hoodSteep", false);
      public static final SN_DoublePreference auto4dist1 = new SN_DoublePreference("auto4dist1", 44844);
      public static final SN_DoublePreference auto4dist2 = new SN_DoublePreference("auto4dist2", -82000);
      public static final SN_DoublePreference auto4turretAngle = new SN_DoublePreference("auto4turretAngle", 340);
    }

    public static final class AutoThreeCargo {
      public static final SN_DoublePreference autoThreeCargoShooterRPM = new SN_DoublePreference(
          "autoThreeCargoShooterRPM", 3255);
    }

    // auto 6
    public static final class ThreeCargo {
      public static final SN_DoublePreference shooterRPM1_6 = new SN_DoublePreference(
          "shooterRPM1_6", ShooterPrefs.shooterPresetUpperFenderRPM.getValue());
      public static final SN_DoublePreference hoodAngle1_6 = new SN_DoublePreference(
          "hoodAngle1_6", 5);
      public static final SN_DoublePreference turretAngle1_6 = new SN_DoublePreference(
          "turretAngle1_6", TurretPrefs.turretSnapAwayIntake.getValue());

      public static final SN_DoublePreference shooterRPM2_6 = new SN_DoublePreference(
          "shooterRPM2_6", 3500);
      public static final SN_DoublePreference hoodAngle2_6 = new SN_DoublePreference(
          "hoodAngle2_6", 5);
      public static final SN_DoublePreference turretAngle2_6 = new SN_DoublePreference(
          "turretAngle2_6", -198);
    }

  }
}