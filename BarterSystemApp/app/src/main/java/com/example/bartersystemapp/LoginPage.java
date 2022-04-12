package com.example.bartersystemapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    public static String emailId;
    ActivityResultLauncher<Intent> mGetPermission;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button signup=findViewById(R.id.signUp);
        Button signin=findViewById(R.id.signIn);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        db=new DatabaseManager(this);
        mGetPermission=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==LoginPage.RESULT_OK)
                {
                    Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginPage.this, SignUpPage.class);
                if(isPermissionGranted())
                    startActivity(i);
                else
                    takePermissions();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(LoginPage.this,HomePage.class);
                emailId=email.getText().toString();
                String pass=password.getText().toString();
                if(isPermissionGranted()){
                    if(emailId==null || pass==null)
                    {
                        Toast.makeText(LoginPage.this, "Null values encountered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Boolean var=db.checkuser(emailId,pass);
                        if(var)
                            startActivity(i1);
                        else
                            Toast.makeText(LoginPage.this, "Invalid email ID or password", Toast.LENGTH_SHORT).show();
                    }}
                else
                    takePermissions();
            }
        });

    }

    public void takePermissions(){
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.R){
            try {
                Intent permission=new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                permission.addCategory("android.intent.category.DEFAULT");
                mGetPermission.launch(permission);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
        }
    }

    public boolean isPermissionGranted(){
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else
        {
            int readExternalStoragePermission=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return readExternalStoragePermission==PackageManager.PERMISSION_GRANTED;
        }
    }
}