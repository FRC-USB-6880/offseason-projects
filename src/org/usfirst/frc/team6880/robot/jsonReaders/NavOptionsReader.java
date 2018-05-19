package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class NavOptionsReader extends JsonReader {
	String navOptStr;
    public JSONObject navOptObj;
    public JSONObject imuObj=null;
    public JSONObject rangeObj=null;
    public JSONObject encoderVarsObj=null;

    public NavOptionsReader(String filePath, String navOptStr) {
        super(filePath);
        String key="";
        try {
            key = JsonReader.getKeyIgnoreCase(rootObj, navOptStr);
            this.navOptObj =(JSONObject) rootObj.get(key);
            key = JsonReader.getKeyIgnoreCase(navOptObj, "IMU");
            if (key != null) {
                imuObj = (JSONObject) navOptObj.get(key);
            }
            key = JsonReader.getKeyIgnoreCase(navOptObj, "RangeSensor");
            if (key != null) {
                rangeObj = (JSONObject) navOptObj.get(key);
            }
            key = JsonReader.getKeyIgnoreCase(navOptObj, "DriveSysParameters");
            if (key != null) {
                encoderVarsObj = (JSONObject) navOptObj.get(key);
            }
        } catch (Exception e) {
            System.out.println("frc6880: key: "+key);
        	e.printStackTrace();
        }
    }

    public boolean imuExists() {
        return (this.imuObj != null);
    }

    public boolean rangeSensorExists() { return (this.rangeObj != null); }

    public boolean encoderVarsExist() { return (this.encoderVarsObj != null); }

    public double getIMUVariableDouble(String variableName) {
        double value=0.0;
        try {
            value = getDouble(imuObj, variableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (value);
    }

    public double getDoubleDriveSysEncVar(String varName) {
        double maxSpeed = 1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, varName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }

    public double getTurningMaxSpeed() {
        double maxSpeed=1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, "TurningMaxSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }

    public double getStraightLineMaxSpeed() {
        double maxSpeed=1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, "StraightLineMaxSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }
}
