package com.example.bartersystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button signup=findViewById(R.id.signUp);
        Button signin=findViewById(R.id.signIn);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginPage.this,SignUpPage.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(LoginPage.this,HomePage.class);
                 String emailId=email.getText().toString();
                    String pass=password.getText().toString();
                    if (emailId.equals("barter@gmail.com")) {
                        if(pass.equals("barter")) {
                            startActivity(i1);
                        }

                    }
                    else
                    {
                        Toast.makeText(LoginPage.this, "Invalid email ID or password", Toast.LENGTH_SHORT).show();;
                    }
                }
        });
    }
}