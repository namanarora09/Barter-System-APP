package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SellPage extends AppCompatActivity {

    int SELECT_IMAGE_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        TextView sellTitle=findViewById(R.id.sellTitle);
        Spinner category=findViewById(R.id.category);
        TextView description=findViewById(R.id.description);
        ImageButton uploadImage=findViewById(R.id.uploadImage);
        Button cancelSell=findViewById(R.id.cancelSell);
        Button addButton=findViewById(R.id.addButton);

        List<String> categories=new ArrayList<>();
        categories.add(0,"Select");
        categories.add("Electronics");
        categories.add("Kid's wear");
        categories.add("Men's wear");
        categories.add("Ladies' wear");
        categories.add("Home");
        categories.add("Grocery");
        categories.add("Beauty");
        categories.add("Others");

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select"))
                {

                }
                else {
                    String item=adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upload=new Intent();
                upload.setType("image/*");
                upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(upload,"Title"),SELECT_IMAGE_CODE);
            }
        });

        cancelSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel=new Intent(SellPage.this,HomePage.class);
                startActivity(cancel);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add=new Intent(SellPage.this,HomePage.class);
                startActivity(add);
            }
        });

        String title=sellTitle.getText().toString();
        String desc=description.getText().toString();
    }
}