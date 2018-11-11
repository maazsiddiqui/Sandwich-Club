package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String VALUE_EMPTY = "Unknown";

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject(KEY_NAME);
            String mainName = name.getString(KEY_MAIN_NAME);
            JSONArray jsonAlsoKnownAs = name.getJSONArray(KEY_ALSO_KNOW_AS);
            List<String> alsoKnownAs = new ArrayList<>(jsonAlsoKnownAs.length());
            for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonAlsoKnownAs.getString(i));
            }

            if (alsoKnownAs.isEmpty()) alsoKnownAs.add(VALUE_EMPTY);

            String placeOfOrigin = sandwichJson.getString(KEY_PLACE_OF_ORIGIN);

            if (placeOfOrigin.isEmpty()) placeOfOrigin = VALUE_EMPTY;

            String description = sandwichJson.getString(KEY_DESCRIPTION);

            if (description.isEmpty()) description = VALUE_EMPTY;

            String image = sandwichJson.getString(KEY_IMAGE);

            JSONArray jsonIngredients = sandwichJson.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = new ArrayList<>(jsonIngredients.length());
            for (int i = 0; i < jsonIngredients.length(); i++) {
                ingredients.add(jsonIngredients.getString(i));
            }

            if (ingredients.isEmpty()) ingredients.add(VALUE_EMPTY);

            return new Sandwich(mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
