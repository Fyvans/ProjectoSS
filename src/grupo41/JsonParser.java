package grupo41;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

	//specific getters
	public String getKind(JSONObject js){return (String) js.get("kind"); }
	public String getName(JSONObject js){return (String) js.get("name");}
	
	public JSONObject getleft(JSONObject js){return getObject(js, "left");}
	public JSONObject getright(JSONObject js){return getObject(js, "right");}
	
	public ArrayList<String> getArguments(JSONObject js, String valueargs ){
		ArrayList<String> result = new ArrayList<String>();
		JSONArray ja = (JSONArray) js.get(valueargs);
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = ja.iterator();
		while(i.hasNext()){
			JSONObject j = i.next();
			if(j.get("kind").equals("variable")){
				result.add((String) j.get("name"));
			}
		}
		return result;
	}
	
	
	//ifs && Whiles
	public JSONObject getBody(JSONObject js){
		return (JSONObject) js.get("body");
	} 
	
	public JSONObject getAlternate(JSONObject js) {
		return (JSONObject) js.get("alternate");
	}
	
	public String getCallName(JSONObject js){return getName(getObject(js, "what"));}
	
	
	public String getBinVarLeft(JSONObject js){
		return getName(getObject(js, "left"));
	}

	public String getBinVarRight(JSONObject js){
		return getName(getObject(js, "right"));
	}
	
	
	//general getters
	public ArrayList<JSONObject> getArray(JSONObject js, String s){
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		JSONArray ja = (JSONArray) js.get(s);
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = ja.iterator();
		while(i.hasNext()){
			result.add(i.next());
		}
		return result;
	}
	
	public JSONObject getObject(JSONObject jo, String s){
		return (JSONObject) jo.get(s);
	}

	
	
	
	
	
}
