package app.fatoumata.safarytravel.ui.main.info.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import app.fatoumata.safarytravel.databinding.CountryGeographyViewBinding;
import app.fatoumata.safarytravel.databinding.CountryInfoViewBinding;

public class CountryGeographyView extends LinearLayout {
    private CountryGeographyViewBinding binding;

    public CountryGeographyView(Context context) {
        super(context);
        init(context);
    }

    public CountryGeographyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountryGeographyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        binding = CountryGeographyViewBinding.inflate(LayoutInflater.from(context), this, true);
    }


}