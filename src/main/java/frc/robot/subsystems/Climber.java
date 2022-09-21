// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.components.SN_DoubleSolenoid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotMap.ClimberMap;
import frc.robot.RobotPreferences.ClimberPrefs;

public class Climber extends SubsystemBase {

  TalonFX climbMotor;
  SN_DoubleSolenoid pivotPiston;

  /** Creates a new Climber. */
  public Climber() {
    climbMotor = new TalonFX(ClimberMap.CLIMB_MOTOR_CAN);

    pivotPiston = new SN_DoubleSolenoid(
        RobotMap.CLIMBER_PCM,
        PneumaticsModuleType.CTREPCM,
        ClimberMap.PIVOT_PISTON_SOLENOID_PCM_A,
        ClimberMap.PIVOT_PISTON_SOLENOID_PCM_B);

    configure();
  }

  public void configure() {

    climbMotor.configFactoryDefault();
    TalonFXConfiguration config = new TalonFXConfiguration();

    config.slot0.kF = ClimberPrefs.climbF.getValue();
    config.slot0.kP = ClimberPrefs.climbP.getValue();
    config.slot0.kI = ClimberPrefs.climbI.getValue();
    config.slot0.kD = ClimberPrefs.climbD.getValue();
    config.slot0.closedLoopPeakOutput = ClimberPrefs.climbClosedLoopSpeed.getValue();
    config.slot0.allowableClosedloopError = ClimberPrefs.climbAllowableClosedLoopError.getValue();

    climbMotor.configAllSettings(config);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
