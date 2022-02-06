package com.kojh.BlockMarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent i = getIntent();
        String resName = i.getStringExtra("ResName");
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setText(resName);

    }
    public void onClick(View view){
        AuthUI.getInstance().signOut(this);
    }

}
