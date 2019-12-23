package com.geo.messanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button mRegister, mLogin;

    FirebaseUser mFirebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mFirebaseUser != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegister = findViewById(R.id.register);
        mLogin = findViewById(R.id.login);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });
    }


}
