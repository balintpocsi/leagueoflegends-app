/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.controllbeans;

import com.reaktorlabs.entity.Perk;
import com.reaktorlabs.entity.Summoner;
import com.reaktorlabs.logic.UrlConnection;
import com.reaktorlabs.service.ChampionService;
import com.reaktorlabs.service.PerkService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Bálint
 */

@ManagedBean
@RequestScoped
public class MatchHistory implements Serializable{
    
    private String input;
    private String output;
    private List<Long> matchIds = new ArrayList<>();
    private HashMap<Long, List<Long>> accountToMatchMap = new HashMap<>();
    private HashMap<String, List<Boolean>> nameBooleanListMap = new HashMap<>();
    private HashMap<Long, Double> summonerIdWinRateMap = new HashMap<>();
    private List<Summoner> list = new ArrayList<>();
    private List<Perk> listPerks = new ArrayList<>();
    private double winRateTeam1;
    private double winRateTeam2;
    private boolean isMethodComplete;
    int slider = 5;

    @Inject
    ChampionService championService;
    
    @Inject
    PerkService perkService;

    public MatchHistory() {
    }
    
    
public void submit() throws Exception{
    
    FacesContext context = FacesContext.getCurrentInstance();
    double team1 = 0d;
    double team2 = 0d;
    int summonerListLength = 0;
    int halfOfTeam = 0;
    int teamSplitIndex = 0;
    
    try {
        accountToMatchMap = matchByAccountId();
        summonerIdWinRateMap = matchDetails(accountToMatchMap);
        list = printMatch(summonerIdWinRateMap);
        summonerListLength = list.size();
        halfOfTeam = summonerListLength/2;
        teamSplitIndex = (summonerListLength-2)/2;
        isMethodComplete = true;
        input=input.replace("%20", " ");
        
        } catch (FileNotFoundException e) {
            isMethodComplete=false;
            e.printStackTrace();
            input=input.replace("%20", " ");
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The summoner "+input+" is not currently in a game.", "Details"));
        } catch (IOException e) {
            isMethodComplete=false;
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A problem has occured!", "Details"));
             e.printStackTrace();
        }

    for (int i = 0; i<summonerListLength;i++){
        if(i<=teamSplitIndex){
         team1 = list.get(i).getWinRate()+team1;
        } else {
         team2 = list.get(i).getWinRate()+team2;
        }
    }

     setWinRateTeam1(team1/halfOfTeam);
     setWinRateTeam2(team2/halfOfTeam);
     
  }


public HashMap<Long, List<Long>> matchByAccountId() throws IOException, InterruptedException{
        
        List<Long> accountIdList = getAccountIdsOfParticipants();
        
        HashMap<Long, List<Long>> accountIdToMatchIdMap = new HashMap<>();
        
        for(int j = 0; j < accountIdList.size();j++){
            
            List<Long> returnValueOfMatchIdList = new ArrayList<>();
            Long accountId = accountIdList.get(j);
            String url = "https://eun1.api.riotgames.com/lol/match/v3/matchlists/by-account/"+accountId;
	
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Origin", "https://developer.riotgames.com");
        con.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("X-Riot-Token", "RGAPI-e9948e13-6e61-40b3-848d-ae7e310386d2");
        con.setRequestProperty("Accept-Language", "hu-HU,hu;q=0.9,en-US;q=0.8,en;q=0.7");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        
        StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }   }
        
        JSONObject myResponse = new JSONObject(response.toString());
        JSONArray matchesArray = myResponse.getJSONArray("matches");
        accountIdToMatchIdMap.put(accountId, returnValueOfMatchIdList); 
        
            for (int i = 0; i<slider;i++){                                  
                JSONObject matchObj = matchesArray.getJSONObject(i);
                Long gameId = matchObj.getLong("gameId");
                returnValueOfMatchIdList.add(gameId);
                accountIdToMatchIdMap.put(accountId, returnValueOfMatchIdList);
            }
        }
        return accountIdToMatchIdMap;
    }
    
    public List<Long> getAccountIdsOfParticipants() throws IOException, InterruptedException{
            
    List<String> namesOfSummoners = getParticipantsName();
    List<String> namesOfSummoners2 = new ArrayList<>();
    List<Long> accountIdsOfSummoners = new ArrayList<>();
            
        for (int i = 0;i<namesOfSummoners.size();i++){
                
            String spaceRemovedName = namesOfSummoners.get(i).replace(" ", "%20");
            namesOfSummoners2.add(spaceRemovedName);
            
        }
        
        for (int i = 0;i<namesOfSummoners.size();i++){
            
            String summonerName = namesOfSummoners2.get(i);       
    
        UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonerName);
        HttpURLConnection con = connection.makeConnection();
    
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        JSONObject myResponse = new JSONObject(response.toString());
        Long accountId = myResponse.getLong("accountId");
        accountIdsOfSummoners.add(accountId);
        
        }
            
        return accountIdsOfSummoners;
    }
    
    public void submit2(){
        System.out.println("SLIDER VALUE: "+slider);;
    }
    
    public List<String> getParticipantsName() throws IOException, InterruptedException{
           
        List<String> nameListOfSummoners = new ArrayList<>();
        Long summonerId = summonerIdBySummonerName(input); 
        UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/spectator/v3/active-games/by-summoner/"+summonerId);
        HttpURLConnection con = connection.makeConnection();
        
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
    
        JSONObject myResponse = new JSONObject(response.toString());
        JSONArray jsonArrayObj = myResponse.getJSONArray("participants");
        
            for (int i = 0;i<jsonArrayObj.length();i++){

                String summonerArrayObject = jsonArrayObj.getJSONObject(i).getString("summonerName");
                nameListOfSummoners.add(summonerArrayObject);
            }
        
        return nameListOfSummoners;
        
        }
        
    public Long summonerIdBySummonerName(String summonerName) throws IOException, InterruptedException{
           
        UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonerName);
        HttpURLConnection con = connection.makeConnection();


        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JSONObject myResponse = new JSONObject(response.toString());

        return myResponse.getLong("id");
    
    }
    
        public HashMap<Long, Double> matchDetails(HashMap<Long, List<Long>> map) throws IOException, InterruptedException{
            
            Thread.sleep(1000);
            HashMap<Long, Double> nameRateMap = new HashMap<>();
            Long sumId = 0L;
            Set<Long> accountSet = map.keySet();
            int counter = 0;
            
            for ( Long accountIdSet : accountSet ) {
                
                Thread.sleep(1000);
                List<Boolean> booleanList = new ArrayList<>();
                double winCounter = 0;
                List<Long> matchIdsList = map.get(accountIdSet);
                double matchNumbersInStatistics = matchIdsList.size();
                
                for (int j = 0;j<matchIdsList.size();j++){
                    
                    Thread.sleep(1000);
                    counter=counter+1;
                    Long matchId = matchIdsList.get(j);
                
                UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/match/v3/matches/"+matchId);
                HttpURLConnection con = connection.makeConnection();

                StringBuilder response;
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))) {
                        String inputLine;
                        response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }   }
                JSONObject myResponse = new JSONObject(response.toString());
                JSONArray participantIdentitiesArray = myResponse.getJSONArray("participantIdentities");
                    
                    for (int k = 0; k<participantIdentitiesArray.length();k++){
                        
                        Long playerObjectCurrentAccountId = participantIdentitiesArray.getJSONObject(k).getJSONObject("player").getLong("currentAccountId");
                                if (Objects.equals(accountIdSet, playerObjectCurrentAccountId)){
                                    int actualPlayerObjectParticipantId = participantIdentitiesArray.getJSONObject(k).getInt("participantId");
                                    JSONArray detailedStatsOfParticipantsArrayObject = myResponse.getJSONArray("participants");
                                    Long sId = myResponse.getJSONArray("participantIdentities").getJSONObject(actualPlayerObjectParticipantId-1).getJSONObject("player").getLong("summonerId");
                                    Boolean playerWinResult = detailedStatsOfParticipantsArrayObject.getJSONObject(actualPlayerObjectParticipantId-1).getJSONObject("stats").getBoolean("win");
                                    sumId = sId;
                                      booleanList.add(playerWinResult);
                                        if (playerWinResult){
                                        winCounter = winCounter+1;
                                        }
                             }
                    }
                } 
                    double winRate = (winCounter/matchNumbersInStatistics)*100;
                    nameRateMap.put(sumId, winRate);
                    
            }
           
            return nameRateMap;
    
        }
        
        public Long summonerId(String summonerName) throws IOException{
    
        UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonerName);
        HttpURLConnection con = connection.makeConnection();
    
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        JSONObject myResponse = new JSONObject(response.toString());
          return myResponse.getLong("id");
            
            
    }
    
    public List<Summoner> printMatch(Map<Long, Double> map) {
        
        List<Summoner> summoners = new ArrayList<>();
        
        try {
            
            Long summonerId = summonerId(this.input);
            UrlConnection connection = new UrlConnection("https://eun1.api.riotgames.com/lol/spectator/v3/active-games/by-summoner/"+summonerId);
            HttpURLConnection con = connection.makeConnection();
            
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            
            JSONObject matchParticipantsObject = new JSONObject(response.toString());
            JSONArray jsonArrayObj = matchParticipantsObject.getJSONArray("participants");
            
            for (int i = 0;i<jsonArrayObj.length();i++){
                
                JSONObject summonerArrayObject = jsonArrayObj.getJSONObject(i).getJSONObject("perks");
                JSONArray perksArray = summonerArrayObject.getJSONArray("perkIds");
                List<Perk> sumPerks = new ArrayList<>();
                Perk[] runes = new Perk[6];
                
                for (int j = 0; j<perksArray.length();j++){
                    
                    int perkNumber = (Integer)perksArray.get(j);
                    runes[j] = perkService.getPerkById(perkNumber);
                    Perk perk = perkService.getPerkById(perkNumber);
                    sumPerks.add((perk));
                 
                }
                
                this.listPerks = sumPerks;
                Long profileIconId = jsonArrayObj.getJSONObject(i).getLong("profileIconId");
                int championId = jsonArrayObj.getJSONObject(i).getInt("championId");
                String summonerName = jsonArrayObj.getJSONObject(i).getString("summonerName");
                Long spell2Id = jsonArrayObj.getJSONObject(i).getLong("spell2Id");
                Long spell1Id = jsonArrayObj.getJSONObject(i).getLong("spell1Id");
                Long teamId = jsonArrayObj.getJSONObject(i).getLong("teamId");
                Long summonerIdAttributeInArray = jsonArrayObj.getJSONObject(i).getLong("summonerId");
                String championNameByItsId = championService.getChampionNameById(championId);
                
                //----> Summoner rank data
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MatchHistory.class.getName()).log(Level.SEVERE, null, ex);
                }
                UrlConnection connection2 = new UrlConnection("https://eun1.api.riotgames.com/lol/league/v3/positions/by-summoner/"+summonerIdAttributeInArray);
                HttpURLConnection con2 = connection2.makeConnection();
        
                StringBuilder response2;
                try (BufferedReader in2 = new BufferedReader(
                        new InputStreamReader(con2.getInputStream()))) {
                    String inputLine2;
                    response2 = new StringBuilder();
                    while ((inputLine2 = in2.readLine()) != null) {
                        response2.append(inputLine2);
                    }
                }
                
                JSONArray myResponse2 = new JSONArray(response2.toString());
                boolean dataForRankIsNullBoolean = myResponse2.isNull(0);
                String rank="Unranked";
                String tier="Unranked";
                int wins=0;
                int losses=0;
                
                if (!dataForRankIsNullBoolean){
                
                    for (int z = 0; z<myResponse2.length();z++ ){
                        
                        if (myResponse2.getJSONObject(z).getString("queueType").equals("RANKED_SOLO_5x5")){

                            JSONObject playerSoloRank = myResponse2.getJSONObject(z);
                            rank = playerSoloRank.getString("rank");
                            tier = playerSoloRank.getString("tier");
                            wins = playerSoloRank.getInt("wins");
                            losses = playerSoloRank.getInt("losses");
                        }
                    }
                } 
                
                try {
                    //---> CHAMPION SCORE DATA
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MatchHistory.class.getName()).log(Level.SEVERE, null, ex);
                }
                UrlConnection connection3 = new UrlConnection("https://eun1.api.riotgames.com/lol/champion-mastery/v3/champion-masteries/by-summoner/"+summonerIdAttributeInArray);
                HttpURLConnection con3 = connection3.makeConnection();
                StringBuilder response3;
                try (BufferedReader in3 = new BufferedReader(
                        new InputStreamReader(con3.getInputStream()))) {
                    String inputLine3;
                    response3 = new StringBuilder();
                    while ((inputLine3 = in3.readLine()) != null) {
                        response3.append(inputLine3);
                    }
                }
                
                JSONArray myResponse3 = new JSONArray(response3.toString());
                int championLevel=0;
                int championPoints=0;
                for (int p = 0;p<myResponse3.length();p++){
                    if(championId==myResponse3.getJSONObject(p).getInt("championId")){
                        championLevel = myResponse3.getJSONObject(p).getInt("championLevel");
                        championPoints = myResponse3.getJSONObject(p).getInt("championPoints");
                    }
                }
                
                Double winRate = map.get(summonerIdAttributeInArray);
                summoners.add(new Summoner(profileIconId, championId, summonerName, sumPerks, spell2Id, spell1Id, teamId, summonerId, championNameByItsId, runes, winRate, rank, tier, wins, losses, championLevel, championPoints));
                
            }
            
            return summoners;
        }   catch (IOException ex) {
            Logger.getLogger(MatchHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return summoners;
    }
    
    public HashMap<Long, List<Long>> getAccountToMatchMap() {
        return accountToMatchMap;
    }

    public void setAccountToMatchMap(HashMap<Long, List<Long>> accountToMatchMap) {
        this.accountToMatchMap = accountToMatchMap;
    }
   
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input.replace(" ", "%20");
    }

    public List<Long> getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(List<Long> matchIds) {
        this.matchIds = matchIds;
    }

    public HashMap<String, List<Boolean>> getNameBooleanListMap() {
        return nameBooleanListMap;
    }

    public void setNameBooleanListMap(HashMap<String, List<Boolean>> nameBooleanListMap) {
        this.nameBooleanListMap = nameBooleanListMap;
    }

    public HashMap<Long, Double> getSummonerIdWinRateMap() {
        return summonerIdWinRateMap;
    }

    public void setSummonerIdWinRateMap(HashMap<Long, Double> summonerIdWinRateMap) {
        this.summonerIdWinRateMap = summonerIdWinRateMap;
    }

    public List<Summoner> getList() {
        return list;
    }

    public void setList(List<Summoner> list) {
        this.list = list;
    }

    public List<Perk> getListPerks() {
        return listPerks;
    }

    public void setListPerks(List<Perk> listPerks) {
        this.listPerks = listPerks;
    }

    public double getWinRateTeam1() {
        return winRateTeam1;
    }

    public void setWinRateTeam1(double winRateTeam1) {
        this.winRateTeam1 = winRateTeam1;
    }

    public double getWinRateTeam2() {
        return winRateTeam2;
    }

    public void setWinRateTeam2(double winRateTeam2) {
        this.winRateTeam2 = winRateTeam2;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isIsMethodComplete() {
        return isMethodComplete;
    }

    public void setIsMethodComplete(boolean isMethodComplete) {
        this.isMethodComplete = isMethodComplete;
    }

    public int getSlider() {
        return slider;
    }

    public void setSlider(int slider) {
        this.slider = slider;
    }

}
