package app.fatoumata.safarytravel.service.api;

import java.util.List;

import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryApi {

    @GET("region/{region}")
    public Call<List<CountryOfRegionDto>> getCountryOfRegion(@Path("region") String region);

    @GET("name/{name}")
    public Call<List<CountryOfRegionDto>> getCountryByName(@Path("name") String name);
}

