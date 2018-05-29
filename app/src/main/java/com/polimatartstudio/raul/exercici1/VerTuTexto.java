package com.polimatartstudio.raul.exercici1;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class VerTuTexto extends AppCompatActivity {

    EditText txtUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tu_texto);

        txtUsr = findViewById(R.id.txtUsr);

        Intent intent = getIntent();
        txtUsr.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

    }
}
