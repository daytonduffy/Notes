package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void login(View view) {
        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();

        EditText name = (EditText) findViewById(R.id.username);
        String username = name.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.notes>", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();

        goToNotes(username);
    }

    private void goToNotes(String username) {

        Intent intent = new Intent(this, Notes.class);
        intent.putExtra("message", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("<com.example.notes>", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            goToNotes(sharedPreferences.getString(usernameKey, ""));
        } else {
            setContentView(R.layout.activity_main);
        }
    }
}