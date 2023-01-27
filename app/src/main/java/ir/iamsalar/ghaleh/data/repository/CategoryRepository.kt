package ir.iamsalar.ghaleh.data.repository

import ir.iamsalar.ghaleh.data.db.DB
import ir.iamsalar.ghaleh.data.db.entities.category.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val db: DB) {


    suspend fun add(category: Category) {
        db.categoryDao().add(category)
    }
    suspend fun remove(category: Category) {
        db.categoryDao().delete(category)
    }


    val getAll: Flow<List<Category>> = db.categoryDao().getAll()



}