package com.example.groupingsmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // These are the necessary MainActivity variables
    String[] groupList;
    ListView groupings;
    Button createNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the groupList
        groupList = readFile("data");

        // Sets up the groupings ListView
        groupings = findViewById(R.id.groupings);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                groupList);
        groupings.setAdapter(arrayAdapter);

        // Controls the grouping's ListView onClick (i.e. what happens when a group is clicked)
        groupings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long l){
                // Gets the group name that was clicked
                String group = adapter.getItemAtPosition(position).toString();

                // Creates the group class intent, attaches the group name, and starts the activity.
                Intent intent = new Intent(MainActivity.this,Group.class);
                intent.putExtra("group",group);
                startActivity(intent);
            }
        });

        // Creates and controls the Create New Button
        createNew = findViewById(R.id.createNew);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates the create new intent and starts the activity.
                Intent intent = new Intent(MainActivity.this,createNew.class);
                startActivity(intent);
            }
        });
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

}
