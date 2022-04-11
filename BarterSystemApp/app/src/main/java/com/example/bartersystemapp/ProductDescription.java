package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductDescription extends AppCompatActivity {

    StorageReference mStorageReference;
    String titleFinal,idFinal,descFinal,emailFinal,imageFinal;
    ImageView mainImageView;
    TextView title,description;
    Button makeOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        mainImageView=findViewById(R.id.ProductImage);
        title=findViewById(R.id.titleProduct);
        description=findViewById(R.id.descriptionProduct);
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
        Toast.makeText(this, "Loading image...", Toast.LENGTH_SHORT).show();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        StorageReference islandRef=mStorageReference.child(imageFinal);
        final long ONE_MEGABYTE=1024*1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                DisplayMetrics dm=new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                mainImageView.setMinimumHeight(dm.heightPixels);
                mainImageView.setMinimumWidth(dm.widthPixels);
                mainImageView.setImageBitmap(bm);
            }
        });

        makeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent offer=new Intent(ProductDescription.this,MakeOffer.class);
                startActivity(offer);

            }
        });
    }
    private void getData(){
        if(getIntent().hasExtra("titleSearchResult") && getIntent().hasExtra("categorySearchResult")){
            titleFinal=getIntent().getStringExtra("titleSearchResult");
            idFinal=getIntent().getStringExtra("idSearchResult");
            descFinal=getIntent().getStringExtra("descSearchResult");
            emailFinal=getIntent().getStringExtra("emailSearchResult");
            imageFinal=getIntent().getStringExtra("imageSearchResult");
        }
        else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){

        title.setText(titleFinal);
        description.setText(descFinal);
    }


}