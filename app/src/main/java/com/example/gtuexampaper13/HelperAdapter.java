package com.example.gtuexampaper13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.MyViewClass> {

    public  ArrayList<String> na;
    Context context;
    Integer[] img;
    int p;
    public ArrayList<String> na2;
    CardView cardView;
    private int lastPosition = -1;
    String[] totalp;


    public HelperAdapter(ArrayList<String> na , ArrayList<String> na2 , Integer[] img ,String[] totalp , int p, Context context)
    {
        this.na=na;
        this.img=img;
        this.context=context;
        this.p=p;
        this.na2=na2;
        this.totalp = totalp;
    }

    public class MyViewClass extends RecyclerView.ViewHolder{

        TextView name,totalpaper;
        TextView email;
        ImageView i;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            i = (ImageView) itemView.findViewById(R.id.icon);
            totalpaper = (TextView) itemView.findViewById(R.id.subtitle);
            cardView=(CardView)itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    if(p==999) {
                        intent = new Intent(context, MainActivity2.class);
                        intent.putExtra("p1", getLayoutPosition());
                        intent.putStringArrayListExtra("branch_list", na);
                    }
                    else
                    {
                        intent = new Intent(context, MainActivity3.class);
                        intent.putStringArrayListExtra("sem_list", na);
                        intent.putExtra("p1", p);
                        intent.putExtra("p2", getLayoutPosition());
                        intent.putStringArrayListExtra("b_list", na2);
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public HelperAdapter.MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        MyViewClass myViewClass = new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull HelperAdapter.MyViewClass holder, int position) {
        holder.name.setText(na.get(position));
        holder.totalpaper.setText("Total Paper :- " + totalp[position]);
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
}
