package com.example.gtuexampaper13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;


import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    RecyclerView recyclerView,recyclerView42;
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> paper = new ArrayList<>();
    ArrayList<String> syllabus = new ArrayList<>();
    ArrayList<String> sycode = new ArrayList<>();
    ArrayList<String> pcode = new ArrayList<>();
    int temp=0,p1,p2,p3,ptr=0;
    String branch,sem,s,syllabus1;
    ArrayList<String> xyz = new ArrayList<>();
    ArrayList<String> sub = new ArrayList<>();
    ArrayList<String> pqr= new ArrayList<>();

    private AdLoader adLoader ;

    // simple boolean to check the status of ad
    private boolean adLoaded=false;

    //creating Template View object
    TemplateView template;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        if ( !(connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected() ) )
        {
            Toast.makeText(MainActivity4.this, R.string.internet,
                    Toast.LENGTH_LONG).show();
        }

        // Initializing the Google Admob SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //Toast.makeText(MainActivity4.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView42  = findViewById(R.id.recyclerView42);

        p1=getIntent().getIntExtra("p1",0);
        p2=getIntent().getIntExtra("p2",0);
        p3=getIntent().getIntExtra("p3",0);
        xyz=getIntent().getStringArrayListExtra("bname");
        pqr=getIntent().getStringArrayListExtra("semname");
        sub=getIntent().getStringArrayListExtra("sub");
        s=sub.get(p3);
        sem=pqr.get(p2);
        branch=xyz.get(p1);

        setTitle(s);
        Log.d("myTag",branch);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager42 = new LinearLayoutManager(getApplicationContext());
        recyclerView42.setLayoutManager(linearLayoutManager42);

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("gtu");

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String abc1 = userData.getString("Branch");
                String abc2 = userData.getString("Sem");
                String abcsub = userData.getString("Subject full name");
                String abcyear = userData.getString("year");

                if (branch.equals(abc1) && sem.equals(abc2) && s.equals(abcsub) ) {

                   /* for (int j = 0; j < year.size(); j++) {
                        String n = year.get(j);
                        // Log.d("myTag",n);

                        if (abcyear.equals(n)) {
                            temp = 1;
                        }
                    }*/

                    if (temp == 0 && !(userData.getString("year").equals("***"))) {
                        year.add(userData.getString("year"));
                        paper.add(userData.getString("url"));
                        pcode.add(userData.getString("papercode"));
                    }

                    if(temp==0 &&ptr!=1) {
                        syllabus.add(userData.getString("Syallbus"));
                        sycode.add(userData.getString("code"));
                        ptr = 1;
                    }

                    temp = 0;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ptr=0;
        helper4 helperAdapter = new helper4(year,paper,pcode,MainActivity4.this);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(helperAdapter);


        helper42 helperAdapter42 = new helper42(syllabus,sycode,this);

        GridLayoutManager layoutManager42=new GridLayoutManager(this,1);
        recyclerView42.setLayoutManager(layoutManager42);

        recyclerView42.setAdapter(helperAdapter42);

        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-4744854543760031/2170853918")
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
        }
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
    protected void onRestart() {
        super.onRestart();
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        recyclerView.startAnimation(anim);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        recyclerView42.startAnimation(rotate);

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
}