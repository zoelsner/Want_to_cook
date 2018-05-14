package com.example.zacharyoelsner.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class SearchActvity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Recipe> recipes;

    private int dietChoice = 0;
    private int servingChoice = 0;
    private int preparationChoice = 0;

    private List<String> dietStrings = new ArrayList<>();
    private List<String> servingStrings = new ArrayList<>();
    private List<String> preparationStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        this.recipes = this.getIntent().getExtras().getParcelableArrayList("recipes");

        this.initializeSpinners();
        this.initializeButton();
    }

    private void initializeSpinners() {
        // Initialize Spinner options

        // Diet based on data from database
        this.dietStrings.add("");
        for (Recipe recipe : this.recipes) {
            if (!this.dietStrings.contains(recipe.dietLabel)) {
                this.dietStrings.add(recipe.dietLabel);
            }
        }

        // Static choices for serving size
        this.servingStrings.add("");
        this.servingStrings.add("less than 4"); // 1
        this.servingStrings.add("4-6"); // 2
        this.servingStrings.add("7-9"); // 3
        this.servingStrings.add("more than 10"); // 4

        // Static choices for preparation times
        this.preparationStrings.add("");
        this.preparationStrings.add("30 minutes or less"); // 1
        this.preparationStrings.add("less than 1 hour"); // 2
        this.preparationStrings.add("more than 1 hour"); // 3

        // Set the spinner adapters
        setSpinnerAdapter(R.id.spinner_diet, this.dietStrings);
        setSpinnerAdapter(R.id.spinner_servings, this.servingStrings);
        setSpinnerAdapter(R.id.spinner_prep, this.preparationStrings);
    }

    void Search() {
        // Get the appropriate results based on the selected options
        ArrayList<Recipe> searchResults = new ArrayList<>();

        for (Recipe recipe : recipes) {
            // default: add it to the result list
            // when it doesn't fit a criteria: don't add it
            boolean add = true;

            if (this.dietChoice > 0) {
                // Exclude things which don't fit this criteria
                if (!recipe.dietLabel.equals(this.dietStrings.get(this.dietChoice))) {
                    add = false;
                }
            }

            if (this.servingChoice > 0) {
                // Exclude things which don't fit this criteria
                switch (this.servingChoice) {
                    // less than 4 servings
                    case 1:
                        // if it isn't this, exclude it
                        if (recipe.servings >= 4) {
                            add = false;
                        }
                        break;

                    // 4 - 6 servings
                    case 2:
                        // if it isn't this, exclude it
                        if (recipe.servings < 4 || recipe.servings > 6) {
                            add = false;
                        }
                        break;

                    // 7 - 9 servings
                    case 3:
                        // if it isn't this, exclude it
                        if (recipe.servings < 7 || recipe.servings > 9) {
                            add = false;
                        }
                        break;

                    // 10 + servings
                    case 4:
                        // if it isn't this, exclude it
                        if (recipe.servings < 10) {
                            add = false;
                        }
                        break;
                }
            }

            if (this.preparationChoice > 0) {
                // Exclude things which don't fit this criteria
                int prepTime = recipe.getPrepTime();
                if (this.preparationChoice == 1 && prepTime != Recipe.LESS_THAN_30) {
                    add = false;
                }
                if (this.preparationChoice == 2 && prepTime == Recipe.MORE_THAN_HOUR) {
                    add = false;
                }
                if (this.preparationChoice == 3 && prepTime != Recipe.MORE_THAN_HOUR) {
                    add = false;
                }
            }

            if (add) {
                searchResults.add(recipe);
            }
        }

        // Start the ResultActivity, passing the results
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putParcelableArrayListExtra("results", searchResults);
        startActivity(resultIntent);
    }

    private void initializeButton() {
        Button submitButton = findViewById(R.id.search_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActvity.this.Search();
            }
        });
    }

    private void setSpinnerAdapter(int viewId, List<String> options) {
        Spinner spinner = findViewById(viewId);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                options
        );
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_diet:
                this.dietChoice = position;
                break;
            case R.id.spinner_servings:
                this.servingChoice = position;
                break;
            case R.id.spinner_prep:
                this.preparationChoice = position;
                break;
        }
    }

    //@Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
