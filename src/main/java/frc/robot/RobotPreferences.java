package frc.robot;

import com.frcteam3255.preferences.SN_DoublePreference;
import com.frcteam3255.preferences.SN_IntPreference;

public final class RobotPreferences {
  public static final class DrivetrainPrefs {
    public static final SN_DoublePreference arcadeSpeed = new SN_DoublePreference("arcadeSpeed", 1);
    public static final SN_DoublePreference arcadeTurn = new SN_DoublePreference("arcadeTurn", 1);
  }

  public static final class HoodPrefs {
  }

  public static final class ShooterPrefs {
    public static final SN_DoublePreference shooterMotorSpeed = new SN_DoublePreference("shooterMotorSpeed", 1);
    public static final SN_DoublePreference shooterMotorTargetVelocity = new SN_DoublePreference(
        "shooterMotorTargetVelocity", 5000.0);

    public static final SN_DoublePreference shooterF = new SN_DoublePreference("kF", 0);
    public static final SN_DoublePreference shooterP = new SN_DoublePreference("kP", 1);
    public static final SN_DoublePreference shooterI = new SN_DoublePreference("kI", 0);
    public static final SN_DoublePreference shooterD = new SN_DoublePreference("kD", 0);
  }

  public static final class TurretPrefs {
    public static final SN_DoublePreference turretMotorEncoderAngleMultiplier = new SN_DoublePreference(
        "turretMotorEncoderAngleMultipler", 90);

    public static final SN_DoublePreference turretMaxEncoderCount = new SN_DoublePreference("turretMaxEncoderCount",
        999999);

    public static final SN_DoublePreference turretMinEncoderCount = new SN_DoublePreference("turretMinEncoderCount",
        -999999);
  }

  public static final class TransferPrefs {
    public final static SN_DoublePreference transferSpeed = new SN_DoublePreference("transferSpeed", 0.80);

  }

  public static final class IntakePrefs {
    public final static SN_DoublePreference collectSpeed = new SN_DoublePreference("collectSpeed", 0.80);
    public final static SN_DoublePreference rejectSpeed = new SN_DoublePreference("rejectSpeed", -0.80);
    public final static SN_IntPreference colorSensorMinProximity = new SN_IntPreference("colorSensorMinProximity",
        1000);
  }

  public static final class VisionPrefs {
  }

  public static final class ClimberPrefs {
    public static final SN_DoublePreference climberMotorSpeed = new SN_DoublePreference("climberMotorSpeed", 0.5);
    public static final SN_DoublePreference climberMaxEncoderCount = new SN_DoublePreference("climberMaxEncoderCount",
        200000);
  }
}