/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.service;

import com.reaktorlabs.repository.ChampionRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Bálint
 */
@Stateless
public class ChampionService {
    
    @Inject
    private ChampionRepository repository;
    

    public ChampionService() {
    }
    
    public String getChampionNameById(int championId){
    
    String championNameByItsId = repository.getChampionById(championId);
    return championNameByItsId;
    }
    
}
