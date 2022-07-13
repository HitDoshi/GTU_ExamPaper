package com.example.gtuexampaper13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class helper4 extends RecyclerView.Adapter<helper4.MyViewClass> {

    public ArrayList<String> name,paper,pcode;
    Activity context;
    int p;
    CardView cardView3;
    private InterstitialAd mInterstitialAd;

    public helper4(ArrayList<String> name,ArrayList<String> paper ,ArrayList<String> pcode, Activity context)
    {
        this.name=name;
        this.context=context;
        this.paper = paper;
        this.pcode = pcode;

    }

    public class MyViewClass extends RecyclerView.ViewHolder{

        TextView n,m;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            n = (TextView) itemView.findViewById(R.id.name);
            cardView3 = (CardView) itemView.findViewById(R.id.cardView3);
            m = (TextView) itemView.findViewById(R.id.p);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MobileAds.initialize(context, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {}
                    });

                    intad();

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(context);

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();

                                mInterstitialAd = null;

                                Intent intent;

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paper.get(getLayoutPosition())));
                                context.startActivity(browserIntent);
                            }
                        });
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");

                        Intent intent;

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paper.get(getLayoutPosition())));
                        context.startActivity(browserIntent);
                    }

                       /* Intent intent;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paper.get(getLayoutPosition())));
                        context.startActivity(browserIntent);*/

                    /*intent = new Intent(context, MainActivity5.class);
                    intent.putExtra("p4",getLayoutPosition());
                    intent.putStringArrayListExtra("paper",paper);
                    context.startActivity(intent);*/
                    }
            });
        }
    }
    @NonNull
    @Override
    public MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hel4,parent,false);
        MyViewClass myViewClass = new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewClass holder, int position) {
        holder.n.setText(name.get(position));
        holder.m.setText("Code :- "+pcode.get(position));

        String ws = name.get(position);

        char a = ws.charAt(0);
        int p = position;

        cardView3.setBackgroundResource(R.drawable.gradient_list);
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    void intad()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,"ca-app-pub-4744854543760031/7677674928", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
                });
    }
}

