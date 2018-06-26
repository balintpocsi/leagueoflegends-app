/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.entity;


import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Bálint
 */
public class Summoner implements Serializable{
    private Long profileIconId;
    private int championId;
    private String summonerName;
    private List<Perk> perks;
    private String perkToString;
    private Long spell2Id;
    private Long spell1Id;
    private Long teamId;
    private Long summonerId;
    private String championNameByItsId;
    private Perk[] runes;
    private double winRate;
    private String rank;
    private String tier;
    private int wins;
    private int losses;
    private int championLevel;
    private int championPoints;
    
    
    
    

    public Summoner() {
    }

    public Summoner(String summonerName) {
        this.summonerName = summonerName;
    }
    
    
    
    public Summoner(Long profileIconId, int championId, String summonerName, List<Perk> perks, Long spell2Id, Long spell1Id, Long teamId, Long summonerId, String championNameByItsId, Perk[] runes) {
        this.profileIconId = profileIconId;
        this.championId = championId;
        this.summonerName = summonerName;
        this.perks = perks;
        this.spell2Id = spell2Id;
        this.spell1Id = spell1Id;
        this.teamId = teamId;
        this.summonerId = summonerId;
        this.championNameByItsId = championNameByItsId;
        this.runes = runes;
        
    }

    public Summoner(Long profileIconId, int championId, String summonerName, List<Perk> perks, Long spell2Id, Long spell1Id, Long teamId, Long summonerId, String championNameByItsId, Perk[] runes, double winRate, String rank, String tier, int wins, int losses, int championLevel, int championPoints) {
        this.profileIconId = profileIconId;
        this.championId = championId;
        this.summonerName = summonerName;
        this.perks = perks;
        this.spell2Id = spell2Id;
        this.spell1Id = spell1Id;
        this.teamId = teamId;
        this.summonerId = summonerId;
        this.championNameByItsId = championNameByItsId;
        this.runes = runes;
        this.winRate = winRate;
        this.rank = rank;
        this.tier = tier;
        this.wins = wins;
        this.losses = losses;
        this.championLevel=championLevel;
        this.championPoints = championPoints;
    }

    
    
    public Long getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(int championLevel) {
        this.championLevel = championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }
    
    
    
    


    public Long getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Long spell2Id) {
        this.spell2Id = spell2Id;
    }

    public Long getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Long spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(Long summonerId) {
        this.summonerId = summonerId;
    }

    public List<Perk> getPerks() {
        return perks;
       
    }

    public void setPerks(List<Perk> perks) {
        this.perks = perks;
    }

    public String getChampionNameByItsId() {
        return championNameByItsId;
    }

    public void setChampionNameByItsId(String championNameByItsId) {
        this.championNameByItsId = championNameByItsId;
    }

    public String getPerkToString() {
        return perks.get(1).getName();
    }

    public void setPerkToString(String perkToString) {
        this.perkToString = perkToString;
    }

    public Perk[] getRunes() {
        return runes;
    }

    public void setRunes(Perk[] runes) {
        this.runes = runes;
    }
    
    public String printRune(){
    return runes[0].getName();
    }
    
    

    
}
