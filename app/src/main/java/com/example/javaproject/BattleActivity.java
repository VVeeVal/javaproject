package com.example.javaproject;

import static android.os.SystemClock.sleep;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BattleActivity extends AppCompatActivity {
    private Storage storage;
    private TextView battleLog;
    private Button selectLutemon1;
    private Button selectLutemon2;
    private Button startBattle;
    private Lutemon lutemon1;
    private Lutemon lutemon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        storage = Storage.getInstance();
        if (storage.getLutemonsInStorage().size() < 2) 
        {
            Toast.makeText(this, "Need at least 2 Lutemons to battle!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initializeViews();
        setupButtons();
    }

    // initializes views from layout
    private void initializeViews() 
    {
        battleLog = findViewById(R.id.battleLog);
        selectLutemon1 = findViewById(R.id.selectLutemon1);
        selectLutemon2 = findViewById(R.id.selectLutemon2);
        startBattle = findViewById(R.id.startBattle);

        selectLutemon1.setText("Select First Lutemon");
        selectLutemon2.setText("Select Second Lutemon");
    }

    // sets up button click listeners
    private void setupButtons() 
    {
        selectLutemon1.setOnClickListener(v -> showLutemonSelection(1));
        selectLutemon2.setOnClickListener(v -> showLutemonSelection(2));
        startBattle.setOnClickListener(v -> startBattle());
    }

    // shows dialog to select lutemon
    private void showLutemonSelection(int buttonNum) 
    {
        ArrayList<Lutemon> lutemons = storage.getLutemonsInStorage();
        String[] lutemonNames = new String[lutemons.size()];
        for (int i = 0; i < lutemons.size(); i++) 
        {
            lutemonNames[i] = lutemons.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Lutemon " + buttonNum)
               .setItems(lutemonNames, (dialog, which) -> 
               {
                   if (buttonNum == 1) 
                   {
                       lutemon1 = lutemons.get(which);
                       selectLutemon1.setText(lutemon1.getName());
                   } 
                   else 
                   {
                       lutemon2 = lutemons.get(which);
                       selectLutemon2.setText(lutemon2.getName());
                   }
               });
        builder.show();
    }

    // starts battle between selected lutemons
    private void startBattle() 
    {
        if (lutemon1 == null || lutemon2 == null) 
        {
            Toast.makeText(this, "Choose 2 Lutemons to battle!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (lutemon1 == lutemon2) 
        {
            Toast.makeText(this, "Choose 2 different Lutemons to battle!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        battleLog.setText(""); // clear previous battle log
        appendToBattleLog(String.format("Battle starts between %s and %s!\n",
            lutemon1.getName(), lutemon2.getName()));

        // battle continues until one lutemon's health reaches 0
        while (lutemon1.getHealth() > 0 && lutemon2.getHealth() > 0) 
        {
            // lutemon1 attacks lutemon2
            appendToBattleLog(String.format("\n%s attacks %s",
                lutemon1.getName(), lutemon2.getName()));
            lutemon2.defense(lutemon1);
            appendToBattleLog(String.format("\n%s health: %d",
                lutemon2.getName(), lutemon2.getHealth()));

            // check if lutemon2 was defeated
            if (lutemon2.getHealth() <= 0) 
            {
                break;
            }
            // lutemon2 attacks lutemon1
            appendToBattleLog(String.format("\n%s attacks %s",
                lutemon2.getName(), lutemon1.getName()));
            lutemon1.defense(lutemon2);
            appendToBattleLog(String.format("\n%s health: %d",
                lutemon1.getName(), lutemon1.getHealth()));
        }

        // determine winner and award experience
        Lutemon winner = lutemon1.getHealth() > 0 ? lutemon1 : lutemon2;
        winner.addExperience(10);

        appendToBattleLog(String.format("\n\n%s wins!", winner.getName()));
        appendToBattleLog(String.format("\n%s gained 10 experience points", winner.getName()));

        // heal both lutemons
        lutemon1.heal();
        lutemon2.heal();

        // reset selection buttons
        selectLutemon1.setText("Select First Lutemon");
        selectLutemon2.setText("Select Second Lutemon");
        lutemon1 = null;
        lutemon2 = null;
    }

    // appends text to battle log
    private void appendToBattleLog(String text) 
    {
        battleLog.append(text);
    }
}
