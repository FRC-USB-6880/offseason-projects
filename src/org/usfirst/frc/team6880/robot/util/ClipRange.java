package org.usfirst.frc.team6880.robot.util;


public class ClipRange {
    public static double clip(double num, double lower, double upper)
    {
        if(num < lower)
            num = lower;
        else if(num > upper)
            num = upper;
        return num;
    }
    
    public static int clip(int num, int lower, int upper)
    {
        if(num < lower)
            num = lower;
        else if(num > upper)
            num = upper;
        return num;
    }
    
    public static float clip(float num, float lower, float upper)
    {
        if(num < lower)
            num = lower;
        else if(num > upper)
            num = upper;
        return num;
    }
    
    public static long clip(long num, long lower, long upper)
    {
        if(num < lower)
            num = lower;
        else if(num > upper)
            num = upper;
        return num;
    }
}