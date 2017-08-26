package cn.onlyloveyd.onearticle.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupWindow
import cn.onlyloveyd.onearticle.R
import cn.onlyloveyd.onearticle.bean.Article
import cn.onlyloveyd.onearticle.bean.Data
import cn.onlyloveyd.onearticle.bean.Date
import cn.onlyloveyd.onearticle.database.ArticleInDb
import cn.onlyloveyd.onearticle.database.BookmarksTable
import cn.onlyloveyd.onearticle.database.database
import cn.onlyloveyd.onearticle.extensions.parseList
import cn.onlyloveyd.onearticle.http.Retrofitance
import cn.onlyloveyd.onearticle.utils.Constants
import cn.onlyloveyd.onearticle.utils.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)
        nav_right_view.setNavigationItemSelectedListener(this)
        iv_left_nav.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        iv_right_nav.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                drawer_layout.closeDrawer(GravityCompat.END)
            } else {
                drawer_layout.openDrawer(GravityCompat.END)
            }
        }
        getTodayArticle()
    }


    override fun onResume() {
        super.onResume()
    }

    fun getTodayArticle() {
        if (!Utils.isNetworkAvailable(this)) {
            toast("网络请求错误")
            return
        }
        Retrofitance.getInstance().getTodayArticle(generateObserver(), 1)
    }

    fun getRandomArticle() {
        if (!Utils.isNetworkAvailable(this)) {
            toast("网络请求错误")
            return
        }
        Retrofitance.getInstance().getRandomArticle(generateObserver(), 1)
    }

    fun getArticleByDate(date: String) {
        if (!Utils.isNetworkAvailable(this)) {
            toast("网络请求错误")
            return
        }
        Retrofitance.getInstance().getArticelByDate(generateObserver(), 1, date)
    }

    fun generateObserver(): Observer<Article> = object : Observer<Article> {
        override fun onComplete() {
        }

        override fun onError(e: Throwable?) {
            toast("网络请求错误")
            e?.printStackTrace()
        }

        override fun onNext(value: Article?) {
            value?.let {
                tv_title.text = it.data.title
                tv_author.text = it.data.author
                tv_count.text = getString(R.string.wc, it.data.wc)
                val result: Spanned
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result = Html.fromHtml(it.data.content, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    result = Html.fromHtml(it.data.content)
                }
                tv_content.text = result
                Constants.date = it.data.date
                article = value
            }
        }

        override fun onSubscribe(d: Disposable?) {
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        drawer_layout.closeDrawer(GravityCompat.END)
        when (item.itemId) {
            R.id.nav_favorite -> {
                insertArticleToDB()
            }
            R.id.nav_pre -> {
                Constants.date?.let { getArticleByDate(it.prev) }
            }
            R.id.nav_next -> {
                Constants.date?.let { getArticleByDate(it.next) }
            }
            R.id.nav_random -> {
                getRandomArticle()
            }
            R.id.nav_bookmark -> {
                val intent = Intent()
                intent.setClass(this, BookmarksActivity::class.java)
                startActivityForResult(intent, 10001)
            }
            R.id.nav_curr -> {
                getTodayArticle()
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            val value: ArticleInDb = data?.extras?.getSerializable("Article") as ArticleInDb
            tv_title.text = value.title
            tv_author.text = value.author
            tv_count.text = getString(R.string.wc, value.wc)
            val result: Spanned
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(value.content, Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.fromHtml(value.content)
            }
            tv_content.text = result
            Constants.date = Date(value.prev, value.curr, value.next)
            article = Article(Data(value.author, value.content, Date(value.prev, value.curr, value.next), "", value.title, value.wc))


        }
    }

    fun showReadSettingsWindows() {
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_popup_window, null, false)
        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(drawer_layout, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
        popupWindow.animationStyle = R.style.PopupAnimation
    }

    fun insertArticleToDB() {
        article?.let {
            doAsync {
                try {
                    database.use {
                        val dailyRequest = "${BookmarksTable.TITLE} = ? "
                        val articlesList = select(BookmarksTable.NAME).whereSimple(dailyRequest, it.data.title).parseList { ArticleInDb(HashMap(it)) }
                        if (articlesList.isNotEmpty()) {
                            uiThread {
                                toast("文章已经收藏，请查阅“我的收藏")
                            }
                        } else {
                            val values = ContentValues()
                            values.put("title", it.data.title)
                            values.put("author", it.data.author)
                            values.put("content", it.data.content)
                            values.put("wc", it.data.wc)
                            values.put("prev", it.data.date.prev)
                            values.put("curr", it.data.date.curr)
                            values.put("next", it.data.date.next)
                            val result = insert("Bookmarks", null, values)
                            if (result == -1L) {
                                uiThread {
                                    toast("收藏失败")
                                }
                            } else {
                                uiThread {
                                    toast("收藏成功")
                                }
                            }
                        }
                    }
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    uiThread {
                        toast("收藏失败")
                    }
                }

            }
        }
    }
}
