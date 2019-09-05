package com.pageupp.restaurant.data.network;

import com.pageupp.restaurant.model.SearchResponse;


import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {

    @GET("search")
    Call<SearchResponse> getSearchResult(
            @Query("q") String qeryTerm);



}
