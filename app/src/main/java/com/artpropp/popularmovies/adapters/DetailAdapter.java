package com.artpropp.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.databinding.ViewHolderDetailsBinding;
import com.artpropp.popularmovies.databinding.ViewHolderReviewBinding;
import com.artpropp.popularmovies.databinding.ViewHolderTrailerBinding;
import com.artpropp.popularmovies.models.Review;
import com.artpropp.popularmovies.models.Trailer;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_DETAILS = 0;
    private static final int VIEW_TYPE_TRAILER = 1;
    private static final int VIEW_TYPE_REVIEW = 2;

    private DetailViewModel mViewModel;
    private List<Trailer> mTrailers;
    private List<Review> mReviews;

    public DetailAdapter(DetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public int getItemViewType(int position) {
        int numberOfTrailers = 0;
        if (mViewModel.getTrailers().getValue() != null) {
            numberOfTrailers = mViewModel.getTrailers().getValue().size();
        }
        if (position == 0) {
            return VIEW_TYPE_DETAILS;
        } else if (position <= numberOfTrailers) {
            return VIEW_TYPE_TRAILER;
        } else {
            return VIEW_TYPE_REVIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case VIEW_TYPE_DETAILS:
                ViewHolderDetailsBinding detailsBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_details, viewGroup, false);
                detailsBinding.setModel(mViewModel);
                detailsBinding.setLifecycleOwner(mViewModel.getLifecycleOwner());
                return new DetailsViewHolder(detailsBinding);

            case VIEW_TYPE_TRAILER:
                ViewHolderTrailerBinding trailerBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_trailer, viewGroup, false);
                trailerBinding.setModel(mViewModel);
                return new TrailerViewHolder(trailerBinding);

            case VIEW_TYPE_REVIEW:
            default:
                ViewHolderReviewBinding reviewBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_review, viewGroup, false);
                reviewBinding.setModel(mViewModel);
                return new ReviewViewHolder(reviewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_DETAILS:
                // nothing to do here
                break;

            case VIEW_TYPE_TRAILER:
                TrailerViewHolder trailerViewHolder = (TrailerViewHolder) viewHolder;
                if (mTrailers != null && !mTrailers.isEmpty()) {
                    trailerViewHolder.getBinding().setTrailer(mTrailers.get(position-1));
                }
                break;

            case VIEW_TYPE_REVIEW:
                ReviewViewHolder reviewViewHolder = (ReviewViewHolder) viewHolder;
                if (mReviews != null) {
                    int offsetByTrailers = (mTrailers != null) ? mTrailers.size() : 0;
                    reviewViewHolder.getBinding().setReview(mReviews.get(position-offsetByTrailers-1));
                }
                break;

            default:
                // nothing to do here

        }

    }

    @Override
    public int getItemCount() {
        int count = 1;
        if (mTrailers != null) {
            count += mTrailers.size();
        }
        if (mReviews != null) {
            count += mReviews.size();
        }
        return count;
    }


    /********************* INNER CLASSES *********************/

    class DetailsViewHolder extends RecyclerView.ViewHolder {

        DetailsViewHolder(ViewHolderDetailsBinding binding) {
            super(binding.getRoot());
            binding.executePendingBindings();
        }

    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        ViewHolderTrailerBinding mBinding;

        TrailerViewHolder(ViewHolderTrailerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderTrailerBinding getBinding() {
            return mBinding;
        }
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        ViewHolderReviewBinding mBinding;

        ReviewViewHolder(ViewHolderReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        ViewHolderReviewBinding getBinding() {
            return mBinding;
        }
    }

}
