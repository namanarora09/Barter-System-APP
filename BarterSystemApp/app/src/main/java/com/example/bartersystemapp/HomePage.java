package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    boolean DoublePressToExit=false;
    DatabaseManager db;
    ArrayList<String> titleHome,OfferedHome,idHome,emailHome;
    MyAdapterHome myAdapterHome;
    RecyclerView recyclerHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView menuImage=findViewById(R.id.menu);
        Button sell=findViewById(R.id.sell);
        SearchView mySearhView=(SearchView)findViewById(R.id.search);
        recyclerHome=findViewById(R.id.RecycleHome);
        db=new DatabaseManager(this);
        titleHome=new ArrayList<>();
        OfferedHome=new ArrayList<>();
        idHome=new ArrayList<>();
        emailHome=new ArrayList<>();

        storedataHome();
        mySearhView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent searchHome=new Intent(HomePage.this,SearchResult.class);
                startActivity(searchHome);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sellpage=new Intent(HomePage.this,SellPage.class);
                startActivity(sellpage);
            }
        });
        myAdapterHome=new MyAdapterHome(HomePage.this,idHome,titleHome,emailHome,OfferedHome);
        recyclerHome.setAdapter(myAdapterHome);
        recyclerHome.setLayoutManager(new LinearLayoutManager(this));
    }

    void storedataHome(){
        Cursor cursor=db.readOffers();
        if(cursor.getCount()!=0)
        {
            while(cursor.moveToNext())
            {
                titleHome.add(cursor.getString(1));
                OfferedHome.add(cursor.getString(4));
                idHome.add(cursor.getString(0));
                emailHome.add(cursor.getString(3));
            }
        }
    }

    public void showPopup(View v)
    {
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.about:
                Intent About=new Intent(HomePage.this,AboutPage.class);
                startActivity(About);
                break;
            case R.id.logout:
                Intent LogOut=new Intent(HomePage.this,LoginPage.class);
                startActivity(LogOut);
                break;
            case R.id.quit:
                finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if(DoublePressToExit)
        {
            finishAffinity();
        }
        else{
            DoublePressToExit=true;
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
        }


        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DoublePressToExit=false;
            }
        },2000);
    }
}