package com.example.chef_in_home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    Button signOut;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);


        signOut = findViewById(R.id.signOut);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        // checkUser();


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(AdminActivity.this, "User sign out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUser() {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(AdminActivity.this, "User not sign", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity.this,LoginActivity.class );
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AdminActivity.this, "User sign", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}