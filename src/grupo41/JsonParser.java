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
	private String kind = "kind";
	
	//right
	private String right = "right";
	//lookup
	private String kindOffsetLookup = "offsetlookup";
	private String offsetKind = "what";
	
	//encapsed
	private String kindencapsed = "encapsed";
	
	//call
	private String kindcall = "call";
	
	//left
	private String left = "left";
	
	private String name = "name";
	
	public JsonParser(){
		
	}
	
	//specific getters offset
	public String getOffsetWhatKind(JSONObject js){ return getValueStr(getValueObj(js, "what"), kind);}
	public String getOffsetWhatname(JSONObject js){ return getValueStr(getValueObj(js, "what"), name);}
	//public String getOffsetkind(JSONObject js){ return getValueStr(getValueObj(js, "offset"), name);}
	//public String getOffsetname(JSONObject js){ return getValueStr(getValueObj(js, "offset"), name);}
	
	//specific getters
	public String getArgument(JSONObject js){return Type(js, "variable") ?  getValueStr(js, "name") :  getValueStr(js, "value"); }
	
	//specific getters
	public String getName(JSONObject js){return getValueStr(js, name);}
	public String getkind(JSONObject js){return getValueStr(js, kind);}
	public JSONObject getleft(JSONObject js){return getValueObj(js, left);}
	public JSONObject getright(JSONObject js){return getValueObj(js, right);}
	

	//ir buscar um objecto
		public Object getValue(JSONObject js, String w){
			Object gets =  (Object) js.get(w);
			return gets;
		}
		
		//ir buscar um valor, normalmente string
		public String getValueStr(JSONObject js, String w){
			String gets =  (String) js.get(w);
			return gets;
		}
		
		//ir buscar um jsonobject
		public JSONObject getValueObj(JSONObject js, String w){
			JSONObject gets =  (JSONObject) js.get(w);
			return gets;
		}
		
		//ir buscar os elementos de uma jsonarray para uma arraylist
		public ArrayList<JSONObject> getArray(JSONArray ja){
			ArrayList<JSONObject> childs = new ArrayList<JSONObject>();
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iter = ja.iterator();
			while(iter.hasNext()){
				childs.add(iter.next());
			}
			return childs;
		}
		
		
		
		
		//teste
		public Boolean Type(JSONObject j, String t){
			return getkind(j).equals(t) ? true : false;
		}
}
