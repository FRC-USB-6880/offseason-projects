package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveDist implements RobotTask {
	FRCRobot robot;
	double speed;
    double startingLocation;
    double travelDist;
    double angleToMaintain;
	boolean movingForward;
	
	public TaskMoveDist(FRCRobot robot, double speed, double travelDist) {
		this.robot = robot;
		this.travelDist = travelDist;
		this.speed = speed;
		if (travelDist >= 0)
		    movingForward = true;
		else
		    movingForward = false;
		System.out.format("frc6880: TaskMoveDist created: speed=%f, targetDist=%f\n", speed, travelDist);
	}
	
	public void initTask()
	{
        //Set starting location of the encoders
        startingLocation = robot.driveSys.getEncoderDist();
        //Get the direction we want to travel
        angleToMaintain = robot.navigation.gyro.getYaw();
        System.out.format("frc6880: angleToMaintain = %f, startingLocation = %f\n", angleToMaintain, startingLocation);
	}
	
	public boolean runTask()
	{
        double distTravelled = robot.driveSys.getEncoderDist() - startingLocation;
//        System.out.format("distance travelled = %f, totalDistToTravel=%f\n", distTravelled, travelDist);
        //If robot still has remaining distance
        if (Math.abs(distTravelled) < Math.abs(travelDist) )
        {
            //Go straight and slow down before we reach out target distance
            double curSpeed = this.speed * (1 - Math.abs(distTravelled / travelDist));
            curSpeed = Math.max(Math.min(curSpeed, 0.1), this.speed);
            curSpeed = movingForward ? curSpeed : -curSpeed;
//            System.out.format("frc6880: Calling navigation.driveDirection(%f,%f)\n", curSpeed, angleToMaintain);
            robot.navigation.driveDirection(curSpeed, angleToMaintain);
//            robot.driveSys.tankDrive(curSpeed, curSpeed);
            return false;
        }
        //Else stop the robot and tell robot to go to next task
        robot.driveSys.tankDrive(0, 0);
        return true;
	}
}
