package org.usfirst.frc.team6880.robot.driveTrain;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class VictorSPDriveSystem implements DriveSystem {
	FRCRobot robot;
	VictorSP motorL1;
	VictorSP motorL2;
	SpeedControllerGroup motorLeft;
	VictorSP motorR1;
	VictorSP motorR2;
	SpeedControllerGroup motorRight;
	DifferentialDrive drive;
	Encoder leftEnc;
	Encoder rightEnc;
	DriveTrainReader configReader;
	WheelSpecsReader wheelSpecsReader;
	
	private double wheelDiameter;
	private double wheelCircumference;
	private double distancePerCount;
	
	/*
	 *  TODO
	 *  We will need to support at least 2 different drive trains:
	 *  1) 4 Motor 4 VictorSP controller drive train (this is what the current off season robot uses)
	 *  2) 4 Motor 4 Talon SRX controller drive train (this is what the new competition robot will use)
	 *  
	 *  The goal is to use object oriented concepts to organize the driveTrain package in 
	 *  such a way that the users of DriveSystem class (Navigation, FRCRobot, Tasks, etc.)
	 *  do not have to know which specific drive train is being currently used.
	 */
	
	public VictorSPDriveSystem(FRCRobot robot, String driveSysName)
	{
		this.robot = robot;
        configReader = new DriveTrainReader(JsonReader.driveTrainsFile, driveSysName);
        String wheelType = configReader.getWheelType();
        wheelSpecsReader = new WheelSpecsReader(JsonReader.wheelSpecsFile, wheelType);
		
		wheelDiameter = wheelSpecsReader.getDiameter();
		wheelCircumference = Math.PI * wheelDiameter;
		// We will assume that the same encoder is used on both left and right sides of the drive train. 
		distancePerCount = wheelCircumference / configReader.getEncoderValue("LeftEncoder", "CPR");
		
		// TODO  Use configReader.getChannelNum() method to identify the
		//   channel numbers where each motor controller is plugged in
		motorL1 = new VictorSP(configReader.getChannelNum("Motor_L1"));
		motorL2 = new VictorSP(configReader.getChannelNum("Motor_L2"));
		motorLeft = new SpeedControllerGroup(motorL1, motorL2);
		motorR1 = new VictorSP(configReader.getChannelNum("Motor_R1"));
		motorR2 = new VictorSP(configReader.getChannelNum("Motor_R2"));
		motorRight = new SpeedControllerGroup(motorR1, motorR2);
		drive = new DifferentialDrive(motorLeft, motorRight);
		int[] encoderChannelsLeft = configReader.getEncoderChannels("LeftEncoder");
		leftEnc = new Encoder(encoderChannelsLeft[0], encoderChannelsLeft[1], false, Encoder.EncodingType.k4X);
		leftEnc.setDistancePerPulse(distancePerCount);
        int[] encoderChannelsRight = configReader.getEncoderChannels("RightEncoder");
		rightEnc = new Encoder(encoderChannelsRight[0], encoderChannelsRight[1], true, Encoder.EncodingType.k4X);
		rightEnc.setDistancePerPulse(distancePerCount);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public void arcadeDrive(double speed, double rotationRate)
	{
		drive.arcadeDrive(speed, rotationRate);
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getEncoderDist()
	{
		return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
	}
}