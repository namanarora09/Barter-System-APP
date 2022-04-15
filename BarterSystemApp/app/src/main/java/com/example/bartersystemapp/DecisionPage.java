package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DecisionPage extends AppCompatActivity {

    String titledecision,offerdecision,iddecision,emaildecision;
    ImageView acceptoffer,rejectoffer,backoffer;
    TextView titleoffer,offeroffer,emailoffer;
    int idInteger;
    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_page);
        acceptoffer=findViewById(R.id.acceptdecision);
        rejectoffer=findViewById(R.id.rejectdecision);
        titleoffer=findViewById(R.id.titleDecision);
        offeroffer=findViewById(R.id.offerdecision);
        backoffer=findViewById(R.id.backDecision);
        emailoffer=findViewById(R.id.emailDecision);
        db=new DatabaseManager(this);

        backoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backOffer=new Intent(DecisionPage.this,HomePage.class);
                startActivity(backOffer);
            }
        });

        getDataOffer();
        setDataOffer();

        Intent barter=new Intent(DecisionPage.this,HomePage.class);

        acceptoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAds(idInteger);
                db.deleteOffers(iddecision,emaildecision);
                startActivity(barter);
            }
        });

        rejectoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteOffers(iddecision,emaildecision);
                startActivity(barter);
            }
        });
    }

    private void getDataOffer(){
        if(getIntent().hasExtra("titleHome")){
            titledecision=getIntent().getStringExtra("titleHome");
            iddecision=getIntent().getStringExtra("idHome");
            emaildecision=getIntent().getStringExtra("emailHome");
            offerdecision=getIntent().getStringExtra("OfferedHome");
            idInteger=Integer.parseInt(iddecision);
        }
    }

    private void setDataOffer(){
        titleoffer.setText(titledecision);
        offeroffer.setText(offerdecision);
        emailoffer.setText(emaildecision);
    }
}