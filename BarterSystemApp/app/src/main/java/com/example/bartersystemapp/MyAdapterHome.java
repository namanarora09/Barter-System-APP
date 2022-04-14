package com.example.bartersystemapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterHome extends RecyclerView.Adapter<MyAdapterHome.MyViewHolderHome> {

    ArrayList idHome,titleHome,OfferedHome,emailHome;
    public static String idLast,titleLast,OfferedLast,emailLast;
    Context context;

    MyAdapterHome(Context context, ArrayList idHome,ArrayList titleHome,ArrayList emailHome,ArrayList OfferedHome)
    {
        this.context=context;
        this.idHome=idHome;
        this.titleHome=titleHome;
        this.OfferedHome=OfferedHome;
        this.emailHome=emailHome;
    }

    @NonNull
    @Override
    public MyViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row_homepage,parent,false);
        return new MyViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterHome.MyViewHolderHome holder, int position) {
        holder.titleHome.setText(String.valueOf(titleHome.get(position)));
        holder.emailHome.setText(String.valueOf(emailHome.get(position)));

        holder.mainLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DecisionPage.class);
                idLast=(String) idHome.get(position);
                titleLast=(String) titleHome.get(position);
                emailLast=(String) emailHome.get(position);
                OfferedLast=(String) OfferedHome.get(position);
                intent.putExtra("idHome",idLast);
                intent.putExtra("titleHome",titleLast);
                intent.putExtra("emailHome",emailLast);
                intent.putExtra("OfferedHome",OfferedLast);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleHome.size();
    }

    public class MyViewHolderHome extends RecyclerView.ViewHolder {
        TextView titleHome,emailHome;
        ConstraintLayout mainLayout2;
        public MyViewHolderHome(@NonNull View itemView) {
            super(itemView);
            titleHome=itemView.findViewById(R.id.titleHome);
            emailHome=itemView.findViewById(R.id.emailHome);
            mainLayout2=itemView.findViewById(R.id.mainLayout2);
        }
    }
}
