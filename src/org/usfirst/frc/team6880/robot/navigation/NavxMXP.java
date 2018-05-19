package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Timer;

public class NavxMXP implements Gyro {
	FRCRobot robot;
	AHRS navx_device;
	
	public NavxMXP(FRCRobot robot)
	{
	    int numRetries=0;
	    int maxRetries=200;

	    this.robot = robot;
		this.navx_device = new AHRS(Port.kMXP);
		
		numRetries = 0;
		maxRetries = 200;
		while (!navx_device.isConnected() && (numRetries < maxRetries))
		{
			System.out.println("frc6880: navxMXP is not yet connected");
		    Timer.delay(.02);
		    numRetries++;
		}
		if (numRetries < maxRetries)
		    System.out.println("frc6880: navxMXP is connected");
		else {
		    System.err.println("frc6880: Error! navxMXP connection timeout!");
		    return;
		}
		
        numRetries = 0;
        maxRetries = 200;
		while (navx_device.isCalibrating() && (numRetries < maxRetries))
		{
			System.out.println("frc6880: navxMXP still callibrating");
			Timer.delay(.02);
            numRetries++;
		}
        if (numRetries < maxRetries)
            System.out.println("frc6880: navxMXP done with calibration");
        else
        {
            System.err.println("frc6880: Error! navxMXP calibration timeout!");
            return;
        }

        navx_device.zeroYaw();
	}
	
	// TODO:
	//  Sometimes Yaw value does not get refreshed often enough.  So we need to estimate
	//  the yaw value in the absence of a fresh reading.  This estimation can be done by
	//  a simple linear estimator.  Keep track of most recent angular velocity (degrees per second)
	//  and time stamp of the last refresh.  
	//     estimatedYaw = lastKnownYaw + (lastKnowAngularVelocity * elapsedTime)
	// This will be very important if we find out that current time interval of 20 milliseconds
	// for TimedRobot is too high and reduce that value.
	
	public double getYaw()
	{	
		return (double) navx_device.getYaw();
	}
	
	public double getPitch()
	{	
		return (double) navx_device.getPitch();
	}
	
	public double getRoll()
	{
		return (double) navx_device.getRoll();
	}
}