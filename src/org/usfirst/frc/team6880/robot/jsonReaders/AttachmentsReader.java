package org.usfirst.frc.team6880.robot.jsonReaders;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AttachmentsReader extends JsonReader {
    
    String attachmentName;
    JSONObject attchObj;

    public AttachmentsReader(String filePath, String attachmentName) {
        super(filePath);

        try {
            this.attachmentName = attachmentName;
            String key = getKeyIgnoreCase(rootObj, attachmentName);
            this.attchObj = (JSONObject) rootObj.get(key);
        } catch (Exception e) {
            System.err.println("frc6880:  Error while trying to get the specs for attachment " + attachmentName);
            e.printStackTrace();
        }
        System.out.println("attachmentName = " + attachmentName);
    }
    
    public int[] getLiftPos_encoderCounts(String liftPos) {
        int[]  encoderCntRange = {0, 5000};
        JSONArray jsArr;
        
        try {
            String key = getKeyIgnoreCase(attchObj, liftPos);
            jsArr = (JSONArray) attchObj.get(key);
            encoderCntRange[0] = ((Long) jsArr.get(0)).intValue();
            encoderCntRange[1] = ((Long) jsArr.get(1)).intValue();
        } catch (Exception e) {
            System.err.println("frc6880:  Error while trying to read the lift position " + liftPos);
            e.printStackTrace();
        }
        
        return (encoderCntRange);
    }
    
    public int getLiftControllerCANid() {
        int liftID=-1;
        
        try {
            String key = getKeyIgnoreCase(attchObj, "liftMotorCANid");
            liftID = ((Long) attchObj.get(key)).intValue();
        } catch (Exception e) {
            System.err.println("frc6880:  Error while trying to get liftMotorCANid");
            e.printStackTrace();
        }
        
        return (liftID);
    }
    
    public double getLiftSpoolDiameter() {
        double diameter = 2.0;
        
        try {
            String key = getKeyIgnoreCase(attchObj, "spoolDiameter");
            diameter = (double) attchObj.get(key);
        } catch (Exception e) {
            System.err.println("frc6880:  Error while trying to get spool diameter");
            e.printStackTrace();
        }
        return (diameter);
    }
    
    public double getLiftMotorOpenLoopRampTime() {
        double rampTime = 2.0;
        try {
            String key = getKeyIgnoreCase(attchObj, "openloop_rampTime");
            rampTime = (double) attchObj.get(key);
        } catch (Exception e) {
            System.err.println("frc6880:  Error while trying to get open loop ramp time");
            e.printStackTrace();
        }
        return (rampTime);        
    }
}
