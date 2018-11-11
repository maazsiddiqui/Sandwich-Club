package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView mIngredientsIv;
    @BindView(R.id.origin_tv)
    TextView mPlaceOfOriginTv;
    @BindView(R.id.description_tv)
    TextView mDescriptionTv;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTv;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        // placeholder image is from https://commons.wikimedia.org/wiki/File:Sandwich.jpg
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.baseline_broken_image_24)
                .into(mIngredientsIv);

        mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        mDescriptionTv.setText(sandwich.getDescription());

        String formattedIngredients = android.text.TextUtils.join(", ",
                sandwich.getIngredients());
        mIngredientsTv.setText(formattedIngredients);

        String formattedAlsoKnownAs = android.text.TextUtils.join(", ",
                sandwich.getAlsoKnownAs());

        mAlsoKnownAsTv.setText(formattedAlsoKnownAs);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
