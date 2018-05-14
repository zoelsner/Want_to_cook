package com.example.zacharyoelsner.database;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;


public class Recipe implements Parcelable {


    public String title;
    public String url;
    public String description;
    public Integer servings;
    public String prepTime;
    public String image;
    public String dietLabel;



    public Recipe() {}

    public static ArrayList<Recipe> getRecipeFromFile(String filename, Context context){
        ArrayList<Recipe> RecipeList = new ArrayList<>();

        try{
            String jsonString = loadJsonFromAsset("recipe.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");


            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();

                JSONObject recipeJSON = recipes.getJSONObject(i);

                recipe.title = recipeJSON.getString("title");
                recipe.description = recipeJSON.getString("description");
                recipe.url = recipeJSON.getString("url");
                recipe.image = recipeJSON.getString("image");
                recipe.servings = recipeJSON.getInt("servings");
                recipe.prepTime = recipeJSON.getString("prepTime");
                recipe.dietLabel = recipeJSON.getString("dietLabel");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RecipeList;
    }



    public static final int LESS_THAN_30 = 0;
    public static final int HOUR_TO_30_MINS = 0;
    public static final int MORE_THAN_HOUR = 0;


    public int getPrepTime(){

        String[] split = this.prepTime.split(" ");
        int firstPrep = Integer.parseInt(split[0]);

        if (split[1].charAt(0) == 'h') {
            return Recipe.MORE_THAN_HOUR;
        } else {
            return firstPrep < 30 ? Recipe.LESS_THAN_30 : Recipe.HOUR_TO_30_MINS;
        }

    }



   ////UNDER THIS IS FINISHED/////




    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    public String toString() {
        return "Recipe {"
                + "title: " + this.title
                + ", servings: " + this.servings
                + ", prepTime: " + this.prepTime
                + ", dietLabel: " + this.dietLabel
                + "}";
    }


    //PARCERABLE STUFF//
    ///////////////////

    private Recipe(Parcel in) {
        title = in.readString();
        url = in.readString();
        description = in.readString();
        servings = in.readByte() == 0x00 ? null : in.readInt();
        prepTime = in.readString();
        image = in.readString();
        dietLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(description);
        if (servings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(servings);
        }
        dest.writeString(prepTime);
        dest.writeString(image);
        dest.writeString(dietLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}