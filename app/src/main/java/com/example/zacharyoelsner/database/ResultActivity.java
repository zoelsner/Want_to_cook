package com.example.zacharyoelsner.database;

/**
 * Created by zacharyoelsner on 3/28/18.
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;



public class ResultActivity extends AppCompatActivity {


    public ArrayList <Recipe> recipe_results;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe);

            this.recipe_results = this.getIntent().getExtras().getParcelableArrayList("results");

            TextView resultText = findViewById(R.id.textView_recipe_result);
            ListView resultList = findViewById(R.id.listView_recipe_result);

            resultText.setText(
                    getResources().getQuantityString(
                            R.plurals.results_label,
                            recipe_results.size(),
                            recipe_results.size()
                    )
            );

            resultList.setAdapter(new RecipeAdapter(this, this.recipe_results));

//        this.createNotification();
        }
}

