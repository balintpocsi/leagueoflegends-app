/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Bálint
 */

@Entity
@Table(name = "champion")
public class Champion implements Serializable{
    
    @Id
    private int id;
    private String name;

    public Champion(){
    }

    public Champion(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  
    
    
    
    
    
}
