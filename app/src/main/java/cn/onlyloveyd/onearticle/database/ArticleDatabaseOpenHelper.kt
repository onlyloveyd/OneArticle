/**
 * Copyright 2017 yidong
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.onlyloveyd.onearticle.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import cn.onlyloveyd.onearticle.app.App
import org.jetbrains.anko.db.*

/**
 * 文 件 名: MyDatabaseOpenHelper
 * 创 建 人: 易冬
 * 创建日期: 2017/6/20 11:25
 * 描   述：
 */
class ArticleDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, ArticleDatabaseOpenHelper.DB_NAME, null, ArticleDatabaseOpenHelper.DB_VERSION) {
    companion object {

        val  DB_NAME: String = "article.db"
        val  DB_VERSION: Int = 1
        var instance: ArticleDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): ArticleDatabaseOpenHelper {
            if (instance == null) {
                instance = ArticleDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Bookmarks", true,
                BookmarksTable.ID to INTEGER + PRIMARY_KEY + UNIQUE,
                BookmarksTable.TITLE to TEXT,
                BookmarksTable.AUTHOR to TEXT,
                BookmarksTable.CONTENT to TEXT,
                BookmarksTable.WC to TEXT,
                BookmarksTable.PREV to TEXT,
                BookmarksTable.CURR to TEXT,
                BookmarksTable.NEXT to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(BookmarksTable.NAME, true)
        onCreate(db)
    }
}

// Access property for Context
val Context.database: ArticleDatabaseOpenHelper
    get() = ArticleDatabaseOpenHelper.getInstance(getApplicationContext())
