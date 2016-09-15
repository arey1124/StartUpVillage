package com.sv.carkharido.carkharido;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Rocky on 8/28/2016.
 */
public class CustomAdapter extends BaseAdapter{
    LayoutInflater inflate;
    ArrayList<CarDetails> arr=new ArrayList<>();Context context;
    TextView text,text2,id;
    ImageView  img;
    Cursor cr;
    byte[] img1;
    CustomAdapter(Context context, Cursor cr)
    {

        this.context=context;
        this.cr=cr;
        CarDetails details;
        cr.moveToFirst();
        for(int i=0;i<cr.getCount();i++)
        {
            if(i==0)
                cr.moveToFirst();

            details= new CarDetails();
            img1 = cr.getBlob(cr.getColumnIndex("img"));
            Bitmap b1 = BitmapFactory.decodeByteArray(img1, 0, img1.length);
            Bitmap b2= Bitmap.createScaledBitmap(b1,600,400,true);

            details.setCarimg(b2);
            details.setName(cr.getString(1));
            details.setPrice(cr.getString(2));
            details.setId(cr.getString(3));
            arr.add(details);
            cr.moveToNext();

        }
    }
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflate= LayoutInflater.from(context);
        view=inflate.inflate(R.layout.cardetails,null);
        img=(ImageView)view.findViewById(R.id.img);
        text=(TextView)view.findViewById(R.id.text);
        text2=(TextView)view.findViewById(R.id.text2);
        id=(TextView)view.findViewById(R.id.car_id);
        img.setImageBitmap(arr.get(i).getCarimg());
        text.setText(arr.get(i).getName());
        text2.setText(arr.get(i).getPrice());
        id.setText(arr.get(i).getId());

        return view;
    }
}
