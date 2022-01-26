package frc.robot;

import com.frcteam3255.preferences.SN_DoublePreference;

public final class RobotPreferences {
    public static final class DrivetrainPrefs {
    }

    public static final class HoodPrefs {
    }

    public static final class ShooterPrefs {
        public static final SN_DoublePreference shooterMotorVelocity = new SN_DoublePreference("shooterMotorVelocity",
                1);
        public static final SN_DoublePreference shooterMotorTargetVelocity = new SN_DoublePreference(
                "shooterMotorTargetVelocity", 5000.0);
    }

    public static final class TurretPrefs {
    }

    public static final class TransferPrefs {
    }

    public static final class IntakePrefs {
    }

    public static final class VisionPrefs {
    }

    public static final class ClimberPrefs {
    }

}