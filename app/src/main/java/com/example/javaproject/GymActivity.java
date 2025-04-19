package com.example.javaproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GymActivity extends AppCompatActivity {
    private Storage storage;
    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private Lutemon selectedLutemon;
    private TextView statsText;
    private Button trainButton;
    private Button endTrainingButton;

    // initializes gym activity and sets up UI elements
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        storage = Storage.getInstance();
        
        // initialize views
        recyclerView = findViewById(R.id.gymRecyclerView);
        trainButton = findViewById(R.id.trainButton);
        endTrainingButton = findViewById(R.id.endTrainingButton);
        statsText = findViewById(R.id.statsText);

        // set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LutemonAdapter(storage.getLutemonsInStorage());

        adapter.setOnItemClickListener(lutemon -> {
            selectedLutemon = lutemon;
            updateStatsText();
            trainButton.setEnabled(true);
        });

        recyclerView.setAdapter(adapter);

        // set up train button
        trainButton.setEnabled(false);
        trainButton.setOnClickListener(v -> {
            if (selectedLutemon != null) {
                selectedLutemon.train();
                updateStatsText();
                adapter.notifyDataSetChanged();
            }
        });

        // set up end training button
        endTrainingButton.setOnClickListener(v -> {
            selectedLutemon = null;
            trainButton.setEnabled(false);
            updateStatsText();
            finish();
        });
    }

    // updates lutemon data
    @Override
    protected void onResume() 
    {
        super.onResume();
        adapter.updateData(storage.getLutemonsInStorage());
    }

    // updates stats text with selected lutemon's data
    private void updateStatsText() 
    {
        if (selectedLutemon != null) {
            String stats = String.format("Selected: %s\nAttack: %d\nDefense: %d\nExperience: %d",
                    selectedLutemon.getName(),
                    selectedLutemon.getAttack(),
                    selectedLutemon.getDefense(),
                    selectedLutemon.getExperience());
            statsText.setText(stats);
        } else {
            statsText.setText("Select a Lutemon to train");
        }
    }
}
