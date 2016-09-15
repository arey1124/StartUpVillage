package com.sv.carkharido.carkharido;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SearchUsedCars extends AppCompatActivity {


    CardView cd_mumbai,cd_delhi,cd_jaipur;

    RelativeLayout home,usedcars,newcars,sell;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_used_cars);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);





        cd_mumbai=(CardView)findViewById(R.id.cd1);
        cd_delhi=(CardView)findViewById(R.id.cd2);
        cd_jaipur=(CardView)findViewById(R.id.cd3);


        home=(RelativeLayout)findViewById(R.id.home);
        usedcars=(RelativeLayout)findViewById(R.id.used);

        newcars=(RelativeLayout)findViewById(R.id.newimg);


        back =(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchUsedCars.this,Home.class);
                startActivity(i);
            }
        });



        sell=(RelativeLayout)findViewById(R.id.sell);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchUsedCars.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchUsedCars.this,Home.class);
                startActivity(i);
            }
        });

        usedcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchUsedCars.this,CitySearch.class);
                startActivity(i);
            }
        });

        newcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchUsedCars.this,NewCarSearch.class);
                startActivity(i);
            }
        });



        cd_mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchUsedCars.this,CitySearch.class);
                i.putExtra("cityname","Mumbai");
                startActivity(i);


            }
        });


        cd_delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SearchUsedCars.this,CitySearch.class);
                i.putExtra("cityname","Delhi");
                startActivity(i);

            }
        });

        cd_jaipur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SearchUsedCars.this,CitySearch.class);
                i.putExtra("cityname","Jaipur");
                startActivity(i);

            }
        });


    }

}
