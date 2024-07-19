package com.example.chef_in_home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class ChefActivity extends AppCompatActivity {

    private static final String TAG = "ChefActivity";

    private TextView textViewChefName, textViewChefSpecialty, textViewChefAvailability,
            textViewChefContact, textViewChefExperience, textViewChefRating, textViewChefBio;

    // Firebase
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textViewChefName = findViewById(R.id.textViewChefName);
        textViewChefSpecialty = findViewById(R.id.textViewChefSpecialty);
        textViewChefAvailability = findViewById(R.id.textViewChefAvailability);
        textViewChefContact = findViewById(R.id.textViewChefContact);
        textViewChefExperience = findViewById(R.id.textViewChefExperience);
        textViewChefRating = findViewById(R.id.textViewChefRating);
        textViewChefBio = findViewById(R.id.textViewChefBio);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("chefs");


        String chefId = "-O0lhiRkip6-pfGwH4ka";


        databaseReference.child(chefId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Chef chef = dataSnapshot.getValue(Chef.class);
                    if (chef != null) {
                        textViewChefName.setText(chef.getName());
                        textViewChefSpecialty.setText(chef.getSpecialty());
                        textViewChefAvailability.setText(chef.isAvailable() ? "Available" : "Not Available");
                        textViewChefContact.setText(chef.getContact());
                        textViewChefExperience.setText(chef.getExperience());
                        textViewChefRating.setText(String.valueOf(chef.getRating()));
                        textViewChefBio.setText(chef.getBio());
                    } else {
                        Log.d(TAG, "Chef data is null");
                    }
                } else {
                    Log.d(TAG, "Chef data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read chef details", databaseError.toException());
                Toast.makeText(ChefActivity.this, "Failed to load chef details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profileIntent = new Intent(ChefActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_sign_out:
                Intent signOutIntent = new Intent(ChefActivity.this, SignOutActivity.class);
                startActivity(signOutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
