/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Bálint
 */
public class UrlConnection implements Serializable{
    private String url;
    private String apiKey = "RGAPI-930dcf97-3dc6-4e53-84e0-07760b22a4f4";
    

    public UrlConnection(String url){
        this.url = url;
   
        
    }
    
    public HttpURLConnection makeConnection() throws MalformedURLException, IOException{
    
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("GET");
        
        con.setRequestProperty("Origin", "https://developer.riotgames.com");
        con.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("X-Riot-Token", apiKey);
        con.setRequestProperty("Accept-Language", "hu-HU,hu;q=0.9,en-US;q=0.8,en;q=0.7");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        
        return con;
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    
    
    
    
    public String UrlConnection() throws Exception{
        
        Long summonerId = 4574457L;
        
         String url = "https://eun1.api.riotgames.com/lol/spectator/v3/active-games/by-summoner/"+summonerId;
        
	
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("GET");
        
        con.setRequestProperty("Origin", "https://developer.riotgames.com");
        con.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("X-Riot-Token", "RGAPI-1bb26a60-088d-41b8-8a27-21a31ea77776");
        con.setRequestProperty("Accept-Language", "hu-HU,hu;q=0.9,en-US;q=0.8,en;q=0.7");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        List myList = new ArrayList();
        JSONObject myResponse = new JSONObject(response.toString());
        JSONArray jsonArrayObj = myResponse.getJSONArray("participants");
        
            System.out.println(jsonArrayObj);
            System.out.println(jsonArrayObj.toString());
            System.out.println(jsonArrayObj.length()+">>>>>>>>>>>>>>>>>>>>>>>>");
            
            String responseArray = jsonArrayObj.toString();
            
            JSONObject jsonObj2 = jsonArrayObj.getJSONObject(5);
            System.out.println(jsonObj2+"##############################");
            
            String jsonObj3 = jsonArrayObj.getJSONObject(5).getString("summonerName");
            
            System.out.println(jsonObj3+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            
            for (int i = 0;i<jsonArrayObj.length();i++){
            myList.add(jsonArrayObj.getJSONObject(i).getString("summonerName"));
                System.out.println("@@@@@@@Summoner name: "+jsonArrayObj.getJSONObject(i).getString("summonerName"));
            }
            
            return myList.toString();
        
      
        
        /*List<String> myList = new ArrayList<>();
        
        JSONArray myArray = new JSONArray(response.toString());
            System.out.println(myArray);
        return myArray.toList();*/
        
      
        /*JSONObject myResponse = new JSONObject(response.toString());
            
            System.out.println(myResponse.getString("participants"));
          return myResponse.getString("participants");*/
          
        
        
        
         
	    }
    
}
