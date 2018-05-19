package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class MotorSpecsReader extends JsonReader {
	JSONObject motorObj;
    String motorType;

    public MotorSpecsReader(String filePath, String motorType) {
        super(filePath);
        try {
            motorType = JsonReader.getKeyIgnoreCase(rootObj, motorType);
            motorObj = (JSONObject) rootObj.get(motorType);
            this.motorType = motorType;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getMaxSpeed() {
        double maxSpeed = 0.0;

        try {
            maxSpeed = getDouble(motorObj, "maxSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }

    public int getCPR() {
        int CPR = 0; // Counts Per Revolution

        try {
            CPR = getInt(motorObj, "CPR");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (CPR);

    }
}
