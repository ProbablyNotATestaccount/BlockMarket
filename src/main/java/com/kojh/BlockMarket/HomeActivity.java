package com.kojh.BlockMarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements OrderList.OnFragmentInteractionListener, OpenOrderList.OnFragmentInteractionListener {
    public boolean homeFragVisible;
    public Fragment frag_perm;
    FirebaseFirestore db;
    Map<String,Object> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();
        getMenuFromServer(savedInstanceState);

        ViewGroup vg = findViewById(R.id.FragSV);
        homeFragVisible = true;


    }

    public void getMenuFromServer(Bundle savedInstanceState){

        if(db==null){
            Log.d("firebase","null");
        }
        DocumentReference doc = db.collection("Restaurants").document("Gruhub_Options");
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snap = task.getResult();
                    if(snap.exists()){
                        if(savedInstanceState==null){
                            Bundle args = new Bundle();
                            Map<String, Object> res = snap.getData();
                            menus = res;
                            String[] names = new String[res.size()];
                            int j = 0;
                            for(Map.Entry<String,Object> entry : res.entrySet()){
                                names[j] = entry.getKey();
                                j++;
                            }
                            Fragment frag = new OrderList();
                            args.putStringArray("param1",names);

                            frag.setArguments(args);


                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.HomeFragmentContainer,frag,null)
                                    .commit();
                        }
                    }
                    else {
                        Log.d("OPTION_GET","no options");
                    }
                }
                else {
                    Log.d("OPTION_GET","database err");
                }
            }
        });

//        getSupportFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//                .add(R.id.HomeFragmentContainer,new OrderList(),null)
//                .commit();
//
    }


    public void onRestClick(View v){
        Log.d("BUTTON_CLICK","buttons clicked");
        String res_name = ((Button)v).getText().toString();
        Log.d("Map Class",menus.get(res_name).getClass().getName());
        ArrayList<String> o = (ArrayList<String>)menus.get(res_name);
//        Log.d("SubMenu",menus.get(res_name).toString());
        Intent i = new Intent(this,OrderingActivity.class);
        String[] items = new String[o.size()];

        int j = 0;
        for(String s : o){
            items[j] = s;
            j++;
        }
        i.putExtra("resName",res_name);
      i.putExtra("items",items);
        startActivity(i);
    }


    public void onOrderMenuClick(View v){
        Log.d("clicker_tester",(String)v.getTag(R.id.tag_id));
        Intent i = new Intent(this,ClaimOrder.class);
        i.putExtra("doc_uid",(String)v.getTag(R.id.tag_id));
        startActivity(i);
    }



    public void OnAccClick(View v){
        Intent i = new Intent(this,AccountUpdate.class);
        startActivity(i);

    }


    public void onHomeClick(View v){
        if(!homeFragVisible){
            if(db==null){
                Log.d("firebase","null");
            }
            DocumentReference doc = db.collection("Restaurants").document("Gruhub_Options");
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot snap = task.getResult();
                        if(snap.exists()){

                                Bundle args = new Bundle();
                                Map<String, Object> res = snap.getData();
                                menus = res;
                                String[] names = new String[res.size()];
                                int j = 0;
                                for(Map.Entry<String,Object> entry : res.entrySet()){
                                    names[j] = entry.getKey();
                                    j++;
                                }
                                Fragment frag = new OrderList();
                                args.putStringArray("param1",names);

                                frag.setArguments(args);


                                getSupportFragmentManager().beginTransaction()
                                        .setReorderingAllowed(true)
                                        .replace(R.id.HomeFragmentContainer,frag,null)
                                        .commit();
                        }
                        else {
                            Log.d("OPTION_GET","no options");
                        }
                    }
                    else {
                        Log.d("OPTION_GET","database err");
                    }
                }
            });homeFragVisible=true;
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.HomeFragmentContainer,new OrderList(),null)
//                    .setReorderingAllowed(true)
//                    .commit();
//            homeFragVisible=true;
        }

    }

    public void onOrdersClick(View v){
        Log.d("orders_click","clicked");
        if(homeFragVisible){
            db.collection("OfficialReqs").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int n = task.getResult().size();
                            String[] priceArr = new String[n];
                            String[] resArr = new String[n];
                            String[] uidArr = new String[n];
                            int j = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                uidArr[j] = doc.getId();
                                priceArr[j] = (String) doc.get("Price");
                                resArr[j] = (String) doc.get("Restaurant");
                                j++;
                            }

                            Fragment frag = new OpenOrderList();
                            Bundle args = new Bundle();
                            args.putStringArray("restArr", resArr);
                            args.putStringArray("uidArr", uidArr);
                            args.putStringArray("priceArr", priceArr);
                            frag.setArguments(args);
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.HomeFragmentContainer, frag, null)
                                    .commit();

                        }
                        homeFragVisible = false;
                    }
                });
        }
    }




    //empty, makes it work
    @Override
    public void onFragmentInteraction(Uri uri){

    }






}

