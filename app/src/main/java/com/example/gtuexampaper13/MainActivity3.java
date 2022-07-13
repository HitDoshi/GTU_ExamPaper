package com.example.gtuexampaper13;

import static com.google.android.gms.ads.AdSize.BANNER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
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

public class MainActivity3 extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> sub = new ArrayList<>() ;
    ArrayList<String> credit = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    int temp=0,p1,p2;
    ArrayList<String> semname = new ArrayList<>();
    ArrayList<String> bname = new ArrayList<>();
    TemplateView template;
    private boolean adLoaded=false;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView3);

        p1=getIntent().getIntExtra("p1",0);
        p2=getIntent().getIntExtra("p2",0);
        semname=getIntent().getStringArrayListExtra("sem_list");
        bname=getIntent().getStringArrayListExtra("b_list");

        String s = semname.get(p2);
        String b = bname.get(p1);

        setTitle("Semester :- " + s);
        //Log.d("myTag", b);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("gtu");

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String abc1 = userData.getString("Branch");
                String abc2 = userData.getString("Sem");
                String abcsub = userData.getString("Subject full name");

                if (b.equals(abc1) && s.equals(abc2)) {
                    for (int j = 0; j < sub.size(); j++) {

                        String n = sub.get(j);
                        // Log.d("myTag",n);

                        if (abcsub.equals(n)) {
                            temp = 1;
                        }
                    }

                    if (temp == 0) {
                        sub.add(userData.getString("Subject full name"));
                        credit.add(userData.getString("Credits"));
                        code.add(userData.getString("code"));
                    }

                    temp = 0;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        helper helperAdapter = new helper(bname,semname,sub,p1,p2,credit,code,MainActivity3.this);
        recyclerView.setAdapter(helperAdapter);

        AdView adView = new AdView(this);

        adView.setAdSize(BANNER);

        adView.setAdUnitId("ca-app-pub-4744854543760031/1379859884");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView2);
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

        // Initializing the Google Admob SDK
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //Toast.makeText(MainActivity3.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    ColorDrawable background;
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
                        TemplateView template = findViewById(R.id.nativeTemplateView);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        if (adLoaded) {
            template.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation( MainActivity3.this, R.anim.down_to_up);
        recyclerView.startAnimation(animation);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
}