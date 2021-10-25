package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    EditText editText;
    int noteid = -1;

    public void save(View view) {
        EditText edit = (EditText) findViewById(R.id.edit);
        String text = edit.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.notes>", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");


        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (Notes.notes.size() + 1);
            dbHelper.saveNotes(username, title, text, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, text, username);
        }


        // both save and update seem to have the same issue actually
        // when the save button is pushed it always creates a new entry in the list
        // but it does not save old ones, all links go to the most recently made note
        // thats why save new makes new number, its just adding more and more to the list
        // where as update is supposed to read it and it says its 1 every time

        // every add note thinks its the 1st one
        // every update updates 1st but all and gets confused?


        Intent intent = new Intent(this, Notes.class);
        intent.putExtra("message", username);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        editText = (EditText) findViewById(R.id.edit);

        Intent intent = getIntent();

        noteid = intent.getIntExtra("noteid", -1);



        if (noteid != -1) {
            Note note = Notes.notes.get(noteid);
            String noteContent = note.getContent();

            editText.setText(noteContent);
        }
    }
}