package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class HomePage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    boolean DoublePressToExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView menuImage=findViewById(R.id.menu);
        Button sell=findViewById(R.id.sell);
        SearchView mySearhView=(SearchView)findViewById(R.id.search);

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
            case R.id.details:
                return true;
            case R.id.about:
                return true;
            case R.id.logout:
                Intent LogOut=new Intent(HomePage.this,LoginPage.class);
                startActivity(LogOut);
            case R.id.quit:
                finishAffinity();
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