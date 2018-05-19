/**
 * 
 */
package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

import edu.wpi.first.wpilibj.Timer;

public class TaskWaitForTime implements RobotTask {
    FRCRobot robot;
    double secondsToWait;
    double startingTime;

    public TaskWaitForTime(FRCRobot robot, double seconds) {
        this.robot = robot;
        this.secondsToWait = seconds;
        
    }

    @Override
    public void initTask() {
        startingTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean runTask() {
        if (Timer.getFPGATimestamp() < (startingTime + secondsToWait))
            return false;
        else
            return true;
    }

}
