package com.example.nn_dashboard;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM tbl_products")
    List<Product> getAllProducts();

    @Insert
    void insertProduct(Product product);
}
