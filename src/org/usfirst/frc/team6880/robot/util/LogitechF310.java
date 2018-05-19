package org.usfirst.frc.team6880.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechF310 extends Joystick{
	// LogitechF310 axes
		private static final int AXIS_LEFT_X = 0;
		private static final int AXIS_LEFT_Y = 1;
		private static final int AXIS_SHOULDER = -1;		//TBD
		private static final int AXIS_RIGHT_X = 4;
		private static final int AXIS_RIGHT_Y = 5;
		private static final int AXIS_LEFT_TRIGGER = 2;
		private static final int AXIS_RIGHT_TRIGGER = 3;
		
		//LogitechF310 POV
		private static final int POV_DPAD = 0;
		
		// LogitechF310 buttons
		private static final int BUTTON_A = 0;
		private static final int BUTTON_B = 1;
		private static final int BUTTON_X = 2;
		private static final int BUTTON_Y = 3;
		private static final int BUTTON_BUMPER_LEFT = 4;
		private static final int BUTTON_BUMPER_RIGHT = 5;
		private static final int BUTTON_BACK = 6;
		private static final int BUTTON_START = 7;
		private static final int BUTTON_LEFT_STICK = 8;
		private static final int BUTTON_RIGHT_STICK = 9;
		private static final int BUTTON_MODE = -1;
		private static final int BUTTON_LOGITECH = -1;
		
		public LogitechF310(int portNum)
		{
			super(portNum);
		}
		
		//LogitechF310 axes getters
		
		public double leftStickX()
		{
			return getRawAxis(AXIS_LEFT_X);
		}
		
		public double leftStickY()
		{
			return getRawAxis(AXIS_LEFT_Y);
		}
		
		public double rightStickX()
		{
			return getRawAxis(AXIS_RIGHT_X);
		}
		
		public double rightStickY()
		{
			return getRawAxis(AXIS_RIGHT_Y);
		}
		
		public double leftTrigger()
		{
			return getRawAxis(AXIS_LEFT_TRIGGER);
		}
		
		public double rightTrigger()
		{
			return getRawAxis(AXIS_RIGHT_TRIGGER);
		}
		
		//LogitechF310 button getters
		
		public boolean dpadRight()
		{
			int direction = getPOV(POV_DPAD);
			if(direction == 90)
				return true;
			else
				return false;
		}
		
		public boolean dpadLeft()
		{
			int direction = getPOV(POV_DPAD);
			if(direction == 270)
				return true;
			else
				return false;
		}
		
		public boolean dpadUp()
		{
			int direction = getPOV(POV_DPAD);
			if(direction == 0)
				return true;
			else
				return false;
		}
		
		public boolean dpadDown()
		{
			int direction = getPOV(POV_DPAD);
			if(direction == 180)
				return true;
			else
				return false;
		}
		
		public boolean a()
		{
			return getRawButton(BUTTON_A);
		}
		
		public boolean b()
		{
			return getRawButton(BUTTON_B);
		}
		
		public boolean x()
		{
			return getRawButton(BUTTON_X);
		}
		
		public boolean y()
		{
			return getRawButton(BUTTON_Y);
		}
		
		public boolean leftBumper()
		{
			return getRawButton(BUTTON_BUMPER_LEFT);
		}
		
		public boolean rightBumper()
		{
			return getRawButton(BUTTON_BUMPER_RIGHT);
		}
		
		public boolean leftStickButton()
		{
			return getRawButton(BUTTON_LEFT_STICK);
		}
		
		public boolean rightStickButton()
		{
			return getRawButton(BUTTON_RIGHT_STICK);
		}
		
		public boolean back()
		{
			return getRawButton(BUTTON_BACK);
		}
		
		public boolean start()
		{
			return getRawButton(BUTTON_START);
		}
		
		public boolean mode()
		{
			return getRawButton(BUTTON_MODE);
		}
		
		public boolean home()
		{
			return getRawButton(BUTTON_LOGITECH);
		}
}
