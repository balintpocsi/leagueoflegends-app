/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.controllbeans;

import com.reaktorlabs.entity.Perk;
import com.reaktorlabs.logic.UrlConnection;
import com.reaktorlabs.repository.PerkRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Bálint
 */

@ManagedBean
@RequestScoped
public class PerksSave implements Serializable{
    
    @Inject
    private PerkRepository repository;
    private List<Perk> listPerks = new ArrayList<>();

    public PerksSave() {
    }

  
     public void savePerks() throws IOException{
     UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/static-data/v3/reforged-runes");
    HttpURLConnection con = connection.makeConnection();
    
    BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        
        JSONArray myResponse = new JSONArray(response.toString());
        
         
        //System.out.println(jsonObject.getString("runePathName"));
        
        for(int i = 0;i<myResponse.length();i++){
            
            JSONObject jsonObject = myResponse.getJSONObject(i);
            
            String runePathName = jsonObject.getString("runePathName");
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            //String shortDesc = jsonObject.getString("shortDesc");
            String longDesc2 = jsonObject.getString("longDesc");
            String longDesc = longDesc2.replace("<br>","");
         
            Perk newPerk = new Perk(runePathName, name, id, longDesc);
            
            System.out.println(newPerk.getName()+" :: ID = : "+newPerk.getId());
            System.out.println(newPerk.getLongDesc());
           
            repository.createPerk(newPerk);
            
            listPerks.add(newPerk);
            
            
        
        
        }
        
        
        
        
         
        
         
         
        
        
        
        
        
        
        
        
        
     }
     
      public List<Perk> getListPerks() {
        return listPerks;
    }

    public void setListPerks(List<Perk> listPerks) {
        this.listPerks = listPerks;
    }
    
}
