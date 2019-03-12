package com.artpropp.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.databinding.ViewHolderDetailsBinding;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;

public class DetailAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_DETAILS = 0;

    private DetailViewModel mViewModel;

    public DetailAdapter(DetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DETAILS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        ViewHolderDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_holder_details, viewGroup, false);
        binding.setModel(mViewModel);
        return new DetailsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int viewType) {
        // TODO: implement other view type bindings
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    /********************* INNER CLASSES *********************/

    class DetailsViewHolder extends RecyclerView.ViewHolder {

        DetailsViewHolder(ViewHolderDetailsBinding binding) {
            super(binding.getRoot());
            binding.executePendingBindings();
        }

    }


}
