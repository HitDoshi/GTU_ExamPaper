package com.example.gtuexampaper13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class HelperAdapter2 extends RecyclerView.Adapter<HelperAdapter2.MyViewClass> {

    public ArrayList<String> na;
    Activity context;
    Integer[] img;
    int p;
    public ArrayList<String> na2;
    public ArrayList<String> totals;
    CardView cardView;
    private int lastPosition = -1;
    //private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;

    public HelperAdapter2(ArrayList<String> na, ArrayList<String> na2, Integer[] img, ArrayList<String> totals, int p, Activity context) {
        this.na = na;
        this.img = img;
        this.context = context;
        this.p = p;
        this.na2 = na2;
        this.totals = totals;

        Log.d("Hi","not go");

         MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadad();

    }

    public class MyViewClass extends RecyclerView.ViewHolder{

        TextView name, totalpaper;
        TextView email;
        CircleImageView i;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            i = (CircleImageView) itemView.findViewById(R.id.icon);
            totalpaper = (TextView) itemView.findViewById(R.id.subtitle);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

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

                        Intent intent;
                        if (p == 999) {
                            intent = new Intent(context, MainActivity2.class);
                            intent.putExtra("p1", getLayoutPosition());
                            intent.putStringArrayListExtra("branch_list", na);
                        } else {
                            intent = new Intent(context, MainActivity3.class);
                            intent.putStringArrayListExtra("sem_list", na);
                            intent.putExtra("p1", p);
                            intent.putExtra("p2", getLayoutPosition());
                            intent.putStringArrayListExtra("b_list", na2);
                        }
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent;
                        if (p == 999) {
                            intent = new Intent(context, MainActivity2.class);
                            intent.putExtra("p1", getLayoutPosition());
                            intent.putStringArrayListExtra("branch_list", na);
                        } else {
                            intent = new Intent(context, MainActivity3.class);
                            intent.putStringArrayListExtra("sem_list", na);
                            intent.putExtra("p1", p);
                            intent.putExtra("p2", getLayoutPosition());
                            intent.putStringArrayListExtra("b_list", na2);
                        }
                        context.startActivity(intent);
                    }

                    /*MobileAds.initialize(context, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
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
                                if (p == 999) {
                                    intent = new Intent(context, MainActivity2.class);
                                    intent.putExtra("p1", getLayoutPosition());
                                    intent.putStringArrayListExtra("branch_list", na);
                                } else {
                                    intent = new Intent(context, MainActivity3.class);
                                    intent.putStringArrayListExtra("sem_list", na);
                                    intent.putExtra("p1", p);
                                    intent.putExtra("p2", getLayoutPosition());
                                    intent.putStringArrayListExtra("b_list", na2);
                                }
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");

                        Intent intent;
                        if (p == 999) {
                            intent = new Intent(context, MainActivity2.class);
                            intent.putExtra("p1", getLayoutPosition());
                            intent.putStringArrayListExtra("branch_list", na);
                        } else {
                            intent = new Intent(context, MainActivity3.class);
                            intent.putStringArrayListExtra("sem_list", na);
                            intent.putExtra("p1", p);
                            intent.putExtra("p2", getLayoutPosition());
                            intent.putStringArrayListExtra("b_list", na2);
                        }
                        context.startActivity(intent);
                    }*/
                }
            });
        }
    }

    @NonNull
    @Override
    public HelperAdapter2.MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
        MyViewClass myViewClass = new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull HelperAdapter2.MyViewClass holder, int position) {
        holder.name.setText("Semester :- " + na.get(position));
        holder.totalpaper.setText("Total Subject :- " + totals.get(position));
        holder.i.setImageResource(img[position]);
        /*if(position%2!=0)
            cardView.setBackgroundColor(Color.parseColor("#C0C0C0"));
        else
            cardView.setBackgroundColor(Color.parseColor("#D3D3D3"));
*/
    }

    @Override
    public int getItemCount() {
        return na.size();
    }

    /*void intad()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
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
    }*/

     void loadad() {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, "ca-app-pub-4744854543760031/4574490124",
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
