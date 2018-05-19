package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskTurnLeft implements RobotTask {
	FRCRobot robot;
	double endDirection;
	double currentDirection;
	boolean wrapNegYaw;
	double angle;
	
	public TaskTurnLeft(FRCRobot robot, double angle) 
	{
		this.robot = robot;
		this.angle = angle;
	}
	
	public void initTask()
	{
		//Calculate the end direction
		endDirection = robot.navigation.gyro.getYaw() + angle;
		//If the end direction is above 180, we need to take the negative yaw values and wrap them to positive
		if (endDirection > 180.0)
		{
			wrapNegYaw = true;
		}
		else
		{
			wrapNegYaw = false;
		}
	}
	
	public boolean runTask()
	{
		//Store current direction to minimize method calls
		currentDirection = robot.navigation.gyro.getYaw();
		//If robot hasn't turned enough
		if ((wrapNegYaw && currentDirection < 0 ? currentDirection + 360 : currentDirection) < endDirection)
		{
			//Keep turning at half speed
			robot.driveSys.tankDrive(-0.5, 0.5);
		}
		//Else stop the robot and tell robot to go to next task
		else
		{
			robot.driveSys.tankDrive(0.0, 0.0);
			return true;
		}
		return false;
	}
}
