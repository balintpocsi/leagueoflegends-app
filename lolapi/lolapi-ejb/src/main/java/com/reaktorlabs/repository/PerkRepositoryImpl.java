/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.repository;

import com.reaktorlabs.entity.Perk;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bálint
 */
@Stateless
public class PerkRepositoryImpl implements PerkRepository{
    
    @PersistenceContext(name = "helloLOL")
    private EntityManager entityManager;

    @Override
    public void createPerk(Perk perk) {
       entityManager.merge(perk);
    }

    @Override
    public Perk getPerkById(int id) {
        Perk perk = entityManager.find(Perk.class, id);
        return perk;
    }
}
    

