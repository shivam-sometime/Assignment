package com.pageupp.restaurant.ui.Search;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LifecycleOwner;

import androidx.lifecycle.ViewModelProviders;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.pageupp.restaurant.R;
import com.pageupp.restaurant.adapter.ExpandableAdapter;
import com.pageupp.restaurant.model.Restaurant;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchListner, LifecycleOwner {

    SearchListViewModel viewModel;
    ProgressDialog pd;
    SearchListner searchListner;

    EditText et_search_keyword;

    ExpandableListView expandableListView;
    ExpandableAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);



        et_search_keyword = findViewById(R.id.et_search_keyword);
        expandableListView = findViewById(R.id.expandableList);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();


        viewModel = ViewModelProviders.of(this).get(SearchListViewModel.class);
        searchListner = this;

        expandableListAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);


    }

    public void onClick(View view) {

        String keyword =  et_search_keyword.getText().toString().trim();
        if(!TextUtils.isEmpty(keyword)){

            hideKeyboardFrom(this,view);

            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();
            expandableListAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);

            expandableListView.setAdapter(expandableListAdapter);

            new SearchListViewModel().getSearchResponse(searchListner, keyword);

        }else{
            Toast.makeText(this, "Please provide valid keyword", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onStarted() {
       // Log.e("Pd", "Started");
        pd.show();
    }

    @Override
    public void onSucess(List<Restaurant> searchResponseLiveData, List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {

       // Log.e("Pd", "Success");
        pd.dismiss();
       // Log.e("List Size", searchResponseLiveData.size() + "");


        expandableListAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });

    }

    @Override
    public void onError(String message) {
       // Log.e("Pd", "Error");
        pd.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
       // Log.e("Error message", message + "");

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
