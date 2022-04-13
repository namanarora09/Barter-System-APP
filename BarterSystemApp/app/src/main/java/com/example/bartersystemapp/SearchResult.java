package com.example.bartersystemapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {

    DatabaseManager DB;
    ArrayList<String> titleSearchResult,categorySearchResult,idSearchResult,descSearchResult,emailSearchResult,imageSearchResult;
    MyAdapter myAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=findViewById(R.id.recycleView);
        ImageView backSearchResult=findViewById(R.id.backSearchResult);
        DB=new DatabaseManager(this);
        titleSearchResult=new ArrayList<>();
        categorySearchResult=new ArrayList<>();
        idSearchResult=new ArrayList<>();
        descSearchResult=new ArrayList<>();
        emailSearchResult=new ArrayList<>();
        imageSearchResult=new ArrayList<>();

        storealldata();
        backSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backResult=new Intent(SearchResult.this,HomePage.class);
                startActivity(backResult);
            }
        });
        myAdapter=new MyAdapter(SearchResult.this,titleSearchResult,categorySearchResult, idSearchResult,descSearchResult,emailSearchResult,imageSearchResult);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void storealldata(){
        Cursor cursor=DB.readAds();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                idSearchResult.add(cursor.getString(0));
                titleSearchResult.add(cursor.getString(2));
                categorySearchResult.add(cursor.getString(3));
                descSearchResult.add(cursor.getString(4));
                imageSearchResult.add(cursor.getString(5));
                emailSearchResult.add(cursor.getString(1));
            }
        }
    }
}