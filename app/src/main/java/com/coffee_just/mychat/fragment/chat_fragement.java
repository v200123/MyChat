package com.coffee_just.mychat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coffee_just.mychat.ChatActivity;
import com.coffee_just.mychat.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 这个是选择聊天人的界面的编写，需要使用Glide
 */
public class chat_fragement extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_chat_fragment,container,false);
        mRecyclerView = v.findViewById(R.id.show_chat_list_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter();
        mRecyclerView.setAdapter(adapter);

        return v;
    }



    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_chat_fragment_item,parent,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(getActivity()).load("https://apic.douyucdn.cn/upload/avatar/default/11_middle.jpg").into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CircleImageView mImageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mImageView = itemView.findViewById(R.id.show_chat_head_image);
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                startActivity(i);
            }
        }
    }
}

