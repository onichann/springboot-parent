package com.wt.springboot.designPatterns.creationalPatterns.Prototype;

import java.io.Serializable;

public class Ninjutsu implements Serializable{
    private static final long serialVersionUID=111221L;

    private int damage;//破坏力
    private String name;

    public Ninjutsu(int damage, String name) {
        this.damage = damage;
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
