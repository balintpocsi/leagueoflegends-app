/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reaktorlabs.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Bálint
 */
@Entity
@Table
public class Perk implements Serializable {
    
    @Id
    private int id;
    private String runePathName;
    private String name;
    
    @Column(length=780)
    private String longDesc;
    

    public Perk() {
    }

    public Perk(int id) {
        this.id = id;
    }

    public Perk(String runePathName, String name, int id, String longDesc) {
        this.runePathName = runePathName;
        this.name = name;
        this.id = id;
        this.longDesc = longDesc;
    }

    public String getRunePathName() {
        return runePathName;
    }

    public void setRunePathName(String runePathName) {
        this.runePathName = runePathName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }


    
}
