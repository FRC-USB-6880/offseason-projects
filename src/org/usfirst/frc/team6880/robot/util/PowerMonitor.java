/**
 * 
 */
package org.usfirst.frc.team6880.robot.util;

import org.usfirst.frc.team6880.robot.FRCRobot;

/**
 * Class to monitor the Power consumption by various motors and pneumatics
 * compressor and take actions as necessary.
 *
 */
public class PowerMonitor {
    FRCRobot robot;

    /**
     * TODO:
     * Add methods to reduce the motor speed if the pneumatics compressor is turned on.
     * This is a nice to have feature.
     */
    public PowerMonitor(FRCRobot robot) {
        // TODO Auto-generated constructor stub
        this.robot = robot;
    }
    
    public void displayCurrentPower() {
        System.out.format("Total Current = %f, Temperature = %f\n", 
                robot.pdp.getTotalCurrent(), robot.pdp.getTemperature());
    }

}
