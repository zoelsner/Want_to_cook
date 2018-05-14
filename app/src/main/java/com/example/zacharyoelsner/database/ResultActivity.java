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


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe);
//
//        this.recipe_results = this.getIntent().getExtras().getParcelableArrayList("results");
//
//        TextView textview_results = findViewById(R.id.textView_recipe_result);
//        ListView listview_results = findViewById(R.id.listView_recipe_result);
//
//        textview_results.setText(
//                getResources().getQuantityString(
//                        R.plurals.results_label,
//                        recipe_results.size(),
//                        recipe_results.size()
//                )
//        );
//
//        listview_results.setAdapter(new RecipeAdapter(this, this.recipe_results));
//
////        this.createNotification();
//    }
//
//
//}



//    private void createNotification() {
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("notif1", "you're almost there", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//        String notificationText = "The instructions for " + "food here" + " can be found here!";
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notif1")
//                .setContentTitle("Cooking Instruction")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
//                .setSmallIcon(android.R.drawable.sym_def_app_icon)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(contentIntent)
//                .setAutoCancel(true);
//
//        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notify(970970, builder.build());
//    }
//}

