package com.example.basicactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.basicactivity.databinding.FragmentDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private JSONArray resultList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // fetch our results
        resultList = ((MainActivity)getActivity()).getResultList();

        final String description = getArguments().getString("description");

        setDetailFields(description);
    }

    public void setDetailFields(String description) {
        JSONObject thisFood;
        for (int i=0; i < resultList.length(); i++) {
            try {
                String current = resultList.getJSONObject(i).getString("description");
                if (current.equals(description)) {
                    thisFood = resultList.getJSONObject(i);

                    // set display values
                    JSONArray nutrients = thisFood.getJSONArray("foodNutrients");
                    binding.itemName.setText(description);

                    String serving = nutrients.getJSONObject(0).getString("nutrientName");
                    //binding.servingValue.setText(getNutrientStringById(nutrients, ));
                    binding.calorieValue.setText(getNutrientStringById(nutrients,"2047" ));
                    binding.fatValue.setText(getNutrientStringById(nutrients, "204"));
                    //binding.cholValue.setText(getNutrientStringById(nutrients, ));

                    binding.sodiumValue.setText(getNutrientStringById(nutrients, "307"));
                    binding.carbsValue.setText(getNutrientStringById(nutrients, "205"));
                    binding.proteinValue.setText(getNutrientStringById(nutrients, "203"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getNutrientStringById (JSONArray nutrients, String id) {
        try {
            for (int i = 0; i < nutrients.length(); i++) {
                if (nutrients.getJSONObject(i).getString("nutrientID") == id) {
                    String name = nutrients.getJSONObject(i).getString("nutrientName");
                    String number = nutrients.getJSONObject(i).getString("value");
                    String units = nutrients.getJSONObject(i).getString("unitName");
                    return String.format("%s %s%s", name, number, units);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}