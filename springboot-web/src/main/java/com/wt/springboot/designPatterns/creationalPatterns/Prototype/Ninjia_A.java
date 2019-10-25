package com.wt.springboot.designPatterns.creationalPatterns.Prototype;

public class Ninjia_A implements Cloneable {
    private String name;//名字
    private int rp;//血槽
    private Ninjutsu ninjutsu;//忍术

    @Override
    protected Ninjia_A clone() throws CloneNotSupportedException {
        Ninjia_A ninjia_a=null;
        return (Ninjia_A) super.clone();
    }

    public Ninjia_A(String name, int rp, Ninjutsu ninjutsu) {
        this.name = name;
        this.rp = rp;
        this.ninjutsu = ninjutsu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public Ninjutsu getNinjutsu() {
        return ninjutsu;
    }

    public void setNinjutsu(Ninjutsu ninjutsu) {
        this.ninjutsu = ninjutsu;
    }
}
