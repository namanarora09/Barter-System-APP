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
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
public class SellPage extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST=1;
    String item,title,desc,imageName;
    int Id;
    TextView description;
    public ProgressBar mProgressBar;
    ImageView mImageView;

    public Uri mImageUri;
    public StorageReference mStorageRef;
    public DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        TextView sellTitle=findViewById(R.id.sellTitle);
        Spinner category=findViewById(R.id.category);
        description=findViewById(R.id.description);
        ImageButton uploadImage=findViewById(R.id.uploadImage);
        Button cancelSell=findViewById(R.id.cancelSell);
        Button addButton=findViewById(R.id.addButton);
        ImageView backSell=findViewById(R.id.backSell);

        mProgressBar=findViewById(R.id.progress_bar);
        mImageView=findViewById(R.id.photo);

        mStorageRef= FirebaseStorage.getInstance().getReference();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference();

        desc=description.getText().toString();

        backSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToHome=new Intent(SellPage.this,HomePage.class);
                startActivity(backToHome);
            }
        });

        List<String> categories=new ArrayList<>();
        categories.add(0,"Electronics");
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
                String item = adapterView.getItemAtPosition(i).toString();
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
                title=sellTitle.getText().toString();
                uploadFile();
                Intent add=new Intent(SellPage.this,HomePage.class);
                startActivity(add);
            }
        });

    }
    private void openFileChooser()
    {
        Intent choose=new Intent();
        choose.setType("image/*");
        choose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(choose,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mImageUri=data.getData();
            mImageView.setImageURI(mImageUri);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri!=null){
            imageName=title+"."+getFileExtension(mImageUri);
            StorageReference fileReference=mStorageRef.child(imageName);

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            Toast.makeText(SellPage.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            Upload upload=new Upload(title,taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
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
                            double progress=(100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}