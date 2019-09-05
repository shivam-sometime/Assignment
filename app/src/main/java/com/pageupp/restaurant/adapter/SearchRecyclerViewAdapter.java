package com.pageupp.restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pageupp.restaurant.R;
import com.pageupp.restaurant.model.Restaurant;
import com.pageupp.restaurant.model.SearchResponse;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.RecyclerViewHolder> {

    Context context;
    List<Restaurant> responseList;

    private List<String> mListHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> mListChild;

    public SearchRecyclerViewAdapter(Context context, List<Restaurant> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    public SearchRecyclerViewAdapter(Context context, List<String> mListHeader, HashMap<String, List<String>> mListChild) {
        this.context = context;
        this.mListHeader = mListHeader;
        this.mListChild = mListChild;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.response_list_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Restaurant searchResponseViewModel = responseList.get(position);
        holder.tv_resto_name.setText(searchResponseViewModel.getRestaurant().getName());
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void addItems(List<Restaurant> searchResponses) {
        this.responseList = searchResponses;
        notifyDataSetChanged();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_resto_name;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_resto_name = itemView.findViewById(R.id.tv_resto_name);
        }
    }

}
