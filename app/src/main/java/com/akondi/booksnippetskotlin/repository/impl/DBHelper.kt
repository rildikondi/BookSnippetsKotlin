package com.akondi.booksnippetskotlin.repository.impl

import android.content.Context
import android.database.sqlite.SQLiteQueryBuilder
import com.akondi.booksnippetskotlin.mvp.model.Book
import com.akondi.booksnippetskotlin.repository.BooksRepository
import com.akondi.booksnippetskotlin.utils.sqlite.SQLiteAssetHelper
import io.reactivex.Single
import java.util.ArrayList

class DBHelper(context: Context) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER), BooksRepository {

    override fun getBooks(): Single<List<Book>> {
        return Single.fromCallable{
            try {
                books
            } catch (e: Throwable) {
                throw RuntimeException("boom!")
            }
        }
    }

    val books: List<Book>
        get() {
            val db = getReadableDatabase()
            val qb = SQLiteQueryBuilder()

            val sqlSelect = arrayOf("ProductName", "ProductId", "Quantity", "Price", "Discount")
            val sqlTable = "OrderDetail"

            qb.tables = sqlTable
            val c = qb.query(db, sqlSelect, null, null, null, null, null)

            val result = ArrayList<Book>()
            if (c.moveToFirst()) {
                do {
                    result.add(
                        Book(
                            c.getString(c.getColumnIndex("ProductId")),
                            c.getString(c.getColumnIndex("ProductName")),
                            c.getString(c.getColumnIndex("Quantity")),
                            c.getString(c.getColumnIndex("Price")),
                            c.getString(c.getColumnIndex("Discount"))
                        )
                    )
                } while (c.moveToNext())
            }
            return result
        }

    override fun insertBook(order: Book) {
        val db = getReadableDatabase()
        val query = String.format(
            "INSERT INTO OrderDetail(ProductId, ProductName, Quantity, Price, Discount) VALUES ('%s', '%s', '%s', '%s', '%s');",
            order.ProductId,
            order.ProductName,
            order.Quantity,
            order.Price,
            order.Discount
        )
        db.execSQL(query)
    }

    fun cleanCart() {
        val db = getReadableDatabase()
        val query = String.format("DELETE FROM OrderDetail")
        db.execSQL(query)
    }

    fun addToFavorites(foodId: String) {
        val db = getReadableDatabase()
        val query = String.format("INSERT INTO Favorites(FoodId) VALUES('%s');", foodId)
        db.execSQL(query)
    }

    fun removeFromFavorites(foodId: String) {
        val db = getReadableDatabase()
        val query = String.format("DELETE FROM Favorites WHERE FoodId='%s';", foodId)
        db.execSQL(query)
    }

    fun isFavorite(foodId: String): Boolean {
        val db = getReadableDatabase()
        val query = String.format("SELECT * FROM Favorites WHERE FoodId = '%s';", foodId)
        val cursor = db.rawQuery(query, null)
        if (cursor.getCount() <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    companion object {

        private val DB_NAME = "BookSnippetsDB.db"
        private val DB_VER = 1
    }
}
