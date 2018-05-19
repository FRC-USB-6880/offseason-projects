package org.usfirst.frc.team6880.robot.driveTrain;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.util.ClipRange;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TalonSRXDriveSystem implements DriveSystem {
	FRCRobot robot;
	WPI_TalonSRX motorL1;
	WPI_TalonSRX motorL2;
	SpeedControllerGroup motorLeft;
	WPI_TalonSRX motorR1;
	WPI_TalonSRX motorR2;
	SpeedControllerGroup motorRight;
	DifferentialDrive drive;
	Encoder leftEnc;
	Encoder rightEnc;
	DriveTrainReader configReader;
	WheelSpecsReader wheelSpecsReader;
	
	private double wheelDiameter;
	private double wheelCircumference;
	private double distancePerCount;
	private double mult;
	
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
	
	public TalonSRXDriveSystem(FRCRobot robot, String driveSysName)
	{
		this.robot = robot;
        configReader = new DriveTrainReader(JsonReader.driveTrainsFile, driveSysName);
        String wheelType = configReader.getWheelType();
        wheelSpecsReader = new WheelSpecsReader(JsonReader.wheelSpecsFile, wheelType);
		
		wheelDiameter = wheelSpecsReader.getDiameter();
		wheelCircumference = Math.PI * wheelDiameter;
		// We will assume that the same encoder is used on both left and right sides of the drive train. 
		distancePerCount = wheelCircumference / configReader.getEncoderValue("LeftEncoder", "CPR");
		System.out.format("frc6880: distancePerCount = %f\n", distancePerCount);
		
		// TODO  Use configReader.getChannelNum() method to identify the
		//   channel numbers where each motor controller is plugged in
		motorL1 = new WPI_TalonSRX(configReader.getDeviceID("Motor_L1"));
		motorL2 = new WPI_TalonSRX(configReader.getDeviceID("Motor_L2"));
		motorL1.setInverted(true);
		motorL2.setInverted(true);
		if (configReader.isFollower("Motor_L1"))
		{
		    motorL1.follow(motorL2);
		    motorLeft = new SpeedControllerGroup(motorL2);
		    System.out.println("Leader: Motor_L2, Follower: Motor_L1");
		}
		else if (configReader.isFollower("Motor_L2")) 
		{
		    motorL2.follow(motorL1);
		    motorLeft = new SpeedControllerGroup(motorL1);
            System.out.println("Leader: Motor_L1, Follower: Motor_L2");
		}
		else
		    motorLeft = new SpeedControllerGroup(motorL1, motorL2);

		motorR1 = new WPI_TalonSRX(configReader.getDeviceID("Motor_R1"));
		motorR2 = new WPI_TalonSRX(configReader.getDeviceID("Motor_R2"));
		motorR1.setInverted(true);
		motorR2.setInverted(true);

        if (configReader.isFollower("Motor_R1"))
        {
            motorR1.follow(motorR2);
            motorRight = new SpeedControllerGroup(motorR2);
            System.out.println("Leader: Motor_R2, Follower: Motor_R1");
        }
        else if (configReader.isFollower("Motor_R2")) 
        {
            motorR2.follow(motorR1);
            motorRight = new SpeedControllerGroup(motorR1);
            System.out.println("Leader: Motor_R1, Follower: Motor_R2");
        }
        else
            motorRight = new SpeedControllerGroup(motorR1, motorR2);

        drive = new DifferentialDrive(motorLeft, motorRight);

        int[] encoderChannelsLeft = configReader.getEncoderChannels("LeftEncoder");
        System.out.format("frc6880: Left encoder channels = [%d, %d]\n", encoderChannelsLeft[0], encoderChannelsLeft[1]);
        leftEnc = new Encoder(encoderChannelsLeft[0], encoderChannelsLeft[1], false, Encoder.EncodingType.k4X);
//        leftEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        String encoderType;
        encoderType = configReader.getEncoderType("LeftEncoder");
        if (encoderType.equals("GreyHill-63R128")) {
            double gearRatio = configReader.getGearRatio("Left");
            System.out.println("frc6880: Left gear ratio = " + gearRatio);
            leftEnc.setDistancePerPulse(distancePerCount / gearRatio);
        } else {
            leftEnc.setDistancePerPulse(distancePerCount);            
        }
        int[] encoderChannelsRight = configReader.getEncoderChannels("RightEncoder");
        System.out.format("frc6880: Right encoder channels = [%d, %d]\n", encoderChannelsRight[0], encoderChannelsRight[1]);
        rightEnc = new Encoder(encoderChannelsRight[0], encoderChannelsRight[1], true, Encoder.EncodingType.k4X);
//        rightEnc = new Encoder(2, 3, true, Encoder.EncodingType.k4X);
        encoderType = configReader.getEncoderType("RightEncoder");
        if (encoderType.equals("GreyHill-63R128")) {
            double gearRatio = configReader.getGearRatio("Right");
            System.out.println("frc6880: Right gear ratio = " + gearRatio);
            rightEnc.setDistancePerPulse(distancePerCount / gearRatio);
        } else {
            rightEnc.setDistancePerPulse(distancePerCount);
        }
        mult = 1.0;
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(mult*leftSpeed, mult*rightSpeed);
	}
	
	public void arcadeDrive(double speed, double rotationRate)
	{
		drive.arcadeDrive(mult*speed, mult*rotationRate);
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getEncoderDist()
	{
		return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
//		return leftEnc.getDistance();
	}
	public void setLoSpd()
	{
	}
	public void setHiSpd()
	{
	}
	public boolean isMoving()
	{
		if(drive.isAlive())
    		return true;
    	return false;
	}
	public void changeMultiplier(double mult)
    {
    	this.mult = mult;
    }
    public Gears getCurGear() {
	// TODO Auto-generated method stub
    	return Gears.LOW;
    }
}