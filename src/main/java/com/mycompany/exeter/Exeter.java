/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author jaivi
 */
public class Exeter {
    // hashmap to find frequency
    static HashMap <String,Integer> hm = new HashMap <> ();
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //to find time and memory
        long start = System.nanoTime();
        int mb = 1024 * 1024; 
        Runtime instance = Runtime.getRuntime();
        //Map for translating from english to french
        Map<String,String> variableMap = fillMap();
        //path file to take input text file
	Path path = Paths.get("C:\\Users\\jaivi\\Documents\\NetBeansProjects\\rakesh_projects\\Exeter\\t8.shakespeare.txt");
        //paths for output text and csv files 
        Path finalpath1 = Paths.get("C:\\Users\\jaivi\\Documents\\NetBeansProjects\\rakesh_projects\\Exeter\\t8.shakespeare.translated.txt");
        Path finalpath2 = Paths.get("C:\\Users\\jaivi\\Documents\\NetBeansProjects\\rakesh_projects\\Exeter\\frequency.csv");
        Path finalpath3 = Paths.get("C:\\Users\\jaivi\\Documents\\NetBeansProjects\\rakesh_projects\\Exeter\\performance.txt");
	Stream<String> lines;
	try {
            //lines for storing input lines
            lines = Files.lines(path,Charset.forName("UTF-8"));
            //storing replaced lines in list 
            List<String> replacedLines = (List<String>) lines.map(line -> replaceTag(line,variableMap)).collect(Collectors.toList());
            //writing replaced lines into the output file(t8.shakespeare.translated.txt
            Files.write(finalpath1, replacedLines, Charset.forName("UTF-8"));
            lines.close();
            
            //writing translated words and their frequency into the outputfile(frequency.csv)
            List<String> freq = new ArrayList<> ();
            for(String s1 : hm.keySet()){
                freq.add(s1+","+ variableMap.get(s1)+","+hm.get(s1));
            }
            Files.write(finalpath2, freq, Charset.forName("UTF-8"));
	} 
        catch (IOException e) {
            e.printStackTrace();
	}
        //time taken
        long duration = (System.nanoTime() - start)/1000000000;
        // used memory
        int usedMemory = (int) ((instance.totalMemory() - instance.freeMemory()) / mb);
        //writing time taken and memory used in performance text file
        List <String> performance = new ArrayList <> ();
        performance.add("Time to process: 0 minutes "+duration+" seconds");
        performance.add("Memory used: "+usedMemory+" MB");
        Files.write(finalpath3, performance, Charset.forName("UTF-8"));
    }   
        
    public static Map<String,String> fillMap() throws FileNotFoundException, IOException{
        //Mapping the english and french words
	Map<String,String> map = new HashMap<String,String>();
        //taking french_dictionary.csv file for translating
        String path = "C:\\Users\\jaivi\\Documents\\NetBeansProjects\\rakesh_projects\\Exeter\\french_dictionary.csv";
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        //storing english words as key and french words as value in map.
        while((line = br.readLine()) != null){
            String [] values = line.split(",");
            map.put(values[0], values[1]);
        }
        return map;
    }
    
    private static String replaceTag(String str, Map<String,String> map) {
        //iterating through every key and replacing the english words with french words
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //replacing lower case words
            if (str.contains(entry.getKey())){
                str = str.replace(entry.getKey(), entry.getValue());
                if(hm.containsKey(entry.getKey())){
                    hm.put(entry.getKey(),hm.get(entry.getKey())+1);
                }
                else{
                    hm.put(entry.getKey(),1);
                }
                
            }
            //replacing upper case words
            if (str.contains(entry.getKey().toUpperCase())) {
                str = str.replace(entry.getKey().toUpperCase(), entry.getValue());
                if(hm.containsKey(entry.getKey())){
                    hm.put(entry.getKey(),hm.get(entry.getKey())+1);
                }
                else{
                    hm.put(entry.getKey(),1);
                }
                
            }
            //replacing first letter upper case words
            if (str.contains(upper(entry.getKey()))) {
                str = str.replace(upper(entry.getKey()), entry.getValue());
                if(hm.containsKey(entry.getKey())){
                    hm.put(entry.getKey(),hm.get(entry.getKey())+1);
                }
                else{
                    hm.put(entry.getKey(),1);
                }
            }
	}
	return str;
    }
    
    
    public static String upper(String s){
        //to change first letter of a word to upper case 
        String res = s.substring(0, 1).toUpperCase() + s.substring(1); // J + avatpoint  
        return res;
    }
    
    
    
}
