package com.epimarket.app;

public class Product
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
}
