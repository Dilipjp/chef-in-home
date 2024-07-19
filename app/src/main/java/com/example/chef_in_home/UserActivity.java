package com.example.chef_in_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChefs;
    private ChefAdapter chefAdapter;
    private List<Chef> chefList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewChefs = findViewById(R.id.recyclerViewChefs);
        recyclerViewChefs.setLayoutManager(new LinearLayoutManager(this));

        chefList = new ArrayList<>();
        chefAdapter = new ChefAdapter(chefList, new ChefAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chef chef) {
                Intent intent = new Intent(UserActivity.this, ChefDetailsActivity.class);
                intent.putExtra("chefId", chef.getId());
                startActivity(intent);
            }
        });

        recyclerViewChefs.setAdapter(chefAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("chefs");

        //this data only for testing

//        addChef("John Doe", "Italian Cuisine", true, "+1 (123) 456-7890", "10 years", 4.5, "Experienced chef specializing in traditional Italian dishes.");
//        addChef("Jane Smith", "French Cuisine", true, "+1 (234) 567-8901", "8 years", 4.2, "Passionate chef with expertise in French culinary arts.");
//        addChef("Michael Johnson", "Japanese Cuisine", false, "+1 (345) 678-9012", "12 years", 4.7, "Renowned for creating authentic Japanese dishes with a modern twist.");
//        addChef("Sarah Lee", "Vegetarian Cuisine", true, "+1 (456) 789-0123", "6 years", 4.0, "Specializes in innovative vegetarian dishes that appeal to health-conscious diners.");
//        addChef("David Brown", "Mediterranean Cuisine", true, "+1 (567) 890-1234", "15 years", 4.9, "Award-winning chef known for his expertise in Mediterranean flavors and culinary techniques.");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefList.clear();
                for (DataSnapshot chefSnapshot : snapshot.getChildren()) {
                    Chef chef = chefSnapshot.getValue(Chef.class);
                    if (chef != null) {
                        chef.setId(chefSnapshot.getKey());
                        chefList.add(chef);
                    }
                }
                chefAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, "Failed to load chefs", Toast.LENGTH_SHORT).show();
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
                Intent profileIntent = new Intent(UserActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_sign_out:
                Intent signOutIntent = new Intent(UserActivity.this, SignOutActivity.class);
                startActivity(signOutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addChef(String name, String specialty, boolean available, String contact, String experience, double rating, String bio) {
        String chefId = databaseReference.push().getKey();
        Chef chef = new Chef(chefId, name, specialty, available, contact, experience, rating, bio);
        databaseReference.child(chefId).setValue(chef)
                .addOnSuccessListener(aVoid -> Toast.makeText(UserActivity.this, "Chef added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(UserActivity.this, "Failed to add chef: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
