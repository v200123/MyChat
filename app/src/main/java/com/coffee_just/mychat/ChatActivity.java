package com.coffee_just.mychat;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_just.mychat.bean.Message;
import com.coffee_just.mychat.bean.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Message> mMessages = new ArrayList<>();
    private Button mButton;
    private EditText mEditText;
    private Adapter adapter;
    private Socket clientSocket;
    private static final String TAG = "TAG";
    private static final String HOST = "192.168.2.172";
    private static final int PORT = 4008;
    private PrintWriter printWriter;
    private BufferedReader in;
    private String receiveMsg,sendMsg;
    boolean flag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_recycleview);
        mRecyclerView = findViewById(R.id.chat_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(mMessages);
        mRecyclerView.setAdapter(adapter);
        mButton = findViewById(R.id.Btn_send);
        mEditText = findViewById(R.id.chat_input);
        connectService service = new connectService();
        Thread t = new Thread(service);
        t.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mButton.setOnClickListener(view -> {
            if (!mEditText.getText().toString().equals("")) {
                Message msg = new Message(mEditText.getText().toString(), Message.TYPE_SENT);
                mMessages.add(msg);
                adapter.notifyItemInserted(mMessages.size() - 1);
                mRecyclerView.scrollToPosition(mMessages.size() - 1);
                if (clientSocket != null) {
                    sendMsg(msg.getContent());
                    mEditText.setText("");
                }
            }


//                if(clientSocket!=null)
//                {
//
//                android.os.Message message = handler.obtainMessage();
//                message.what =1 ;
//                message.obj = msg.getContent();
//                handler.sendMessage(message);
//                }
        });
    }

    private class connectService implements Runnable {

        @Override
        public void run() {
            System.out.println("线程在运行。。。。。。。。。。。。");
            try {
                clientSocket = new Socket(HOST, PORT);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                receiverMsg();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "run: " + e.getMessage());
            }
            }
        }

    private void receiverMsg() {
        System.out.println("zhixingle");
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,Void> Task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String Line;
                        while ((receiveMsg = in.readLine()) != null) {
                            Line = receiveMsg;
                            Log.d(TAG, "doInBackground: \n\n\n"+Line);
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Task.execute();

    }




    private void sendMsg(String Msg) {
       new Thread(() -> {
           printWriter.println(Msg+"|"+ User.InstanceUser().getId().toString());
           Log.d(TAG, "sendMsg: \n"+printWriter.checkError());
           Log.d(TAG, "seneMsg: \n" + Msg);

       }).start();
    }
}



class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    private ArrayList<Message> mMessageArrayList;

    public Adapter(ArrayList list) {
        mMessageArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_activity_recycleview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = mMessageArrayList.get(position);
        if (msg.getType() == Message.TYPE_RECEIVED) {
// 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLinearLayout.setVisibility(View.VISIBLE);
            holder.rightLinearLayout.setVisibility(View.GONE);
            holder.leftText.setText(msg.getContent());
        } else if(msg.getType() == Message.TYPE_SENT) {
// 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLinearLayout.setVisibility(View.VISIBLE);
            holder.leftLinearLayout.setVisibility(View.GONE);
            holder.rightText.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMessageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout leftLinearLayout,rightLinearLayout;
        private TextView leftText,rightText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLinearLayout = itemView.findViewById(R.id.left_layout);
            rightLinearLayout = itemView.findViewById(R.id.right_layout);
            leftText = itemView.findViewById(R.id.left_msg);
            rightText = itemView.findViewById(R.id.right_msg);
        }
    }


}
