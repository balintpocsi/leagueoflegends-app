/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.service;

import com.reaktorlabs.entity.Perk;
import com.reaktorlabs.repository.PerkRepository;
import javax.inject.Inject;

/**
 *
 * @author Bálint
 */
public class PerkService {
    
    @Inject
    private PerkRepository repository;

    public PerkService() {
    }
    
    public Perk getPerkById(int id){
        
        Perk perk = repository.getPerkById(id);
        return perk;
    
    }
    
}
