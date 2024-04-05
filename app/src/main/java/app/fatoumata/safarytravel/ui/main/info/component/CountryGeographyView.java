package app.fatoumata.safarytravel.ui.main.info.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import app.fatoumata.safarytravel.databinding.CountryGeographyViewBinding;

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

    public void setupMap(ArrayList<Double> latLong){


        IMapController mapController = binding.mapView.getController();
        mapController.setZoom(5);
        GeoPoint startPoint = new GeoPoint(latLong.get(0), latLong.get(1));
        mapController.setCenter(startPoint);

//        binding.mapView.getController().setCenter(startPoint);
//        MyLocationNewOverlay oMapLocationOverlay = new MyLocationNewOverlay(binding.mapView);
//        binding.mapView.getOverlays().add(oMapLocationOverlay);
//        oMapLocationOverlay.enableFollowLocation();
//        oMapLocationOverlay.enableMyLocation();
//        oMapLocationOverlay.enableFollowLocation();

//        CompassOverlay compassOverlay = new CompassOverlay(context, binding.mapView);
//        compassOverlay.enableCompass();
//        binding.mapView.getOverlays().add(compassOverlay);
    }

    private void init(Context context) {
        binding = CountryGeographyViewBinding.inflate(LayoutInflater.from(context), this, true);
    }


}