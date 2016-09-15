package com.sv.carkharido.carkharido;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.ByteArrayOutputStream;

import dmax.dialog.SpotsDialog;

public class NewCarSearch extends AppCompatActivity {

    private static final String PREFS_NAME = "newfirstStart";
    Cursor cr,cr1,car_det;
    GridView gridView;

    EditText search;
    SQLiteDatabase database;
    byte[] img2=null;
    CustomAdapter customAdapter;

    int images[]={R.drawable.rollsroyce,R.drawable.mercedes,R.drawable.jaguar,R.drawable.bmwi8,R.drawable.lambo};
    String[] car_names={"Rolls Royce","Mercedes","Jaguar","BMW I8","Lamborghini"};
    String[] car_price={"₹ 2.34 crores","₹ 80.92 lacs","₹ 92.34 lacs","₹ 1.56 crores","₹ 6.89 crores"};
    String[] car_instock={"Not Available","Available","Available","Available","Not Available"};

    RelativeLayout home,newcar,used,sell;


    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcarsearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        home=(RelativeLayout)findViewById(R.id.home);
        newcar=(RelativeLayout)findViewById(R.id.newimg);
        used=(RelativeLayout)findViewById(R.id.used);
        sell=(RelativeLayout)findViewById(R.id.sell);


        back =(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarSearch.this,Home.class);
                startActivity(i);
            }
        });




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarSearch.this,Home.class);
                startActivity(i);
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewCarSearch.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewCarSearch.this,SearchUsedCars.class);
                startActivity(i);
            }
        });
        search=(EditText)findViewById(R.id.searchcar);

        gridView=(GridView)findViewById(R.id.grid1);

        database=openOrCreateDatabase("info",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF  NOT EXISTS " + "NewCarInfo" +
                " (img VARCHAR(20), "+"name"+ " VARCHAR(20), "+"price" +" VARCHAR(20), "+"id" +" VARCHAR(20), "+"availability"+" VARCHAR(20));");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean newfirstStart = settings.getBoolean("newfirstStart", true);


        cr=database.rawQuery("select * from NewCarInfo",null);
        cr.moveToFirst();
        customAdapter=new CustomAdapter(NewCarSearch.this,cr);

        if(newfirstStart) {

            new DownloadImage().execute();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("newfirstStart", false);
            editor.commit();
        }


        gridView.setAdapter(customAdapter);

        customAdapter.notifyDataSetChanged();







        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView car_id = (TextView)view.findViewById(R.id.car_id);
                car_det =database.rawQuery("select * from NewCarInfo WHERE id='"+car_id.getText().toString().trim()+"'",null);
                car_det.moveToFirst();
                String car_name,car_price,stock,carid;

                byte[] carimg;


                //carimg =cr.getBlob(cr.getColumnIndex("img"));
                car_name = car_det.getString(1);
                car_price = car_det.getString(2);
                carid=car_det.getString(3);
                stock= car_det.getString(4);

                Intent in = new Intent(NewCarSearch.this,NewCarProfile.class);
                in.putExtra("name",car_name);
                //in.putExtra("img",carimg);
                in.putExtra("price",car_price);
                in.putExtra("id",carid);
                in.putExtra("stock",stock);
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
                    cr1=database.rawQuery("select * from NewCarInfo WHERE name LIKE '%"+charSequence.toString()+"%'",null);
                    cr1.moveToFirst();
                    CustomAdapter customAdapter=new CustomAdapter(NewCarSearch.this,cr1);
                    gridView.setAdapter(customAdapter);



                }
                else{
                    cr=database.rawQuery("select * from NewCarInfo",null);
                    cr.moveToFirst();
                    CustomAdapter customAdapter=new CustomAdapter(NewCarSearch.this,cr);
                    gridView.setAdapter(customAdapter);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





    }

    private class DownloadImage extends AsyncTask<String,ImageView,Bitmap> {

        Bitmap bitmap=null;

        AlertDialog pd;

        @Override
        protected void onPreExecute() {
            pd=new SpotsDialog(NewCarSearch.this,R.style.Custom);
            pd.show();

        }





        @Override
        protected Bitmap doInBackground(String... params) {


            for(int m=0;m<images.length;m++){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),images[m]);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img2 =bos.toByteArray();

                ContentValues cv=new ContentValues();

                cv.put("img",img2);
                cv.put("name",car_names[m]);
                cv.put("price",car_price[m]);
                cv.put("availability",car_instock[m]);
                cv.put("id",Integer.toString(m+21));
                database.insert("NewCarInfo",null,cv);
            }


            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap btm) {
            cr=database.rawQuery("select * from NewCarInfo",null);
            cr.moveToFirst();
            CustomAdapter customAdapter=new CustomAdapter(NewCarSearch.this,cr);
            gridView.setAdapter(customAdapter);
            pd.dismiss();
            super.onPostExecute(btm);
        }




    }

}
