/**
 * 
 */
package org.usfirst.frc.team6880.robot.attachments;
import org.usfirst.frc.team6880.robot.FRCRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * TODO
 * add methods grabCube(), releaseCube(), ....
 *
 */
public class CubeHandler
{
	DoubleSolenoid cubeHandler;
	FRCRobot robot;
	private boolean open;
    /**
     * 
     */
    public CubeHandler(FRCRobot robot)
    {
        // TODO Auto-generated constructor stub
    	this.robot = robot;
    	cubeHandler = robot.pcmObj.initializeDoubleSolenoid(2, 3);
        cubeHandler.set(Value.kOff);
        open = false;
    }
    
    public void grabCube()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kForward);
    	open = false;
    }
    public void releaseCube()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kReverse);
    	open = true;
    }
    
    public void idle()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kOff);
    }
    
    public boolean isOpen()
    {
    	return open;
    }
}
