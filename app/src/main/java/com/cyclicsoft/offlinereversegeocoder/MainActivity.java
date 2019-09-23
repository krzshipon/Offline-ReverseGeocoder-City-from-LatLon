package com.cyclicsoft.offlinereversegeocoder;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.GeoName;
import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.ReverseGeoCoder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tvCity, tvCountry;
    private EditText etLat, etLon;
    private Button btGeocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
        });
        intUi();

        btGeocode.setOnClickListener(view -> {
            String latS = etLat.getText().toString();
            String lonS = etLon.getText().toString();
            if(TextUtils.isEmpty(latS) || TextUtils.isEmpty(lonS)){
                Toast.makeText(this, "Please check lat lon", Toast.LENGTH_SHORT).show();
                return;
            }
            Double lat = Double.parseDouble(latS.trim());
            Double lon = Double.parseDouble(lonS.trim());
            geocode(lat , lon);
        });




    }

    private void geocode(Double lat, Double lon) {
        if(lat == null || lon == null){
            return;
        }
        new Thread(() -> {
            GeoName city = Objects.requireNonNull(ReverseGeoCoder.getInstance()).nearestPlace(lat, lon);
            if(city == null){
                return;
            }
            runOnUiThread(()->{
                tvCity.setText(city.getCityNameByLanguageCode(Locale.getDefault().getLanguage()));
                tvCountry.setText(city.countryCode);
            });
        }).start();
    }

    private void intUi() {
        tvCity = findViewById(R.id.tv_city);
        tvCountry = findViewById(R.id.tv_country);
        etLat = findViewById(R.id.et_lat);
        etLon = findViewById(R.id.et_lon);
        btGeocode = findViewById(R.id.bt_geocode);


        AdView mAdView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(MainActivity.this, "Add loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e(TAG, "errorCode : "+errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }
}
