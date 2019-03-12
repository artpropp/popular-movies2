package com.artpropp.popularmovies.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.artpropp.popularmovies.utilities.ImageService;
import com.squareup.picasso.Callback;

public class ViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            ImageService.loadPoster(imageUrl, imageView, new Callback() {
                @Override
                public void onSuccess() {
                    // nothing else to do here
                }

                @Override
                public void onError(Exception e) {
                    imageView.setVisibility(View.GONE);
                }
            });
        }
    }

}
