package grupo41;

import java.util.ArrayList;

public class Vulnerabilities {
	private String _name;
	private ArrayList<String> _entryPoints;
	private ArrayList<String> _validationFuncs;
	private ArrayList<String> _sensitiveSynks;
	
	
	public Vulnerabilities(String name, ArrayList<String> entries, ArrayList<String> valids, ArrayList<String> sens){
		_name = name;
		_entryPoints = entries;
		_validationFuncs = valids;
		_sensitiveSynks = sens;
	}
	
	//gets
	public String getName(){
		return _name;	
	}
	
	public ArrayList<String> getEntryPoints(){
		return _entryPoints;
		
	}
	
	public ArrayList<String> getValidFuncs(){
		return _validationFuncs;
		
	}
	
	public ArrayList<String> getSensitiveSynks(){
		return _sensitiveSynks;
	}
	
	public Boolean isEntry(String entry){
		return _entryPoints.contains(entry);
	}
	
	
}
