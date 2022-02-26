package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SearchResult extends AppCompatActivity {


    String s1[];
    String s2[];
    int images[]={R.drawable.transparencya,R.drawable.transparencyb,R.drawable.transparencyc,R.drawable.transparencyd,R.drawable.transparencye, R.drawable.transparencyf,R.drawable.transparencyg,R.drawable.transparencyh,R.drawable.transparencyi,R.drawable.transparencyj};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        RecyclerView recyclerView=findViewById(R.id.recycleView);
        ImageView backSearchResult=findViewById(R.id.backSearchResult);
        backSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backResult=new Intent(SearchResult.this,HomePage.class);
                startActivity(backResult);
            }
        });

        s1=getResources().getStringArray(R.array.Titles);
        s2=getResources().getStringArray(R.array.description);

        MyAdapter myAdapter=new MyAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}