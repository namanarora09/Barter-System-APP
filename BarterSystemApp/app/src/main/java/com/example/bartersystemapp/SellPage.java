package com.example.bartersystemapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SellPage extends AppCompatActivity {

    int Id;
    Uri imageUri;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    String name;
    ProgressBar progressbar;
    StorageTask muploadTask;

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
        ImageView backSell=findViewById(R.id.backSell);
        progressbar=findViewById(R.id.ProgressBar);
        mStorageRef= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

        backSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToHome=new Intent(SellPage.this,HomePage.class);
                startActivity(backToHome);
            }
        });

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
                while(true) {
                    if (adapterView.getItemAtPosition(i).equals("Select")) {
                        Toast.makeText(SellPage.this, "Select a category", Toast.LENGTH_SHORT).show();
                    } else {
                        String item = adapterView.getItemAtPosition(i).toString();
                        break;
                    }
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
                openFileChooser();
                if(muploadTask!=null && muploadTask.isInProgress())
                {
                    Toast.makeText(SellPage.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openFileChooser();
                }

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
    public void openFileChooser()
    {
        Intent upload=new Intent();
        upload.setType("image/*");
        upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(upload,SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_IMAGE_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri=data.getData();

        }
    }

    //geting the file extention
    public String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void uploadFile()
    {
        if(imageUri != null)
        {
            Random random=new Random();
            int val= random.nextInt(1000);
            name=Integer.toString(val)+"."+getFileExtension(imageUri);
            StorageReference fileReference=mStorageRef.child(name);
            muploadTask=fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setProgress(0);
                        }
                    },500);

                    Toast.makeText(SellPage.this,"Upload successful",Toast.LENGTH_LONG).show();
                    ImageUpload upload=new ImageUpload(name,taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    String uploadId=mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SellPage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0+taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressbar.setProgress((int) progress);
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}