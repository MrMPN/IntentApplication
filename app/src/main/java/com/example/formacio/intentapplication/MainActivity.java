package com.example.formacio.intentapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GET = 2;
    ImageView takenPhoto;
    Button takePhotoButton;
    Button selectFileButton;
    EditText inputWeb;
    EditText inputPhone;
    EditText inputDate;
    EditText inputHour;
    EditText inputText;
    Button webButton;
    Button phoneButton;
    Button alarmButton;
    Button textButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        takenPhoto = findViewById(R.id.photoTaken);
        takePhotoButton = findViewById(R.id.takePhoto);
        selectFileButton = findViewById(R.id.selectFile);
        inputWeb = findViewById(R.id.webText);
        inputPhone = findViewById(R.id.phoneText);
        inputDate = findViewById(R.id.dateText);
        inputHour = findViewById(R.id.hourText);
        inputText = findViewById(R.id.randomText);
        webButton = findViewById(R.id.webButton);
        phoneButton = findViewById(R.id.phoneButton);
        alarmButton = findViewById(R.id.alarmButton);
        textButton = findViewById(R.id.randomButton);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebpage();
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber();
            }
        });
    }


    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    private void openWebpage(){
        String webpage = inputWeb.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpage));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void dialPhoneNumber(){
        String phoneNumber = inputPhone.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void createAlarm(String message, int hour, int minutes) {

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && data.hasExtra("data")){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        takenPhoto.setImageBitmap(bitmap);
                    }
                }
                break;

            case REQUEST_IMAGE_GET:
                if (resultCode == RESULT_OK){
                    Uri imageUri = data.getData();
                    try {
                        Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        takenPhoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
