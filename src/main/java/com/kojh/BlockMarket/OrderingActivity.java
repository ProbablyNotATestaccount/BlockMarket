package com.kojh.BlockMarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderingActivity extends AppCompatActivity {
    ArrayList<View> MenuSelectors;
    String resName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        getSupportActionBar().hide();
        MenuSelectors = new ArrayList<>();
        Intent i = getIntent();
        String[] arr = i.getStringArrayExtra("items");
        resName = i.getStringExtra("resName");
        buildUI(arr);
    }

    public void buildUI(String[] arr){
        final ViewGroup vg = (ViewGroup)findViewById(R.id.MenuItemsLL);

        LayoutInflater inflater = getLayoutInflater();
        for(String x : arr){
            View v = inflater.inflate(R.layout.order_sub_menu,null);
            TextView tv = v.findViewById(R.id.quantTV);
            TextView itemTV = v.findViewById(R.id.MenuItemTV);
            itemTV.setText(x);
            Button incDn = (Button)v.findViewById(R.id.IncDownBtn);
            incDn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qt = Integer.parseInt(tv.getText().toString());
                    qt = Math.max(0,qt-1);
                    tv.setText(Integer.toString(qt));
                }
            });

            Button incUp = (Button)v.findViewById(R.id.IncUpBtn);
            incUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qt = Integer.parseInt(tv.getText().toString());
                    qt++;
                    tv.setText(Integer.toString(qt));
                }
            });

            MenuSelectors.add(v);
            vg.addView(v,0);

        }
    }

    public void finishOrdering(View v){
        Intent i = new Intent(this,ConfirmOrderActivity.class);
        String str = "";
        for(View vx : MenuSelectors){
            TextView item_n = vx.findViewById(R.id.MenuItemTV);
            TextView item_qt = vx.findViewById(R.id.quantTV);
            if(!item_qt.getText().toString().equals("0")) {
                str += item_n.getText().toString() + " - ";
                str += item_qt.getText().toString() + "\n";
            }
        }


        i.putExtra("str",str);
        i.putExtra("resName",resName);
        startActivity(i);
        finish();
    }


    public void onBackClick(View v){
        finish();
    }

}
