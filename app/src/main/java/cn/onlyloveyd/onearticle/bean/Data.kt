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
package cn.onlyloveyd.onearticle.bean

/**
 * 文 件 名: Data
 * 创 建 人: 易冬
 * 创建日期: 2017/6/20 08:13
 * 描   述：
 */
data class Date(var prev : String, var curr:String, var next:String)
data class Data(var author:String , var content:String , var date:Date, var digest:String, var title:String, var wc:String)
data class Article(var data:Data)


data class DBArticle(val title: String, val author: String, val content:String ,val wc: String, val prev:String, val curr:String, val next:String)