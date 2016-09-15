package com.sv.carkharido.carkharido;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class Home extends AppCompatActivity {


    private static final String PREFS_NAME = "firstStart";
    RelativeLayout usedcars,searchnewcars,review,favourite;

    RelativeLayout usedcar,newcar,sell;

    ContentValues cv;
    byte[] img2=null;
    int k=0;
    boolean insert = false;
    SQLiteDatabase database;

    int images[] ={R.drawable.swiftdezire,R.drawable.i10,R.drawable.polo,R.drawable.cruze,R.drawable.i20,R.drawable.safari,R.drawable.beetle,R.drawable.xuv500,R.drawable.amaze,R.drawable.hondacity,R.drawable.elentra,R.drawable.sonata,R.drawable.bmw,R.drawable.audi,R.drawable.crv,R.drawable.verna,R.drawable.jazz,R.drawable.beetle};
    String[] car_names={"Swift Dzire","i10","Polo","Cruze","i20","Tata Safari","Beetle","XUV500","Amaze","Honda City","Elentra","Sonata","BMW","Audi","CRV","Verna","Jazz","Scorpio"};
    String[] car_price={"₹ 6.23 lacs","₹ 4.87 lacs","₹ 7.21 lacs","₹ 13.87 lacs","₹ 5.78 lacs","₹ 8.34 lacs","₹ 20. 66 lacs","₹ 9.82 lacs","₹ 8.63 lacs","₹ 7.89 lacs","₹ 11.23 lacs","₹ 15.55 lacs","₹ 22.33 lacs","₹ 24.56 lacs","₹ 12.67 lacs","₹ 7.80 lacs","₹ 6.67 lacs","₹ 7.45 lacs"};
    String[] car_city={"Jaipur","Jaipur","Jaipur","Jaipur","Jaipur","Jaipur","Mumbai","Mumbai","Mumbai","Mumbai","Mumbai","Mumbai","Delhi","Delhi","Delhi","Delhi","Delhi","Delhi"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        database=openOrCreateDatabase("info",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF  NOT EXISTS " + "CarInfo" +
                " (img VARCHAR(20), "+"name"+ " VARCHAR(20), "+"price" +" VARCHAR(20), "+"id" +" VARCHAR(20), "+"city"+" VARCHAR(20));");
        //database.execSQL("create TABLE IF not exists CarInfo (img VARCHAR(20),name VARCHAR(20),price VARCHAR2(20),city VARCHAR2(20))");








        searchnewcars=(RelativeLayout)findViewById(R.id.menu1);
        usedcars=(RelativeLayout)findViewById(R.id.menu2);
        favourite=(RelativeLayout)findViewById(R.id.menu3);
        review=(RelativeLayout)findViewById(R.id.menu4);

        sell=(RelativeLayout)findViewById(R.id.sell);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"Coming Soon!",Toast.LENGTH_SHORT).show();
            }
        });

        usedcar=(RelativeLayout)findViewById(R.id.used);
        newcar=(RelativeLayout)findViewById(R.id.newimg);


        usedcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,SearchUsedCars.class);
                startActivity(i);
            }
        });

        newcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,NewCarSearch.class);
                startActivity(i);
            }
        });



        usedcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,SearchUsedCars.class);
                startActivity(i);
            }
        });

        searchnewcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,NewCarSearch.class);
                startActivity(i);
            }
        });

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean firstStart = settings.getBoolean("firstStart", true);

        if(firstStart) {

            new DownloadImage().execute();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    private class DownloadImage extends AsyncTask<String,ImageView,Bitmap> {

        Bitmap bitmap=null;

        AlertDialog pd;

        @Override
        protected void onPreExecute() {
            pd=new SpotsDialog(Home.this,R.style.Custom);
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
                cv.put("city",car_city[m]);
                cv.put("id",Integer.toString(m+1));
                database.insert("CarInfo",null,cv);
            }


            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap btm) {

            pd.dismiss();
            super.onPostExecute(btm);
        }




    }
}
