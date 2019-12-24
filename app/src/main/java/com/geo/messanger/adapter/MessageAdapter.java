package com.geo.messanger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geo.messanger.MessageActivity;
import com.geo.messanger.R;
import com.geo.messanger.model.Chat;
import com.geo.messanger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private String imageURL;
    private Context mContext;
    FirebaseUser mUser;
    private List<Chat> mChat;


    public MessageAdapter(Context mContext, List<Chat> mChat, String imageURL) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());

        if(imageURL.equals("default")){
            holder.img.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(mContext).load(imageURL).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView show_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.profile_img);
            show_message = itemView.findViewById(R.id.show_msg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mChat.get(position).getSender().equals(mUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
