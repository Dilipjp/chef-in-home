package com.example.chef_in_home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChefDetailsActivity extends AppCompatActivity {

    private TextView textViewChefName, textViewChefSpecialty, textViewChefAvailability, textViewChefExperience, textViewChefRating, textViewChefBio;
    private Button buttonRequestChef;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        textViewChefName = findViewById(R.id.textViewChefName);
        textViewChefSpecialty = findViewById(R.id.textViewChefSpecialty);
        textViewChefAvailability = findViewById(R.id.textViewChefAvailability);
        textViewChefExperience = findViewById(R.id.textViewChefExperience);
        textViewChefRating = findViewById(R.id.textViewChefRating);
        textViewChefBio = findViewById(R.id.textViewChefBio);
        buttonRequestChef = findViewById(R.id.buttonRequestChef);

        String chefId = getIntent().getStringExtra("chefId");

        databaseReference = FirebaseDatabase.getInstance().getReference("chefs").child(chefId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Chef chef = snapshot.getValue(Chef.class);
                if (chef != null) {
                    textViewChefName.setText(chef.getName());
                    textViewChefSpecialty.setText(chef.getSpecialty());
                    textViewChefAvailability.setText(chef.isAvailable() ? "Available" : "Not Available");
                    textViewChefExperience.setText(chef.getExperience());
                    textViewChefRating.setText("*" + chef.getRating());
                    textViewChefBio.setText(chef.getBio());
                    buttonRequestChef.setEnabled(chef.isAvailable());
                } else {
                    Toast.makeText(ChefDetailsActivity.this, "Chef details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChefDetailsActivity.this, "Failed to load chef details: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonRequestChef.setOnClickListener(v -> {
            Toast.makeText(ChefDetailsActivity.this, "Request sent to the chef", Toast.LENGTH_SHORT).show();
        });
    }
}
