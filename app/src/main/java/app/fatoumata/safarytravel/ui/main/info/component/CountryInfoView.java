package app.fatoumata.safarytravel.ui.main.info.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.fatoumata.safarytravel.R;
import app.fatoumata.safarytravel.databinding.ActivityCountryBinding;
import app.fatoumata.safarytravel.databinding.CountryInfoViewBinding;

public class CountryInfoView extends LinearLayout {
    private CountryInfoViewBinding binding;

    public CountryInfoView(Context context) {
        super(context);
        init(context);
    }

    public CountryInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountryInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        binding = CountryInfoViewBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public ImageView getFlagView() {
        return  binding.flag;
    }
    public void setOfficialName(String officialName) {
        binding.officialName.setText(officialName);
    }

    public void setCapital(String capital) {
        binding.capital.setText(capital);
    }

    public void setPopulation(String population) {
        binding.population.setText(population);
    }

    public void setLanguages(String languages) {
        binding.languages.setText(languages);
    }

    public void setMoney(String money) {
        binding.money.setText(money);
    }
}