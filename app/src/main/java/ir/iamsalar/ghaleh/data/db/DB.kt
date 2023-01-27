package ir.iamsalar.ghaleh.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.iamsalar.ghaleh.data.db.entities.category.Category
import ir.iamsalar.ghaleh.data.db.entities.category.CategoryDao

@Database(entities = [Category::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    companion object {
        @Volatile
        var INSTANCE: DB? = null
        fun getInstance(context: Context): DB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DB::class.java,
                    "ghaleh.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }

}