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

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;

public class helper42 extends RecyclerView.Adapter<helper42.MyViewClass> {

    public ArrayList<String> syllabus,sycode;
    Activity context;
    int p;
    CardView cardView4;
    private RewardedAd mRewardedAd;

    public helper42(ArrayList<String> syllabus,ArrayList<String>sycode ,Activity context)
    {
        this.syllabus=syllabus;
        this.context=context;
        this.sycode = sycode;

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadad();

    }



    public class MyViewClass extends RecyclerView.ViewHolder{

        TextView n,m;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            n = (TextView) itemView.findViewById(R.id.name);
            cardView4 = (CardView) itemView.findViewById(R.id.cardView4);
            m = (TextView) itemView.findViewById(R.id.s);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent;
                    /*intent = new Intent(context, MainActivity5.class);
                    intent.putExtra("p4",getLayoutPosition());
                    intent.putStringArrayListExtra("paper",paper);
                    context.startActivity(intent);*/

                    if (mRewardedAd != null) {
                        Context activityContext = context;
                        mRewardedAd.show(context, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                // Handle the reward.
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();
                            }
                        });

                        mRewardedAd = null;

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(syllabus.get(getLayoutPosition())));
                        context.startActivity(browserIntent);
                    }
                    else
                    {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(syllabus.get(getLayoutPosition())));
                        context.startActivity(browserIntent);
                    }

                   /*Uri uri = Uri.parse("http://docs.google.com/viewer?url=" + syllabus.get(getLayoutPosition()));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri,"text/html");
                    context.startActivity(intent);*/
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hel42,parent,false);
        MyViewClass myViewClass = new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewClass holder, int position) {
        holder.n.setText("Syllabus");
        holder.m.setText("Code :- "+ sycode.get(position));

        cardView4.setBackgroundResource(R.drawable.gradient_list);
    }

    @Override
    public int getItemCount() {
        return syllabus.size();
    }

    void loadad() {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, "ca-app-pub-4744854543760031/9433609729",
                adRequest, new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("RewaededAD:-", "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d("RewaededAD:-", "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("RewaededAD:-", "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                    }
                });
    }
}
