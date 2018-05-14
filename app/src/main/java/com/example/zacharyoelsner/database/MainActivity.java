package com.example.zacharyoelsner.database;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import android.widget.Button;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;





public class MainActivity extends AppCompatActivity {

    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load data
        final ArrayList<Recipe> recipes = Recipe.getRecipeFromFile("recipes.json", this);

        // Setup button
        Button startCookingButton = findViewById(R.id.button1);
        startCookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open SearchActivity with the loaded data
                Intent startSearchIntent = new Intent(MainActivity.this, SearchActvity.class);
                startSearchIntent.putParcelableArrayListExtra("recipes", recipes);
                startActivity(startSearchIntent);
            }
        });
    }
}

