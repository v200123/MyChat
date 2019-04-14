package com.coffee_just.mychat;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.coffee_just.mychat.bean.User;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("ChatMsg", "聊天消息", NotificationManager.IMPORTANCE_HIGH);
        }
        if (new File(getFilesDir() + "/date").exists()) {
            Intent i = new Intent(this, LoginedActivity.class);
            loadUser();
            startActivity(i);
            new Handler().postDelayed(this::finish, 100);
        }

        loginBtn.setOnClickListener((view) -> {
            saveUser();
            Intent i = new Intent(this, LoginedActivity.class);
            startActivity(i);

        });
    }

    private void saveUser() {
//        BufferedWriter writer = null;
//        try {
//            user = User.InstanceUser();
//            user.setName(userName.getText().toString());
//            writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("date", Context.MODE_PRIVATE)));
////            Log.d("User", "saveUser: "+User.InstanceUser());
//            writer.write(user.toString());
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        user = User.InstanceUser();
        user.setName(userName.getText().toString());
        ObjectOutputStream write = null;

        try {
            write = new ObjectOutputStream(openFileOutput("date", Context.MODE_PRIVATE));
            write.writeObject(user);
            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadUser() {
        ObjectInputStream read = null;

        try {
            read = new ObjectInputStream(openFileInput("date"));
            user = (User) read.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
