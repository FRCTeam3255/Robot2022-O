/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transfer extends SubsystemBase {
  /**
   * Creates a new Transfer. */

  private TalonSRX topBeltMotor;
  private TalonSRX bottomBeltMotor;
  private ColorSensorV3 colorSensor;

  public Transfer() {
    topBeltMotor = new TalonSRX(RobotMap.Transfer.TOP_BELT_MOTOR_CAN);
    bottomBeltMotor = new TalonSRX(RobotMap.Transfer.BOTTOM_BELT_MOTOR_CAN);
    colorSensor = new ColorSensorV3(RobotMap.Sensor.COLOR_SENSOR);
    configure();
  }

  public void configure() {
    topBeltMotor.configureFactoryDefault();
    bottomBeltMotor.configureFactoryDefault();
    colorSensor.configureFactoryDefault();
    
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
