package grupo41;

import java.util.ArrayList;

public class Variable {
	private Boolean _tainted;
	private String _name;
	//private String type , se for necessario.
	//private ArrayList<String> funs;
	//private ArrayList<String> vars;
	
	
	public Variable(String name){
		_name = name;
		_tainted = false;
	}
	
	//getters
	public Boolean getState(){return _tainted;}

	public String getNAme(){
		return _name;
	}
	
	
	//setters
	public void setState(){
		if(_tainted){
			_tainted = false;
		}
		else{
			_tainted = true;
		}
	}
	
	
	
}
