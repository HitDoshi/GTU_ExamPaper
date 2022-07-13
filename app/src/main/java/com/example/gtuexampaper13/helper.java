package com.example.gtuexampaper13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class helper extends RecyclerView.Adapter<helper.MyViewClass> {

    public ArrayList<String> sub=null, credit=null, code=null;
    public ArrayList<String> bname, semname;
    Activity context;
    int p, p1, p2,a;
    CardView cardView2;
    //private RewardedAd mRewardedAd;
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "Something";

    public helper(ArrayList<String> bname, ArrayList<String> semname, ArrayList<String> sub, int p1, int p2,
                  ArrayList<String> credit, ArrayList<String> code, Activity context) {
        this.bname = bname;
        this.semname = semname;
        this.sub = sub;
        this.context = context;
        this.credit = credit;
        this.code = code;
        this.p1 = p1;
        this.p2 = p2;

       /* MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadad();
*/

        /*MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        intad();*/
    }

    public class MyViewClass extends RecyclerView.ViewHolder {

        TextView n;
        TextView m;
        TextView l;
        TextView email;
        ImageView i;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            n = (TextView) itemView.findViewById(R.id.name);
            m = (TextView) itemView.findViewById(R.id.subtitle);
            //l = (TextView) itemView.findViewById(R.id.code);

            i = (ImageView) itemView.findViewById(R.id.icon);
            cardView2 = (CardView) itemView.findViewById(R.id.cardView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MobileAds.initialize(context, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {}
                    });

                    intad();

                   /* if (mRewardedAd != null) {
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

                        intent = new Intent(context, MainActivity4.class);
                        intent.putExtra("p1", p1);
                        intent.putExtra("p2", p2);
                        intent.putExtra("p3", getLayoutPosition());
                        intent.putStringArrayListExtra("bname", bname);
                        intent.putStringArrayListExtra("semname", semname);
                        intent.putStringArrayListExtra("sub", sub);

                        context.startActivity(intent);
                    }
                    else {
                        Intent intent;

                        intent = new Intent(context, MainActivity4.class);
                        intent.putExtra("p1", p1);
                        intent.putExtra("p2", p2);
                        intent.putExtra("p3", getLayoutPosition());
                        intent.putStringArrayListExtra("bname", bname);
                        intent.putStringArrayListExtra("semname", semname);
                        intent.putStringArrayListExtra("sub", sub);

                        context.startActivity(intent);

                    }*/

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(context);

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();

                                mInterstitialAd = null;

                                Intent intent;

                                intent = new Intent(context, MainActivity4.class);
                                intent.putExtra("p1", p1);
                                intent.putExtra("p2", p2);
                                intent.putExtra("p3", getLayoutPosition());
                                intent.putStringArrayListExtra("bname", bname);
                                intent.putStringArrayListExtra("semname", semname);
                                intent.putStringArrayListExtra("sub", sub);

                                context.startActivity(intent);
                            }
                        });
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");

                        Intent intent;

                        intent = new Intent(context, MainActivity4.class);
                        intent.putExtra("p1", p1);
                        intent.putExtra("p2", p2);
                        intent.putExtra("p3", getLayoutPosition());
                        intent.putStringArrayListExtra("bname", bname);
                        intent.putStringArrayListExtra("semname", semname);
                        intent.putStringArrayListExtra("sub", sub);

                        context.startActivity(intent);
                    }
                    /*Intent intent;

                    intent = new Intent(context, MainActivity4.class);
                    intent.putExtra("p1", p1);
                    intent.putExtra("p2", p2);
                    intent.putExtra("p3", getLayoutPosition());
                    intent.putStringArrayListExtra("bname", bname);
                    intent.putStringArrayListExtra("semname", semname);
                    intent.putStringArrayListExtra("sub", sub);

                    context.startActivity(intent);*/
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub, parent, false);
        MyViewClass myViewClass = new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewClass holder, int position) {
        holder.n.setText(sub.get(position));
        holder.m.setText("Credit :- " + credit.get(position));
        //holder.l.setText("Code :- " + code.get(position));


    /*    if(position%2!=0)
            cardView2.setBackgroundColor(Color.parseColor("#C0C0C0"));
        else
            cardView2.setBackgroundColor(Color.parseColor("#D3D3D3"));

*/
    }

    @Override
    public int getItemCount() {
        return sub.size();
    }

    void intad()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,"ca-app-pub-4744854543760031/5723920263", adRequest,
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

   /* void loadad() {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
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
    }*/
}
