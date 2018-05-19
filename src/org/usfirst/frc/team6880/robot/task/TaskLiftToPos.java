package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskLiftToPos implements RobotTask {
    private FRCRobot robot;
    private double power;
    private String pos;
    private double target;
    private double tolerance;
    
    public TaskLiftToPos(FRCRobot robot, double power, String pos, double tolerance) {
        this.robot = robot;
        this.power = power;
        this.pos = pos;
        this.tolerance = tolerance;
    }

    @Override
    public void initTask() {
        // TODO Auto-generated method stub
        if (pos.equalsIgnoreCase("mid"))
            target = robot.lift.midRange[0];
        else if (pos.equalsIgnoreCase("high"))
            target = robot.lift.highRange[1];
        else if (pos.equalsIgnoreCase("low"))
            target = robot.lift.lowRange[0];
    }

    @Override
    public boolean runTask() {
        double diff = Math.abs(robot.lift.getCurPos() - (long)target);
        if (diff <= tolerance * target)
        {
            robot.lift.moveWithPower(0.0);
            return true;
        }
        else
        {
            robot.lift.setTargetHeight((long)target);
            robot.lift.moveToHeight(power);
            return false;
        }
    }

}
