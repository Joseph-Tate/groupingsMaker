package com.example.groupingsmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewCreateGroupings extends AppCompatActivity {

    // The necessary variables
    String name;
    String[] users;
    TextView group;
    Button createGroupings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_create_groupings);

        // This gets the groupName and users
        name = getIntent().getStringExtra("group");
        users = getIntent().getStringArrayExtra("members");

        // This creates the group EditText and adds group name
        group = findViewById(R.id.groupName);
        group.setText(name);
    }
}
