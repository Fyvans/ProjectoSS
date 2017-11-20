package grupo41;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author José
 */
//import harding.compsci.graphics.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;

public class pattern {
    public static ArrayList<Vulnerability> read_pat(String location){
    	ArrayList<Vulnerability> vulns = new ArrayList<Vulnerability>();
        //inicializacao material para popular vulnerabilidade
    	String nome = "";
        ArrayList<String> entry = new ArrayList<String>();
        ArrayList<String> val = new ArrayList<String>();
        ArrayList<String> sense = new ArrayList<String>();
        
        try {
            // Conteudo
            String content = "src/grupo41/patterns.txt";

            // Cria arquivo
            //File file = new File(content);

            FileReader ler = new FileReader(location);
            BufferedReader reader = new BufferedReader(ler);  
            String linha;
            int nrlinha = 0;
            while( (linha = reader.readLine()) != null ){
                //String phrase = "the music made   it   hard      to        concentrate";
                nrlinha++;
                String delims = "[,]";
                String[] tokens = linha.split(delims);
                if (tokens[0] != ""){
                    switch (nrlinha % 5){
                            case 1: nome=tokens[0];
                            break;
                            case 2: for (String s: tokens){
                                entry.add(s);
                            }
                            break;
                            case 3: for (String s: tokens){
                                val.add(s);
                            }break;
                            case 4: for (String s: tokens){
                                sense.add(s);
                            }break;
                            case 0:
                            	Vulnerability v = new Vulnerability(nome, entry, val, sense);
                            	vulns.add(v);
                                nome = "";
                                entry = new ArrayList<String>();
                                val = new ArrayList<String>();
                                sense = new ArrayList<String>();
                                break;
                    }
                }
          
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return vulns;
    }
}
    
    
         