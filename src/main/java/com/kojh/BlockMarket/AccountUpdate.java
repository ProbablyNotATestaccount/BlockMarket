package com.kojh.BlockMarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AccountUpdate extends AppCompatActivity {
    FirebaseFirestore db;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);
        db = FirebaseFirestore.getInstance();
        FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
        TextView aid_tv = findViewById(R.id.AID_TV);
        uid = usr.getUid(); getSupportActionBar().hide();

        TextView email_tv = findViewById(R.id.EMAIL_TV);
        aid_tv.setText(usr.getEmail().substring(0,usr.getEmail().indexOf("@")));
        email_tv.setText(usr.getEmail());

    }

    public void onBackClick(View v){
        finish();
    }

    public void onAccUpdateClick(View v){
        EditText et = findViewById(R.id.Venmo_ET);
        String venmo = et.getText().toString();
        EditText pht = findViewById(R.id.Phn_ET);
        String phn = pht.getText().toString();
        Map<String,Object> map = new HashMap<>();

        map.put("Venmo",venmo);
        map.put("PhoneNumber",phn);
        db.collection("Users").document(uid)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Account Update","Updated");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Account Update","Not Updated");
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error While Updating",Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
