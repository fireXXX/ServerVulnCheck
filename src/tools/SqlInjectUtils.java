package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SqlInjectUtils {
	private ArrayList options = null;
	private String data = null;
	private String apihost = "http://127.0.0.1:8775/";
	private String taskid = null;

	public SqlInjectUtils(ArrayList options, String data) {
		this.options = options;
		this.data = data;
	}
	
	public String run() {
		if (!newTask())
			return null;
		
		if (!setOptions())
			return null;
		
		if (!startScan())
			return null;
		
		int curtime = 0;
		int step = 5;
		while(true) {
			String status = getScanStatus();
            if (status.equals("running")) {
            	try {   
            		Thread.currentThread().sleep(step*1000);
            	} catch(Exception e) {
            		return null;
            	}
                curtime += step;
                if (curtime > 1800)
                    break;            	
            } else if (status.equals("terminated"))
                break;
            else {
            	step += 5;
                break;
            }
		}
		
		String data = getScanData();
		return data;
	}
	
	private boolean newTask() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(apihost+"task/new", "GET")) {
			Logger.getInstance().insertLog("Error", "SimpleSqlInject start server fail!");
			return false;
		}
		
		String jsonstring = utils.readAllResponse();
		if (jsonstring == null)
			return false;
		
		JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject)parse.parse(jsonstring);
            taskid = json.get("taskid").getAsString();
            
            if (taskid.length() == 0)
            	return false;
        } catch (JsonIOException e) {
            return false;
        } catch (JsonSyntaxException e) {
        	return false;
        }
        
        return true;
	}
	
	private boolean delTask() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(apihost+"task/"+this.taskid+"/delete", "GET"))
			return false;
		
		return true;
	}
	
	private boolean startScan() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		if (!utils.openConnection(apihost+"scan/"+this.taskid+"/start", "POST", headers))
			return false;
		
		String result = utils.getPostResponse(data);
		if (result == null)
			return false;
		
		JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject)parse.parse(result);
            String engineid = json.get("engineid").getAsString();
            String success = json.get("success").getAsString();
            
            if (engineid.length()>0 && success.length()>0)
            	return true;
        } catch (JsonIOException e) {
            return false;
        } catch (JsonSyntaxException e) {
        	return false;
        }
        
        return false;
	}
	
	private String getScanStatus() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(apihost+"scan/"+this.taskid+"/status", "GET"))
			return "error";
		
		String jsonstring = utils.readAllResponse();
		if (jsonstring == null)
			return "error";
		
		JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject)parse.parse(jsonstring);
            String status = json.get("status").getAsString();
            
            if (status.equals("running"))
            	return "running";
            else if (status.equals("terminated"))
            	return "terminated";
        } catch (JsonIOException e) {
        	return "error";
        } catch (JsonSyntaxException e) {
        	return "error";
        }
        
        return "error";
	}
	
	private String getScanData() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(apihost+"scan/"+this.taskid+"/data", "GET"))
			return null;
		
		String jsonstring = utils.readAllResponse();
		if (jsonstring == null)
			return null;
		
		JsonParser parse =new JsonParser();
        try {
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json=(JsonObject)parse.parse(jsonstring);
            JsonElement ele = json.get("data");
            if (!ele.isJsonNull()) {
            	String data = gson.toJson(ele);
            	if (data.length() > 0)
                	return data;
            } else 
            	return null;
        } catch (JsonIOException e) {
            return null;
        } catch (JsonSyntaxException e) {
        	return null;
        } catch (Exception e) {
        	return null;
        }
        
        return null;
	}
	
	private boolean setOptions() {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		if (!utils.openConnection(apihost+"option/"+this.taskid+"/set", "POST", headers))
			return false;
		
		String result = utils.getPostResponse(data);
		if (result == null)
			return false;
		
		JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject)parse.parse(result);
            
            String success = json.get("success").getAsString();
            if (success.toLowerCase().equals("true"))
            	return true;
        } catch (JsonIOException e) {
            return false;
        } catch (JsonSyntaxException e) {
        	return false;
        }
        
        return true;
	}
}
