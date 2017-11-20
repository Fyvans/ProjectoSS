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


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//output vulneravel ou nao

public class Analyser {
	
	private static JsonParser jp = new JsonParser(); 
	private static ArrayList<Vulnerability> vulns =  new ArrayList<Vulnerability>();
	private static ArrayList<Variable> vars = new ArrayList<Variable>();
	
	
	private static Boolean vulneravel = false;
	
	public static void createVar(Variable var){
		if(!vars.contains(var))
		{
			vars.add(var);
		}
	}
	
	public static Boolean varExist(String name){
		for(Variable v: vars){
			if(v.getNAme().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static Variable getVarByName(String name){
		for(Variable v: vars){
			if(v.getNAme().equals(name)){
				return v;
			}
		}
		return null;
	}
	
	public static void analyseAssign(JSONObject jo){
		//get left and right
		JSONObject left = jp.getleft(jo);
		JSONObject right = jp.getright(jo);
		//create left side var to analyse
		Variable var = new Variable(jp.getName(left));
		//analyse right side
		switch(jp.getKind(right)){
			case "offsetlookup":
				String callName = jp.getCallName(right);
				for(Vulnerability v: vulns){ 
					if(v.isEntry(callName)){
						var.setState(true);
						var.setAssigned(callName);
						break;
					}
				}
				createVar(var);
				break;
			case "encapsed":
				for(String s: jp.getArguments(right, "value")){
					if(varExist(s)){
						var.setState(getVarByName(s).getState() || var.getState()); //se for tainted contamina a outra variavel
						var.setAssigned("s");
					}
				}
				createVar(var);
				break;
			case "bin":
				if(getVarByName(jp.getBinVarLeft(right))!=null){
					var.setState(getVarByName(jp.getBinVarLeft(right)).getState() || var.getState());
				}
				if(getVarByName(jp.getBinVarRight(right))!=null){
					var.setState(getVarByName(jp.getBinVarRight(right)).getState() || var.getState());
				}	
				createVar(var);
				break;
			case "call":
				for(Vulnerability v: vulns){ 
					String san = jp.getCallName(right);
					if(v.isValidationFunc(san)){
						var.setState(false);
						var.setSan(san);
						break;
					}
					if(v.isSensitiveSynk(jp.getCallName(right))){
						for(String s: jp.getArguments(right, "arguments")){
							if(varExist(s)){
								if(getVarByName(s).getState()){
									System.out.println("This Slice is vulnerable");
									return;
								}
							}
						}
					}
				}
				break;
				
		default:
			if(jp.getKind(right).equals("Variable")){
				var.setAssigned(jp.getName(right));
				if(varExist(jp.getName(right))){
					var.setState(getVarByName(jp.getName(right)).getState());;
				}
			}
			createVar(var);
			break;
		}
	}
	
	public static Boolean analyseOffsetLookup(JSONObject jo){
		String callName = jp.getCallName(jo);
		for(Vulnerability v: vulns){ 
			if(v.isEntry(callName)){
				return vulneravel || true;
			}
		}
		return false;
	}
	
	public static Boolean analyseCall(JSONObject jo){
		for(Vulnerability v: vulns){
			if(v.isValidationFunc(jp.getCallName(jo))){
				return vulneravel && false;
			}
			if(v.isSensitiveSynk(jp.getCallName(jo))){
				for(String s: jp.getArguments(jo, "arguments")){
					if(varExist(s)){
							if(getVarByName(s).getState()){
								System.out.println("This Slice is vulnerable");
								return vulneravel || true;
							}
						}
					}
				}
		}
		return vulneravel;
	}
	
	//IF
	public static void analyseIf(JSONObject jo){
		for(JSONObject jif: jp.getArray(jp.getBody(jo), "children")){
			if(jp.getKind(jif).equals("assign")){
				analyseAssign(jif);
			};
		}
		for(JSONObject jal: jp.getArray(jp.getAlternate(jo), "children")){
			if(jp.getKind(jal).equals("assign")){
				analyseAssign(jal);
			};
		}
	}
	
	public static void main(String[] args) {
		
		if(args.length == 0){
			System.out.println("please refer a slice to analyse");
			return;
		}
		//path para o ficheiro patterns.txt
		if(args.length > 1){
			vulns = pattern.read_pat(args[1]);
		}else{
			vulns = pattern.read_pat("grupo41/patterns.txt");
		}
	
		String slicePath = args[0];
		
		try{
			//Read json file
			FileReader reader = new FileReader(slicePath);
			
			//create json object
			JSONParser jsparser = new JSONParser();
			JSONObject jsonbject = (JSONObject) jsparser.parse(reader);
			
			//get children structure
			for(JSONObject jo: jp.getArray(jsonbject, "children")){
				
			//ASSIGN
				if(jp.getKind(jo).equals("assign")){
					analyseAssign(jo);
					}
			//ECHO
				if(jp.getKind(jo).equals("echo")){
					String san = "";
					for(JSONObject j: jp.getArray(jo, "arguments")){
						if(jp.getKind(j).equals("offsetlookup")){
							vulneravel = analyseOffsetLookup(j);
							}
						if(jp.getKind(j).equals("call")){
							vulneravel = analyseCall(j);
							if(!vulneravel){
								san = jp.getCallName(j);
							}
						}
					}
					if(vulneravel){
						System.out.println("this Slice is vulnerable");
					}
					else{
						System.out.println("this Slice is vulnerable");
						System.out.println("sanitization function: " + san);
					}
				}
			//CALL
					if(jp.getKind(jo).equals("call")){
							analyseCall(jo);
							}
				//IF
					if(jp.getKind(jo).equals("if")){
						for(JSONObject jif: jp.getArray(jp.getBody(jo), "children")){
							if(jp.getKind(jif).equals("assign")){
								analyseAssign(jif);
							};
						}
						for(JSONObject jal: jp.getArray(jp.getAlternate(jo), "children")){
							if(jp.getKind(jal).equals("assign")){
								analyseAssign(jal);
							};
						}
					}
				//WHILE
					if(jp.getKind(jo).equals("while")){
						for(JSONObject jwhi: jp.getArray(jp.getBody(jo), "children")){
							if(jp.getKind(jwhi).equals("assign")){
								analyseAssign(jwhi);
							}
						}
						for(Variable v: vars){
							System.out.println(v.getNAme() + "-  " + v.getState() + " ass " + v.getAssigned());
						}
					}
				}
				
		} catch (IOException | ParseException ex){
			ex.printStackTrace();
		}
	
	
	
	}

}
	
	
	


