package com.kojh.BlockMarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.Striped;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class ClaimOrder extends AppCompatActivity {

    String doc_uid;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_order);
        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        doc_uid = i.getStringExtra("doc_uid");

        buildUI();
    }

    public void buildUI(){
        db.collection("OfficialReqs").document(doc_uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            TextView restTV = findViewById(R.id.ClaimRestTV);
                            TextView andrewTV = findViewById(R.id.ClaimAidTV);
                            TextView detailsTV = findViewById(R.id.ClaimDetTV);
                            TextView priceTV = findViewById(R.id.ClaimPriceTV);

                            restTV.setText((String)doc.get("Restaurant"));
                            andrewTV.setText((String)doc.get("andrew_id"));
                            Map<String,Object> map = (Map)doc.get("OrderItems");
                            Log.d("Order_Map",map.toString());
                            String str = "";
                            for(Map.Entry<String,Object> entry : map.entrySet()){
                                str += entry.getKey() + " - " + map.get(entry.getKey()) + "\n";
                            }
                            detailsTV.setText(str);
                            priceTV.setText("$"+(String)doc.get("Price"));

                        }


                    }
                });
    }


    public void onBackClick(View v){
        finish();
    }

    public void claimButtonClick(View v){
        boolean alr_claimed = false;
        db.collection("OfficialReqs").document(doc_uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().get("claimed")==null){
                                Toast.makeText(getApplicationContext(),"Invalid Claim",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                });
        FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put("claimed",true);
        map.put("claimed_by",usr.getUid());
        db.collection("OfficialReqs").document(doc_uid).update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"You've claimed this block!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"There was an error claiming",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
