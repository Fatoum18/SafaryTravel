package app.fatoumata.safarytravel.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.fatoumata.safarytravel.service.api.CountryApi;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;



public class CountryService {


    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .build();
    static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();


    public static CountryApi api =  retrofit.create(CountryApi.class);


}