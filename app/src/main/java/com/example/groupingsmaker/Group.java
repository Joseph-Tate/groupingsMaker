package com.example.groupingsmaker;

public class Group {
    // List of necessary variables
    String name;
    String[] groupItems;

    // Initializes
    public Group(String name){
        this.name = name;
        this.groupItems = this.readLog();
    }

    // Reads the data log and returns groupItems, creating new file if it doesn't exist yet.
    // FIXME this needs to be created
    private String[] readLog(){
        String[] groupItems;
        groupItems = new String[1];
        groupItems[0] = "YOYO";
        return groupItems;
    }
}
