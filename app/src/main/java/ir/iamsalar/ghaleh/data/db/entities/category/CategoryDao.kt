package ir.iamsalar.ghaleh.data.db.entities.category

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: Category)


    @Query("SELECT * FROM category_table")
    fun getAll(): Flow<List<Category>>

    @Delete
    fun delete(category: Category)
}