package com.sv.carkharido.carkharido;

import android.graphics.Bitmap;

/**
 * Created by arihant on 05/09/16.
 */
public class CarDetails {

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;

    Bitmap Carimg;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    String Name;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    String Price;

    public Bitmap getCarimg() {
        return Carimg;
    }

    public void setCarimg(Bitmap carimg) {
        Carimg = carimg;
    }


}
