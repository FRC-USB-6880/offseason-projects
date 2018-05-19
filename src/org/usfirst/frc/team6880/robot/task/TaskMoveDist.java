package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.util.ClipRange;

public class TaskMoveDist implements RobotTask {
	FRCRobot robot;
	double speed;
    double startingLocation;
    double travelDist;
    double angleToMaintain;
	boolean driveBackwards;
	
	public TaskMoveDist(FRCRobot robot, double speed, double travelDist, double angleToMaintain) {
		this.robot = robot;
		this.travelDist = travelDist;
		this.speed = speed;
		this.angleToMaintain = angleToMaintain;
		if (travelDist >= 0)
		    driveBackwards = false;
		else
		    driveBackwards = true;
		System.out.format("frc6880: TaskMoveDist created: speed=%f, targetDist=%f, angleToMaintain=%f, curYaw=%f\n", 
		        speed, travelDist, angleToMaintain, robot.navigation.gyro.getYaw());
	}
	
	public void initTask()
	{
        robot.driveSys.resetEncoders();
		//Set starting location of the encoders
        startingLocation = robot.driveSys.getEncoderDist();
        //Get the direction we want to travel
//        angleToMaintain = robot.navigation.gyro.getYaw();
        System.out.format("frc6880: startingLocation = %f\n", startingLocation);
	}
	
	public boolean runTask()
	{
        double distTravelled = robot.driveSys.getEncoderDist() - startingLocation;
//        System.out.format("distance travelled = %f, totalDistToTravel=%f\n", distTravelled, travelDist);
        //If robot still has remaining distance
        if (Math.abs(distTravelled) < Math.abs(travelDist) )
        {
            //Go straight and slow down before we reach out target distance
//            double curSpeed = this.speed * (1 - Math.abs(distTravelled / travelDist));
//            curSpeed = ClipRange.clip(curSpeed, 0.2, this.speed);
//            System.out.format("frc6880: Calling navigation.driveDirection(%f,%f)\n", curSpeed, angleToMaintain);
//            robot.navigation.driveDirection(curSpeed, angleToMaintain, driveBackwards);
              robot.navigation.driveDirection(this.speed, angleToMaintain, driveBackwards);
//            robot.driveSys.tankDrive(curSpeed, curSpeed);
            return false;
        }
        //Else stop the robot and tell robot to go to next task
        robot.driveSys.tankDrive(0, 0);
        System.out.println("frc6880: curEncDist: " + robot.driveSys.getEncoderDist());
        return true;
	}
}
