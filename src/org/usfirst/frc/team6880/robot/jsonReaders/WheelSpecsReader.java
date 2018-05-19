package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class WheelSpecsReader extends JsonReader {
	JSONObject wheelSpecObj;
    String wheelType;
    
    public WheelSpecsReader(String filePath, String wheelType) {
        super(filePath);
        try {
            wheelType = getKeyIgnoreCase(rootObj, wheelType);
            wheelSpecObj =  (JSONObject) rootObj.get(wheelType);
            this.wheelType = wheelType;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getDiameter() {
        double diameter = 0.0;

        try {
            diameter = getDouble(wheelSpecObj, "diameter");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (diameter);
    }

    public double getFrictionCoeff() {
        double frictionCoeff = 1.0;

        try {
            frictionCoeff = getDouble(wheelSpecObj, "friction_coeff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (frictionCoeff);
    }
}
