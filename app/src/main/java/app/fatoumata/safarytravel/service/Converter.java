package app.fatoumata.safarytravel.service;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import app.fatoumata.safarytravel.utils.Utils;

public class Converter {


   public static CountryModel countryDtoToModel(CountryOfRegionDto countryOfRegionDto){

        CountryModel countryModel = new CountryModel();
        countryModel.setId(Utils.generateId());
        countryModel.setName(countryOfRegionDto.name.common);
        countryModel.setFlagUrl(countryOfRegionDto.flags.png);
        return  countryModel;

    }

   public static List<CountryModel> countryDtosToModels(List<CountryOfRegionDto> countryOfRegionDtos){

       List<CountryModel>  countryModels =  new ArrayList<>();
       if(countryOfRegionDtos!=null){
            for(CountryOfRegionDto countryOfRegionDto :  countryOfRegionDtos){

                countryModels.add(countryDtoToModel(countryOfRegionDto));
            }
       }
       return countryModels;

    }



}