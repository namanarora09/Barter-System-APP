package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity {

    private DatabaseManager db;
    Boolean var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Button accountCreation=findViewById(R.id.CreateAccount);
        TextView name=findViewById(R.id.Name);
        TextView pass=findViewById(R.id.passwordSetup);
        TextView mobile=findViewById(R.id.Phone);
        TextView email=findViewById(R.id.EmailAddress);
        TextView zip=findViewById(R.id.Zip);
        db=new DatabaseManager(this );

        accountCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String Password=pass.getText().toString();
                String Mobile=mobile.getText().toString();
                String Email=email.getText().toString();
                String Zip=zip.getText().toString();
                Intent create=new Intent(SignUpPage.this,LoginPage.class);
                if(Name==null || Password==null || Mobile==null || Email==null || Zip==null)
                    Toast.makeText(SignUpPage.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                else
                {
                    var = db.insertLogin(Name, Mobile, Email, Zip, Password);
                    if(var){
                        Toast.makeText(SignUpPage.this, "Account created", Toast.LENGTH_SHORT).show();
                        startActivity(create);
                    }
                    else
                        Toast.makeText(SignUpPage.this, "Error creating Account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView backsignup=findViewById(R.id.backSignUp);
        backsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpPage.this,LoginPage.class);
                startActivity(i);
            }
        });
    }
}