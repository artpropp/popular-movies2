package com.artpropp.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.databinding.ViewHolderDetailsBinding;
import com.artpropp.popularmovies.databinding.ViewHolderTrailerBinding;
import com.artpropp.popularmovies.models.Trailer;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_DETAILS = 0;
    private static final int VIEW_TYPE_TRAILER = 1;

    private DetailViewModel mViewModel;
    private List<Trailer> mTrailers;

    public DetailAdapter(DetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    @Override
    public int getItemViewType(int position) {
        int numberOfTrailers = 0;
        if (mViewModel.getTrailers().getValue() != null) {
            numberOfTrailers = mViewModel.getTrailers().getValue().size();
        }
        if (position == 0) {
            return VIEW_TYPE_DETAILS;
        } else if (position < numberOfTrailers) {
            return VIEW_TYPE_TRAILER;
        }
        return VIEW_TYPE_TRAILER; // default, prep for reviews
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
            default:
                ViewHolderTrailerBinding trailerBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder_trailer, viewGroup, false);
                trailerBinding.setModel(mViewModel);
                return new TrailerViewHolder(trailerBinding);
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
                if (mTrailers != null) {
                    trailerViewHolder.getBinding().setTrailer(mTrailers.get(position-1));
                }
                break;

            default:
                // so far, nothing to do here

        }

    }

    @Override
    public int getItemCount() {
        int count = 1;
        if (mTrailers != null) {
            count = count + mTrailers.size();
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

}
