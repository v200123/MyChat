package com.coffee_just.mychat;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coffee_just.mychat.bean.User;

import org.litepal.LitePal;

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
            createNotificationChannel("ChatMsg", "聊天消息", NotificationManager.IMPORTANCE_MAX);
        }
        if (LitePal.findFirst(User.class)!=null) {
            Intent i = new Intent(this, LoginedActivity.class);
            loadUser();
            startActivity(i);
            Toast.makeText(getApplicationContext(), "读取成功", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(this::finish, 100);
        }

        loginBtn.setOnClickListener((view) -> {
            saveUser();
            Intent i = new Intent(this, LoginedActivity.class);
            startActivity(i);

        });
    }

    private void saveUser() {
        //使用文件系统来存储姓名和ID
//        user = User.InstanceUser();
//        user.setName(userName.getText().toString());
//        ObjectOutputStream write = null;
//
//        try {
//            write = new ObjectOutputStream(openFileOutput("date", Context.MODE_PRIVATE));
//            write.writeObject(user);
//            write.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (write != null) {
//                try {
//                    write.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        user = User.InstanceUser();
        user.setName(userName.getText().toString());
        ContentValues values = new ContentValues();
        if (user.save())
        {
            Toast.makeText(getApplicationContext(), "存储成功", Toast.LENGTH_SHORT).show();
        }
        else
        Toast.makeText(getApplicationContext(), "存储失败", Toast.LENGTH_SHORT).show();
    }

    private void loadUser() {
                user = LitePal.findFirst(User.class);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
