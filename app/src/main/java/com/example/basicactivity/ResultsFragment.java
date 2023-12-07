package com.example.basicactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.basicactivity.databinding.FragmentResultsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class ResultsFragment extends Fragment {

    private FragmentResultsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve the search term from previous fragment
        final String resultsSearchTerm = getArguments().getString("searchTerm");

        // Setup REST call to the USDA food API
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=JQ5aRpzEtKywc1sS6YYf4HcGfY0fpMLBULBJ1PcW";

        // value of dataTypes query option for this API needs to be a list in JSON post data, but we only want one type
        List<String> dataTypeList = Collections.singletonList("Foundation");

        // create JSON post data
        JSONObject data = new JSONObject();
        try {
            //TODO: replace static "tomato" with resultsSearchString
            data.put("query", resultsSearchTerm);
            data.put("dataType", new JSONArray(dataTypeList));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final String requestBody = data.toString();

        //TODO: add url params, change to post
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data, response -> {
                String description;
                try {
                    // "foods" key is the list of result objects that matched the search term
                    JSONArray resultList = response.getJSONArray("foods");
                    // TODO: grab the whole list

                    // set results description - temp for testing.
                    description = resultList.getJSONObject(0).getString("description");
                    String resultsForItem = String.format("%s %s", getString(R.string.results_for), resultsSearchTerm);
                    binding.textView.setText(String.format("%s\n%s", resultsForItem, description));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> binding.textView.setText(R.string.api_call_failed)
        );


        // add this to the request queue
        queue.add(request);

        /*
        //TODO: create these from a loop?
        binding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ResultsFragment.this)
                        .navigate(R.id.action_ResultsFragment_to_SearchFragment);
            }
        });
        */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}