package com.polimatartstudio.raul.exercici1;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import static android.app.AlarmManager.RTC;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {


    ImageView imgV;
    Button bTakePicture,bSelectFile,bWeb,bPhone,bAlarm,bNActivity;
    TextInputEditText txtWeb, txtPhone, txtDia, txtHora, txtMin, txtNActivity;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GET = 2;
    static final int REQUEST_GO_WEB = 3;
    static final int REQUEST_CALL_PHONE = 4;
    static final int REQUEST_SET_ALARM = 5;
    static final int REQUEST_NEW_ACTIVITY = 6;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int ACCESS_DOCUMENT_CODE = 200;
    private static final int CALL_PHONE_CODE = 300;
    private static final int SET_ALARM_CODE = 400;

    public static final String EXTRA_MESSAGE = "MENSAJE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTakePicture = findViewById(R.id.bTakePicture);
        bTakePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cTakePicture();
            }
        });

        bSelectFile = findViewById(R.id.bSelectFile);
        bSelectFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cSelectFile();
            }
        });

        bWeb = findViewById(R.id.btnWeb);
        bWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goWeb();
            }
        });

        bPhone = findViewById(R.id.btnPhone);
        bPhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                llamarTlf();
            }
        });

        bAlarm = findViewById(R.id.btnAlarm);
        bAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setAlarm();
            }
        });

        bNActivity = findViewById(R.id.btnNActivity);
        bNActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openNActivity();
            }
        });

        imgV = findViewById(R.id.img);
        txtWeb = findViewById(R.id.txtWeb);
        txtPhone = findViewById(R.id.txtPhone);
        txtDia = findViewById(R.id.txtDia);
        txtHora = findViewById(R.id.txtHora);
        txtMin = findViewById(R.id.txtMin);
        txtNActivity = findViewById(R.id.txtNActivity);
    }

    public void openNActivity(){
        Intent intentNActivity = new Intent(this, VerTuTexto.class);
        intentNActivity.putExtra(EXTRA_MESSAGE, txtNActivity.getText().toString());
        startActivityForResult(intentNActivity, REQUEST_NEW_ACTIVITY);
    }

    public void setAlarm(){
        Intent intent = getIntent();
        /*PendingIntent intent = new PendingIntent();
        AlarmManager alarmManager = this.getSystemService(AlarmManager.class);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,3000, intentSetAlarm.);*/

        AlarmManager am=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intent, PendingIntent.FLAG_CANCEL_CURRENT);

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),(9 * 1000), pendingIntent);



        /*if (checkSelfPermission(Manifest.permission.SET_ALARM)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SET_ALARM},
                    SET_ALARM_CODE);
        }else {*/
            //Intent intentSetAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                //.putExtra(EXTRA_MESSAGE, "TU ALARMA")
                //.putExtra(AlarmClock.EXTRA_DAYS, 6)
               // .putExtra(AlarmClock.EXTRA_HOUR,Integer.valueOf(txtHora.getText().toString()))
                //.putExtra(AlarmClock.EXTRA_MINUTES,Integer.valueOf(txtMin.getText().toString()))
                //.putExtra(AlarmClock.EXTRA_VIBRATE, true);
            /*if(intentSetAlarm.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intentSetAlarm,REQUEST_SET_ALARM);
           }*/
        //}
    }

    public void llamarTlf(){
        if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_CODE);
        }else {
            Intent intentCallPhone = new Intent(Intent.ACTION_DIAL);
            intentCallPhone.setData(Uri.parse("tel:" + txtPhone.getText().toString()));
            if (intentCallPhone.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intentCallPhone, REQUEST_CALL_PHONE);
            }
        }
    }

    public void goWeb(){
        Uri webpage = Uri.parse(txtWeb.getText().toString());
        Intent intentGoWeb = new Intent(Intent.ACTION_VIEW, webpage);
        if (intentGoWeb.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentGoWeb, REQUEST_GO_WEB);
        }
    }

    public void cSelectFile(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    ACCESS_DOCUMENT_CODE);
        }else {
            Intent intentSelectFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentSelectFile.setType("image/*");
            if (intentSelectFile.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intentSelectFile, REQUEST_IMAGE_GET);
            }
        }
    }

    public void cTakePicture(){
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
        Intent intentTackePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentTackePicture.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intentTackePicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            try {
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imgV.setImageBitmap(imgBitmap);
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }else if(requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK){
            Uri fullPhotoUri = data.getData();
            imgV.setImageURI(fullPhotoUri);
        }else if(requestCode == REQUEST_SET_ALARM && resultCode == RESULT_OK){
            Toast.makeText(this, "Alarma programada", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Toast.makeText(this, "Grant results: " + grantResults[0], Toast.LENGTH_SHORT).show();

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acceso a la cámara concedido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Acceso a la cámara denegado", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == ACCESS_DOCUMENT_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acceso a documentos permitido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Acceso a documentos denegado", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == CALL_PHONE_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acceso a las llamadas permitido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Acceso a las llamadas denegado", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == SET_ALARM_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acceso a las alarmas permitido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Acceso a las alarmas denegado", Toast.LENGTH_LONG).show();
            }
        }
    }
}
