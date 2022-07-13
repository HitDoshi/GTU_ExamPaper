package com.example.gtuexampaper13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public ArrayList<String> name = new ArrayList<>();
    public String[] a;
    int k = 0;
    int temp = 0;
    //Integer[] img = {R.drawable.chemical, R.drawable.civil, R.drawable.computer, R.drawable.electrical, R.drawable.ec, R.drawable.information, R.drawable.mechanical};
    Integer[] img = {R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7};
    String[] totalp = {"157", "182", "185", "208", "152", "165", "212"};
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MainActivity6.class);
        startActivity(intent);

        //Toast.makeText(this,"Oncreate-1",Toast.LENGTH_SHORT).show();

        setTitle("GTU Exam Paper");

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        if ( !(connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected() ) )
        {
            Toast.makeText(MainActivity.this, R.string.internet,
                    Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("gtu");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String abc = userData.getString("Branch");

                for (int j = 0; j < name.size(); j++) {
                    String n = name.get(j);
                    //Log.d("myTag",n);
                    if (abc.equals(n)) {
                        temp = 1;
                    }
                }

                if (temp == 0) {
                    name.add(userData.getString("Branch"));
                }
                temp = 0;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HelperAdapter helperAdapter = new HelperAdapter(name, null, img, totalp, 999, MainActivity.this);
        recyclerView.setAdapter(helperAdapter);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4744854543760031/7628545794");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        recyclerView.startAnimation(anim);
    }

    private String JsonDataFromAsset() {

        String json = null;

        try {
            InputStream inputStream = getAssets().open("users.json");
            int sizeOffile = inputStream.available();
            byte[] bufferData = new byte[sizeOffile];
            inputStream.read(bufferData);
            inputStream.close();

            json = new String(bufferData,"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.send_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send:{
                final String appPackageName = getPackageName(); // this is your playstore url id parameter
                try {
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                //Intent intent = new Intent(MainActivity2.this,MainActivity5.class);
                //startActivity(intent);
            break;
        }
            case R.id.review:{
                final String appPackageName = getPackageName(); // this is your playstore url id parameter
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}