package org.usfirst.frc.team6880.robot.driveTrain;

public interface DriveSystem {
	public enum Gears {LOW, HIGH};
	public void tankDrive(double leftSpeed, double rightSpeed);
	public void arcadeDrive(double speed, double rotation);
	public void resetEncoders();
	public double getEncoderDist();
	public void setLoSpd();
	public void setHiSpd();
	public boolean isMoving();
	public Gears getCurGear();
	public void changeMultiplier(double multiplier);
}
