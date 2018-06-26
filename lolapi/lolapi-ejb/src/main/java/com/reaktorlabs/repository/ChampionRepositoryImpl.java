/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.repository;


import com.reaktorlabs.entity.Champion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Bálint
 */
@Stateless
public class ChampionRepositoryImpl implements ChampionRepository  {

    @PersistenceContext(name = "helloLOL")
    private EntityManager entityManager;

    @Override
    public void createChampion(Champion champion) {
        entityManager.persist(champion);
        //.persist-re deteched exception dob???
        //merge-t hasznaltam korabban
       
    }

    @Override
    public String getChampionById(int id) {
        
  Champion champion = entityManager.find(Champion.class, id);
        String name = champion.getName();
        
        return name;
    }


}
