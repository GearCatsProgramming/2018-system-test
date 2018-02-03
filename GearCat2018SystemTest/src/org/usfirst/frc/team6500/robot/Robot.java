/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6500.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 */
public class Robot extends IterativeRobot {

	/***************************************
	 * Simple test program to select each of the Spark motors from the dashboard and check we can power it. 
	 */
	
	private static final int kFrontLeftChannel = 0;
	private static final int kRearLeftChannel = 1;
	private static final int kFrontRightChannel = 2;
	private static final int kRearRightChannel = 3;

	private Joystick m_stick = new Joystick(0);
	
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	Spark frontLeft;
	Spark rearLeft;
	Spark frontRight;
	Spark rearRight;
	
	Spark[] motors = {frontLeft, rearLeft, frontRight, rearRight};
	
	//Creating a RobotDrive object called drive so we can control the motors as a whole, not individually
	MecanumDrive m_drive;
	
	String m_CurrentMotor = "STOP";
	
	final static double HALF_SPEED = 0.5;
	final static double FULL_SPEED = 1.0;
	
	public Robot() {
	
		frontLeft = new Spark(kFrontLeftChannel);
		rearLeft = new Spark(kRearLeftChannel);
		frontRight = new Spark(kFrontRightChannel);
		rearRight = new Spark(kRearRightChannel);
	

	}

	@Override
	public void robotInit() {
		
		m_chooser.addDefault("All Stop", "STOP");
		m_chooser.addObject("Front Left", Integer.toString(kFrontLeftChannel));
		m_chooser.addObject("Back Left", Integer.toString(kRearLeftChannel));
		m_chooser.addObject("Front Right", Integer.toString(kFrontRightChannel));
		m_chooser.addObject("Back Right", Integer.toString(kRearRightChannel));
		
		SmartDashboard.putData("Motor Select", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		String motor = m_chooser.getSelected();
		Boolean changed = false;
		
		changed = motor.equals(m_CurrentMotor);
		
		double throttle = m_stick.getThrottle();
		
		switch (motor)	{
			
			case "STOP":
				AllStop();
				break;
				
			default :
				if (changed) AllStop();
				try {
					setMotor(Integer.parseInt(motor), throttle);
				} catch (Exception e) {}
		}
		
		SmartDashboard.putString("Active Motor", motor);
		SmartDashboard.putNumber("Throttle", throttle);
				
	}

		
	private void setMotor(int motor, double speed) {
		motors[motor].set(speed);		
	}

	private void AllStop() {
		frontLeft.set(0.0);
		rearLeft.set(0.0);
		frontRight.set(0.0);
		rearRight.set(0.0);
		
	}
}
