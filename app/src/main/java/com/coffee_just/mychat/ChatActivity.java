package com.coffee_just.mychat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_just.mychat.bean.Message;

import java.util.ArrayList;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_recycleview);
        mRecyclerView = findViewById(R.id.chat_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(mMessages);
        mRecyclerView.setAdapter(adapter);
        mButton = findViewById(R.id.Btn_send);
        mEditText = findViewById(R.id.chat_input);

        mButton.setOnClickListener(view->{
            if(!mEditText.getText().toString().equals(""))
            {
                Message msg = new Message(mEditText.getText().toString(),Message.TYPE_SENT);
                mMessages.add(msg);
                adapter.notifyItemInserted(mMessages.size()-1);
                mRecyclerView.scrollToPosition(mMessages.size()-1);
                mEditText.setText("");
                Toast.makeText(getApplicationContext(),"陈工发送",Toast.LENGTH_SHORT).show();
            }

        });
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
