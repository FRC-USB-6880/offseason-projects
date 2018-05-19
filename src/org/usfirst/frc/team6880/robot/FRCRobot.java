package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.TalonSRXDriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.VictorSPDriveSystem;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;
import org.usfirst.frc.team6880.robot.util.LogitechF310;
import org.usfirst.frc.team6880.robot.util.PowerMonitor;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	RobotConfigReader configReader;
	public LogitechF310 gamepad;
	public PowerDistributionPanel pdp;
	PowerMonitor powerMon;
	
	AutonomousTasks autonTasks;
	
	public FRCRobot(Robot wpilibrobot)
	{
	    String driveTrainName;

	    this.wpilibrobot = wpilibrobot;
	    this.pdp = new PowerDistributionPanel();
		
		// Important:  Base directory has to be set before trying to read any JSON file
		JsonReader.setBaseDir(JsonReader.baseDir);
		
		configReader = new RobotConfigReader(JsonReader.robotsFile, "TalonSRX-test-robot");
		System.out.println("frc6880: Config reader: " + configReader);
		driveTrainName = configReader.getDriveTrainName();
        System.out.println("frc6880: driveTrainName: " + driveTrainName);
		if (driveTrainName.equals("4VictorSP-1spd-WestCoastDrive"))
		    driveSys = new VictorSPDriveSystem(this, driveTrainName);
		else if (driveTrainName.equals("CAN-4TalonSRX-1spd-WestCoastDrive"))
		    driveSys = new TalonSRXDriveSystem(this, driveTrainName);

        navigation = new Navigation(this, configReader.getNavigationOption());

        gamepad = new LogitechF310(0);
        
        powerMon = new PowerMonitor(this);
	}
	
	public void initTeleOp()
	{
	}
	
	public void runTeleOp()
	{
		//TODO: Map controller sticks to drive system
		//Possible: map misc. controller buttons to tasks?
	    driveSys.arcadeDrive(gamepad.leftStickY(), gamepad.rightStickX());
	}
	
	public void initAutonomous()
	{
		//Reset encoders
		driveSys.resetEncoders();
		autonTasks = new AutonomousTasks(this, configReader.getAutoOption());	
		autonTasks.initFirstTask();
	}
	
	public void runAutonomous()
	{
	    autonTasks.runNextTask();
//	    powerMon.displayCurrentPower();
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}