package com.sv.carkharido.carkharido;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewCarProfile extends AppCompatActivity {

    byte[] carimg;
    String name,price,id,availability=null;

    Cursor c;

    SQLiteDatabase database;



    TextView carname,carprice,carid,caravailable;


    RelativeLayout usedcars,newcars,home,sell;

    ImageView carimage,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        database=openOrCreateDatabase("info",MODE_PRIVATE,null);


        back =(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarProfile.this,NewCarSearch.class);
                startActivity(i);
            }
        });





        carname=(TextView)findViewById(R.id.car_name);
        carid=(TextView)findViewById(R.id.car_id);
        carprice=(TextView)findViewById(R.id.car_price);
        caravailable=(TextView)findViewById(R.id.car_availability);

        carimage=(ImageView)findViewById(R.id.car_picture);



        usedcars=(RelativeLayout)findViewById(R.id.used);

        newcars=(RelativeLayout)findViewById(R.id.newimg);

        home =(RelativeLayout)findViewById(R.id.home);


        sell=(RelativeLayout)findViewById(R.id.sell);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewCarProfile.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        usedcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarProfile.this,CitySearch.class);
                startActivity(i);
            }
        });

        newcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarProfile.this,NewCarSearch.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarProfile.this,Home.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();

        name= i.getStringExtra("name");
        //carimg=i.getByteArrayExtra("img");
        // Bitmap b1 = BitmapFactory.decodeByteArray(carimg, 0, carimg.length);
        price=i.getStringExtra("price");
        id=i.getStringExtra("id");
        availability=i.getStringExtra("stock");

        c=database.rawQuery("select * from NewCarInfo WHERE id='"+id+"'",null);
        c.moveToFirst();

        carimg =c.getBlob(c.getColumnIndex("img"));
        Bitmap b1 = BitmapFactory.decodeByteArray(carimg, 0, carimg.length);
        Bitmap b2= Bitmap.createScaledBitmap(b1,600,400,true);

        carname.setText(name);
        carid.setText(id);
        carprice.setText(price);
        caravailable.setText(availability);

        if(availability.equals("Available")){
            caravailable.setTextColor(Color.parseColor("#FF27CA55"));
        }else{
            caravailable.setTextColor(Color.parseColor("#ec6666"));
        }

        carimage.setImageBitmap(b2);

    }

}
