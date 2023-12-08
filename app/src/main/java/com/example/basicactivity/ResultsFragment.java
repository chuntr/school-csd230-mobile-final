package com.example.basicactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.basicactivity.databinding.FragmentResultsBinding;
import com.example.basicactivity.FoodAdapter.ItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResultsFragment extends Fragment implements FoodAdapter.ItemClickListener {

    private FragmentResultsBinding binding;
    private JSONArray resultList;


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

        // value of dataTypes query option for this API needs to be a list in JSON post data, but we only want one type
        List<String> dataTypeList = Collections.singletonList("Foundation");

        // create JSON post data
        JSONObject data = new JSONObject();
        try {
            data.put("query", resultsSearchTerm);
            data.put("dataType", new JSONArray(dataTypeList));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final String requestBody = data.toString();

        StringBuilder descriptions = new StringBuilder(); // TODO: remove

        // run API request and fetch list of results based on query
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url), data, response -> {
                String description;
                try {
                    // "foods" key is the list of result objects that matched the search term
                    resultList = response.getJSONArray("foods");

                    // create String array of food descriptions
                    String[] foodItems = new String[resultList.length()];

                    binding.resultsHeader.setText(String.format("%s %s", getString(R.string.results_header), resultsSearchTerm));

                    for (int i=0; i < resultList.length(); i++) {
                        String newFood = resultList.getJSONObject(i).getString("description");
                        //descriptions.append(newFood);
                        //descriptions.append("\n");
                        foodItems[i] = newFood;

                    }
                    binding.textView.setText(descriptions);

                    // Create recycler view
                    ArrayList<String> foodItemList = new ArrayList<>(Arrays.asList(foodItems));
                    FoodAdapter adapter = new FoodAdapter(getActivity(), foodItemList, this);

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.recyclerView.setAdapter(adapter);

                    ((MainActivity)getActivity()).setResultList(resultList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> binding.textView.setText(R.string.api_call_failed)
        );
        //binding.textView.setText(descriptions);
        //binding.textView.setVisibility(View.INVISIBLE);

        // add this to the request queue
        queue.add(request);

        /*
        //TODO: create these from a loop?
        binding.???.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle itemDetailData = new Bundle();
                itemDetailsData.putString("searchTerm", binding.textInput.getText().toString());

                NavHostFragment.findNavController(ResultsFragment.this)
                        .navigate(R.id.action_ResultsFragment_to_DetailFragment, itemDetailData)
            }
        });
        */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String item) {
        Bundle itemDetailData = new Bundle();
        //itemDetailsData.putString("searchTerm", binding.textInput.getText().toString());

        NavHostFragment.findNavController(ResultsFragment.this)
                .navigate(R.id.action_ResultsFragment_to_DetailFragment, itemDetailData);

    }
}