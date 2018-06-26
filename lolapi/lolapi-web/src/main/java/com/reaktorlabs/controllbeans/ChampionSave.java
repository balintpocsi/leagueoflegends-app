/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.controllbeans;

import com.reaktorlabs.entity.Champion;
import com.reaktorlabs.logic.UrlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;
import com.reaktorlabs.repository.ChampionRepository;

/**
 *
 * @author Bálint
 */

@ManagedBean
@RequestScoped
public class ChampionSave implements Serializable{
   
    @Inject
    private ChampionRepository repository;
    
    public void saveChamp() throws IOException{
    UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&dataById=false");
    HttpURLConnection con = connection.makeConnection();
    
    BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        JSONObject myResponse = new JSONObject(response.toString());
        JSONObject champObject = myResponse.getJSONObject("data");
        
        JSONArray array = champObject.names();
        System.out.println(array);
        System.out.println(array.length());
        System.out.println(array.get(2));
        System.out.println(array.iterator().next());
        System.out.println(">>>>>>>>>>>>>>");
        String[] champNameArray = printJsonObject(champObject);
        //System.out.println(champNameArray.length);
       
        for (int i = 0;i<champNameArray.length-1;i++){
            
            int id = champObject.getJSONObject(champNameArray[i]).getInt("id");
           System.out.println(champNameArray[i]+": "+id);
           Champion newChampion = new Champion(id,champNameArray[i]);
           
           repository.createChampion(newChampion);
           
        }
        
        

      /*  
        String chname = champObject.getJSONObject("Fiddlesticks").getString("name");
        int chid = champObject.getJSONObject("Fiddlesticks").getInt("id");
        
        System.out.println(chname+": "+chid);
        
        Champion fiddle = new Champion(chid,chname);
        
        System.out.println(fiddle.getName()+": "+fiddle.getId());
        
        System.out.println("XXXXXXXXXXX");
        
        System.out.println(champObject.getJSONObject("Jax").get("title"));
        //Grandmaster at Arms
        System.out.println(champObject.getJSONObject("Jax").toString());
        //{"name":"Jax","id":24,"title":"Grandmaster at Arms","key":"Jax"}
        System.out.println(champObject.getJSONObject("Jax"));
        //{"name":"Jax","id":24,"title":"Grandmaster at Arms","key":"Jax"}
        //ugyanazt printeli
        
        //String keyStr = printJsonObject(champObject);
        //champObject.getJSONObject(keyStr).get("id");
      
        repository.createChampion(fiddle);
        
        
        */
        
        
    
    }
    
    public String[] printJsonObject(JSONObject jsonObj) {
        String[] champNameArray = new String[141];
        int i = 0;
        for (Object key : jsonObj.keySet()) {
            
        //based on you key types
        
            //System.out.println("&&&&&&&&&&&&&: "+jsonObj.keySet());
            //ezt printeli ki:
            //[Jax, Sona, Tristana, Varus, Kaisa, Fiora, Singed, TahmKench, Leblanc, Thresh, Karma, Jhin, Rumble, Udyr, LeeSin, Yorick, Ornn, Kayn, Kassadin, Sivir, MissFortune, Draven, Yasuo, Kayle, Shaco, Renekton, Hecarim, Fizz, KogMaw, Maokai, Lissandra, Jinx, Urgot, Fiddlesticks, Galio, Pantheon, Talon, Gangplank, Ezreal, Gnar, Teemo, Annie, Mordekaiser, Azir, Kennen, Riven, Chogath, Aatrox, Poppy, Taliyah, Illaoi, Pyke, Heimerdinger, Alistar, XinZhao, Lucian, Volibear, Sejuani, Nidalee, Garen, Leona, Zed, Blitzcrank, Rammus, Velkoz, Caitlyn, Trundle, Kindred, Quinn, Ekko, Nami, Swain, Taric, Syndra, Rakan, Skarner, Braum, Veigar, Xerath, Corki, Nautilus, Ahri, Jayce, Darius, Tryndamere, Janna, Elise, Vayne, Brand, Zoe, Graves, Soraka, Xayah, Karthus, Vladimir, Zilean, Katarina, Shyvana, Warwick, Ziggs, Kled, Khazix, Olaf, TwistedFate, Nunu, Rengar, Bard, Irelia, Ivern, MonkeyKing, Ashe, Kalista, Akali, Vi, Amumu, Lulu, Morgana, Nocturne, Diana, AurelionSol, Zyra, Viktor, Cassiopeia, Nasus, Twitch, DrMundo, Orianna, Evelynn, RekSai, Lux, Sion, Camille, MasterYi, Ryze, Malphite, Anivia, Shen, JarvanIV, Malzahar, Zac, Gragas]
        String keyStr = (String)key;
        Object keyvalue = jsonObj.get(keyStr);
            //System.out.println(keyStr);
            
            
            champNameArray[i] = keyStr;
            //System.out.println(champNameArray[i]);
            i++;
            
            
            
            
        //String championNameIterate = (String)jsonObj.get(keyStr);
        
        
        
        //champObject.getJSONObject("Jax").get("title")
        
        //System.out.println(keyStr);
        

        //Print key and value
        //System.out.println("key: "+ keyStr + ", value: " + keyvalue);
        
        //{"name":"Jax","id":24,"title":"Grandmaster at Arms","key":"Jax"}

        //for nested objects iteration if required
        //if (keyvalue instanceof JSONObject)
          //  printJsonObject((JSONObject)keyvalue);
        }
    
    
    return champNameArray;
    }
    
}
