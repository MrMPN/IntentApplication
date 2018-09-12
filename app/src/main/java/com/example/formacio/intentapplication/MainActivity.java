package com.example.formacio.intentapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.formacio.intentapplication.utilities.DateUtils;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GET = 2;
    ImageView takenPhoto;
    Button takePhotoButton;
    Button selectFileButton;
    EditText inputWeb;
    EditText inputPhone;
    Spinner spinnerDate;
    Button inputHour;
    EditText inputText;
    Button webButton;
    Button phoneButton;
    Button alarmButton;
    Button textButton;
    ArrayList<Integer> dayOfWeek;
    int hours;
    int minutes;

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
        spinnerDate = findViewById(R.id.dateText);
        inputHour = findViewById(R.id.hourText);
        inputText = findViewById(R.id.randomText);
        webButton = findViewById(R.id.webButton);
        phoneButton = findViewById(R.id.phoneButton);
        alarmButton = findViewById(R.id.alarmButton);
        textButton = findViewById(R.id.randomButton);

        final TimePickerDialog mTimePicker = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        inputHour.setText(curTime);
                        hours = selectedHour;
                        minutes = selectedMinute;
                    }
                },0, 0, true);

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

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlarm();
            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TextActivity.class);
                intent.putExtra("text", inputText.getText().toString());
                startActivity(intent);
            }
        });

        inputHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show();
            }
        });

        setSpinner();
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selected =  parent.getSelectedItemPosition();
                dayOfWeek = DateUtils.stringToDate(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        takenPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (takenPhoto.getTag() != null) {
//                    Uri uri = (Uri) takenPhoto.getTag();
//                    Intent intent = new Intent(v.getContext(), ImageActivity.class);
//                    intent.putExtra("imageUri", uri);
//                    startActivity(intent);
//                }
//            }
//        });
    }


    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapter);
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
        if (Patterns.WEB_URL.matcher(webpage).matches()) {
            if (!webpage.startsWith("http|https")){
                webpage = "http://" + webpage;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpage));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
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

    public void createAlarm() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_DAYS, dayOfWeek)
                .putExtra(AlarmClock.EXTRA_HOUR, hours)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void setImage(int resultCode, Intent data){
        if (resultCode == RESULT_OK && data.hasExtra("data")){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                takenPhoto.setImageBitmap(bitmap);
            }
        }
    }

    public void getImage(int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                takenPhoto.setImageBitmap(bitmap);
                //takenPhoto.setTag(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                setImage(resultCode, data);
                break;

            case REQUEST_IMAGE_GET:
                getImage(resultCode, data);
                break;
        }
    }
}