/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.logic;

import com.reaktorlabs.entity.Champion;
import com.reaktorlabs.repository.ChampionRepositoryImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.json.JSONObject;
import com.reaktorlabs.repository.ChampionRepository;

/**
 *
 * @author Bálint
 */

@ManagedBean
@RequestScoped
public class ChampionToDB implements Serializable{
    
    ChampionRepository repo = new ChampionRepositoryImpl();
    

    public void saveChampionsToDb() throws IOException, SQLException{
      UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&dataById=false");
    HttpURLConnection con = connection.makeConnection();
    
    BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        JSONObject myResponse = new JSONObject(response.toString());
        JSONObject champObject = myResponse.getJSONObject("data");
        
        String chname = champObject.getJSONObject("Fiddlesticks").getString("name");
        int chid = champObject.getJSONObject("Fiddlesticks").getInt("id");
        
        System.out.println(chname+": "+chid);
        
        Champion fiddle = new Champion(chid,chname);
        repo.createChampion(fiddle);
        
        
        
        /*
 
        JSONArray keys = champObject.names();
        System.out.println(keys.toString());
 
        //System.out.println(keys.getJSONObject(0).getJSONObject("title"));
        
        System.out.println(keys.toString());
        
        String chname = champObject.getJSONObject("Fiddlesticks").getString("title");
        System.out.println(chname);
        
         */  
        
        
        printJsonObject(champObject);
        
        
    }
    
    public static void printJsonObject(JSONObject jsonObj) {
    for (Object key : jsonObj.keySet()) {
        //based on you key types
        String keyStr = (String)key;
        Object keyvalue = jsonObj.get(keyStr);
        //System.out.println(keyStr);
        

        //Print key and value
        //System.out.println("key: "+ keyStr + " value: " + keyvalue);

        //for nested objects iteration if required
        //if (keyvalue instanceof JSONObject)
          //  printJsonObject((JSONObject)keyvalue);
    }
}
   
}
