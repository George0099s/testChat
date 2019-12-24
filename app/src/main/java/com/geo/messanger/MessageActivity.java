package com.geo.messanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geo.messanger.adapter.MessageAdapter;
import com.geo.messanger.model.Chat;
import com.geo.messanger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ImageView profileImg;
    TextView username;

    FirebaseUser mUser;
    DatabaseReference reference;
    ImageButton btnSend;
    EditText text_send;
    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recycler_123);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(getApplicationContext());
        linearLayoutmanager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutmanager);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profileImg = findViewById(R.id.profile_img);
        username = findViewById(R.id.user_name);

        text_send = findViewById(R.id.text_send);
        btnSend = findViewById(R.id.btn_send);

        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        mUser = FirebaseAuth.getInstance().getCurrentUser();



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendVessage(mUser.getUid(), userId, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "you cant send empty msg", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUserName());
                if(user.getImageURL().equals("default")){
                    profileImg.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(profileImg);
                }
                readMessages(mUser.getUid(), userId, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void sendVessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void readMessages(final String myId, final String userId, final String imageURL){
        mChat = new ArrayList<Chat>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                        if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                                chat.getReceiver().equals(userId) &&  chat.getSender().equals(myId)){
                                mChat.add(chat);
                        }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageURL);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
