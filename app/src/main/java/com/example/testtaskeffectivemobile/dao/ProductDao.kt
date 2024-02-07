package com.example.testtaskeffectivemobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testtaskeffectivemobile.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM FavoriteProductEntity")
    fun getProductFlow(): Flow<List<FavoriteProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: FavoriteProductEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(listProduct:List<FavoriteProductEntity>)
    @Query("SELECT COUNT(*) FROM FavoriteProductEntity")
    suspend fun count():Int

    @Query("DELETE FROM FavoriteProductEntity")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM FavoriteProductEntity WHERE id=:productId")
    suspend fun deleteProductById(productId:String)
}