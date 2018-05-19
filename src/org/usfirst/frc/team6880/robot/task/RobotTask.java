package org.usfirst.frc.team6880.robot.task;

public interface RobotTask {
	/**Run at the beginning of the task*/
	public void initTask();
	
	/**Run during the task. Return true to signal the end of the task*/
	public boolean runTask();
}
