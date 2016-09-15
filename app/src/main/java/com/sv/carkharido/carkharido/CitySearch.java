package com.sv.carkharido.carkharido;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CitySearch extends AppCompatActivity {

    Cursor cr,cr1,car_det;
    GridView gridView;
    String city_name;
    EditText search;
    SQLiteDatabase database;

    RelativeLayout home,newcar,used,sell;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        home=(RelativeLayout)findViewById(R.id.home);
        newcar=(RelativeLayout)findViewById(R.id.newimg);
        used=(RelativeLayout)findViewById(R.id.used);
        sell=(RelativeLayout)findViewById(R.id.sell);

        back =(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CitySearch.this,SearchUsedCars.class);
                startActivity(i);
            }
        });




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CitySearch.this,Home.class);
                startActivity(i);
            }
        });

        sell=(RelativeLayout)findViewById(R.id.sell);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CitySearch.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        newcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CitySearch.this,NewCarSearch.class);
                startActivity(i);
            }
        });



        search=(EditText)findViewById(R.id.searchcar);


        gridView=(GridView)findViewById(R.id.grid1);

        Intent i = getIntent();
        city_name=i.getStringExtra("cityname");

        database=openOrCreateDatabase("info",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF  NOT EXISTS " + "CarInfo" +
                " (img VARCHAR(20), "+"name"+ " VARCHAR(20), "+"price" +" VARCHAR(20), "+"id" +" VARCHAR(20), "+"city"+" VARCHAR(20));");
        //database.execSQL("create TABLE IF not exists CarInfo (img VARCHAR(20),name VARCHAR(20),price VARCHAR2(20),city VARCHAR2(20))");

        cr=database.rawQuery("select * from CarInfo WHERE city='"+city_name+"'",null);
        cr.moveToFirst();
        CustomAdapter customAdapter=new CustomAdapter(CitySearch.this,cr);
        gridView.setAdapter(customAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView car_id = (TextView)view.findViewById(R.id.car_id);
                car_det =database.rawQuery("select * from CarInfo WHERE id='"+car_id.getText().toString().trim()+"'",null);
                car_det.moveToFirst();
                String car_name,car_price,city,carid;

                byte[] carimg;


                //carimg =cr.getBlob(cr.getColumnIndex("img"));
                car_name = car_det.getString(1);
                car_price = car_det.getString(2);
                carid=car_det.getString(3);
                city= car_det.getString(4);

                Intent in = new Intent(CitySearch.this,CarProfile.class);
                in.putExtra("name",car_name);
                //in.putExtra("img",carimg);
                in.putExtra("price",car_price);
                in.putExtra("id",carid);
                in.putExtra("city",city);
                startActivity(in);

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().length()>0){
                    cr1=database.rawQuery("select * from CarInfo WHERE name LIKE '%"+charSequence.toString()+"%' AND city='"+city_name+"'",null);
                    cr1.moveToFirst();
                    CustomAdapter customAdapter=new CustomAdapter(CitySearch.this,cr1);
                    gridView.setAdapter(customAdapter);



                }
                else{
                    cr=database.rawQuery("select * from CarInfo WHERE city='"+city_name+"'",null);
                    cr.moveToFirst();
                    CustomAdapter customAdapter=new CustomAdapter(CitySearch.this,cr);
                    gridView.setAdapter(customAdapter);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



}
