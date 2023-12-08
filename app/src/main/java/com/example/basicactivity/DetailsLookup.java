package com.example.basicactivity;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

class DetailsLookup extends ItemDetailsLookup {
    private final RecyclerView mRecyclerView;

    DetailsLookup(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public ItemDetails itemDetails;

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        return itemDetails;
    }
}