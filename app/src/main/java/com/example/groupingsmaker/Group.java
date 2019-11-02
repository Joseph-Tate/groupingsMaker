package com.example.groupingsmaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Group extends AppCompatActivity {

    // These are the necessary Group variables
    TextView groupName;
    ListView userListView;
    Button viewCreateGroupings;
    Button changeMembers;
    Button deleteGroup;
    String name;
    String[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        // This gets the group extra from mainActivities intent and initialises name with it.
        name = getIntent().getStringExtra("group");

        // This reads the users from name.txt
        users = readFile(name);

        // This creates the userListView
        userListView = findViewById(R.id.groupMembers);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Group.this,
                R.layout.support_simple_spinner_dropdown_item, users);
        userListView.setAdapter(arrayAdapter);

        // Creates and sets the groupName textView
        groupName = findViewById(R.id.groupName);
        groupName.setText(name);

        // Creates viewCreateGroupings, changeMembers, and deleteGroup
        viewCreateGroupings = findViewById(R.id.viewCreateGroupings);
        changeMembers = findViewById(R.id.changeMembers);
        deleteGroup = findViewById(R.id.deleteGroup);

        // Controls the viewCreateGroupings button
        viewCreateGroupings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This creates the viewCreateGroupings intent
                Intent intent = new Intent(Group.this, ViewCreateGroupings.class);
                intent.putExtra("members",users);
                startActivity(intent);
            }
        });

        // Controls the deleteGroup button
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This deletes the necessary files
                deleteFile(name + ".txt");
                deleteFromFile("data",name);

                // This brings the user back to home
                Intent mainActivity = new Intent(Group.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    // Makes sure that when back pressed, home reloads. Important for if new group created.
    @Override
    public void onBackPressed(){
        // Creates new Intent and starts
        Intent mainActivity = new Intent(Group.this, MainActivity.class);
        startActivity(mainActivity);
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

    // Reads data from a text file
    public String[] readFile(String file){
        ArrayList<String> lines = new ArrayList<>();
        String[] data;

        // This gets the data
        try{
            // Creates the necessary readers for the file.txt file
            FileInputStream fileInputStream = openFileInput(file + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // This grabs each line from file.txt and appends to an ArrayList<String> called lines.
            String line;
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }

            // This converts the ArrayList<String> to a String[] called data.
            data = new String[lines.size()];
            for(int i = 0; i < lines.size(); i++){
                data[i] = lines.get(i);
            }

            // This closes each reader.
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e){
            // Creates new file.txt file and sets data to empty string array.
            data = new String[0];
            writeFile(file,data);
        } catch (IOException e){
            data = new String[0];
        }
        return data;
    }

    // This deletes a specific line from a file
    public void deleteFromFile(String file, String textToDelete){
        // This gets the lines
        String[] lines = readFile(file);

        // This gets the newlines
        String[] newLines = new String[lines.length - 1];
        int i = 0;
        for(String line: lines){
            if(!line.equals(textToDelete)){
                newLines[i] = line;
                i++;
            }
        }

        // This writes to the file
        writeFile(file,newLines);
    }
}
