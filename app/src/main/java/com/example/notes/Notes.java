package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {

    TextView textView2;

    public static ArrayList<Note> notes = new ArrayList<>();

    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("<com.example.notes>", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.note) {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // display welcome message
        textView2 = (TextView) findViewById(R.id.textView);
        //Intent intent = getIntent();
        //String str = intent.getStringExtra("message");
        //String text = "Welcome " + str + "!";
        //textView2.setText(text);

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.notes>", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String text = "Welcome " + username + "!";
        textView2.setText(text);

        // sqlite instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // initiate notes using read notes
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);



        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);

                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }
}