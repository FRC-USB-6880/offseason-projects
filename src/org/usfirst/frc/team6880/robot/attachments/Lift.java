/**
 * 
 */
package org.usfirst.frc.team6880.robot.attachments;
import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.AttachmentsReader;
import org.usfirst.frc.team6880.robot.jsonReaders.JsonReader;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * TODO
 *
 */
//motor, FRC robot, methods for raising and lowering lift, 
public class Lift
{
	FRCRobot robot;
	WPI_TalonSRX liftMotor;
	private double height;
	public Encoder liftEncoder;
//	public static final long MAX_LOW = 7233;
//	public static final long MAX_MID = 14466;
//	public static final long MAX_HIGH = 21700;
//	public static final long RANGE_VALUE = 7233;
	
	public int[] lowRange = {0,0};
	public int[] midRange = {0,0};
	public int[] highRange = {0,0};
	public int rangeValue;
	
	private double spoolDiameter;
	private double spoolCircumference;
	private double distancePerCount;
	private double curPower;
	private long targetPos;

	AttachmentsReader configReader;

    /**
     * 
     */
    public Lift(FRCRobot robot)
    {
        // TODO Auto-generated constructor stub
    	this.robot = robot;
    	configReader = new AttachmentsReader(JsonReader.attachmentsFile, "Lift");
    	int liftMotorCANid = configReader.getLiftControllerCANid();
    	double liftMotorRampTime = configReader.getLiftMotorOpenLoopRampTime();
        spoolDiameter = configReader.getLiftSpoolDiameter();
        lowRange = configReader.getLiftPos_encoderCounts("liftPos_lowRange");
        midRange =  configReader.getLiftPos_encoderCounts("liftPos_midRange");
        highRange = configReader.getLiftPos_encoderCounts("liftPos_highRange");
        rangeValue = highRange[1]/2;
        
        System.out.println("frc6880: liftMotorCANid = " + liftMotorCANid +
                ", spoolDiameter = " + spoolDiameter + 
                ", loopRampTime = " + liftMotorRampTime +
                ", lowRange = [" + lowRange[0] + "," + lowRange[1] + "]" +
                ", midRange = [" + midRange[0] + "," + midRange[1] + "]" + 
                ", highRange = [" + highRange[0] + "," + highRange[1] + "]");

//      liftMotor = new WPI_TalonSRX(15);
        liftMotor = new WPI_TalonSRX(liftMotorCANid);

//        liftMotor.configOpenloopRamp(0, 0); /* ramp from neutral to full within 1 seconds */

    	height = 0;
//    	liftEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
    	spoolCircumference = Math.PI * spoolDiameter;
    	// TODO: distancePerCount has to be calculated using CPR value
    	//   read from encoder_specs.json file.  
    	distancePerCount = spoolCircumference / 360;
    	curPower = 0.0;
//    	liftEncoder.setDistancePerPulse(distancePerCount);
    	/*
    	 * The status frames with their default period include:
    	 * General Status 1 (10ms)
    	 * Feedback0 Status 2 (20ms)
    	 * Quadrature Encoder Status 3 (160ms)
    	 * Analog Input / Temperature / Battery Voltage Status 4 (160ms)
    	 * Pulse Width Status 8 (160ms)
    	 * Targets Status 10 (160ms)
    	 * PIDF0 Status 14 (160ms)
    	 */
//    	liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
    	liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    	

    	targetPos = 0;
    }
    
    public void stop()
    {
    	liftMotor.set(0);
    }
    
    public boolean checkUpperLimit()
    {
//    	if(getCurPos()>=MAX_HIGH)
        if(getCurPos()>=highRange[1])
    		return true;
    	return false;
    }
    
    public boolean checkLowerLimit()
    {
    	if(getCurPos()<=lowRange[0])
    		return true;
    	return false;
    }
    
    public long getCurPos()
    {
    	return -liftMotor.getSelectedSensorPosition(0);
    }
    
    public void moveWithPower(double power)
    {
//    	if(power<0)
//    		liftMotor.set(checkLowerLimit() ? 0.0 : power);
//    	else if(power>0)
//    		liftMotor.set(checkUpperLimit() ? 0.0 : power);
//    	displayCurrentPosition();
    	liftMotor.set(power);
    	curPower = power;
    }
    public void displayCurrentPosition()
    {
        double value = -liftMotor.getSelectedSensorPosition(0);
        System.out.format("frc6880: Current lift encoder value = %f\n", value);
//        SmartDashboard.putNumber("LiftPos", value);
    }
    
    public boolean moveToHeight(double power)
    {
		if(targetPos+100 < getCurPos()) 
		{
			moveWithPower(-power);
			return false;
		}
		else if(targetPos-100 > getCurPos())
		{
			moveWithPower(power);
			return false;
		}
		
		stop();
		return true;
    }
    
    public void setTargetHeight(long target)
    {
    	if(targetPos>highRange[1])
    		targetPos = highRange[1];
    	else if (targetPos<0)
    		targetPos = 0;
    	else
    		targetPos = target;
    }

	public boolean isMoving() {
		// TODO Auto-generated method stub
		if(curPower != 0.0)
			return true;
		return false;
	}

}
