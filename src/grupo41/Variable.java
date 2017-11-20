package grupo41;

import java.util.ArrayList;

public class Variable {
	private Boolean _tainted;
	private String _name;
	//private String type , se for necessario.
	private String _entry;
	private String _san;
	public String _assignedFrom;
	
	public Variable(String name){
		_name = name;
		_tainted = false;
		_entry = "";
		_san = "";
		_assignedFrom = "";
	}
	
	//getters
	public Boolean getState(){
		return _tainted;}

	public String getNAme(){
		return _name;
	}
	
	public String getSan(){
		return _san;
	}
	
	public String getEntry(){
		return _entry;
	}
	
	public String getAssigned(){
		return _assignedFrom;
	}
	
	//setters
	public void setState(Boolean state){
		_tainted = state;
	}
	
	public void setSan(String san){
		_san = san;
	}
	

	public void addEntry(String f){
		_entry = f;
	}	
	
	public void setAssigned(String v){
		_assignedFrom = v;
	}
	
	
}
