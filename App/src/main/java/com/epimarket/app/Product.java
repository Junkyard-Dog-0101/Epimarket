package com.epimarket.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Serializable
{
    public String id;
    public String designation;
    public String description;
    public String category;
    public String price;
    public String quantities;

    Product(String newId, String newDesignation, String newDescription, String newCategory, String newPrice, String newQuantities)
    {
        id = newId;
        designation = newDesignation;
        description = newDescription;
        category = newCategory;
        price = newPrice;
        quantities = newQuantities;
    }

/*    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>()
    {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Product(Parcel in)
    {
        id = in.readString();
    }*/

}
