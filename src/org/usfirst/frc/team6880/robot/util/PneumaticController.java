/**
 * 
 */
package org.usfirst.frc.team6880.robot.util;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * TODO
 * Create Solenoid/DoubleSolenoid objects; add methods to activate/deactivate solenoids.
 *
 */
public class PneumaticController {
    Compressor compr;
    int pcmCANid;

    /**
     * 
     */
    public PneumaticController(int pcmCANid) {
        // TODO Auto-generated constructor stub
        this.pcmCANid = pcmCANid;
        compr = new Compressor(pcmCANid);
        /*
         * When closed loop control is enabled the PCM will automatically turn the compressor
         *  on when the pressure switch is closed (below the pressure threshold) and turn it 
         *  off when the pressure switch is open (~120PSI).
         */
        compr.setClosedLoopControl(true);
        
    }
    
    public void turnOffCompressor() {
        compr.stop();
    }
    public void turnOnCompressor() {
      compr.start();
    }
    
    public void displayCompressorStatus() {
        System.out.println("frc6880: compressor current = " + compr.getCompressorCurrent() + 
                ", pressure switch status = " + compr.getPressureSwitchValue());
    }
    
    public DoubleSolenoid initializeDoubleSolenoid(int channel_A, int channel_B) {
        DoubleSolenoid exampleDouble = new DoubleSolenoid(pcmCANid, channel_A, channel_B);

        exampleDouble.set(DoubleSolenoid.Value.kOff);
//        exampleDouble.set(DoubleSolenoid.Value.kForward);
//        exampleDouble.set(DoubleSolenoid.Value.kReverse);
        return (exampleDouble);
    }

}
