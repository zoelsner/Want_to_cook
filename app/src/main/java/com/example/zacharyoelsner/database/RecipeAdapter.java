package com.example.zacharyoelsner.database;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

public class RecipeAdapter extends BaseAdapter {

    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    // constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList){

        // initialize instances variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    // methods
    // a list of methods we need to override

    // gives you the number of recipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }


    @Override
    public Object getItem(int position){
        return this.mRecipeList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        // check if the view already exists
        // if yes, you don't need to inflate and findViewbyID again
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_recipe, parent, false);


            // add the views to the holder
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_food_details);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.typeOfFood = convertView.findViewById(R.id.recipe_list_food_type);
            holder.buttonToCook = convertView.findViewById(R.id.button_to_cook);
            // add the holder to the view
            // for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)convertView.getTag();
        }


        // get relative subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        Button buttonToCook = holder.buttonToCook;
        TextView typeOfFood = holder.typeOfFood;



        final Recipe recipe = (Recipe)getItem(position);

        Picasso.with(mContext).load(recipe.image).into(thumbnailImageView);

        titleTextView.setText(recipe.title);

        String pluralDetailText = parent.getResources().getQuantityString(
                R.plurals.list_item_details,
                recipe.servings,
                recipe.servings,
                recipe.prepTime
        );


        servingTextView.setText(pluralDetailText);
        servingTextView.setTextColor(Color.BLUE);

        typeOfFood.setText(recipe.dietLabel);
        typeOfFood.setTextColor(Color.BLUE);


        buttonToCook.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            // https://developer.android.com/training/notify-user/build-notification.html

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(recipe.url));
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            String contentTitle = "Cooking Directions";
            String contentText = "The steps for cooking " + recipe.title + " can be found here!";


        }


        });

    return convertView;

    }

    private static class ViewHolder{
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public Button buttonToCook;
        public TextView typeOfFood;
    }
}