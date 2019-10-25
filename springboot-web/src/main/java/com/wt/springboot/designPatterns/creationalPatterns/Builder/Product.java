package com.wt.springboot.designPatterns.creationalPatterns.Builder;

public class Product {
    private String name;
    private String clothes;
    private String wuqi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClothes() {
        return clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getWuqi() {
        return wuqi;
    }

    public void setWuqi(String wuqi) {
        this.wuqi = wuqi;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", clothes='" + clothes + '\'' +
                ", wuqi='" + wuqi + '\'' +
                '}';
    }
}

