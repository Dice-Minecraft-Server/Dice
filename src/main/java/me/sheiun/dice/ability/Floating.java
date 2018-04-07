/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sheiun.dice.ability;

/**
 *
 * @author SheiUn
 */
public class Floating extends Ability {

    private String name = "漂浮";
    private int level;
    
    public Floating() {
        super();
    }
    

    @Override
    protected void useSkill() {

    }

    @Override
    protected void upgradeSkill() {
        this.level += 1;
    }

}
