package com.example.groupingsmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class createNew extends AppCompatActivity {

    // These are the necessary variables for the createNew activity
    ListView memListView;
    EditText memberName;
    Button addMember;
    ArrayList<String> memList = new ArrayList<>();
    String[] memArray;
    ArrayAdapter<String> arrayAdapter;
    EditText groupName;
    Button createGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        // This sets up the member listView
        memListView = findViewById(R.id.memberList);

        // This grabs the memberName Edit text and the addMember button
        memberName = findViewById(R.id.newMemberName);
        addMember = findViewById(R.id.addMember);

        // This controls the addMember button
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This adds the memberName to the memList each time addMember is clicked
                memList.add(memberName.getText().toString());

                // This creates the entire memList into a String[] called memArray
                memArray = new String[memList.size()];
                for(int i = 0; i < memList.size(); i++){
                    memArray[i] = memList.get(i);
                }

                // This clears the memberName EditText
                memberName.setText("");

                // This resets the memListView and adds all the names
                arrayAdapter = new ArrayAdapter<>(createNew.this,
                        R.layout.support_simple_spinner_dropdown_item,memArray);
                memListView.setAdapter(arrayAdapter);
            }
        });

        // This creates the groupName EditText and the createGroup button
        groupName = findViewById(R.id.groupName);
        createGroup = findViewById(R.id.createGroup);

        // This controls the createGroup button.
        createGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // This writes the groupName to data.txt
                appendFile("data",groupName.getText().toString());

                // This writes the users to groupName.txt
                writeFile(groupName.getText().toString(),memArray);

                // This creates a group intent, adds the groupName as an extra, and starts activity.
                Intent group = new Intent(createNew.this, Group.class);
                group.putExtra("group",groupName.getText().toString());
                startActivity(group);
            }
        });
    }

    // Writes data to any text file
    public void appendFile(String file, String text){
        try{
            // Creates the file append writer, appends text, and closes the writer.
            FileOutputStream fileOutputStream = openFileOutput(file + ".txt",MODE_APPEND);
            fileOutputStream.write((text + "\n").getBytes());
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }

    // Writes data to any text file
    public void writeFile(String file, String[] textToWrite){
        try{
            // This creates the writer
            FileOutputStream fileOutputStream = openFileOutput(file + ".txt",MODE_PRIVATE);

            // This writes the text to new lines
            for(String text: textToWrite){
                fileOutputStream.write((text + "\n").getBytes());
            }

            // This closes the writer
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }
}
