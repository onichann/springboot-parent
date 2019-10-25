package com.wt.springboot.designPatterns.creationalPatterns.Prototype;

/**
 * 深拷贝和浅拷贝
 */
public class Test {
    public static void main(String[] args) {
        Ninjia_A kakaxi=new Ninjia_A("旗木.卡卡西",50,new Ninjutsu(500,"影分身"));

        Ninjia_A kk;
        try {
            kk= (Ninjia_A) kakaxi.clone();

            System.out.println(kakaxi.getNinjutsu()==kk.getNinjutsu());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Ninjia_S naturo=new Ninjia_S("漩涡.那乳托",500,new Ninjutsu(1000,"影分身"));
        Ninjia_S nn;
        try {
            nn= (Ninjia_S) naturo.dclone();
//            nn= CloneUtils.clone(naturo);
            System.out.println(naturo.getNinjutsu()==nn.getNinjutsu());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

