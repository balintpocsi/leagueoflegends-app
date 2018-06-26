/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.repository;

import com.reaktorlabs.entity.Champion;



/**
 *
 * @author Bálint
 */
public interface ChampionRepository {
    
    void createChampion(Champion champion);
    String getChampionById(int id);
    
    
}
