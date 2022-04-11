package com.example.bartersystemapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList titleSearchResult,categorySearchResult,idSearchResult,descSearchResult,emailSearchResult,imageSearchResult;
    String titleExtra,categoryExtra,idExtra,descExtra,emailExtra,imageExtra;
    Context context;

    MyAdapter(Context context, ArrayList titleSearchResult,ArrayList categorySearchResult,ArrayList idSearchResult,ArrayList descSearchResult,ArrayList emailSearchResult,ArrayList imageSearchResult){
        this.context=context;
        this.titleSearchResult=titleSearchResult;
        this.categorySearchResult=categorySearchResult;
        this.idSearchResult=idSearchResult;
        this.descSearchResult=descSearchResult;
        this.emailSearchResult=emailSearchResult;
        this.imageSearchResult=imageSearchResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.titlehere.setText(String.valueOf(titleSearchResult.get(position)));
        holder.categoryhere.setText(String.valueOf(categorySearchResult.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ProductDescription.class);
                titleExtra= (String) titleSearchResult.get(position);
                categoryExtra=(String) categorySearchResult.get(position);
                idExtra=(String)idSearchResult.get(position);
                descExtra=(String)descSearchResult.get(position);
                emailExtra=(String)emailSearchResult.get(position);
                imageExtra=(String)imageSearchResult.get(position);
                intent.putExtra("descSearchResult",descExtra);
                intent.putExtra("emailSearchResult",emailExtra);
                intent.putExtra("imageSearchResult",imageExtra);
                intent.putExtra("idSearchResult",idExtra);
                intent.putExtra("titleSearchResult", titleExtra);
                intent.putExtra("categorySearchResult",categoryExtra);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleSearchResult.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titlehere,categoryhere;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlehere=itemView.findViewById(R.id.titleSearch);
            categoryhere=itemView.findViewById(R.id.categorySearch);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
