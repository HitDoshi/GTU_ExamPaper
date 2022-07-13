package com.example.gtuexampaper13;

import static com.google.android.gms.ads.AdSize.BANNER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.android.gms.ads.AdView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    MainActivity m1 = new MainActivity();
    RecyclerView recyclerView;
    public ArrayList<String> sem = new ArrayList<>();
    public ArrayList<String> totalsub = new ArrayList<>();
    int temp = 0, p1;
    public ArrayList<String> bname = new ArrayList<>();
    Integer[] i = {R.drawable.one, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight};
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView2);

        bname = getIntent().getStringArrayListExtra("branch_list");
        p1 = getIntent().getIntExtra("p1", 0);
        int b = p1;

        String a = bname.get(b);

        setTitle(a);

        //Log.d("myTag",m1.name.get(p1));
        Log.d("mytag", String.valueOf(p1));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("gtu");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String abc1 = userData.getString("Branch");
                String abc = userData.getString("Sem");

                if (a.equals(abc1)) {
                    for (int j = 0; j < sem.size(); j++) {
                        String n = sem.get(j);
                        // Log.d("myTag",n);
                        if (abc.equals(n)) {
                            temp = 1;
                        }
                    }

                    if (temp == 0) {
                        sem.add(userData.getString("Sem"));
                        totalsub.add(userData.getString("totalsub"));
                    }
                    temp = 0;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HelperAdapter2 helperAdapter = new HelperAdapter2(sem, bname, i, totalsub, p1, MainActivity2.this);
        recyclerView.setAdapter(helperAdapter);


        /*AdView adView = new AdView(this);

        adView.setAdSize(BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

       /* manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = manager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener(task -> {

            if(task.isSuccessful())
            {
                reviewInfo = task.getResult();
            }
            else
            {
                Toast.makeText(this,"Rating unsuccessfull",Toast.LENGTH_LONG).show();
            }

        });

        if(reviewInfo!=null)
        {
            Task<Void> flow = manager.launchReviewFlow(this,reviewInfo);
            flow.addOnCompleteListener(task -> {
                Toast.makeText(this,"Rating is completed",Toast.LENGTH_LONG).show();
            });
        }
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation( MainActivity2.this, R.anim.up_to_down);
        recyclerView.startAnimation(animation);*/

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation( MainActivity2.this, R.anim.up_to_down);
        recyclerView.startAnimation(animation);
    }

    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }
}