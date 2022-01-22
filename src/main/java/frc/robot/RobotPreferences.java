package frc.robot;

import com.frcteam3255.preferences.SN_DoublePreference;

public final class RobotPreferences {
    public static final class Drivetrain {
    }

    public static final class Hood {
        public static final SN_DoublePreference hoodCountsPerDegree = new SN_DoublePreference("hoodCountsPerDegree",
                40);
        public static final SN_DoublePreference angleHoodDirectionUp = new SN_DoublePreference("angleHoodDirectionUp",
                -2);
        public static final SN_DoublePreference angleHoodDirectionDown = new SN_DoublePreference(
                "angleHoodDirectionDown", 2);
    }

    public static final class Turret {
    }

    public static final class Transfer {
    }

    public static final class Intake {
    }

    public static final class Vision {
    }

    public static final class Climber {
    }
}