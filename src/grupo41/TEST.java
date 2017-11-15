//package grupo41;
//
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TEST {


	public static Object getValue(JSONObject js, String w){
		Object gets =  (Object) js.get(w);
		return gets;
	}
	
	public static String getValueStr(JSONObject js, String w){
		String gets =  (String) js.get(w);
		return gets;
	}
	
	public static JSONObject getValueObj(JSONObject js, String w){
		JSONObject gets =  (JSONObject) js.get(w);
		return gets;
	}
	
	public static ArrayList<Object> getArray(JSONArray ja){
		ArrayList<Object> childs = new ArrayList<Object>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iter = ja.iterator();
		while(iter.hasNext()){
			childs.add(iter.next());
		}
		return childs;
	}
	
	public Boolean Type(JSONObject j, String t){
		return j.get("kind").equals(t) ? true : false;
	}
	
	
	public static void main(String[] args) {
		String filePath = args[0];
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);
			// create parser
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			//reading fase
			//1st lvl kind
			//System.out.println(jsonObject.get("kind"));
			
			//children
			//System.out.println(jsonObject.get("children") +  "ok");
			JSONArray children =  (JSONArray) jsonObject.get("children");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iter = children.iterator();
			
			while(iter.hasNext()){
				JSONObject child = (JSONObject) iter.next();
				//ASSIGN
				if(child.get("kind").equals("assign")){
					System.out.println("ASSIGNMENT");
					//Left
					JSONObject left =  (JSONObject) child.get("left");
					System.out.println(left.get("kind") + "  " + left.get("name"));
					System.out.println();
					//Right
					JSONObject right =  (JSONObject) child.get("right");
					String k = (String) right.get("kind");
					System.out.println(k);
					switch(k){
					case "offsetlookup":
						System.out.println(getValueStr(( getValueObj( right, "what")), "kind") + "   " + getValue((JSONObject) ( getValue( right, "what")), "name"));
						System.out.println(getValueStr(( getValueObj( right, "offset")), "kind") + "   " + getValue((JSONObject) ( getValue( right, "offset")), "value"));
						System.out.println();
						break;
					case "encapsed": 
						for(Object jo: getArray((JSONArray) getValue(right , "value"))){
							System.out.println(((JSONObject) jo).get("kind").equals("variable") ? "variable " + ((JSONObject) jo).get("name") : "value " + ((JSONObject) jo).get("value") );
						}
						System.out.println();
						System.out.println(( getValue( right, "type")));
						break;
					case "call": 
						System.out.println("kind " + getValueStr(getValueObj( right, "what"), "kind"));
						System.out.println("resolution " + getValueStr(getValueObj(right, "what"), "resolution"));
						System.out.println("name " + getValueStr(getValueObj( right, "what"), "name"));
						System.out.println();
						for(Object ob: getArray((JSONArray) getValue(right , "arguments"))){
							System.out.println(((JSONObject) ob).get("kind").equals("variable") ? "variable " + ((JSONObject) ob).get("name") : ((JSONObject) ob).get("value") );
						}
						break;	
					}
					System.out.println();
				}
				//ECHO
				if(child.get("kind").equals("echo")){
					System.out.println("ECHO");
					for(Object jo: getArray((JSONArray) getValue((JSONObject)child , "arguments"))){
						JSONObject j = getValueObj((JSONObject) jo,"what");
						System.out.println(j.get("kind").equals("variable") ? "variable " + getValueStr(j, "name") :"string " + j.get("value") );
						System.out.println();
						JSONObject o = getValueObj((JSONObject) jo,"offset");
						System.out.println(o.get("kind").equals("variable") ? "variable " + getValueStr(o, "name") :"string " + o.get("value") );
						System.out.println();
					}
				}
			}
			
			//errors
			System.out.println(jsonObject.get("errors"));

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}
