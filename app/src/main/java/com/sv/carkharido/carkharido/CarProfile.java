package com.sv.carkharido.carkharido;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.w3c.dom.Text;

public class CarProfile extends AppCompatActivity {


    byte[] carimg;
    String name,price,id,city=null;

    Cursor c;

    SQLiteDatabase database;



    TextView carname,carprice,carid,carcity;

    ImageView carimage;

    RelativeLayout usedcars,newcars,home,sell;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        database=openOrCreateDatabase("info",MODE_PRIVATE,null);





        usedcars=(RelativeLayout)findViewById(R.id.used);

        newcars=(RelativeLayout)findViewById(R.id.newimg);

        home =(RelativeLayout)findViewById(R.id.home);

        usedcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarProfile.this,CitySearch.class);
                startActivity(i);
            }
        });

        newcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarProfile.this,NewCarSearch.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarProfile.this,Home.class);
                startActivity(i);
            }
        });

        sell=(RelativeLayout)findViewById(R.id.sell);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CarProfile.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });






        carname=(TextView)findViewById(R.id.car_name);
        carid=(TextView)findViewById(R.id.car_id);
        carprice=(TextView)findViewById(R.id.car_price);
        carcity=(TextView)findViewById(R.id.car_city);

        carimage=(ImageView)findViewById(R.id.car_picture);




        Intent i = getIntent();

        name= i.getStringExtra("name");
        //carimg=i.getByteArrayExtra("img");
       // Bitmap b1 = BitmapFactory.decodeByteArray(carimg, 0, carimg.length);
        price=i.getStringExtra("price");
        id=i.getStringExtra("id");
        city=i.getStringExtra("city");

        c=database.rawQuery("select * from CarInfo WHERE id='"+id+"'",null);
        c.moveToFirst();

        carimg =c.getBlob(c.getColumnIndex("img"));
        Bitmap b1 = BitmapFactory.decodeByteArray(carimg, 0, carimg.length);
        Bitmap b2= Bitmap.createScaledBitmap(b1,600,400,true);

        carname.setText(name);
        carid.setText(id);
        carprice.setText(price);
        carcity.setText(city);

        carimage.setImageBitmap(b2);

        back =(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarProfile.this,CitySearch.class);
                i.putExtra("cityname",city);
                startActivity(i);
            }
        });

        //Toast.makeText(CarProfile.this,""+name+" "+price+ " "+id +" "+ city,Toast.LENGTH_SHORT).show();


    }

}
