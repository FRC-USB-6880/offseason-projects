package org.usfirst.frc.team6880.robot.util;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;

public class StateMachine {
	enum LiftStates {LOWRANGE, MIDRANGE, HIRANGE}
	FRCRobot robot;
	LiftStates currentLiftState;
	
	public StateMachine(FRCRobot robot)
	{
		this.robot = robot;
		currentLiftState = LiftStates.LOWRANGE;
	}

	public void switchLiftState(LiftStates state)
	{
		currentLiftState = state;
	}

	public void loop()
	{
		switch(currentLiftState)
		{
			case LOWRANGE:
				if(robot.lift.getCurPos() >= 14466)
					switchLiftState(LiftStates.HIRANGE);
				else if(robot.lift.getCurPos() >= 7233)
					switchLiftState(LiftStates.MIDRANGE);
				else
				{
					robot.driveSys.changeMultiplier(1.0);
				}
				break;
			case MIDRANGE:
				if(robot.lift.getCurPos() >= 14466)
					switchLiftState(LiftStates.HIRANGE);
				else if(robot.lift.getCurPos() < 7233)
					switchLiftState(LiftStates.LOWRANGE);
				else
				{
					robot.driveSys.changeMultiplier(1.0);
					if(robot.driveSys.getCurGear()==DriveSystem.Gears.HIGH)
						robot.driveSys.setLoSpd();
				}
				break;
			case HIRANGE:
				if(robot.lift.getCurPos() < 7233)
					switchLiftState(LiftStates.LOWRANGE);
				else if(robot.lift.getCurPos() < 14466)
					switchLiftState(LiftStates.MIDRANGE);
				else
				{
					robot.driveSys.changeMultiplier(0.7);
					if(robot.driveSys.getCurGear()==DriveSystem.Gears.HIGH)
						robot.driveSys.setLoSpd();
				}
				break;
		}
	}
}
