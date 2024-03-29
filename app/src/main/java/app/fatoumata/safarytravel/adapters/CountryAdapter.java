package app.fatoumata.safarytravel.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.R;
import app.fatoumata.safarytravel.models.CountryModel;

public class CountryAdapter extends ArrayAdapter<CountryModel> {

    public CountryAdapter(@NonNull Context context, List<CountryModel> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View view = convertView;
         if(view == null){
             view = LayoutInflater.from(getContext()).inflate(R.layout.country_cardview, parent,false);
         }
         CountryModel countryModel = getItem(position);

        TextView countryName =  view.findViewById(R.id.country_name);
        ImageView countryFlag =  view.findViewById(R.id.country_flag);
        countryName.setText(countryModel.getName());
        Glide.with(view).load(countryModel.getFlagUrl()).into(countryFlag);

        return  view;

    }
}
