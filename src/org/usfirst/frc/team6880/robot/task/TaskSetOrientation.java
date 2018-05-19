/**
 * 
 */
package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.navigation.Navigation.SpinDirection;

/**
 * Set the orientation of the robot to the given angle
 *
 */
public class TaskSetOrientation implements RobotTask {
    FRCRobot robot;
    double endOrientation;
    double speed;
    double tolerance;
    double currentOrientation;
    SpinDirection spinDir;

    /**
     * 
     */
    public TaskSetOrientation(FRCRobot robot, double orientation, double speed, double tolerance) {
        // TODO Auto-generated constructor stub
        this.robot = robot;
        this.endOrientation = orientation;
        this.speed = speed;
        this.spinDir = SpinDirection.NONE;
        this.tolerance = tolerance;
        
        System.out.format("frc6880: endOrientation = %f, speed=%f\n", endOrientation, speed);
    }

    public void initTask()
    {
        this.currentOrientation = robot.navigation.gyro.getYaw();
        // Determine CW or CCW spin
        this.spinDir = robot.navigation.getSpinDirection(currentOrientation, endOrientation);
        String spinDirStr = (spinDir == SpinDirection.CW) ? "CW" : "CCW";
        // Change the sign of speed depending on spin direction
        if (this.spinDir == SpinDirection.CW)
            speed = Math.abs(speed);
        else
            speed = -1 * Math.abs(speed);

        System.out.format("frc6880: currentOrientation=%f, endOrientation = %f, spinDirection=%s, speed=%f\n", 
                currentOrientation, endOrientation, spinDirStr, speed);
    }

    public boolean runTask() 
    {
        // Continue turning until the robot's orientation is within the tolerance
        // TODO specify the tolerance in a json file
        double degreesToTurn = robot.navigation.getDegreesToTurn(robot.navigation.gyro.getYaw(), endOrientation);
        if (Math.abs(degreesToTurn) > this.tolerance) {
            robot.driveSys.arcadeDrive(0, speed);
            return false;
        } else {
            //Else stop the robot and tell robot to go to next task
            robot.driveSys.tankDrive(0.0, 0.0);
            return true;
        }
    }
}
