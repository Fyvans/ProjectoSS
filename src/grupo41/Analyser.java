package grupo41;
/*

name of vulnerability (e.g., SQL injection)

    • a set of entry points (e.g., $_GET, $_POST),

    • a set of sanitization/validation functions (e.g., mysql_real_escape_string),

    • and a set of sensitive sinks (e.g., mysql_query).

*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Analyser {
	private static JsonParser jp = new JsonParser(); 
	private static ArrayList<Vulnerabilities> vulns =  new ArrayList<Vulnerabilities>();
	private static ArrayList<Variable> vars = new ArrayList<Variable>();
	
	public static void main(String[] args) {
		
		if(args.length == 0){
			System.out.print("Please select a file as input.");
		}
		String filePath = args[0];
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);
			// create parser
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			JSONArray children = (JSONArray) jsonObject.get("children");
			  
			//children
			for(JSONObject b: jp.getArray(children)){
				
				if(jp.getkind(b).equals("assign")){
					Variable v = new Variable(jp.getName(jp.getleft(b)));
					JSONObject r = jp.getright(b);
					switch(jp.getkind(r)){
						case "offsetlookup":
							System.out.println(jp.getOffsetWhatKind(r) + " ---"  + jp.getOffsetWhatname(r));
							
							break;
						case "encapsed": 

							break;
						case "call": 

							break;	
						}
					if(!vars.contains(v)){
						vars.add(v);
					}
				if(jp.getkind(b).equals("echo")){
					JSONArray argus = (JSONArray) jsonObject.get("arguments");
					for(JSONObject a: jp.getArray(argus)){
						
					}
					
				}
				}
				
				
				
				
					
			}
			
			System.out.println("OLD");
			System.out.println();
			System.out.println();
			
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
