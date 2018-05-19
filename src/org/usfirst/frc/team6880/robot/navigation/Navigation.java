package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.util.ClipRange;



public class Navigation {
	FRCRobot robot;
	public Gyro gyro;
	private double gyro_GoStraight_KP = 0.2;
    public enum SpinDirection {CW, CCW, NONE} // ClockWise or CounterClockWise

	public Navigation(FRCRobot robot, String navOptStr) {
		this.robot = robot;
		NavOptionsReader configReader = new NavOptionsReader(JsonReader.navigationFile, navOptStr);
		if(configReader.imuExists())
			this.gyro = new NavxMXP(robot);
		gyro_GoStraight_KP = configReader.getIMUVariableDouble("Kp");
		System.out.format("gyro_GoStraight_Kp = %f\n", gyro_GoStraight_KP);
	}
	
	/**
	 * This method makes the robot drive facing a target direction
	 * @param speed The speed for the robot to drive at; always a positive number
	 * @param targetDirection The desired direction for the robot to drive
	 * @param driveBackwards true if driving backwards; false if driving forwards
	 */
	public void driveDirection(double speed, double targetYaw, boolean driveBackwards)
	{
	    double error, correction, leftSpeed, rightSpeed;
	    error = getDegreesToTurn(gyro.getYaw(), targetYaw);
	    correction = error * gyro_GoStraight_KP / 2;
	    if (driveBackwards) {
            leftSpeed = ClipRange.clip(speed-correction, -0.8, 0.8);
            rightSpeed = ClipRange.clip(speed+correction, -0.8, 0.8);	        
	    } else {
	        leftSpeed = ClipRange.clip(speed+correction, -0.8, 0.8);
	        rightSpeed = ClipRange.clip(speed-correction, -0.8, 0.8);
	    }
//        System.out.format("error=%f, correction=%f, leftSpeed=%f, rightSpeed=%f\n",
//                error, correction, leftSpeed, rightSpeed);
        if (driveBackwards)
            robot.driveSys.tankDrive(-leftSpeed, -rightSpeed);
        else
            robot.driveSys.tankDrive(leftSpeed, rightSpeed);
	    
//		robot.driveSys.arcadeDrive(speed, gyro_GoStraight_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360));
	}

	//TODO Create driveStraightToDistance()
	
	/**
	 * This method makes the robot drive spin to a target orientation
	 * @param targetDirection The desired direction for the robot to point
	 */
	public void spinToDirection(double targetDirection)
	{
	    double turnValue = gyro_GoStraight_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360) / 180;
	    System.out.format("frc6880: turnValue=%f, targetDirection=%f, curYaw=%f\n", 
	            turnValue, targetDirection, gyro.getYaw());
		robot.driveSys.arcadeDrive(0, turnValue);
	}
	
    /**
     * Calculates whether the robot has to spin clockwise or counter clockwise to go from
     * currentYaw to targetYaw
     * @param curYaw
     * @param targetYaw
     * @return CLOCKWISE, COUNTERCLOCKWISE, NONE
     */
    public SpinDirection getSpinDirection(double curYaw, double targetYaw) {
        SpinDirection direction = SpinDirection.NONE;
        double diffYaw = targetYaw - curYaw;

        double degreesToTurn = diffYaw>180 ? diffYaw-360 : diffYaw<-180 ? diffYaw+360 : diffYaw;

        if (degreesToTurn < 0) {
            direction = SpinDirection.CCW;
        } else {
            direction = SpinDirection.CW;
        }
        return (direction);
    }
    
    /**
     * Converts the given targetYaw into the angle to turn.  Returns a value between [-180, +180]
     * @param curYaw  current yaw value
     * @param targetYaw  target yaw value
     *      The targetYaw is with respect to the initial autonomous starting position
     *      The initial orientation of the robot at the beginning of the autonomous period
     *      is '0'. targetYaw is between 0 to 360 degrees.

     * @return degreesToTurn
     */
    public double getDegreesToTurn(double curYaw, double targetYaw) {
        double diffYaw = targetYaw - curYaw;
        double degreesToTurn = diffYaw>180 ? diffYaw-360 : diffYaw<-180 ? diffYaw+360 : diffYaw;
        return (degreesToTurn);
    }
	
	//TODO Create turnForDegrees()
	
	
	//TODO Create turnToHeading()	

	//TODO: Coordinate System?
	//TODO: Computer Vision?
}
