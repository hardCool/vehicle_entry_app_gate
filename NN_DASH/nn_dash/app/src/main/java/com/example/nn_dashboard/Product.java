package com.example.nn_dashboard;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int prod_id;
    public int cat_id;
    public String prod_name;
    public String prod_image;
    public String prod_disc;
    public int price;
    public int prod_status;
}