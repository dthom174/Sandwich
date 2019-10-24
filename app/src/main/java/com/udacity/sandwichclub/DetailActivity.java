package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            Log.v(MainActivity.class.getName(),"made it");
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        TextView knownAsTextView = (TextView)findViewById(R.id.also_known_tv);
        TextView orgin = (TextView)findViewById(R.id.origin_tv);
        TextView ingredients = (TextView)findViewById(R.id.ingredients_tv);
        TextView description = (TextView)findViewById(R.id.description_tv);

        String listOfKnowAs = mSandwich.getAlsoKnownAs() + "";
        String listOfIngredients = mSandwich.getIngredients() + "";

        knownAsTextView.setText(listOfKnowAs.substring(1,listOfKnowAs.length() - 1));
        orgin.setText(mSandwich.getPlaceOfOrigin());
        ingredients.setText(listOfIngredients.substring(1, listOfIngredients.length() - 1));
        description.setText(mSandwich.getDescription());

    }
}
