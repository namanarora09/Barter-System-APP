package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MakeOffer extends AppCompatActivity {

    ImageView backOffer,submitOffer,cancelOffer;
    EditText offerDescription;
    String ProductOffered;
    DatabaseManager db;
    Boolean var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);

        backOffer=findViewById(R.id.backOffer);
        submitOffer=findViewById(R.id.submitOffer);
        cancelOffer=findViewById(R.id.cancelOffer);
        offerDescription=findViewById(R.id.offerDeacription);
        db=new DatabaseManager(this);

        backOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backoffer=new Intent(MakeOffer.this,ProductDescription.class);
                startActivity(backoffer);
            }
        });

        submitOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductOffered=offerDescription.getText().toString();
                Intent submit=new Intent(MakeOffer.this,HomePage.class);
                if (ProductOffered==null)
                    Toast.makeText(MakeOffer.this, "No description given", Toast.LENGTH_SHORT).show();
                else
                {
                    var=db.insertOffer(MyAdapter.idExtra,MyAdapter.titleExtra,MyAdapter.emailExtra,LoginPage.emailId,ProductOffered);
                    if(var)
                    {
                        Toast.makeText(MakeOffer.this, "Offer Sent", Toast.LENGTH_SHORT).show();
                        startActivity(submit);
                    }
                    else
                        Toast.makeText(MakeOffer.this, "Failed to offer", Toast.LENGTH_SHORT).show();

                }
            }
        });

        cancelOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelOffer=new Intent(MakeOffer.this,ProductDescription.class);
                startActivity(cancelOffer);
            }
        });
    }
}