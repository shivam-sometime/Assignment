package com.pageupp.restaurant.ui.Search;

import com.pageupp.restaurant.data.network.MyApi;
import com.pageupp.restaurant.data.network.RetrofitHandler;
import com.pageupp.restaurant.model.Restaurant;
import com.pageupp.restaurant.model.Restaurant_;
import com.pageupp.restaurant.model.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListViewModel extends ViewModel {

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> child;
    List<String> cuisinesList;


    public MutableLiveData<SearchResponse> getSearchResponse(final SearchListner listner, String searchTerm){

        listner.onStarted();

        final MutableLiveData<SearchResponse> searchResponse = new MutableLiveData<>();

        MyApi myApi =  RetrofitHandler.getRetrofitHandler();

        Call<SearchResponse> call = myApi.getSearchResult(searchTerm);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                if(response.isSuccessful()) {

                    if (response.body().getRestaurants().size() != 0) {
                        searchResponse.setValue(response.body());

                       // Log.e("Response", response.toString());
                       // Log.e("Response", response.body().getRestaurants().size() + "");


                        List<Restaurant> searchResponseLiveData = response.body().getRestaurants();


                        cuisinesList = new ArrayList<>();
                        listDataHeader = new ArrayList<>();
                        listDataChild = new HashMap<>();

                        for (int b = 0; b < searchResponseLiveData.size(); b++) {

                            Restaurant_ restaurant = searchResponseLiveData.get(b).getRestaurant();
                            String[] cuisinesString = restaurant.getCuisines().split(",");

                            for (int i = 0; i < cuisinesString.length; i++) {

                                if (!cuisinesList.contains(cuisinesString[i].trim())) {
                                    cuisinesList.add(cuisinesString[i].trim());
                                }
                            }

                        }

                        for (int i = 0; i < cuisinesList.size(); i++) {

                            child = new ArrayList<>();
                            listDataHeader.add(cuisinesList.get(i));

                            for (int x = 0; x < searchResponseLiveData.size(); x++) {

                                Restaurant_ restaurant = searchResponseLiveData.get(x).getRestaurant();

                                if (restaurant.getCuisines().contains(cuisinesList.get(i))) {
                                    child.add(searchResponseLiveData.get(x).getRestaurant().getName()+","+
                                            searchResponseLiveData.get(x).getRestaurant().getAverageCostForTwo());
                                }
                            }

                            listDataChild.put(listDataHeader.get(i), child);

                        }


                        listner.onSucess(searchResponseLiveData,listDataHeader,listDataChild);

                    } else {
                        listner.onError("No Record Found");
                    }
                }else{
                    listner.onError("Request is not Successful");
                }

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                searchResponse.setValue(null);
                listner.onError(t.getMessage());

                t.printStackTrace();
            }
        });


        return searchResponse;
    }


}
