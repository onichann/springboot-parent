package com.wt.springboot.designPatterns.structuralPatterns.Composite;

import com.google.gson.Gson;

public class test {
    public static void main(String[] args) {
        LeafComponent leafComponent1=new LeafComponent("AAA","AAA",true);
        LeafComponent leafComponent2=new LeafComponent("AAB","AAB",true);

        FolderComponents folderComponents1=new FolderComponents("AA","AA",false);
        FolderComponents folderComponents2=new FolderComponents("BB","BB",false);

        FolderComponents folderComponents0=new FolderComponents("A","A",false);

        folderComponents1.add(leafComponent1);
        folderComponents1.add(leafComponent2);

        folderComponents2.add(leafComponent1);
        folderComponents2.add(leafComponent2);

        folderComponents0.add(folderComponents1);
        folderComponents0.add(folderComponents2);
        Gson gson=new Gson();
        System.out.println(gson.toJson(folderComponents0));
    }
}
