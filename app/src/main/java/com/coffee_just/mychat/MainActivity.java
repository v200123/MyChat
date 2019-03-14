package com.coffee_just.mychat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffee_just.mychat.bean.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText userName;
    private Button loginBtn;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.loginName);
        loginBtn = findViewById(R.id.login_btn);
        if (new File(getFilesDir() + "/date").exists()) {
            loadUser();
            userName.setText(user.getName());
            userName.clearFocus();
        }
        loginBtn.setOnClickListener((view) -> {
            saveUser();
            Intent i = new Intent(this, LoginedActivity.class);
            startActivity(i);

        });
    }

    private void saveUser() {
        BufferedWriter writer = null;
        try {
            user = User.InstanceUser();
            user.setName(userName.getText().toString());
            writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("date", Context.MODE_PRIVATE)));
//            Log.d("User", "saveUser: "+User.InstanceUser());
            writer.write(user.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadUser() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput("date")));
            String line;
            String[] lines;
            while ((line = reader.readLine()) != null) {
                lines = line.split("\\|");
                user = User.InstanceUser();
                user.setName(lines[0]);

                Toast.makeText(getApplicationContext(), "sdsdadad", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
