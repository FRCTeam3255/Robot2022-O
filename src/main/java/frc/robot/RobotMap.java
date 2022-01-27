// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

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
// Reminder, there are only 9 DIO on Roborio!
public final class RobotMap {
    public final class DrivetrainMap {
        public static final int LEFT_LEAD_MOTOR_CAN = 0;
        public static final int RIGHT_LEAD_MOTOR_CAN = 1;
        public static final int LEFT_FOLLOW_MOTOR_CAN = 2;
        public static final int RIGHT_FOLLOW_MOTOR_CAN = 3;
    }

    public final class HoodMap {
        public static final int HOOD_MOTOR_CAN = 30;
        public static final int HOOD_LIMIT_SWITCH = 31;
    }

    public final class TurretMap {
        public static final int TURRET_MOTOR_CAN = 20;
    }

    public final class TransferMap {

        public static final int TOP_BELT_MOTOR_CAN = 40;
        public static final int BOTTOM_BELT_MOTOR_CAN = 41;
        public static final int TRANSFER_LIMIT_SWITCH_DIO = 0;

    }

    public final class IntakeMap {

        public static final int INTAKE_MOTOR_CAN = 30;
        public static final int INTAKE_SOLENOID_PCM_A = 0;
        public static final int INTAKE_SOLENOID_PCM_B = 1;
    }

    public final class VisionMap {
    }

    public final class ClimberMap {
        public static final int CLIMBER_MOTOR_CAN = 30;
        public static final int SAFETY_MAG_SWITCH_DIO = 0;
    }

    public final class ShooterMap {
        public static final int LEFT_MOTOR_CAN = 10;
        public static final int RIGHT_MOTOR_CAN = 11;
    }

    public final class ControllerMap {

        public static final int DRIVER_STICK = 0;
    }
}
