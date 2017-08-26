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
package cn.onlyloveyd.onearticle.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import cn.onlyloveyd.onearticle.R
import cn.onlyloveyd.onearticle.adapter.ArticleRvAdapter
import cn.onlyloveyd.onearticle.database.ArticleInDb
import cn.onlyloveyd.onearticle.database.BookmarksTable
import cn.onlyloveyd.onearticle.database.database
import cn.onlyloveyd.onearticle.extensions.parseList
import kotlinx.android.synthetic.main.activity_bookmarks.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * 文 件 名: BookmarksActivity
 * 创 建 人: 易冬
 * 创建日期: 2017/08/26
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：收藏文章界面
 */
class BookmarksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        val ll = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false )
        rv_content.layoutManager = ll
        val mDividerItemDecoration = DividerItemDecoration(ctx,
                     ll.orientation)
        rv_content.addItemDecoration(mDividerItemDecoration)
        iv_back.setOnClickListener { onBackPressed() }
        loadArticle()
    }

    public fun loadArticle() = doAsync {
        database.use {
            val articlesList = select(BookmarksTable.NAME).parseList { ArticleInDb(HashMap(it)) }
            uiThread {
                updateUI(articlesList)
            }
        }
    }


    private fun updateUI(articlesList: List<ArticleInDb>) {
        val adapter = ArticleRvAdapter(articlesList)
        rv_content.adapter = adapter
        rv_content.adapter.notifyDataSetChanged()
    }
}
