package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

import edu.wpi.first.wpilibj.Timer;

public class TaskCubeHandle implements RobotTask {
	private FRCRobot robot;
	private boolean close;
	private Timer timer;
	
	public TaskCubeHandle(FRCRobot robot, boolean close) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.close = close;
	}
	
	@Override
	public void initTask() {
		// TODO Auto-generated method stub
		timer = new Timer();
		timer.start();
	}

	@Override
	public boolean runTask() {
		// TODO Auto-generated method stub
		if(close)
		{
			robot.cubeHandler.grabCube();
			if(timer.get()>0.5)
				return true;
		}
		else
		{
			robot.cubeHandler.releaseCube();
			if(timer.get()>0.5)
				return true;
		}
		return false;
	}

}
