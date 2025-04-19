package com.example.javaproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateLutemonActivity extends AppCompatActivity {
    private Storage storage;
    private EditText nameInput;
    private RadioGroup colorGroup;

    // initializes create lutemon activity and finds UI elements
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        storage = Storage.getInstance();
        nameInput = findViewById(R.id.nameInput);
        colorGroup = findViewById(R.id.colorGroup);
        Button createButton = findViewById(R.id.confirmCreateButton);

        createButton.setOnClickListener(v -> createLutemon());
    }

    // creates a new lutemon based on user input
    private void createLutemon() 
    {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) 
        {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = colorGroup.getCheckedRadioButtonId();
        Lutemon newLutemon = null;

        // create lutemon based on selected color
        if (selectedId == R.id.whiteButton) 
        {
            newLutemon = new WhiteLutemon(name);
        } 
        else if (selectedId == R.id.greenButton) 
        {
            newLutemon = new GreenLutemon(name);
        } 
        else if (selectedId == R.id.pinkButton) 
        {
            newLutemon = new PinkLutemon(name);
        } 
        else if (selectedId == R.id.orangeButton) 
        {
            newLutemon = new OrangeLutemon(name);
        } 
        else if (selectedId == R.id.blackButton) 
        {
            newLutemon = new BlackLutemon(name);
        }

        if (newLutemon != null) 
        {
            storage.addLutemon(newLutemon);
            Toast.makeText(this, "Lutemon created successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } 
        else 
        {
            Toast.makeText(this, "Please select a color", Toast.LENGTH_SHORT).show();
        }
    }
}
