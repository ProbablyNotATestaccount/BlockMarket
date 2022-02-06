package com.kojh.BlockMarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {
    String orderString;
    FirebaseFirestore db;
    String resName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        getSupportActionBar().hide();
        Intent i = getIntent();
        String str = i.getStringExtra("str");
        orderString = str;
        TextView tv = findViewById(R.id.OrderSummary);
        tv.setText(str);
        db = FirebaseFirestore.getInstance();
        resName = i.getStringExtra("resName");
    }

    public void onBackClick(View v){
        finish();
    }

    public void confirmedOrder(View v){

        EditText et = findViewById(R.id.PriceSet);
        String et_t = et.getText().toString();
        Double et_d = Double.parseDouble(et_t);
        if(et_d < 0){
            Toast.makeText(getApplicationContext(),"Input a valid price",Toast.LENGTH_SHORT);
            return;

        }
        DecimalFormat df = new DecimalFormat("#.##");
        String inter = df.format(et_d);
        et_d = Double.parseDouble(inter);
        Map<String,Object> toPush = new HashMap<>();
        Map<String,String> map = new HashMap<>();
        String[] menuItems = orderString.split("\n");
        for(String x : menuItems){
            String[] y = x.split(" - ");
            map.put(y[0],y[1]);
        }
        toPush.put("Price",et_d);
        toPush.put("OrderItems",map);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String andrew_id = email.substring(0,email.indexOf("@"));
        String uid = user.getUid();
        toPush.put("UID",uid);
        toPush.put("Restaurant",resName);
        toPush.put("andrew_id",andrew_id);

        db.collection("UserInputs").add(toPush)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Your Order Has Gone Through",Toast.LENGTH_SHORT).show();
                        Log.d("FIREBASE","successful_push");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Your Order Failed, Please Try Again",Toast.LENGTH_SHORT).show();
                        Log.d("FIREBASE","unsuccessful_push");
                        finish();
                    }
                });



    }

}
