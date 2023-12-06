package com.example.basicactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.basicactivity.databinding.FragmentSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = binding.textView;

        //
        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=JQ5aRpzEtKywc1sS6YYf4HcGfY0fpMLBULBJ1PcW";

                // dataTypes query option for this API needs to be a list in JSON post data, but we only want one type
                List<String> dataTypeList = Collections.singletonList("Foundation");

                // create JSON post data
                JSONObject data = new JSONObject();
                try {
                    data.put("query", "tomato");
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
                            description = resultList.getJSONObject(0).getString("description");
                            textView.setText("Response is: \n" + description);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> textView.setText("That didn't work")
                );


                // add this to the request queue
                queue.add(request);


                /*
                NavHostFragment.findNavController(SearchFragment.this)
                        .navigate(R.id.action_SearchFragment_to_ResultsFragment);

                */
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}