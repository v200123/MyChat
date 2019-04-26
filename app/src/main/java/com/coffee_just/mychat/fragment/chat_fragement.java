package com.coffee_just.mychat.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coffee_just.mychat.ChatActivity;
import com.coffee_just.mychat.R;
import com.coffee_just.mychat.bean.Message;
import com.coffee_just.mychat.bean.User;
import com.coffee_just.mychat.utils.MyToast;

import org.litepal.LitePal;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 这个是选择聊天人的界面的编写，需要使用Glide
 */
public class chat_fragement extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_chat_fragment, container, false);
        mRecyclerView = v.findViewById(R.id.show_chat_list_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
         adapter= new Adapter();
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        adapter.notifyItemChanged(LitePal.count(Message.class)-1);
        MyToast.OutToast(getContext(),"刷新了").show();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_chat_fragment_item, parent, false);

            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(getActivity()).load("https://dwz.cn/efEWc75J").into(holder.mImageView);
            if (LitePal.findFirst(Message.class) != null) {
                String uuid = LitePal.findLast(Message.class).getFromUUID();
                holder.Msg_Name.setText(LitePal.select("name").findFirst(User.class) + "");
                holder.Msg_information.setText(LitePal.findLast(Message.class).getContent());
            }
        }

        @Override
        public int getItemCount() {
            return LitePal.count(User.class);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CircleImageView mImageView;
            private TextView Msg_Name,Msg_information;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mImageView = itemView.findViewById(R.id.show_chat_head_image);
                Msg_Name = itemView.findViewById(R.id.Msg_Name);
                Msg_information = itemView.findViewById(R.id.Msg_information);
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                startActivity(i);
            }
        }
    }
}

