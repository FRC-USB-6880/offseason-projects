package org.usfirst.frc.team6880.robot.jsonReaders;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	public static String baseDir = new String("/home/lvuser/team6880/");
	public static String sensorSpecsFile, wheelSpecsFile, motorSpecsFile, encoderSpecsFile;
	public static String robotsFile, attachmentsFile, driveTrainsFile, navigationFile;
	public static String autonomousRedDir, autonomousBlueDir, autonomousOptFile;
//    private static String sensorSpecsFile = new String(baseDir + "specs/sensor_specs.json");
//    private static String wheelSpecsFile = new String(baseDir + "specs/wheel_specs.json");
//    private static String motorSpecsFile = new String(baseDir + "specs/motor_specs.json");
//    private static String attachments = new String(baseDir + "attachments.json");
//    private static String navigationFile = new String(baseDir + "navigation_options.json");
//    private static String autonomousOptFile = new String(baseDir + "autonomous_options.json");
//    private static String driveSystemsFile = new String(baseDir + "drivesystems.json");
////    private static String opModesDir = new String(baseDir + "/opmodes/");
//    private static String autonomousRedDir =  new String(baseDir + "autonomous/red/");
//    private static String autonomousBlueDir = new String(baseDir + "autonomous/blue/");
	
    private String filePath;
    public String jsonStr;
	private JSONParser parser = null;
	public JSONObject rootObj = null;
	
	public JsonReader(String filePath)
	{
		this.filePath = filePath;
		this.parser = new JSONParser();
		
		FileReader fileReader = null;
        // If the given file path does not exist, give an error
        try {
            fileReader = new FileReader(this.filePath);
        }
        catch (IOException except) {
            System.out.println("frc6880: Error while trying to open "+filePath+". Error: "+except.getMessage());
        }

        try {
            Object obj = parser.parse(fileReader);
            rootObj = (JSONObject) obj;
        }
        catch (Exception except) {
        	System.out.println("frc6880: Error while parsing "+filePath+". Error: "+except.getMessage());
        }
        try {
            fileReader.close();
        } catch (IOException except) {
        	System.out.println("frc6880: Error while trying to close "+filePath+". Error: "+except.getMessage());
        }
        return;
	}
	
	public static void setBaseDir(String baseDir) {
	    JsonReader.baseDir = baseDir;
	    JsonReader.robotsFile = new String(baseDir + "robots.json");
	    JsonReader.sensorSpecsFile = new String(baseDir + "specs/sensor_specs.json");
	    JsonReader.wheelSpecsFile = new String(baseDir + "specs/wheel_specs.json");
	    JsonReader.encoderSpecsFile = new String(baseDir + "specs/encoder_specs.json");
	    JsonReader.motorSpecsFile = new String(baseDir + "specs/motor_specs.json");
	    JsonReader.attachmentsFile = new String(baseDir + "attachments.json");
	    JsonReader.navigationFile = new String(baseDir + "navigation_options.json");
	    JsonReader.autonomousOptFile = new String(baseDir + "autonomous_options.json");
	    JsonReader.driveTrainsFile = new String(baseDir + "drive_trains.json");
//	    JsonReader.opModesDir = new String(baseDir + "/opmodes/");
	    JsonReader.autonomousRedDir =  new String(baseDir + "autonomous/red/");
	    JsonReader.autonomousBlueDir = new String(baseDir + "autonomous/blue/");
	}
	
	public String getString(JSONObject obj, String key) {
        String value=null;
        try {
        	key = getKeyIgnoreCase(obj, key);
            value = (String) obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("frc6880: Error getting value for the key " + key);
        }
        return (value);
    }

    public double getDouble(JSONObject obj, String key) {
        double value=0.0;
        try {
        	key = getKeyIgnoreCase(obj, key);
            value = (double) obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("frc6880: Error getting value for the key " + key);
        }
        return (value);
    }

    public boolean getBoolean(JSONObject obj, String key) {
        boolean value=false;
        try {
        	key = getKeyIgnoreCase(obj, key);
            value = (boolean) obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("frc6880: Error getting value for the key " + key);
        }
        return (value);
    }
    
    public int getInt(JSONObject obj, String key)
    {
    	int value = 0;
    	try{
    		key = getKeyIgnoreCase(obj, key);
    		Long longObj = (Long) obj.get(key);
    		value = (int) longObj.intValue();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("frc6880: Error getting value for the key " + key);
		}
    	return value;
    }
    
    public JSONArray getArray(JSONObject obj, String key)
    {
    	JSONArray array=null;
    	try{
    		key = getKeyIgnoreCase(obj, key);
    		array = (JSONArray) obj.get(key);
    	} catch (Exception e) {
    		 e.printStackTrace();
             System.out.println("frc6880: Error getting value for the key " + key);
		}
    	return array;
    }
    
    public static String getKeyIgnoreCase(JSONObject obj, String key) throws Exception {
//         Set set = obj.keySet();
//         Iterator iter = set.iterator();
//        while (iter.hasNext()) {
//            String key1 = (String)iter.next();
//            if (key1.equalsIgnoreCase(key)) {
//                return (key1);
//            }
//        }
    	
//    	if(obj.containsKey(key))
    		return key;
//        return null;
    }
}
