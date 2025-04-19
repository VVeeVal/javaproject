package com.example.javaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private Storage storage;
    private RecyclerView recyclerView;
    private LutemonAdapter adapter;

    // initializes main activity and sets up UI elements
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = Storage.getInstance();

        // creates default lutemon if none exist
        if (storage.getLutemonsInStorage().isEmpty() && storage.getLutemonsInGym().isEmpty()) 
        {
            storage.addLutemon(new WhiteLutemon("Default Lutemon"));
        }

        // set up recyclerview
        recyclerView = findViewById(R.id.storageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LutemonAdapter(storage.getLutemonsInStorage());
        recyclerView.setAdapter(adapter);

        // set up navigation buttons
        setupButtons();
    }

    // sets up click listeners for all navigation buttons
    // toast is good for errors and such throughout app https://developer.android.com/guide/topics/ui/notifiers/toasts#java
    private void setupButtons() 
    {
        Button createButton = findViewById(R.id.createButton);
        Button battleButton = findViewById(R.id.battleButton);
        Button gymButton = findViewById(R.id.gymButton);

        createButton.setOnClickListener(v -> 
            startActivity(new Intent(this, CreateLutemonActivity.class)));

        battleButton.setOnClickListener(v -> 
        {
            int totalLutemons = storage.getLutemonsInStorage().size() + storage.getLutemonsInGym().size();
            if (totalLutemons < 2) 
            {
                Toast.makeText(this, "You need at least 2 Lutemons to battle!", 
                    Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, BattleActivity.class));
        });

        gymButton.setOnClickListener(v -> 
            startActivity(new Intent(this, GymActivity.class)));
    }

    // updates recyclerview when activity resumes
    @Override
    protected void onResume() 
    {
        super.onResume();
        adapter.updateData(storage.getLutemonsInStorage());
    }
}