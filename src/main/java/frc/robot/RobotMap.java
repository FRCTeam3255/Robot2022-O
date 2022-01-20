// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The RobotMap class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotMap {
    public final class Drivetrain {
        public static final int LEFT_LEAD_MOTOR_CAN = 0;
        public static final int RIGHT_LEAD_MOTOR_CAN = 1;
        public static final int LEFT_FOLLOW_MOTOR_CAN = 2;
        public static final int RIGHT_FOLLOW_MOTOR_CAN = 3;
    }

    public final class Hood {
    }

    public final class Turret {
        public static final int TURRET_MOTOR_CAN = 20;
    }

    public final class Transfer {
    }

    public final class Intake {
    }

    public final class Vision {
    }

    public final class Climber {
    }

    public final class Shooter {
        public static final int TOP_MOTOR_CAN = 10;
        public static final int BOTTOM_MOTOR_CAN = 11;
    }
  
    public final class Controller {
        public static final int DRIVER_STICK = 0;
    }
}