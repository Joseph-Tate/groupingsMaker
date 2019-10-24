package com.example.groupingsmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // These are the necessary MainActivity variables
    String[] groupList;
    ListView groupings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grabs the necessary objects from the interface
        groupings = findViewById(R.id.groupings);

        // Grabs an ArrayList of the groups
        groupList = GroupList.returnGroups();

        // Sets all groups into groupings ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item
                ,groupList);
        groupings.setAdapter(arrayAdapter);
    }
}
