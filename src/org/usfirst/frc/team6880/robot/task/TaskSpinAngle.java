package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskSpinAngle implements RobotTask {
	FRCRobot robot;
	double endOrientation;
	double angle;
	double currentOrientation;
	boolean wrapNegYaw;
	
	public TaskSpinAngle(FRCRobot robot, double angle)
	{
		this.robot = robot;
		this.angle = angle;
		System.out.format("frc6880: Created TaskSpinAngle task. angle = %f\n", angle);
	}
	
	public void initTask()
	{
		//Calculate the end direction
		endOrientation = Math.IEEEremainder(robot.navigation.gyro.getYaw() + angle, 360);
        System.out.format("frc6880: endOrientation = %f\n", endOrientation);
	}
	
	public boolean runTask()
	{
		//If robot isn't facing the end direction
		if (Math.abs(Math.IEEEremainder(endOrientation - robot.navigation.gyro.getYaw(), 360)) > 0.001)
		{
			//Keep on spinning towards the end direction
			robot.navigation.spinToDirection(endOrientation);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
