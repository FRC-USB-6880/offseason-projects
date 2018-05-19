package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

import edu.wpi.first.wpilibj.Timer;

public class TaskLiftForTime implements RobotTask {
	private FRCRobot robot;
	private double power;
	private double time;
	private Timer timer;
	
	public TaskLiftForTime(FRCRobot robot, double power, double time) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.power = power;
		this.time = time;
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
		if(timer.get()<time)
		{
			robot.lift.moveWithPower(power);
			return false;
		}
		robot.lift.moveWithPower(0);
		return true;
	}

}
