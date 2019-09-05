package com.pageupp.restaurant.ui.Search;

import com.pageupp.restaurant.model.Restaurant;

import java.util.HashMap;
import java.util.List;

public interface SearchListner {

    void onStarted();
    void onSucess(List<Restaurant> searchResponseLiveData, List<String> listDataHeader, HashMap<String, List<String>> listDataChild);
    void onError(String message);
}