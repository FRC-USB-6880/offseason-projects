/**
 * 
 */
package org.usfirst.frc.team6880.robot.task;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.AutonomousOptionsReader;
import org.usfirst.frc.team6880.robot.jsonReaders.JsonReader;
import org.usfirst.frc.team6880.robot.task.*;

/**
 * This class maintains the sequence of autonomous tasks to be run.
 *
 */
public class AutonomousTasks {
    RobotTask curTask=null;
    int taskNum=-1;
    boolean tasksDone=false;
    ArrayList<RobotTask> tasks;
    FRCRobot robot;
    AutonomousOptionsReader configReader;
    
    /**
     * 
     */
    public AutonomousTasks(FRCRobot robot, String autoSelection) {
        this.robot = robot;
        configReader = new AutonomousOptionsReader(JsonReader.autonomousOptFile, autoSelection);
        tasks = new ArrayList<RobotTask>();
        JSONArray taskArray = configReader.getAllTasks();

        for(int i=0;i<taskArray.size();i++)
        {
        	try{
	        	JSONObject obj = (JSONObject) taskArray.get(i);
	        	String key = JsonReader.getKeyIgnoreCase(obj, "name");
	        	double speed, tolerance;
	        	switch((String)obj.get(key))
	        	{
	        		case "MoveDistance":
	        			key = JsonReader.getKeyIgnoreCase(obj, "distance");
	        			double dist = (double)obj.get(key);
	        			key = JsonReader.getKeyIgnoreCase(obj, "speed");
	        			speed = (double)obj.get(key);
	        			tasks.add(new TaskMoveDist(robot, speed, dist));
	        			break;
	        		case "SetOrientation":
	        		    key = JsonReader.getKeyIgnoreCase(obj, "targetYaw");
	        		    double targetYaw = (double)obj.get(key);
                        key = JsonReader.getKeyIgnoreCase(obj, "speed");
                        speed = (double)obj.get(key);
                        key = JsonReader.getKeyIgnoreCase(obj, "tolerance");
                        tolerance = (double)obj.get(key);
                        tasks.add(new TaskSetOrientation(robot, targetYaw, speed, tolerance));
	        		    break;
	//        		case "SpinDegrees":
	//        			key = JsonReader.getKeyIgnoreCase(obj, "angle");
	//        			double angle = (double)obj.get(key);
	//        			key = JsonReader.getKeyIgnoreCase(obj, "speed");
	//        			speed = (double)obj.get(key);
	//        			//TODO Add TaskTurnDegrees
	//        			tasks.add(new );
	//        			break;
	//        		case "MoveStraightDist":
	//        			key = JsonReader.getKeyIgnoreCase(obj, "distance");
	//        			dist = (double)obj.get(key);
	//        			key = JsonReader.getKeyIgnoreCase(obj, "speed");
	//        			speed = (double)obj.get(key);
	//        			//TODO Add TaskMoveStraightDist
	//        			tasks.add(new );
	//        			break;
	//        		case "TurnToHeading":
	//        			key = JsonReader.getKeyIgnoreCase(obj, "heading");
	//        			double heading = (double)obj.get(key);
	//        			key = JsonReader.getKeyIgnoreCase(obj, "speed");
	//        			speed = (double)obj.get(key);
	//        			//TODO Add TaskTurnToHeading
	//        			tasks.add(new );
	//        			break;
	    			default:
	    				System.out.println("frc6880: AutonomousTasks: No tasks found");
	    				break;
	        	}
        	} catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        
        //Start with first task
        if (!tasks.isEmpty()) {
            taskNum = 0;
            curTask = tasks.get(0);
            tasksDone = false;
        }
    }
    
    public void initFirstTask()
    {
        if (curTask != null)
            curTask.initTask();
    }
    
    public void runNextTask() {
        //Run the current task. If current task ended
        if (!tasksDone && curTask.runTask())
        {
            //If there are still tasks to run
            if (taskNum + 1 < tasks.size())
            {
                System.out.println("frc6880: AutonomousTasks: Finished running task number " + taskNum);
                //Go to next task
                curTask = tasks.get(++taskNum);
                //Begin the next task
                curTask.initTask();
            }
            else
            {
                tasksDone = true;
                System.out.println("frc6880: AutonomousTasks: Robot has finished running the autonomous tasks");
            }
        }
    }

}
