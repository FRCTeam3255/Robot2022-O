// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.components.SN_DoubleSolenoid;
import com.frcteam3255.preferences.SN_DoublePreference;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
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

    config.slot0.kP = ClimberPrefs.climbP.getValue();
    config.slot0.kI = ClimberPrefs.climbI.getValue();
    config.slot0.kD = ClimberPrefs.climbD.getValue();
    config.slot0.closedLoopPeakOutput = ClimberPrefs.climbClosedLoopSpeed.getValue();
    config.slot0.allowableClosedloopError = ClimberPrefs.climbAllowableClosedLoopError.getValue();

    climbMotor.configAllSettings(config);

    climbMotor.configReverseSoftLimitThreshold(ClimberPrefs.climbMinAnglePosition.getValue());
    climbMotor.configReverseSoftLimitEnable(false);
    climbMotor.setNeutralMode(NeutralMode.Brake);
    climbMotor.setInverted(false);

    pivotPiston.setInverted(false);
  }

  public void setClimberSpeed(double speed) {
    climbMotor.set(ControlMode.PercentOutput, speed * ClimberPrefs.climbOpenLoopSpeed.getValue());
  }

  public void setClimberPosition(SN_DoublePreference position) {
    climbMotor.set(ControlMode.Position, position.getValue(),
        DemandType.ArbitraryFeedForward, ClimberPrefs.climbArbitraryFeedForward.getValue());
  }

  public void setPistonAngled() {
    if (getClimberEncoderCounts() > ClimberPrefs.climbMinAnglePosition.getValue()) {
      pivotPiston.setDeployed();
      climbMotor.configReverseSoftLimitEnable(true);
    }
  }

  public void setPistonPerpendicular() {
    pivotPiston.setRetracted();
    climbMotor.configReverseSoftLimitEnable(false);
  }

  public boolean isPistonAngled() {
    return pivotPiston.isDeployed();
  }

  public double getClimberEncoderCounts() {
    return climbMotor.getSelectedSensorPosition();
  }

  public boolean isClimberMax() {
    return climbMotor.isFwdLimitSwitchClosed() == 1 ? true : false;
  }

  public boolean isClimberMin() {
    return climbMotor.isRevLimitSwitchClosed() == 1 ? true : false;
  }

  @Override
  public void periodic() {
    if (RobotContainer.switchBoard.btn_7.get()) {

      SmartDashboard.putNumber("Climber Encoder Count", getClimberEncoderCounts());
      SmartDashboard.putBoolean("Is Climber Max", isClimberMax());
      SmartDashboard.putBoolean("Is Climber Min", isClimberMin());

      SmartDashboard.putBoolean("Is Climber Angled", isPistonAngled());

    }
  }
}
