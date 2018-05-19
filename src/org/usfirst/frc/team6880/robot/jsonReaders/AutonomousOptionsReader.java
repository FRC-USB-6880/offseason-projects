package org.usfirst.frc.team6880.robot.jsonReaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AutonomousOptionsReader extends JsonReader {
	public JSONArray tasks;
    public AutonomousOptionsReader(String filePath, String autoPos, String autoOption) {

        super(filePath);
        try {
            String key = getKeyIgnoreCase(rootObj, autoPos);
            JSONObject jsonObj = (JSONObject) rootObj.get(key);
            String key2 = getKeyIgnoreCase(jsonObj, autoOption);
            tasks = (JSONArray) jsonObj.get(key2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("autoPos = " + autoPos + ", autoOption = " + autoOption + ", tasks = " + tasks);
    }
    public AutonomousOptionsReader(String filePath){
        super(filePath);
    }

    public JSONObject getTask(int taskNum){
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject)tasks.get(taskNum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getTaskType(int taskNum) {
        String taskType = null;
        JSONObject obj = null;

        try {
            obj = (JSONObject) tasks.get(taskNum);
            taskType = getString(obj, "type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (taskType);
    }

    public String getMethodName(int taskNum) {
        JSONObject obj = null;
        String methodName = null;

        try {
            obj = (JSONObject) tasks.get(taskNum);
            methodName = getString(obj, "method");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (methodName);
    }

    public JSONArray getAllTasks()
    {
    	return this.tasks;
    }
    
    public List<String> getAll(){
    	@SuppressWarnings("unchecked")
		Set<String> keySet = rootObj.keySet();
    	ArrayList<String> rootObjNames = new ArrayList<String>();
        keySet.stream().forEach((key) -> {
        	rootObjNames.add(key);
        });
        return rootObjNames;
    }
}
