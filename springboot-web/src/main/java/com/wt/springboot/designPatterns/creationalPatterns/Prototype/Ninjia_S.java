package com.wt.springboot.designPatterns.creationalPatterns.Prototype;

import java.io.*;

public class Ninjia_S implements Serializable {

    private static final long serialVersionUID = 2169380777227967513L;
    private String name;//名字
    private int rp;//血槽
    private Ninjutsu ninjutsu;//忍术

    public Ninjia_S(String name, int rp, Ninjutsu ninjutsu) {
        this.name = name;
        this.rp = rp;
        this.ninjutsu = ninjutsu;
    }

    protected Object dclone() throws  IOException, ClassNotFoundException {
        Object obj=null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.close();
        ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois=new ObjectInputStream(bis);
        obj= ois.readObject();
        ois.close();
        return obj;
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
