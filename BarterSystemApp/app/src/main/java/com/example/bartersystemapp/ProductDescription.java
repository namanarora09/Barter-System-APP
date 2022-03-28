package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDescription extends AppCompatActivity {

    String data1,data2;
    int myImage;
    ImageView mainImageView;
    TextView title,description;
    Button makeOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        mainImageView=findViewById(R.id.ProductImage);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        makeOffer=findViewById(R.id.offer);
        ImageView backproductdetails=findViewById(R.id.backProductDetails);
        backproductdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(ProductDescription.this,SearchResult.class);
                startActivity(back);
            }
        });

        getData();
        setData();

        makeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent offer=new Intent(ProductDescription.this,MakeOffer.class);
                startActivity(offer);

            }
        });
    }
    private void getData(){
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){
            data1=getIntent().getStringExtra("data1");
            data2=getIntent().getStringExtra("data2");
            myImage=getIntent().getIntExtra("myImage",1);

        }
        else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        title.setText(data1);
        description.setText(data2);
        mainImageView.setImageResource(myImage);
    }


}