## 1. 简介
前段时间学习Kotlin，准备找个东西下手，复杂的内容暂时也处理不了，所以选了“每日一文”的接口作为测试，主要是为了熟悉Kotlin的语法和anko中的数据库操作。
</br>

**************************
## 2. 内容
- Kotlin基本语法
- anko-common使用
- anko-sqlite使用
- Navigation+DrawerLayout实现左右抽屉菜单
- Retrofit + Okhttp + Rxjava2 + Gson 处理网络请求
- Material Design
************************
## 3. 思路
启用开发者模式，打开“显示边界布局”，你可以看到应用唯一的一个主界面采用是一体的，没有控件的分离，猜测采用的是混合式开发，所以呢，对我们没有什么帮助。只能按照自己的方式，实现它的功能。毕竟是为了熟悉语言，不深究。

***********************
## 4. 依赖库

```
    ext.kotlin_version = '1.1.2-5'
    ext.anko_version = '0.10.0'
```

```
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    compile "org.jetbrains.anko:anko-common:$anko_version"
    compile "org.jetbrains.anko:anko-sqlite:$anko_version"
    compile "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.android.support:cardview-v7:25.3.1'
    compile 'com.android.support:support-v13:25.3.1'
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.squareup.retrofit2:retrofit:2.2.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'io.reactivex.rxjava2:rxjava:2.x.y'
    compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'
    compile 'com.squareup.okhttp3:okhttp:3.4.1'
    compile 'com.android.support:support-vector-drawable:25.3.1'
    compile 'info.hoang8f:android-segmented:1.0.6'
    compile 'com.kyleduo.switchbutton:library:1.4.6'
```
***********************
## 5. 效果
![这里写图片描述](http://img.blog.csdn.net/20170731134611131?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcG9vcmtpY2s=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)  
                                          
![这里写图片描述](http://img.blog.csdn.net/20170731134621207?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcG9vcmtpY2s=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170731134645458?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcG9vcmtpY2s=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

***********************
## 6. Github
[OneArticle](https://github.com/onlyloveyd/OneArticle)

************
## 7. 备注
每日一文接口采用非正式方式获取，仅供学习，若有侵权请告知，即刻删除。