package com.artpropp.popularmovies.utilities;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageService {

    private static final String BASE_IMAGE_PATCH = "https://image.tmdb.org/t/p/";
    private static final String SMALL = "w185/";
    private static final String LARGE = "w500/";

    public static void loadTile(String imagePath, ImageView imageView) {
        Picasso.get()
                .load(BASE_IMAGE_PATCH + SMALL + imagePath.trim())
                .into(imageView);
    }

    public static void loadPoster(String imagePath, ImageView imageView, Callback callback) {
        Picasso.get()
                .load(BASE_IMAGE_PATCH + LARGE + imagePath.trim())
                .into(imageView, callback);
    }

}
