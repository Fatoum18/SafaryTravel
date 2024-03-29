package app.fatoumata.safarytravel.service;

import java.util.List;

import app.fatoumata.safarytravel.service.api.CountryApi;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;



public class CountryService {


    static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static CountryApi api =  retrofit.create(CountryApi.class);


}