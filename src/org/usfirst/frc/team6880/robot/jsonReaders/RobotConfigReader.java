/**
 * 
 */
package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author frcusb6880
 *
 */
public class RobotConfigReader extends JsonReader {
	String robotName;
    JSONObject robotObj=null;
    public RobotConfigReader(String filePath, String robotName)
    {
        super(filePath);
        this.robotName = robotName;
        try {
            this.robotObj = (JSONObject) rootObj.get(robotName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDriveTrainName(){
        String name = null;
        try{
            name = getString(robotObj, "DriveTrain");
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String getNavigationOption() {
        String navigationOption = null;
        try{
            navigationOption = getString(robotObj, "Navigation");          
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("frc6880: navigation key not found for the robot named "+robotName);
        }
        return (navigationOption);
    }
    
    public String getAutoOption()
    {
    	String autoOption= "";
    	try
    	{
    		autoOption = getString(robotObj, "autonomous_option");
    	} catch(Exception e)
    	{
    		e.printStackTrace();
    		System.out.println("frc6880: Autonomous key not found for the robot named "+robotName);
    	}
    	return autoOption;
    }

    public String[] getAttachments(String autoOrTeleop) {
        int len = 0;
        String[] attachmentsArr = null;
        JSONArray attachs=null;
        try {
            if (autoOrTeleop.equalsIgnoreCase("Autonomous")) {
                attachs = getArray(robotObj, "autonomous_attachments");
            } else if (autoOrTeleop.equalsIgnoreCase("Teleop")) {
                attachs = getArray(robotObj, "teleop_attachments");
            }
            len = attachs.size();
            System.out.println("frc6880: Length of attachs array = " + len);
            attachmentsArr = new String[len];
            for (int i = 0; i < len; i++) {
                attachmentsArr[i] = (String) attachs.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("frc6880: Problem finding one or more attachments for the robot named " +
                    robotName);
        }
        return (attachmentsArr);
    }

    public double getRobotWidth() {
        double value = 0.0;
        try {
            value = getDouble(robotObj, "RobotWidth");
        } catch (Exception e) {
            System.out.println("frc6880: getRobotWidth(): Could not retrieve the RobotWidth");
            e.printStackTrace();
        }
        return (value);
    }
    
    public void printForDebug() {
        System.out.println("RobotWidth = " + getRobotWidth());
        System.out.println("DriveSystem = " + getDriveTrainName());
    }
}
